package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.BundleKeysEnum;
import ir.saa.android.mt.enums.FragmentsEnum;
import ir.saa.android.mt.repositories.bluetooth.Bluetooth;
import ir.saa.android.mt.repositories.metertester.EnergiesState;
import ir.saa.android.mt.repositories.metertester.MT;
import ir.saa.android.mt.uicontrollers.pojos.TestContor.TestContorParams;

public class TestEnergyViewModel extends AndroidViewModel {
    MT metertester;
    TestContorParams testContorParams;
    Timer timerCheck;

    public MutableLiveData<EnergiesState> energiesStateMutableLiveData;

    public TestEnergyViewModel(@NonNull Application application) {
        super(application);
        metertester = MT.getInstance();
        energiesStateMutableLiveData = new MutableLiveData<>();

        timerCheckStart(2000);
    }

    private void timerCheckStart(long prd) {
        if(timerCheck != null) {
            return;
        }

        timerCheck = new Timer();
        TimerTask timerCheckTask = new TimerTask() {

            @Override
            public void run() {
                readEnergiesStateFromMeter();
            }

        };
        timerCheck.schedule(timerCheckTask, 0, prd);
    }

    private void timerCheckStop() {
        if(timerCheck!=null){
            timerCheck.cancel();
            timerCheck.purge();
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
        try{
            if ((energiesStateMutableLiveData.getValue().energy1AState && energiesStateMutableLiveData.getValue().energy2AState && energiesStateMutableLiveData.getValue().energy3AState) ||
                    (!energiesStateMutableLiveData.getValue().energy1AState && !energiesStateMutableLiveData.getValue().energy3AState && !energiesStateMutableLiveData.getValue().energy3AState)) {
                //go to next fragment
                timerCheckStop();
                writeTestContorParams();

                Bundle bundle = new Bundle();
                bundle.putSerializable(BundleKeysEnum.TestContorParams, testContorParams);

                G.startFragment(FragmentsEnum.AmaliyatFragment, false, bundle);
            } else {
                Toast.makeText(G.context, "جهت کلمپ ها یکسان نیست", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception ex){

        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        timerCheckStop();
    }
}
