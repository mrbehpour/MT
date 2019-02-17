package ir.saa.android.mt.repositories.meterreader;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import ir.saa.android.mt.repositories.IEC.IEC_Bussiness;
import ir.saa.android.mt.repositories.IEC.IEC_Constants;
import ir.saa.android.mt.repositories.IEC.IIECCallback;
import ir.saa.android.mt.repositories.modbus.IModbusCallback;
import ir.saa.android.mt.repositories.modbus.ITransferLayer;
import ir.saa.android.mt.repositories.IEC.IEC;
import ir.saa.android.mt.repositories.metertester.IMTCallback;


public class PROB {
    private static PROB prob_instance = null;
    IEC iec;
    IMTCallback probCallback;

    public PROB() {
        iec = IEC.getInstance();

        iec.setIECCallback(new IIECCallback() {
            @Override
            public void onConnected() {
                probCallback.onConnected();
            }

            @Override
            public void onDisConnected() {
                probCallback.onDisConnected();
            }

            @Override
            public void onConnectionError(String errMsg) {
                probCallback.onConnectionError(errMsg);
            }

            @Override
            public void onReportStatus(String statusMsg) {
                probCallback.onReportStatus(statusMsg);
            }
        });
    }

    // static method to create instance of Singleton class
    public static PROB getInstance() {
        if (prob_instance == null)
            prob_instance = new PROB();

        return prob_instance;
    }

    public void setIECCallback(IMTCallback callback){
        probCallback = callback;
    }

    public void setTransferLayer(ITransferLayer tl){
        iec.setTransferLayer(tl);
    }

    public String StartConnectionWithMeter() throws Exception {

        String result="";
        try {
            result = iec.SendCommandToDevice(IEC_Constants.startCommStr,false,MeterUtility.connectionStatus.getMeterString);
            //if(result.isEmpty()) probCallback.onConnectionError("Empty Meter String");
        } catch (Exception ex) {
            probCallback.onConnectionError(ex.getMessage());
            throw new Exception(ex);
        }
        //TODO-parsing
        return result;
    }

    public String ReadOutMeter(MeterUtility.MeterInfo meterInfo) throws Exception {

        String result="";
        try {
            char newBaudRate = IEC_Bussiness.getNewBaudRateFromMeterString(meterInfo.MeterString);
            Thread.sleep(100);
            result = iec.SendCommandToDevice(IEC_Bussiness.makeAcknowledgementString('0',newBaudRate,MeterUtility.readingMode.readout),false,MeterUtility.connectionStatus.getReadoutString);
        } catch (Exception ex) {
            probCallback.onConnectionError(ex.getMessage());
            throw new Exception(ex);
        }

        return result;
    }

    public String GetP0FromMeter(MeterUtility.MeterInfo meterInfo) throws Exception {

        String result="";
        try {
            char newBaudRate = IEC_Bussiness.getNewBaudRateFromMeterString(meterInfo.MeterString);
            Thread.sleep(100);
            result = iec.SendCommandToDevice(IEC_Bussiness.makeAcknowledgementString('0',newBaudRate,MeterUtility.readingMode.programming),false,MeterUtility.connectionStatus.getP0String);
        } catch (Exception ex) {
            probCallback.onConnectionError(ex.getMessage());
            throw new Exception(ex);
        }
        //TODO-parsing
        return result;
    }

    public String SendPasswordToMeter(MeterUtility.MeterInfo meterInfo) throws Exception {

        String result="";
        try {
        } catch (Exception ex) {
            probCallback.onConnectionError(ex.getMessage());
            throw new Exception(ex);
        }
        //TODO-parsing
        return result;
    }

    public MeterUtility.ReadData ProgrammingReadMeter(MeterUtility.MeterInfo meterInfo) throws Exception {

        List<MeterUtility.ObisItem> lstObis = MeterUtility.getListObis(meterInfo.MaterSummaryName);
        MeterUtility.ReadData readData = new MeterUtility.ReadData();

        String result="";
        try {
            for (MeterUtility.ObisItem obis: lstObis) {
                if(!obis.mainObis.isEmpty()) {
                    String ReadObisStr = MeterUtility.CreateReadObisStr(obis , meterInfo);
                    result = iec.SendCommandToDevice(ReadObisStr,false,MeterUtility.connectionStatus.getObisString);
                    MeterUtility.setReadDataValue(readData,obis.tariffName,SplitData.splitDataInOpenPrntsStar(result));
                }
            }
        } catch (Exception ex) {
            probCallback.onConnectionError(ex.getMessage());
            throw new Exception(ex);
        }
        //TODO-parsing
        return readData;
    }

