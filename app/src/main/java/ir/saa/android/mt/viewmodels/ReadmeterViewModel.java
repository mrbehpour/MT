package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

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
import ir.saa.android.mt.repositories.metertester.IMTCallback;

public class ReadmeterViewModel extends AndroidViewModel {
    Bluetooth bluetooth;
    PROB meterreader;
    Timer timerConnectMeter;
    Timer timer;
    Handler handler=null;

    int numTryConnectToMeter =0;
    int numErrorConnection =0;

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
                numErrorConnection++;
                if(numErrorConnection>3) {

                }
            }

            @Override
            public void onReportStatus(String statusMsg) {
                Log.d("response","onReportStatus : "+statusMsg);
            }
        });

        getStatusMutableLiveData = new MutableLiveData<>();
        readMeterMutableLiveData = new MutableLiveData<>();
        readMeterResultMutableLiveData = new MutableLiveData<>();
        connectionStateMutableLiveData = new MutableLiveData<>();
        recieveDataMutableLiveData = new MutableLiveData<>();
    }


    private void timerConnectMeterStop() {
        if(timerConnectMeter !=null){
            timerConnectMeter.cancel();
            timerConnectMeter.purge();
            timerConnectMeter =null;
        }
    }
    private void timerConnectMeterStart(long prd, long delay) {
        if(timerConnectMeter != null) {
            return;
        }

        if(handler==null)
            handler = new Handler();
        else
            handler.removeCallbacksAndMessages(null);

        timerConnectMeter = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        StartConnectionWithMeter();
                        numTryConnectToMeter++;
                        Log.d("response","timer : " + numTryConnectToMeter);
                    }
                });
            }
        };
        timerConnectMeter.schedule(task, delay, prd);
    }


    public void StartConnectionWithMeter() {
        try {
            final String[] meterString = {null};
            Completable.fromAction(new Action() {
                @Override
                public void run() throws Exception {

                    try {
                        meterString[0] = meterreader.StartConnectionWithMeter();
                        //getStatusMutableLiveData.postValue(String.format("Try To Connect Num %s",numTryConnectToMeter));
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
                            MeterUtility.MeterInfo meterInfo = MeterUtility.getMeterInfo(meterString[0]);
                            readMeterMutableLiveData.postValue(meterInfo);
                            if(meterInfo.ReadMode == MeterUtility.readingMode.readout.name()) {
                                ReadOutMeter(meterInfo);
                            }else{
                                GetP0FromMeter(meterInfo);
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
                     readoutString[0] = meterreader.ReadOutMeter(meterInfo);
                }
            }).subscribeOn(Schedulers.io())
                    .subscribeWith(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onComplete() {

                            MeterUtility.ReadData readData = SplitData.splitReadData(readoutString[0],meterInfo);
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
                    p0String[0] = meterreader.GetP0FromMeter(meterInfo);
                }
            }).subscribeOn(Schedulers.io())
                    .subscribeWith(new CompletableObserver() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onComplete() {

                            SendPasswordMeter(meterInfo,p0String[0]);

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
                        sendPassResultStr[0] = meterreader.SendPasswordToMeter(meterInfo);
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
                    readData[0] = meterreader.ProgrammingReadMeter(meterInfo);
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
            meterreader.DisconnectWithMeter();
            //readMeterMutableLiveData.postValue("disconnecting...");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void timerSetIntervalStart(long prd) {
        if(timer != null) {
            return;
        }

        if(handler==null)
            handler = new Handler();
        else
            handler.removeCallbacksAndMessages(null);

        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        //readTestResultFromMeter();
                    }
                });
            }
        };
        timer.schedule(task, 0, prd);
    }

    private void timerSetIntervalStop() {
        if(timer!=null){
            timer.cancel();
            timer.purge();
            timer=null;
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
        timerSetIntervalStop();
        if(handler!=null) handler.removeCallbacksAndMessages(null);
    }

}
