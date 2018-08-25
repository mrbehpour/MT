package ir.saa.android.mt.repositories.metertester;

import android.util.Log;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import ir.saa.android.mt.application.Converters;
import ir.saa.android.mt.repositories.modbus.IModbusCallback;
import ir.saa.android.mt.repositories.modbus.ITransferLayer;
import ir.saa.android.mt.repositories.modbus.ModBus;
import ir.saa.android.mt.uicontrollers.pojos.TestContor.TestContorParams;

public class MT {

    private static MT mt_instance = null;
    IMTResponse mtListener;
    String totalReciveData="";
    List<RegisterInfo> registerInfoList = new ArrayList<>();
    private final static byte SLAVE_ID=1;
//    String ComStr= new String(new char[]{'/','?','!',(char)0x0D,(char)0x0A});
//    String AckStr= new String(new char[]{(char)0x06,'0','5','0',(char)0x0D,(char)0x0A});

    ModBus modBus;
    IMTCallback imtCallback;

    public enum Phase{
        PhaseA,
        PhaseB,
        PhaseC,
    }

    public enum TestCommands {
        DisableTest,
        StartTest,
        FinishTest,
    }

    public MT() {
        fillRegisterInfoList();
        modBus = ModBus.getInstance();
        modBus.setModbusCallback(new IModbusCallback() {
            @Override
            public void onConnected() {
                imtCallback.onConnected();
            }

            @Override
            public void onDisConnected() {
                imtCallback.onDisConnected();
            }

            @Override
            public void onConnectionError(String errMsg) {
                imtCallback.onConnectionError(errMsg);
            }

            @Override
            public void onReportStatus(String statusMsg) {
                imtCallback.onReportStatus(statusMsg);
            }
        });
    }


    // static method to create instance of Singleton class
    public static MT getInstance() {
        if (mt_instance == null)
            mt_instance = new MT();

        return mt_instance;
    }

    public void setMTCallback(IMTCallback callback){
        imtCallback = callback;
    }

    private void fillRegisterInfoList(){
        registerInfoList.add(new RegisterInfo(RegisterInfo.regNames.ElecParams_ALL,0,81));
        registerInfoList.add(new RegisterInfo(RegisterInfo.regNames.ElecParams_PA,0,27));
        registerInfoList.add(new RegisterInfo(RegisterInfo.regNames.ElecParams_PB,27,27));
        registerInfoList.add(new RegisterInfo(RegisterInfo.regNames.ElecParams_PC,54,27));
        registerInfoList.add(new RegisterInfo(RegisterInfo.regNames.Enegies_PA,4,6));
        registerInfoList.add(new RegisterInfo(RegisterInfo.regNames.Enegies_PB,31,6));
        registerInfoList.add(new RegisterInfo(RegisterInfo.regNames.Enegies_PC,58,6));
        registerInfoList.add(new RegisterInfo(RegisterInfo.regNames.Test_Init_Params,220,6));
        registerInfoList.add(new RegisterInfo(RegisterInfo.regNames.Test_Command,228,1));
        registerInfoList.add(new RegisterInfo(RegisterInfo.regNames.Test_Result,222,28));
        registerInfoList.add(new RegisterInfo(RegisterInfo.regNames.Paulse_Counter,867,1));
    }

    private RegisterInfo findRegisterInfo(RegisterInfo.regNames regName){
        for(RegisterInfo rn :  registerInfoList){
            if(rn.registerName.equals(regName)){
                return rn;
            }
        }

        return  null;
    }

    public void setTransferLayer(ITransferLayer tl){
        modBus.setTransferLayer(tl);
    }

    public ElectericalParams ReadElectericalParams(Phase p) throws Exception{
        totalReciveData="";
        String result = "";
        RegisterInfo ri=new RegisterInfo();

        try {
            switch (p) {
                case PhaseA:
                    ri=findRegisterInfo(RegisterInfo.regNames.ElecParams_PA);
                    break;
                case PhaseB:
                    ri=findRegisterInfo(RegisterInfo.regNames.ElecParams_PB);
                    break;
                case PhaseC:
                    ri=findRegisterInfo(RegisterInfo.regNames.ElecParams_PC);
                    break;
            }
            result = modBus.readInputRegister(SLAVE_ID, ri.registerAddress, ri.registerLenght);
        }catch (Exception ex){
            imtCallback.onConnectionError(ex.getMessage());
            throw new Exception(ex);
        }
        return splitElectericalParams(result);
    }

