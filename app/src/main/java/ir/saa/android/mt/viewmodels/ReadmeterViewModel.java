package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.SharePrefEnum;
import ir.saa.android.mt.repositories.bluetooth.Bluetooth;
import ir.saa.android.mt.repositories.meterreader.MeterUtility;
import ir.saa.android.mt.repositories.meterreader.PROB;
import ir.saa.android.mt.repositories.meterreader.SplitData;
import ir.saa.android.mt.repositories.meterreader.StatusReport;
import ir.saa.android.mt.repositories.metertester.IMTCallback;

public class ReadmeterViewModel extends AndroidViewModel {
    Bluetooth bluetooth;
    PROB meterreader;
    MeterUtility.connectionStatus lastCnnStatus;
    boolean connectToMeter =false;

    public MutableLiveData<String> getStatusMutableLiveData;
    public MutableLiveData<MeterUtility.MeterInfo> readMeterMutableLiveData;
    public MutableLiveData<MeterUtility.ReadData> readMeterResultMutableLiveData;
    public MutableLiveData<Boolean> connectionStateMutableLiveData;
    public MutableLiveData<Integer> recieveDataMutableLiveData;

    public ReadmeterViewModel(@NonNull Application application) {
        super(application);
        meterreader = PROB.getInstance();
        bluetooth = Bluetooth.getInstance();
        meterreader.setTransferLayer(bluetooth);
        meterreader.setIECCallback(new IMTCallback() {
            @Override
            public void onConnected() {
                connectionStateMutableLiveData.postValue(true);
                Log.d("response","onConnected");
            }

            @Override
            public void onDisConnected() {
                Log.d("response","onDisConnected");
            }

            @Override
            public void onConnectionError(String errMsg) {
                Log.d("response","onConnectionError : "+errMsg);
                //DisconnectWithMeter();
            }

            @Override
            public void onReportStatus(String statusMsg) {
                getStatusMutableLiveData.postValue(statusMsg);
                Log.d("response","onReportStatus : "+statusMsg);
            }

            @Override
            public void onResponseTimeout(int noResponseTime) {
                Log.d("response","onNoResponse " + noResponseTime);
                CheckNoResponseOperation(noResponseTime);
            }
        });

        getStatusMutableLiveData = new MutableLiveData<>();
        readMeterMutableLiveData = new MutableLiveData<>();
        readMeterResultMutableLiveData = new MutableLiveData<>();
        connectionStateMutableLiveData = new MutableLiveData<>();
        recieveDataMutableLiveData = new MutableLiveData<>();


    }

