package ir.saa.android.mt.repositories.metertester;

import android.support.annotation.NonNull;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import ir.saa.android.mt.repositories.modbus.IModbusCallback;
import ir.saa.android.mt.repositories.modbus.ITransferLayer;
import ir.saa.android.mt.repositories.modbus.ITransferLayerCallback;
import ir.saa.android.mt.repositories.modbus.ModBus;

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
        registerInfoList.add(new RegisterInfo(RegisterInfo.regNames.Test_Command,224,1));
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

//    public void ReadCounter(){
//        totalReciveData="";
//        transferLayer.writeStringToDevice(ComStr, false);
//    }

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
        RegisterInfo ri = findRegisterInfo(RegisterInfo.regNames.ElecParams_ALL);
        try {
            String result = modBus.readInputRegister(SLAVE_ID, ri.registerAddress, ri.registerLenght);
            energiesStates=new EnergiesState(splitEnrgyActive(result.substring(0,54)),splitEnrgyReActive(result.substring(0,54)),
                                             splitEnrgyActive(result.substring(54,108)),splitEnrgyReActive(result.substring(54,108)),
                                             splitEnrgyActive(result.substring(108,162)),splitEnrgyReActive(result.substring(108,162)));

        } catch (Exception ex) {
            imtCallback.onConnectionError(ex.getMessage());
            throw new Exception(ex);
        }
        //TODO-parsing
        return energiesStates;
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

//    public void SendACK(){
//        totalReciveData="";
//        transferLayer.writeStringToDevice(AckStr,false);
//    }

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

    private double splitEnrgyActive(String responseStr){
        return Double.parseDouble (responseStr.substring(22, 30));
    }

    private double splitEnrgyReActive(String responseStr){
        return Double.parseDouble (responseStr.substring(30, 38));
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

    private String showByteArray(byte[] dataArr){
        String dataStr="";
        for(byte b:dataArr) {
            dataStr += String.format("%s ",String.valueOf(b));
        }

        return  dataStr;
    }

    private int byteArrayToInt(byte[] byteArr) {
        return ByteBuffer.wrap(byteArr).getInt();
    }

    private String ArrayByte2Hex(byte[] selArray){
        final StringBuilder builder=new StringBuilder();
        for(byte b : selArray){
            builder.append(String.format("%02x" , b));
        }

        return builder.toString().toUpperCase();

    }

}
