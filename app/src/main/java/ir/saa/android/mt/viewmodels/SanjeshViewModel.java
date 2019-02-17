package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ir.saa.android.mt.adapters.testdisplay.TestResultItemDisplay;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.SharePrefEnum;
import ir.saa.android.mt.model.entities.TestDtl;
import ir.saa.android.mt.model.entities.TestInfo;
import ir.saa.android.mt.repositories.bluetooth.Bluetooth;
import ir.saa.android.mt.repositories.metertester.ElectericalParams;
import ir.saa.android.mt.repositories.metertester.IMTCallback;
import ir.saa.android.mt.repositories.metertester.MT;
import ir.saa.android.mt.repositories.metertester.TestResult;
import ir.saa.android.mt.uicontrollers.pojos.TestContor.TestContorFieldName;

public class SanjeshViewModel extends AndroidViewModel {
    Bluetooth bluetooth;
    MT metertester;

    List<ElectericalParams> sanjeshResult;
    Timer timer;
    Handler handler=null;

    public MutableLiveData<List<ElectericalParams>> sanjeshResultMutableLiveData;
    public MutableLiveData<Boolean> connectionStateMutableLiveData;

    public SanjeshViewModel(@NonNull Application application) {
        super(application);
        metertester = MT.getInstance();
        bluetooth = Bluetooth.getInstance();
        metertester.setTransferLayer(bluetooth);
        metertester.setMTCallback(new IMTCallback() {
            @Override
            public void onConnected() {
                connectionStateMutableLiveData.postValue(true);
                readSanjeshResultFromMeter();
                Log.d("response","onConnected");
            }

            @Override
            public void onDisConnected() {
                Log.d("response","onDisConnected");
            }

            @Override
            public void onConnectionError(String errMsg) {
                connectionStateMutableLiveData.postValue(false);
                Log.d("response","onConnectionError : "+errMsg);
            }

            @Override
            public void onReportStatus(String statusMsg) {
                Log.d("response","onReportStatus : "+statusMsg);
            }
        });

        sanjeshResultMutableLiveData = new MutableLiveData<>();
        connectionStateMutableLiveData = new MutableLiveData<>();

        timerSetIntervalStart(2000);
    }

    public void readSanjeshResultFromMeter(){
        try {
            sanjeshResult = metertester.ReadAllElectericalParams();
            sanjeshResultMutableLiveData.postValue(sanjeshResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initTranseferLayer(){
        String BluetoothDeviceName = G.getPref(SharePrefEnum.ModuleBluetoothName);
        if(BluetoothDeviceName==null) BluetoothDeviceName="";
        if(BluetoothDeviceName.equals("")) connectionStateMutableLiveData.postValue(false);
        bluetooth.disconnnect();
        bluetooth.init(BluetoothDeviceName);
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
                        readSanjeshResultFromMeter();
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

    @Override
    protected void onCleared() {
        super.onCleared();
        timerSetIntervalStop();
        if(handler!=null) handler.removeCallbacksAndMessages(null);
    }

}
