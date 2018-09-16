package ir.saa.android.mt.viewmodels;

import android.annotation.TargetApi;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.FragmentsEnum;
import ir.saa.android.mt.model.entities.TestDtl;
import ir.saa.android.mt.model.entities.TestInfo;
import ir.saa.android.mt.repositories.bluetooth.Bluetooth;
import ir.saa.android.mt.repositories.metertester.IMTCallback;
import ir.saa.android.mt.repositories.metertester.MT;
import ir.saa.android.mt.repositories.metertester.TestResult;
import ir.saa.android.mt.repositories.roomrepos.TestDtlRepo;
import ir.saa.android.mt.repositories.roomrepos.TestInfoRepo;
import ir.saa.android.mt.uicontrollers.pojos.TestContor.TestContorParams;

public class AmaliyatViewModel extends AndroidViewModel {
    Bluetooth bluetooth;
    MT metertester;
    TestContorParams testContorParams;
    Timer timerCheck;
    double ErrPercAvr=0;
    volatile boolean setTimer=false;
    int lastPaulseCounter=0;
    int myPaulseCounter=0;
    List<TestResult> testResultList;
    Timer timer;
    Handler handler=null;
    TestInfoRepo testInfoRepo;
    TestDtlRepo testDtlRepo;

//    public MutableLiveData<Boolean> setTimerMutableLiveData;
    public MutableLiveData<String> testResultMutableLiveData;
    public MutableLiveData<Integer> testRoundNumMutableLiveData;
    public MutableLiveData<List<TestResult>> testResultListMutableLiveData;
    public MutableLiveData<MT.TestCommands> testStatusMutableLiveData;

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
        if(testInfoRepo==null){
            testInfoRepo=new TestInfoRepo(application);
        }
        if(testDtlRepo==null){
            testDtlRepo=new TestDtlRepo(application);
        }

        testResultMutableLiveData = new MutableLiveData<>();
        testRoundNumMutableLiveData = new MutableLiveData<>();
        testResultListMutableLiveData = new MutableLiveData<>();
        testStatusMutableLiveData = new MutableLiveData<>();
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
            if(paulseCounter>=metertester.maxRoundTest) finishTest();
            testRoundNumMutableLiveData.postValue(paulseCounter);

            if(paulseCounter>0) {
                if(lastPaulseCounter<paulseCounter) {

                    for (int pl_cnt = lastPaulseCounter + 1; pl_cnt <= paulseCounter; pl_cnt++) {
                        testResult = metertester.ReadTestResult(pl_cnt, testContorParams);
                        testResultList.add(testResult);
                        Log.d("response miss round", pl_cnt + "");
                    }
//                    testResult = metertester.ReadTestResult(paulseCounter, testContorParams);
//                    testResultList.add(testResult);


                    if (!setTimer) {
                        int readInterval=Integer.parseInt(testResult.Time_Period1);
                        if(readInterval==0) readInterval=1;
                        timerCheckStart(readInterval * 100);
                        timerSetIntervalStop();
                        handler.removeCallbacksAndMessages(null);
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

    public void checkTestStatus(){
        boolean result=false;
        MT.TestCommands startTestStatus =  metertester.ReadTestCommand();
        if(!startTestStatus.equals(MT.TestCommands.StartTest)) {
            result=true;
        }

        testStatusMutableLiveData.postValue(startTestStatus);
    }

    public void startTest() {
        testStatusMutableLiveData.postValue(MT.TestCommands.StartTest);

        metertester.SendTestCommand(MT.TestCommands.StartTest);
        lastPaulseCounter = 0;
        myPaulseCounter = 0;
        ErrPercAvr = 0;
        setTimer = false;
        testResultList = new ArrayList<>();

        timerSetIntervalStart(250);
    }

    public void finishTest(){
        testStatusMutableLiveData.postValue(MT.TestCommands.FinishTest);

        timerSetIntervalStop();
        if(handler!=null) handler.removeCallbacksAndMessages(null);
        metertester.SendTestCommand(MT.TestCommands.FinishTest);
        timerCheckStop();
        showResult();
    }

    private void showResult(){
        testResultListMutableLiveData.postValue(testResultList);
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
                        readTestResultFromMeter();
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

    public Long insertTestDtl(TestDtl testDtl){
        return testDtlRepo.insertTestDtl(testDtl);
    }

    public Long insertTestInfo(TestInfo testInfo){
        return testInfoRepo.insertTestInfo(testInfo);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        timerSetIntervalStop();
        if(handler!=null) handler.removeCallbacksAndMessages(null);
    }

}
