package ir.saa.android.mt.repositories.IEC;

import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

import ir.saa.android.mt.application.Converters;
import ir.saa.android.mt.repositories.meterreader.ASCII;
import ir.saa.android.mt.repositories.meterreader.MeterUtility;
import ir.saa.android.mt.repositories.meterreader.StatusReport;
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

    private IEC(){
        timerStart(3000);
    }

    ITransferLayer transferLayer;
    String totalReciveData="";
    String tempReciveData="";

    Boolean isIECRunningKey = true;
    Boolean waitForResponse = false;
    int noResponseNum = 0;

    IIECCallback IECCallback;

    //---------Timeout Checker-------
    private Timer timer;

    TimerTask timerTask = new TimerTask() {

        @Override
        public void run() {
            if(isIECRunningKey){//
                if(waitForResponse){
                    noResponseNum++;
                    IECCallback.onResponseTimeout(noResponseNum);
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
            timerTask.cancel();
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
                    noResponseNum = 0;
                    tempReciveData = new String(responseArray, "US-ASCII");
                    //IECCallback.onReportStatus(tempReciveData);
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
            //Log.d("write IEC",meterLog(writeData));
            noResponseNum = 0;
            transferLayer.writeByteArrayToDevice(dataArray);

            waitForResponse=true;
            isIECRunningKey=true;

            if(writeData.equals(IEC_Constants.ABORT_CONNECTION)) {
                waitForResponse=false;
                isIECRunningKey=false;
            }

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

    public void stopOperation(){
        isIECRunningKey=false;
        waitForResponse=false;
        noResponseNum=0;
    }

    private boolean checkResponseStr(String responseStr, MeterUtility.connectionStatus cnnStatus){
        boolean res=false;
        String progItem="";

        switch (cnnStatus) {
            case getMeterString:
                if (responseStr.contains("/") && responseStr.contains(String.valueOf(ASCII.CR)) &&  responseStr.contains(String.valueOf(ASCII.LF))) {
                    progItem = StatusReport.createProgressItem(StatusReport.progressBarMode.reportStatus,"Get Meter String...",0);
                    res=true;
                }
                break;

            case getReadoutString:
                if (responseStr.contains(String.valueOf(ASCII.STX)) && responseStr.contains("!") && responseStr.contains(String.valueOf(ASCII.ETX))){
                    progItem = StatusReport.createProgressItem(StatusReport.progressBarMode.reportStatus,"Reading Meter...",0);
                    res=true;
                }
                break;

            case getP0String:
                if (responseStr.startsWith(String.valueOf(ASCII.SOH)) && responseStr.contains("(") && responseStr.contains(")") && responseStr.lastIndexOf(ASCII.ETX) == responseStr.length() - 2) {
                    progItem = StatusReport.createProgressItem(StatusReport.progressBarMode.reportStatus,"Sending Password To Meter...",0);
                    res=true;
                }
                break;

            case getPassResult:
                if (responseStr.contains(String.valueOf(ASCII.ACK))) {
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
        if(res) {
            if(!progItem.isEmpty())IECCallback.onReportStatus(progItem);
            isIECRunningKey=false;
        }
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
