package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ir.saa.android.mt.repositories.bluetooth.Bluetooth;
import ir.saa.android.mt.repositories.metertester.IMTCallback;
import ir.saa.android.mt.repositories.metertester.MT;
import ir.saa.android.mt.repositories.metertester.TestResult;
import ir.saa.android.mt.uicontrollers.pojos.TestContor.TestContorParams;

public class AmaliyatViewModel extends AndroidViewModel {
    Bluetooth bluetooth;
    MT metertester;
    TestContorParams testContorParams;
    Timer timerCheck;
    double ErrPercAvr=0;
    boolean setTimer=false;
    int lastPaulseCounter=0;
    int myPaulseCounter=0;
    List<TestResult> testResultList;

//    Handler handler;

    public MutableLiveData<String> testResultMutableLiveData;
    public MutableLiveData<Integer> testRoundNumMutableLiveData;
    public MutableLiveData<List<TestResult>> testResultListMutableLiveData;

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
        testResultListMutableLiveData = new MutableLiveData<>();
    }

    public void setTestContorParams(TestContorParams testContorParams){
        this.testContorParams=testContorParams;
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

    private void timerCheckRestart(long prd) {
        timerCheck.cancel();
        timerCheck.purge();

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
            if(paulseCounter>=250) finishTest();
            testRoundNumMutableLiveData.postValue(paulseCounter);

            if(paulseCounter>0) {
                if(lastPaulseCounter<paulseCounter) {
                    testResult = metertester.ReadTestResult(paulseCounter,testContorParams);
                    testResultList.add(testResult);
                    if (!setTimer) {
                        timerCheckStart(Integer.parseInt(testResult.Time_Period1) * 100);
                        setTimer = true;
                    }

                    ErrPercAvr += testResult.ErrPerc;
                    myPaulseCounter++;
                    Log.d("response round Err Perc",  testResult.ErrPerc + "," + ErrPercAvr);
                    testResultMutableLiveData.postValue(String.format("%.2f", ErrPercAvr/myPaulseCounter));

                    lastPaulseCounter=paulseCounter;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startTest() {
        metertester.SendTestCommand(MT.TestCommands.StartTest);
        lastPaulseCounter=0;
        myPaulseCounter=0;
        ErrPercAvr=0;
        setTimer=false;
        testResultList = new ArrayList<>();

        while (!setTimer){
            readTestResultFromMeter();
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//        handler =  new Handler();
//        Runnable myRunnable = new Runnable() {
//            public void run() {
//                readTestResultFromMeter();
//            }
//        };
//        handler.postDelayed(myRunnable,250);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    readTestResultFromMeter();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }, 250);

    }

    public void finishTest(){
        metertester.SendTestCommand(MT.TestCommands.FinishTest);
        timerCheckStop();
        showResult();
    }

    private void showResult(){
        testResultListMutableLiveData.postValue(testResultList);
    }

}
