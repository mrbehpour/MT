package ir.saa.android.mt.repositories.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.UUID;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.saa.android.mt.repositories.modbus.ITransferLayer;
import ir.saa.android.mt.repositories.modbus.ITransferLayerCallback;

public class Bluetooth implements ITransferLayer {


    private static Bluetooth bt_instance = null;
    private ITransferLayerCallback bluetoothListener;

    Thread bluetoothThread;
    Thread workerThread;

    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;

    String bluetoothDeviceName="";

    OutputStream mmOutputStream;
    InputStream mmInputStream;

    boolean stopWorker;
    int bytesAvailable;

    //Standard SerialPortService ID
    UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");;

    // private constructor restricted to this class itself
    private Bluetooth() {
    }

    // static method to create instance of Singleton class
    public static Bluetooth getInstance() {
        if (bt_instance == null)
            bt_instance = new Bluetooth();

        return bt_instance;
    }



    @Override
    public void writeByteArrayToDevice(byte[] writeData) {
        if(!isConnected()) {
            bluetoothListener.onConnectionError(bt_err_connectionFailed);
            return;
        }

        if (writeData == null || writeData.length == 0) return;

        if (mmOutputStream != null) {
            try {
                mmOutputStream.write(writeData, 0, writeData.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void writeStringToDevice(String writeData, Boolean ConevertHex) {
        if(!isConnected()) {
            bluetoothListener.onConnectionError(bt_err_connectionFailed);
            return;
        }

        byte[] dataArray;
        dataArray = writeData.getBytes();
//        if(ConevertHex){
//            dataArray = HexConvert.hex2ByteArray2By2(writeData);
//        }
//        else {
//            dataArray = writeData.getBytes();
//        }
        //writeLog("Write_Data Send",writeData);
        //lastSendedCommand = writeData;
        writeByteArrayToDevice(dataArray);

        Log.i("response_rec",showByteArray(dataArray));
    }

    private String showByteArray(byte[] dataArr){
        String dataStr="";
        for(byte b:dataArr) {
            dataStr += String.format("%s ",String.valueOf(b));
        }

        return  dataStr;
    }


    @Override
    public String getDeviceResponse(){
        return "";
    }

    @Override
    public void init(String btDeviceName) {

        bluetoothDeviceName = btDeviceName;

        Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                if(!isConnected()) {
                    findBT();
                    connect();
                    return true;
                }else {
                    return false;
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new SingleObserver<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        bluetoothListener.onReportStatus(bt_status_connectig);
                    }

                    @Override
                    public void onSuccess(Boolean isFirstTime) {
                        if(isConnected()) {
                            if(isFirstTime) {
                                initInputOutput();
                                listenForRecieve();
                            }
                            bluetoothListener.onConnected();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        bluetoothListener.onConnectionError(bt_err_connectionFailed+" : "+e.getMessage());
                    }
                });
    }

    @Override
    public void setTransferLayerCallback(ITransferLayerCallback iTransferLayerCallback) {
        bluetoothListener = iTransferLayerCallback;
    }

    @Override
    public boolean isConnected() {
        boolean res=false;

        if(mmSocket!=null){
            res =  mmSocket.isConnected();
        }
        return res;
    }


    void initInputOutput(){
        try {
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void findBT() throws btException{
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if(!mBluetoothAdapter.isEnabled()) {

            throw new btException(bt_err_bluetoothIsOff, new Throwable(String.valueOf(btException.class)));
        }

        if(mBluetoothAdapter == null) {
            throw new btException(bt_err_deviceIsNotInit, new Throwable(String.valueOf(btException.class)));
        }

        boolean deviceFound = false;
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if(pairedDevices.size() > 0)
        {
            for(BluetoothDevice device : pairedDevices)
            {
                if(device.getName().equals(bluetoothDeviceName.trim())) {
                    mmDevice = device;
                    deviceFound=true;
                    break;
                }
            }
            if(!deviceFound) throw new btException(bt_err_deviceNotPaired, new Throwable(String.valueOf(btException.class)));
        }
        else {
            throw new btException(bt_err_deviceNotPaired, new Throwable(String.valueOf(btException.class)));
        }
    }

    public List<String> getPairedDevice(){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        List<String> pairedDevice = new ArrayList<>();

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if(pairedDevices.size() > 0)
        {
            for(BluetoothDevice device : pairedDevices)
            {
                pairedDevice.add(device.getName().trim());
            }
        }

        return  pairedDevice;
    }

    boolean connect(){
        try {
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            return true;
            //new connectAsync().execute("");
        }
        catch (Exception e) {
            return false;
        }
    }

    public void disconnnect() {
        stopWorker = true;
        if(mmOutputStream!=null) {
            try {
                mmOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(mmInputStream!=null) {
            try {
                mmInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(mmSocket!=null) {
            try {
                mmSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        bluetoothListener.onDisConnected();

    }

    void listenForRecieve() {
        stopWorker = false;

        workerThread = new Thread(new Runnable()
        {
            public void run() {
                while(!stopWorker) {
                    try {
                        bytesAvailable = mmInputStream.available();
                        if (bytesAvailable > 0) {
                            byte[] packetBytes = new byte[bytesAvailable];
                            mmInputStream.read(packetBytes);
                            bluetoothListener.onRecieveData(packetBytes);
                        }
                    } catch (UnsupportedEncodingException e) {
                        stopWorker = true;
                        e.printStackTrace();
                    } catch (IOException e) {
                        stopWorker = true;
                        e.printStackTrace();
                    }
                }
            }
        });
        workerThread.start();

//        //Mode_Bus
//        workerThread = new Thread(new Runnable()
//        {
//            public void run() {
//                try {
//                    bytesAvailable = mmInputStream.available();
//                    if (bytesAvailable > 0) {
//
//                        byte[] packetBytes = new byte[bytesAvailable];
//                        bytesAvailable=0;
//
//                        mmInputStream.read(packetBytes);
//                        responseListener.onGetResponse(packetBytes);
//
//                    }
//                } catch (UnsupportedEncodingException e) {
//                    stopWorker = true;
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    stopWorker = true;
//                    e.printStackTrace();
//                }
//            }
//
//        });
//        workerThread.start();
    }

    static final String bt_err_deviceNotPaired="Selected Device Is Not In Paired List!";
    static final String bt_err_bluetoothIsOff="Bluetooth Is Off Or Permission Is Denided!";
    static final String bt_err_deviceIsNotInit="Selected Device Is Not Init Succesfuly!";
    static final String bt_err_noResponse="No Response From Device!";
    static final String bt_err_connectionFailed="Can Not Connect To Device!";
    static final String bt_err_connectionAlreadyOK="Bluetooth Already Connected";
    static final String bt_status_connectig="Try To Connect...";

    public class btException extends Exception {
        Throwable cause;
        String message;

        public btException() {
            super();
        }

        public btException(String message, Throwable cause)
        {
            super(message, cause);

            this.cause = cause;
            this.message = message;
        }
    }

    Timer timer;

//    void timerStart(long prd) {
//        if(timer != null) {
//            return;
//        }
//        timer = new Timer();
//        timer.scheduleAtFixedRate(timerTask, prd, prd);
//    }
//
//    TimerTask timerTask = new TimerTask() {
//        @Override
//        public void run() {
//            if(anythingRecieved=false){
//                responseListener.onConnectionError(bt_err_noResponse);
//            }
//            anythingRecieved=false;
//        }
//    };

//    private class connectAsync extends AsyncTask<String, Void, String>{
//        @Override
//        protected String doInBackground(String... params) {
//            try {
//                mmSocket.connect();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            try {
//                if(isConnected()) {
//                    mmOutputStream = mmSocket.getOutputStream();
//                    mmInputStream = mmSocket.getInputStream();
//                    listenForRecieve();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//        }
//
//        @Override
//        protected void onPreExecute() {
//        }
//
//        @Override
//        protected void onProgressUpdate(Void... values) {
//        }
//    }

}
