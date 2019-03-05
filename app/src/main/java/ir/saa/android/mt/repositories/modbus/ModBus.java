package ir.saa.android.mt.repositories.modbus;

import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import ir.saa.android.mt.application.Converters;

public class ModBus {

    private static ModBus _instance;

    public static ModBus getInstance(){
        synchronized (ModBus.class) {
            if(_instance==null)
                _instance = new ModBus();
            return _instance;
        }
    }
    private ModBus(){
        timerStart(2500);
    }

    private final byte READ_HOLDING_REGISTER_FC =0x03;
    private final byte READ_INPUT_REGISTER_FC =0x04;
    private final byte WRITE_SINGLE_REGISTER_FC=0x06;
    private final byte WRITE_MULTIPLE_REGISTER_FC =0x10;

    private byte Last_Register_FC =0x00;
    private String Last_Send_Command="";

    ITransferLayer transferLayer;
    String totalReciveData="";

    Boolean isModbusRunningKey = true;
    Boolean waitForResponse = false;

    IModbusCallback modbusCallback;

    private int[] table = {
            0x0000, 0xC0C1, 0xC181, 0x0140, 0xC301, 0x03C0, 0x0280, 0xC241,
            0xC601, 0x06C0, 0x0780, 0xC741, 0x0500, 0xC5C1, 0xC481, 0x0440,
            0xCC01, 0x0CC0, 0x0D80, 0xCD41, 0x0F00, 0xCFC1, 0xCE81, 0x0E40,
            0x0A00, 0xCAC1, 0xCB81, 0x0B40, 0xC901, 0x09C0, 0x0880, 0xC841,
            0xD801, 0x18C0, 0x1980, 0xD941, 0x1B00, 0xDBC1, 0xDA81, 0x1A40,
            0x1E00, 0xDEC1, 0xDF81, 0x1F40, 0xDD01, 0x1DC0, 0x1C80, 0xDC41,
            0x1400, 0xD4C1, 0xD581, 0x1540, 0xD701, 0x17C0, 0x1680, 0xD641,
            0xD201, 0x12C0, 0x1380, 0xD341, 0x1100, 0xD1C1, 0xD081, 0x1040,
            0xF001, 0x30C0, 0x3180, 0xF141, 0x3300, 0xF3C1, 0xF281, 0x3240,
            0x3600, 0xF6C1, 0xF781, 0x3740, 0xF501, 0x35C0, 0x3480, 0xF441,
            0x3C00, 0xFCC1, 0xFD81, 0x3D40, 0xFF01, 0x3FC0, 0x3E80, 0xFE41,
            0xFA01, 0x3AC0, 0x3B80, 0xFB41, 0x3900, 0xF9C1, 0xF881, 0x3840,
            0x2800, 0xE8C1, 0xE981, 0x2940, 0xEB01, 0x2BC0, 0x2A80, 0xEA41,
            0xEE01, 0x2EC0, 0x2F80, 0xEF41, 0x2D00, 0xEDC1, 0xEC81, 0x2C40,
            0xE401, 0x24C0, 0x2580, 0xE541, 0x2700, 0xE7C1, 0xE681, 0x2640,
            0x2200, 0xE2C1, 0xE381, 0x2340, 0xE101, 0x21C0, 0x2080, 0xE041,
            0xA001, 0x60C0, 0x6180, 0xA141, 0x6300, 0xA3C1, 0xA281, 0x6240,
            0x6600, 0xA6C1, 0xA781, 0x6740, 0xA501, 0x65C0, 0x6480, 0xA441,
            0x6C00, 0xACC1, 0xAD81, 0x6D40, 0xAF01, 0x6FC0, 0x6E80, 0xAE41,
            0xAA01, 0x6AC0, 0x6B80, 0xAB41, 0x6900, 0xA9C1, 0xA881, 0x6840,
            0x7800, 0xB8C1, 0xB981, 0x7940, 0xBB01, 0x7BC0, 0x7A80, 0xBA41,
            0xBE01, 0x7EC0, 0x7F80, 0xBF41, 0x7D00, 0xBDC1, 0xBC81, 0x7C40,
            0xB401, 0x74C0, 0x7580, 0xB541, 0x7700, 0xB7C1, 0xB681, 0x7640,
            0x7200, 0xB2C1, 0xB381, 0x7340, 0xB101, 0x71C0, 0x7080, 0xB041,
            0x5000, 0x90C1, 0x9181, 0x5140, 0x9301, 0x53C0, 0x5280, 0x9241,
            0x9601, 0x56C0, 0x5780, 0x9741, 0x5500, 0x95C1, 0x9481, 0x5440,
            0x9C01, 0x5CC0, 0x5D80, 0x9D41, 0x5F00, 0x9FC1, 0x9E81, 0x5E40,
            0x5A00, 0x9AC1, 0x9B81, 0x5B40, 0x9901, 0x59C0, 0x5880, 0x9841,
            0x8801, 0x48C0, 0x4980, 0x8941, 0x4B00, 0x8BC1, 0x8A81, 0x4A40,
            0x4E00, 0x8EC1, 0x8F81, 0x4F40, 0x8D01, 0x4DC0, 0x4C80, 0x8C41,
            0x4400, 0x84C1, 0x8581, 0x4540, 0x8701, 0x47C0, 0x4680, 0x8641,
            0x8201, 0x42C0, 0x4380, 0x8341, 0x4100, 0x81C1, 0x8081, 0x4040,
    };

