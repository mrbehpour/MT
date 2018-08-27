package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.FragmentsEnum;
import ir.saa.android.mt.repositories.bluetooth.Bluetooth;
import ir.saa.android.mt.repositories.metertester.EnergiesState;
import ir.saa.android.mt.repositories.metertester.IMTCallback;
import ir.saa.android.mt.repositories.metertester.MT;
import ir.saa.android.mt.repositories.metertester.TestResult;
import ir.saa.android.mt.uicontrollers.pojos.TestContor.TestContorParams;

public class AmaliyatViewModel extends AndroidViewModel {
    Bluetooth bluetooth;
    MT metertester;
    TestContorParams testContorParams;
    Timer timerCheck;

    public MutableLiveData<String> testResultMutableLiveData;
    public MutableLiveData<Integer> testRoundNumMutableLiveData;

    public AmaliyatViewModel(@NonNull Application application) {
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

        testResultMutableLiveData = new MutableLiveData<>();
        testRoundNumMutableLiveData = new MutableLiveData<>();
    }

    private void timerCheckStart(long prd) {
        if(timerCheck != null) {
            return;
        }

        timerCheck = new Timer();

        TimerTask timerCheckTask = new TimerTask() {

            @Override
            public void run() {
                readTestResultFromMeter();
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

    public void readTestResultFromMeter(){

        TestResult testResult=null;
        try {
            int paulseCounter = metertester.ReadPaulseCounter();
            testRoundNumMutableLiveData.postValue(paulseCounter);

            if(paulseCounter>0){
                testResult = metertester.ReadTestResult(paulseCounter);
            }

            double ErrPerc = Math.abs(Double.parseDouble(testResult.MeterEnergy_Period1_A))+
                    Math.abs(Double.parseDouble(testResult.MeterEnergy_Period1_B))+
                    Math.abs(Double.parseDouble(testResult.MeterEnergy_Period1_C));
            ErrPerc = ErrPerc * 47.59552;
            ErrPerc = (((3600000/2000)-ErrPerc)/ErrPerc)*100;

            testResultMutableLiveData.postValue(String.format("%.2f", ErrPerc));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startTest(){
        metertester.SendTestCommand(MT.TestCommands.StartTest);
        timerCheckStart(1000);
    }

    public void finishTest(){
        metertester.SendTestCommand(MT.TestCommands.FinishTest);
        timerCheckStop();
    }

}