    public String DisconnectWithMeter() throws Exception {

        String result="";
        try {
            result = iec.SendCommandToDevice(IEC_Constants.ABORT_CONNECTION,false,MeterUtility.connectionStatus.getObisString);
        } catch (Exception ex) {
            probCallback.onConnectionError(ex.getMessage());
            throw new Exception(ex);
        }
        //TODO-parsing
        return result;
    }

//    private void SendPasswordToMeter() throws IOException, InterruptedException {
//        String SendPassStr="";
//
//        lastSendedPass=Bussiness.getLastPassword(mPass,meterPassIndex);
//
//        if(lastSendedPass=="NoMorePass" || lastSendedPass=="NoPassSet"){
//            writeLog("Write_Data Send Password",lastSendedPass);
//            disconnectMeterCommunication(G.get_string(R.string.read_wrong_password));
//            return;
//        }
//        else{
//            meterPassIndex++;
//            appendReadStatusText("ارسال کلمه عبور جدید");
//        }
//
//        writeLog("Write_Data Send Password",lastSendedPass);
//
//        switch (metertype){
//            case IRANTECH:
//            case INTECH:
//                if(setDateMode) {
//                    SendPassStr = Bussiness.createMeterCommandStr(CommConst.P1_Command,String.format("(%s)",lastSendedPass));
//                }
//                else {
//                    SendPassStr = "NoPassNeed";
//                    meterPassIndex=0;
//                }
//                break;
//
//            case RENAN:
//                if(setDateMode) {
//                    Thread.sleep(200);
//                    SendPassStr=Bussiness.createMeterCommandStr(CommConst.P1_Command,String.format("(%s)",Bussiness.CalculateRENENPassword(Bussiness.up2len(lastSendedPass,12,"0"),P0_Str)));
//                    //SendPassStr=Bussiness.createMeterCommandStr(CommConst.P1_Command,String.format("(%s)",Bussiness.CalculateRENENPassword("331100611113",P0_Str)));
//                }
//                else {
//                    SendPassStr = "NoPassNeed";
//                }
//                break;
//
//            case ELESTER_A1350i:
//            case ELESTER_A120:
//                SendPassStr=Bussiness.createMeterCommandStr(CommConst.P2_Command,String.format("(%s)",Bussiness.CalculateElesterPassword(lastSendedPass,P0_Str)));
//                break;
//
//            case AMP:
//            case AMP_E:
//            case AMP_J:
//                SendPassStr=Bussiness.createMeterCommandStr(CommConst.P2_Command,String.format("(%s)",Bussiness.CalculateAmpyPassword(lastSendedPass,P0_Str)));
//                break;
//
//            case JAM:
//            case TOOSFIOUS:
//            case ABB:
//                if(setDateMode) {
//                    SendPassStr = Bussiness.createMeterCommandStr(CommConst.P1_Command,String.format("(%s)",lastSendedPass));
//                }
//                else {
//                    SendPassStr = "NoPassNeed";
//                    meterPassIndex=0;
//                }
//                break;
//
//            case PTA:
//                SendPassStr = Bussiness.createMeterCommandStr(CommConst.P1_Command,String.format("(%s)",lastSendedPass));
//                break;
//
//            case ACE:
//                SendPassStr=Bussiness.createMeterCommandStr(CommConst.P2_Command,String.format("(%s)",Bussiness.CalculateACE2000Password(lastSendedPass,P0_Str)));
//                break;
//
//            case RAHROVAN303:
//            case JAM110:
//            case RAHROVAN:
//                SendPassStr = Bussiness.createMeterCommandStr(CommConst.P1_Command,String.format("(%s)",lastSendedPass));
//                break;
//        }
//
//        if(SendPassStr.equals("NoPassNeed")){
//            if(getSomeObisProg){
//                readstatus = readStatus.readSomeObisProgramming;
//                ReadSomeObisProgrammingAction();
//            }
//            else {
//                readstatus = readStatus.readProgramming;
//                ReadProgrammingAction();
//            }
//        }
//        else {
//            writeStringToDevice(SendPassStr,false);
//            setWaitForResponse(true);
//            readstatus = readStatus.getPassResult;
//        }
//    }
}