    public void setTransferLayer(ITransferLayer tl){
        transferLayer = tl;
        transferLayer.setTransferLayerCallback(new ITransferLayerCallback() {
            @Override
            public void onConnected() {
                modbusCallback.onConnected();
            }

            @Override
            public void onDisConnected() {
                modbusCallback.onDisConnected();
                isModbusRunningKey=false;
            }

            @Override
            public void onConnectionError(String errMsg) {
                modbusCallback.onConnectionError(errMsg);
            }

            @Override
            public void onReportStatus(String statusMsg) {
                modbusCallback.onReportStatus(statusMsg);
            }

            @Override
            public void onRecieveData(byte[] responseArray) {
                totalReciveData += Converters.ArrayByte2Hex(responseArray);
            }
        });
    }

    public void setModbusCallback(IModbusCallback callback){
        modbusCallback = callback;
    }

    //-------check response-------
    private boolean checkResponseStr(String responseStr){
        boolean res=false;
        if(responseStr.isEmpty()) return res;

        switch (Last_Register_FC) {
            case READ_INPUT_REGISTER_FC:
                if (responseStr.length() > 12) {
                    if (responseStr.substring(2, 4).equals("04") && Integer.parseInt(responseStr.substring(4, 6), 16) * 2 + 10 == responseStr.length()) {
//                        if( Converters.hexString2ByteArray(responseStr.substring(0,responseStr.length()-4)).equals(ModRTU_CRC_Int()))
                        res = true;
                    }
                }
                break;

            case READ_HOLDING_REGISTER_FC:
                if (responseStr.length() > 12) {
                    if (responseStr.substring(2, 4).equals("03") && Integer.parseInt(responseStr.substring(4, 6), 16) * 2 + 10 == responseStr.length()) {
//                        if( Converters.hexString2ByteArray(responseStr.substring(0,responseStr.length()-4)).equals(ModRTU_CRC_Int()))
                        res = true;
                    }
                }
                break;

            case WRITE_SINGLE_REGISTER_FC:
                if (Last_Send_Command.equals(responseStr)) {
                    res = true;
                }
                break;

            case WRITE_MULTIPLE_REGISTER_FC:
                if(responseStr.length()>=12 && Last_Send_Command.substring(0,12).equals(responseStr.substring(0,12))) {
                    res = true;
                }
                break;
        }

        waitForResponse = !res;
        return  res;
    }
    //-----------------------

    //-----------Calc CRC------------
    private byte[] ModRTU_CRC_Int(byte[] buf) {
        int crc = 0xFFFF;
        for (byte b : buf) {
            crc = (crc >>> 8) ^ table[(crc ^ b) & 0xff];
        }

        return Converters.ConvertInt2ByteArray(crc,true);
    }
    //--------------------------------

