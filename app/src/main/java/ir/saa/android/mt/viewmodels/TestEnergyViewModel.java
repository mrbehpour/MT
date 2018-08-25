package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.FragmentsEnum;
import ir.saa.android.mt.repositories.bluetooth.Bluetooth;
import ir.saa.android.mt.repositories.metertester.IMTCallback;
import ir.saa.android.mt.repositories.metertester.MT;
import ir.saa.android.mt.repositories.metertester.EnergiesState;
import ir.saa.android.mt.uicontrollers.pojos.TestContor.TestContorParams;

public class TestEnergyViewModel extends AndroidViewModel {
    Bluetooth bluetooth;
    MT metertester;
    TestContorParams testContorParams;
    Timer timerCheck;

    public MutableLiveData<EnergiesState> energiesStateMutableLiveData;


    public TestEnergyViewModel(@NonNull Application application) {
        super(application);
        metertester = MT.getInstance();
        bluetooth = Bluetooth.getInstance();
        metertester.setTransferLayer(bluetooth);
        metertester.setMTCallback(new IMTCallback() {
            @Override
            public void onConnected() {

                Log.d("response","onConnected");
            }

            @Override
            public void onDisConnected() {
                Log.d("response","onDisConnected");
            }

            @Override
            public void onConnectionError(String errMsg) {
                Log.d("response","onConnectionError : "+errMsg);
            }

            @Override
            public void onReportStatus(String statusMsg) {
                Log.d("response","onReportStatus : "+statusMsg);
            }
        });
//        bluetooth.init("BTM0604C2P");
        bluetooth.init("OP-700");
        energiesStateMutableLiveData = new MutableLiveData<>();

        timerCheckStart(2000);

    }

    TimerTask timerCheckTask = new TimerTask() {

        @Override
        public void run() {
            readEnergiesStateFromMeter();
        }

    };


    private void timerCheckStart(long prd) {
        if(timerCheck != null) {
            return;
        }

        timerCheck = new Timer();
        timerCheck.scheduleAtFixedRate(timerCheckTask, 0, prd);

    }

    private void timerCheckStop() {
        if(timerCheck!=null){
            timerCheck.cancel();
            timerCheck=null;
        }
    }

    public void setTestContorParams(TestContorParams testContorParams){
        this.testContorParams=testContorParams;
    }

    public void writeTestContorParams(){
        try {
            String s =  metertester.SendTestContorParams(testContorParams);
            Log.d("response tcp",s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readEnergiesStateFromMeter(){

        EnergiesState energiesState;
        try {
            energiesState = metertester.ReadEnergiesState();
            energiesStateMutableLiveData.postValue(energiesState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void confirmEnergies(){
        if((energiesStateMutableLiveData.getValue().energy1AState && energiesStateMutableLiveData.getValue().energy2AState && energiesStateMutableLiveData.getValue().energy3AState) ||
                (!energiesStateMutableLiveData.getValue().energy1AState && !energiesStateMutableLiveData.getValue().energy3AState && !energiesStateMutableLiveData.getValue().energy3AState)   ){
            //go to next fragment
            timerCheckStop();
            writeTestContorParams();
            G.startFragment(FragmentsEnum.AmaliyatFragment,false,null);
        }
        else{
            Toast.makeText(G.context,"جهت کلمپ ها یکسان نیست",Toast.LENGTH_SHORT).show();
        }
    }
}
