package ir.saa.android.mt.repositories.IEC;

import android.os.Handler;
import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

import ir.saa.android.mt.application.Converters;
import ir.saa.android.mt.repositories.meterreader.ASCII;
import ir.saa.android.mt.repositories.meterreader.MeterUtility;
import ir.saa.android.mt.repositories.modbus.ITransferLayer;
import ir.saa.android.mt.repositories.modbus.ITransferLayerCallback;

public class IEC {

    private static IEC _instance;

    public static IEC getInstance(){
        synchronized (IEC.class) {
            if(_instance==null)
                _instance = new IEC();
            return _instance;
        }
    }

    private IEC(){ }

    ITransferLayer transferLayer;
    String totalReciveData="";
    String tempReciveData="";

    Boolean isIECRunningKey = true;
    Boolean waitForResponse = false;
    Boolean noResponse = false;

    IIECCallback IECCallback;

    //---------Timeout Checker-------
    private Timer timer;

    TimerTask timerTask = new TimerTask() {

        @Override
        public void run() {
            if(isIECRunningKey){//
                if(waitForResponse && noResponse){
                    isIECRunningKey=false;
                    IECCallback.onConnectionError("Response Time Out!");
                    Log.d("response","IEC Time Out.");
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

    public void setTransferLayer(ITransferLayer tl){
        transferLayer = tl;
        transferLayer.setTransferLayerCallback(new ITransferLayerCallback() {
            @Override
            public void onConnected() {
                IECCallback.onConnected();
            }

            @Override
            public void onDisConnected() {
                IECCallback.onDisConnected();
                isIECRunningKey=false;
            }

            @Override
            public void onConnectionError(String errMsg) {
                IECCallback.onConnectionError(errMsg);
            }

            @Override
            public void onReportStatus(String statusMsg) {
                IECCallback.onReportStatus(statusMsg);
            }

            @Override
            public void onRecieveData(byte[] responseArray) {
                try {
                    noResponse = false;
                    tempReciveData = new String(responseArray, "US-ASCII");
                    totalReciveData += tempReciveData;
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void setIECCallback(IIECCallback callback){
        IECCallback = callback;
    }

    public synchronized String SendCommandToDevice(String writeData, Boolean ConevertHex, MeterUtility.connectionStatus cnnStatus) throws Exception {
        String result="";
        if(transferLayer.isConnected()) {
            totalReciveData = "";
            byte[] dataArray;
            if (ConevertHex) {
                dataArray = Converters.hex2ByteArray2By2(writeData);
            } else {
                dataArray = writeData.getBytes();
            }
            Log.d("write IEC",meterLog(writeData));
            transferLayer.writeByteArrayToDevice(dataArray);

            noResponse =true;
            waitForResponse=true;
            isIECRunningKey=true;

            timerStart(5000);

            while (isIECRunningKey) {
                if (checkResponseStr(totalReciveData,cnnStatus)) {
                    Log.d("response IEC",meterLog(totalReciveData));
                    result = totalReciveData;
                    break;
                }
            }
        }
        else {
            IECCallback.onConnectionError("Transfer Layer is Disconnected");
            throw new Exception("Transfer Layer is Disconnected");
        }
        return  result;
    }

    private boolean checkResponseStr(String responseStr, MeterUtility.connectionStatus cnnStatus){
        boolean res=false;

        switch (cnnStatus) {
            case getMeterString:
                if (responseStr.contains("/") && responseStr.contains(String.valueOf(ASCII.CR)) &&  responseStr.contains(String.valueOf(ASCII.LF))) {
                    res=true;
                }
                break;

            case getReadoutString:
                if (responseStr.contains(String.valueOf(ASCII.STX)) && responseStr.contains("!") && responseStr.contains(String.valueOf(ASCII.ETX))){
                    res=true;
                }
                break;

            case getP0String:
                if (responseStr.startsWith(String.valueOf(ASCII.SOH)) && responseStr.contains("(") && responseStr.contains(")") && responseStr.lastIndexOf(ASCII.ETX) == responseStr.length() - 2) {
                    res=true;
                }
                break;

            case getObisString:
                if (responseStr.contains(String.valueOf(ASCII.STX)) && (responseStr.contains(String.valueOf(ASCII.ETX)) || responseStr.contains(String.valueOf(ASCII.EOT))) && responseStr.contains("(") && responseStr.contains(")")) {
                    responseStr = responseStr.substring(1,responseStr.length()-2);
                    res = true;
                }
                break;
        }

        waitForResponse = !res;
        //if(res) timerStop();
        return  res;
    }

    private String meterLog( String rec){
        rec=rec.replace( ""+ASCII.STX,"<STX>");
        rec=rec.replace( ""+ASCII.ETX,"<ETX>");
        rec=rec.replace( ""+ASCII.SOH,"<SOH>");
        rec=rec.replace( ""+ASCII.EOT,"<EOT>");
        rec=rec.replace( ""+ASCII.ACK,"<ACK>");
        rec=rec.replace( ""+ASCII.NAK,"<NAK>");
        rec=rec.replace( ""+ASCII.CR,"<CR>");
        rec=rec.replace( ""+ASCII.LF,"<LF>");
        return rec;
    }

}