    //--------Main Methods-----------
    public synchronized String readHoldingRegister(byte slaveID, int startAddress, int QR) throws Exception {

        String result ="";
        if(transferLayer.isConnected()) {
            byte[] ADR_Byte = Converters.ConvertInt2ByteArray(startAddress, false);
            byte[] QR_Byte = Converters.ConvertInt2ByteArray(QR, false);

            byte[] tempArray = new byte[]{slaveID, READ_HOLDING_REGISTER_FC, ADR_Byte[0], ADR_Byte[1], QR_Byte[0], QR_Byte[1]};
            byte[] CRC_Byte = ModRTU_CRC_Int(tempArray);
//            byte[] dataArray = new byte[]{slaveID, READ_INPUT_REGISTER_FC, ADR_Byte[0], ADR_Byte[1], QR_Byte[0], QR_Byte[1], CRC_Byte[0], CRC_Byte[1]};
            byte[] dataArray = Converters.ConcatenateTwoArray(tempArray,CRC_Byte);

            totalReciveData="";
            Last_Register_FC = READ_HOLDING_REGISTER_FC;
            Last_Send_Command = Converters.ArrayByte2Hex(dataArray);

            transferLayer.writeByteArrayToDevice(dataArray);
            Log.d("response read holding",Last_Send_Command);

            waitForResponse=true;
            isModbusRunningKey=true;

            while (isModbusRunningKey) {
                if (checkResponseStr(totalReciveData)) {
                    Log.d("response ans_holding",totalReciveData);
                    result = totalReciveData;
                    break;
                }
            }
        }
        else {
            Log.d("response","Disconn.");
            modbusCallback.onConnectionError("Transfer Layer is Disconnected");
            throw new Exception("Transfer Layer is Disconnected");
        }
        return result;
    }

    public synchronized String readInputRegister(byte slaveID, int startAddress, int QR) throws Exception {
        String result ="";
        if(transferLayer.isConnected()) {
            byte[] ADR_Byte = Converters.ConvertInt2ByteArray(startAddress, false);
            byte[] QR_Byte = Converters.ConvertInt2ByteArray(QR, false);

            byte[] tempArray = new byte[]{slaveID, READ_INPUT_REGISTER_FC, ADR_Byte[0], ADR_Byte[1], QR_Byte[0], QR_Byte[1]};
            byte[] CRC_Byte = ModRTU_CRC_Int(tempArray);
//            byte[] dataArray = new byte[]{slaveID, READ_INPUT_REGISTER_FC, ADR_Byte[0], ADR_Byte[1], QR_Byte[0], QR_Byte[1], CRC_Byte[0], CRC_Byte[1]};
            byte[] dataArray = Converters.ConcatenateTwoArray(tempArray,CRC_Byte);

            totalReciveData="";
            Last_Register_FC = READ_INPUT_REGISTER_FC;
            Last_Send_Command = Converters.ArrayByte2Hex(dataArray);

            transferLayer.writeByteArrayToDevice(dataArray);
            Log.d("response read input",Last_Send_Command);

            waitForResponse=true;
            isModbusRunningKey=true;

            while (isModbusRunningKey) {
                if (checkResponseStr(totalReciveData)) {
                    Log.d("response ans_input",totalReciveData);
                    result = totalReciveData;
                    break;
                }
            }
        }
        else {
            modbusCallback.onConnectionError("Transfer Layer is Disconnected");
            throw new Exception("Transfer Layer is Disconnected");
        }
        return result;
    }