    public void StartConnectionWithMeter() {
        if (connectToMeter) return;
        try {
            final String[] meterString = {null};
            Completable.fromAction(new Action() {
                @Override
                public void run() throws Exception {
                    try {
                        lastCnnStatus = MeterUtility.connectionStatus.getMeterString;
                        meterString[0] = meterreader.StartConnectionWithMeter(lastCnnStatus);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }).subscribeOn(Schedulers.io())
                    .subscribeWith(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            //timerConnectMeterStart(1000,1000);
                        }

                        @Override
                        public void onComplete() {
                            //timerConnectMeterStop();
                            if(meterString[0]!=null && !meterString[0].isEmpty() ) {
                                connectToMeter =true;
                                MeterUtility.MeterInfo meterInfo = MeterUtility.getMeterInfo(meterString[0]);
                                readMeterMutableLiveData.postValue(meterInfo);
                                if (meterInfo.ReadMode == MeterUtility.readingMode.readout.name()) {
                                    ReadOutMeter(meterInfo);
                                } else {
                                    GetP0FromMeter(meterInfo);
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                        }
                    });



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ReadOutMeter(MeterUtility.MeterInfo meterInfo){
        try {
            final String[] readoutString = {""};
            Completable.fromAction(new Action() {
                @Override
                public void run() throws Exception {
                    lastCnnStatus = MeterUtility.connectionStatus.getReadoutString;
                    readoutString[0] = meterreader.ReadOutMeter(meterInfo, lastCnnStatus);
                }
            }).subscribeOn(Schedulers.io())
                    .subscribeWith(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onComplete() {

                            MeterUtility.ReadData readData = SplitData.splitReadData(MeterUtility.RemoveReadoutChars(readoutString[0]),meterInfo);
                            readMeterResultMutableLiveData.postValue(readData);
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            DisconnectWithMeter();
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void GetP0FromMeter(MeterUtility.MeterInfo meterInfo){
        try {
            final String[] p0String = {""};
            Completable.fromAction(new Action() {
                @Override
                public void run() throws Exception {
                    lastCnnStatus = MeterUtility.connectionStatus.getP0String;
                    p0String[0] = meterreader.GetP0FromMeter(meterInfo, lastCnnStatus);
                }
            }).subscribeOn(Schedulers.io())
                    .subscribeWith(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onComplete() {

                            SendPasswordMeter(meterInfo,SplitData.splitDataInPrnts(p0String[0]));

                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void SendPasswordMeter(MeterUtility.MeterInfo meterInfo,String P0String){
        try {
            final String[] sendPassResultStr = {""};
            Completable.fromAction(new Action() {
                @Override
                public void run() throws Exception {
                    if(meterInfo.NeedPassForRead) {
                        lastCnnStatus = MeterUtility.connectionStatus.getPassResult;
                        String PassworString="KERM";
                        sendPassResultStr[0] = meterreader.SendPasswordToMeter(meterInfo, PassworString, P0String, lastCnnStatus);
                    }else{
                        ProgrammingReadMeter(meterInfo);
                    }
                }
            }).subscribeOn(Schedulers.io())
                    .subscribeWith(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            Boolean sendPassResult = false;
                            sendPassResult = MeterUtility.CheckSendPassResultStr(sendPassResultStr[0],meterInfo);
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if(sendPassResult) {
                                //TODO start read meter
                                ProgrammingReadMeter(meterInfo);
                            }else{
                                //TODO try new pass
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void ProgrammingReadMeter(MeterUtility.MeterInfo meterInfo){
        try {
            //final String[] ProgReadString = {""};
            final MeterUtility.ReadData[] readData = {new MeterUtility.ReadData()};
            Completable.fromAction(new Action() {
                @Override
                public void run() throws Exception {
                    lastCnnStatus = MeterUtility.connectionStatus.getObisString;
                    readData[0] = meterreader.ProgrammingReadMeter(meterInfo, lastCnnStatus);
                }
            }).subscribeOn(Schedulers.io())
                    .subscribeWith(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            readMeterResultMutableLiveData.postValue(readData[0]);
                            DisconnectWithMeter();
                        }

                        @Override
                        public void onError(Throwable e) {

                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void DisconnectWithMeter(){
        try {
            lastCnnStatus = MeterUtility.connectionStatus.disConnecting;
            meterreader.DisconnectWithMeter(lastCnnStatus);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  void CheckNoResponseOperation(int noResponseTime){
        switch (lastCnnStatus) {
            case getMeterString:
                if (noResponseTime > 3) {
                    try {
                        meterreader.StopOperation();
                        StartConnectionWithMeter();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case getReadoutString:
            case getObisString:
                if (noResponseTime > 3) {
                    try {
                        meterreader.StopOperation();
                        DisconnectWithMeter();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    public void initTranseferLayer(){
        String BluetoothDeviceName = G.getPref(SharePrefEnum.ModuleBluetoothName);
        if(BluetoothDeviceName==null) BluetoothDeviceName="";
        if(BluetoothDeviceName.equals("")) connectionStateMutableLiveData.postValue(false);
        bluetooth.disconnnect();
        bluetooth.init(BluetoothDeviceName);
    }



    @Override
    protected void onCleared() {
        super.onCleared();
    }

}