    public List<ElectericalParams> ReadAllElectericalParams() throws Exception {
        List<ElectericalParams> electericalParams=new ArrayList<>();
        totalReciveData="";
        RegisterInfo ri = findRegisterInfo(RegisterInfo.regNames.ElecParams_ALL);
        try {
            String result = modBus.readInputRegister(SLAVE_ID, ri.registerAddress, ri.registerLenght);

            electericalParams.add(splitElectericalParams(result.substring(0,54)));
            electericalParams.add(splitElectericalParams(result.substring(54,108)));
            electericalParams.add(splitElectericalParams(result.substring(108,162)));

        } catch (Exception ex) {
            imtCallback.onConnectionError(ex.getMessage());
            throw new Exception(ex);
        }
        //TODO-parsing
        return electericalParams;
    }

    public EnergiesState ReadEnergiesState() throws Exception {

        EnergiesState energiesStates;
        totalReciveData="";
        RegisterInfo ri;
        try {
            String[] result=new String[3];
            ri = findRegisterInfo(RegisterInfo.regNames.Enegies_PA);
            result[0] = splitRawDate(modBus.readInputRegister(SLAVE_ID, ri.registerAddress, ri.registerLenght));

            ri = findRegisterInfo(RegisterInfo.regNames.Enegies_PB);
            result[1] = splitRawDate(modBus.readInputRegister(SLAVE_ID, ri.registerAddress, ri.registerLenght));

            ri = findRegisterInfo(RegisterInfo.regNames.Enegies_PC);
            result[2] = splitRawDate(modBus.readInputRegister(SLAVE_ID, ri.registerAddress, ri.registerLenght));
            Log.d("response",result[0]+" , "+result[1]+" , "+result[2]);
            energiesStates=new EnergiesState(checkEnergyState(result[0].substring(0,8)),checkEnergyState(result[0].substring(8,16)),
                                             checkEnergyState(result[1].substring(0,8)),checkEnergyState(result[1].substring(8,16)),
                                             checkEnergyState(result[2].substring(0,8)),checkEnergyState(result[2].substring(8,16)));

        } catch (Exception ex) {
            imtCallback.onConnectionError(ex.getMessage());
            throw new Exception(ex);
        }
        //TODO-parsing
        return energiesStates;
    }

    public int ReadPaulseCounter() throws Exception {

        String result="";
        totalReciveData="";
        RegisterInfo ri;
        try {
            ri = findRegisterInfo(RegisterInfo.regNames.Paulse_Counter);
            result = splitRawDate(modBus.readInputRegister(SLAVE_ID, ri.registerAddress, ri.registerLenght));
            //result = modBus.readInputRegister(SLAVE_ID, ri.registerAddress, ri.registerLenght);
            Log.d("response paulse",result);
        } catch (Exception ex) {
            imtCallback.onConnectionError(ex.getMessage());
            throw new Exception(ex);
        }
        //TODO-parsing
//        return Integer.parseInt(result);
        return Integer.parseInt(result,16);
    }

    public TestResult ReadTestResult(int resultRound) throws Exception {

        String result="";
        totalReciveData="";
        RegisterInfo ri;
        try {
            ri = findRegisterInfo(RegisterInfo.regNames.Test_Result);
            resultRound=resultRound%20;
            result = modBus.readInputRegister(SLAVE_ID, ri.registerAddress+((resultRound-1)*28), ri.registerLenght);
            Log.d("response test res",result);
        } catch (Exception ex) {
            imtCallback.onConnectionError(ex.getMessage());
            throw new Exception(ex);
        }
        //TODO-parsing
        return splitTestResult(result);
    }

    public String SendTestCommand(TestCommands st){
        totalReciveData="";
        String result = "";
        RegisterInfo ri=new RegisterInfo();
        ri=findRegisterInfo(RegisterInfo.regNames.Test_Command);
        try {
            switch (st) {
                case DisableTest:
                    result = modBus.writeSingleRegister(SLAVE_ID, ri.registerAddress,  0);
                    break;
                case StartTest:
                    result = modBus.writeSingleRegister(SLAVE_ID, ri.registerAddress, 1);
                    break;
                case FinishTest:
                    result = modBus.writeSingleRegister(SLAVE_ID, ri.registerAddress, 2);
                    break;
            }
        }catch (Exception ex){
            imtCallback.onConnectionError(ex.getMessage());
        }
        return result;
    }