    public synchronized String writeSingleRegister(byte slaveID, int writeAddress, int value) throws Exception {
        String result ="";
        if(transferLayer.isConnected()) {
            byte[] ADR_Byte = Converters.ConvertInt2ByteArray(writeAddress, false);
            byte[] VAL_Byte = Converters.ConvertInt2ByteArray(value, false);

            byte[] tempArray = new byte[]{slaveID, WRITE_SINGLE_REGISTER_FC, ADR_Byte[0], ADR_Byte[1], VAL_Byte[0], VAL_Byte[1]};
            byte[] CRC_Byte = ModRTU_CRC_Int(tempArray);
            byte[] dataArray = new byte[]{slaveID, WRITE_SINGLE_REGISTER_FC, ADR_Byte[0], ADR_Byte[1], VAL_Byte[0], VAL_Byte[1], CRC_Byte[0], CRC_Byte[1]};

            totalReciveData="";
            Last_Register_FC = WRITE_SINGLE_REGISTER_FC;
            Last_Send_Command = Converters.ArrayByte2Hex(dataArray);
            transferLayer.writeByteArrayToDevice(dataArray);
            Log.d("response write multi",Last_Send_Command);

            waitForResponse=true;
            isModbusRunningKey=true;

            while (isModbusRunningKey) {
                if (checkResponseStr(totalReciveData)) {
                    result = totalReciveData;
                    break;
                }
            }
        }
        else {
            modbusCallback.onConnectionError("Transfer Layer is Disconnected");
            throw new Exception("Transfer Layer is Disconnected");
        }
        return result;
    }

    public synchronized String writeMultipleRegister(byte slaveID, int writeAddress, byte[] VAL_Byte) throws Exception {
        String result ="";
        if(transferLayer.isConnected()) {
            byte[] ADR_Byte = Converters.ConvertInt2ByteArray(writeAddress, false);
            byte[] QR_Byte = Converters.ConvertInt2ByteArray(VAL_Byte.length/2, false);
            byte[] LEN_Byte = Converters.ConvertInt2ByteArray(VAL_Byte.length, false);

            byte[] tempArray = new byte[]{slaveID, WRITE_MULTIPLE_REGISTER_FC, ADR_Byte[0], ADR_Byte[1], QR_Byte[0], QR_Byte[1], LEN_Byte[1]};
            byte[] dataArray = Converters.ConcatenateTwoArray(tempArray,VAL_Byte);
            byte[] CRC_Byte = ModRTU_CRC_Int(dataArray);
            dataArray = Converters.ConcatenateTwoArray(dataArray,CRC_Byte);
//            byte[] dataArray = new byte[]{slaveID, WRITE_MULTIPLE_REGISTER_FC, ADR_Byte[0], ADR_Byte[1], LEN_Byte[0], LEN_Byte[1], VAL_Byte[0], VAL_Byte[1], CRC_Byte[0], CRC_Byte[1]};

            totalReciveData="";
            Last_Register_FC = WRITE_MULTIPLE_REGISTER_FC;
            Last_Send_Command = Converters.ArrayByte2Hex(dataArray);
            transferLayer.writeByteArrayToDevice(dataArray);
            Log.d("response write multi",Last_Send_Command);

            waitForResponse=true;
            isModbusRunningKey=true;

            while (isModbusRunningKey) {
                if (checkResponseStr(totalReciveData)) {
                    result = totalReciveData;
                    break;
                }
            }
        }
        else {
            modbusCallback.onConnectionError("Transfer Layer is Disconnected");
            throw new Exception("Transfer Layer is Disconnected");
        }
        return result;
    }
    //--------------------------------

    //--------Methods-----------
    //--------------------------

    //---------Timeout Checker-------
    private Timer timer;

    TimerTask timerTask = new TimerTask() {

        @Override
        public void run() {
            if(isModbusRunningKey){//
                if(waitForResponse){
                    waitForResponse = false;
                    isModbusRunningKey=false;
                    Log.d("response","Time Out.");
                }
            }
        }
    };

    private void timerStart(long prd) {
        if(timer != null) {
            return;
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, prd, prd);
    }

    private void timerStop() {

        if(timer!=null){
            timer.cancel();
            timer.purge();
            timer = null;
        }
    }
    //-------------------------------

    public void AbortOperation(){

    }

    public void Dispose(){
        //Log.d("response","Disposing.");
        waitForResponse = false;
        isModbusRunningKey = false;
    }



}