    public String SendTestContorParams(TestContorParams tcp) {
        String result = "";
        RegisterInfo ri = findRegisterInfo(RegisterInfo.regNames.Test_Init_Params);
        byte[] tmp;
        byte[] data = Converters.ConvertInt4ByteArray(tcp.ContorConst);

        tmp = Converters.ConvertInt2ByteArray(tcp.SensorRatio,false);
        data = Converters.ConcatenateTwoArray(data,tmp);

        tmp = Converters.ConvertInt2ByteArray(tcp.CTCoeff,false);
        data = Converters.ConcatenateTwoArray(data,tmp);

        tmp = Converters.ConvertInt2ByteArray(1,false);
        data = Converters.ConcatenateTwoArray(data,tmp);

        tmp = Converters.ConvertInt2ByteArray(tcp.Active?1:2,false);
        data = Converters.ConcatenateTwoArray(data,tmp);

        tmp = Converters.ConvertInt2ByteArray(tcp.SinglePhase?1:2,false);
        data = Converters.ConcatenateTwoArray(data,tmp);

        tmp = Converters.ConvertInt2ByteArray(1,false);
        data = Converters.ConcatenateTwoArray(data,tmp);

        try {
            result = modBus.writeMultipleRegister(SLAVE_ID, ri.registerAddress,data);
        }catch (Exception ex){
            imtCallback.onConnectionError(ex.getMessage());
        }
        return result;
    }

    private String splitRawDate(String responseStr){
        return responseStr.substring(6,responseStr.length()-4);
    }

    //parse data
    private ElectericalParams splitElectericalParams(String responseStr){
        ElectericalParams ep=new ElectericalParams();
        if(responseStr.trim().length()>0) {
            ep.AVRMS = calVoltage(responseStr.substring(6, 14));
            ep.AIRMS = calAmp(responseStr.substring(14, 22));
            ep.AWATTHR = calPower(responseStr.substring(22, 30));
            ep.AVARHR = calPower(responseStr.substring(30, 38));
            ep.AVAHR = calPower(responseStr.substring(38, 46));
            ep.ANGLE0 = calAngle(responseStr.substring(46, 50));
            ep.AWATT = calPower(responseStr.substring(74, 82));
            ep.AVAR = calPower(responseStr.substring(82, 90));
            ep.AVA = calPower(responseStr.substring(90, 98));
        }
        return ep;
    }

    private TestResult splitTestResult(String responseStr){
        TestResult tr=new TestResult();
        if(responseStr.trim().length()>0) {
            tr.MeterEnergy_Period1_A = calPower(responseStr.substring(6, 14));
            tr.MeterEnergy_Period1_B = calPower(responseStr.substring(14, 22));
            tr.MeterEnergy_Period1_C = calPower(responseStr.substring(22, 30));
            tr.Time_Period1 = responseStr.substring(30, 38);
            tr.AIRMS_Period1 = responseStr.substring(38, 46);
            tr.BIRMS_Period1 = responseStr.substring(46, 58);
            tr.CIRMS_Period1 = responseStr.substring(58, 66);
            tr.NIRMS_Period1 = responseStr.substring(66, 74);
            tr.AVRMS_Period1 = responseStr.substring(74, 82);
            tr.BVRMS_Period1 = responseStr.substring(82, 90);
            tr.CVRMS_Period1 = responseStr.substring(90, 98);
            tr.ANGLE0_Period1 = responseStr.substring(98, 102);
            tr.ANGLE1_Period1 = responseStr.substring(102, 106);
            tr.ANGLE2_Period1 = responseStr.substring(106, 110);
            tr.Period_Period1_A = responseStr.substring(110, 114);
            tr.Period_Period1_B = responseStr.substring(114, 118);
            tr.Period_Period1_C = responseStr.substring(118, 122);
        }
        return tr;
    }

    private boolean checkEnergyState(String responseStr){
        return responseStr.substring(0,2).equals("00")?true:false;
    }

    private String calVoltage(String responseStr){
        String result = "";
        try {
            double d = Integer.parseInt(responseStr, 16) / (double) 11536;
            result = String.format("%.2f", d);
        }catch (Exception ex){
            imtCallback.onConnectionError(ex.getMessage());
        }
        return result;

    }

    private String calAmp(String responseStr){
        double d=Integer.parseInt(responseStr,16)/(double)3840;
        return String.format("%.2f", d);
    }

    private String calAngle(String responseStr){
        int ang=Integer.parseInt(responseStr,16);
        double pf=Math.cos(ang*360*50/(double)256);
        return String.valueOf(pf);
    }

    private String calPower(String responseStr){

        double d=twosComplement(responseStr)/(double)5288;
        return String.format("%.2f", d);
    }

    private int twosComplement(String responseStr){
        int v;
        v = Integer.parseInt(responseStr.substring(2,responseStr.length()),16);

        if(responseStr.substring(0,2).equals("FF")){
            v = 16777215-v+1;
        }

        return v;
    }

}
