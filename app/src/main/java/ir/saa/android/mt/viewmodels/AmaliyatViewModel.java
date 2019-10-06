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
import ir.saa.android.mt.model.entities.GPSInfo;
import ir.saa.android.mt.model.entities.TestAllInfo;
import ir.saa.android.mt.model.entities.TestDtl;
import ir.saa.android.mt.model.entities.TestInfo;
import ir.saa.android.mt.repositories.bluetooth.Bluetooth;
import ir.saa.android.mt.repositories.metertester.IMTCallback;
import ir.saa.android.mt.repositories.metertester.MT;
import ir.saa.android.mt.repositories.metertester.TestResult;
import ir.saa.android.mt.repositories.roomrepos.GPSInfoRepo;
import ir.saa.android.mt.repositories.roomrepos.TestDtlRepo;
import ir.saa.android.mt.repositories.roomrepos.TestInfoRepo;
import ir.saa.android.mt.uicontrollers.pojos.TestContor.TestContorFieldName;
import ir.saa.android.mt.uicontrollers.pojos.TestContor.TestContorParams;

public class AmaliyatViewModel extends AndroidViewModel {
    Bluetooth bluetooth;
    MT metertester;
    TestContorParams testContorParams;
    Timer timerCheck;
    double ErrPercAvr=0;
    volatile boolean setTimer=false;
    int lastPaulseCounter=0;
    int numErrorPulseNum =0;
    int numErrorConnection =0;
    int myPaulseCounter=0;



    boolean testPause = false;
    List<TestResult> testResultList;
    Timer timer;
    Handler handler=null;
    TestInfoRepo testInfoRepo;
    TestDtlRepo testDtlRepo;
    GPSInfoRepo gpsInfoRepo;

    String lastSendCommand;

//    public MutableLiveData<Boolean> setTimerMutableLiveData;
    public MutableLiveData<Boolean> doManualTestMutableLiveData;
    public MutableLiveData<String> testResultMutableLiveData;
    public MutableLiveData<Integer> testRoundNumMutableLiveData;
    public MutableLiveData<List<TestResult>> testResultListMutableLiveData;
    public MutableLiveData<MT.TestCommands> testStatusMutableLiveData;
    public MutableLiveData<Integer> cancelTestProcess;

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
                Log.d("response","onConnectionError : " + errMsg);
                handleError(errMsg);
            }

            @Override
            public void onReportStatus(String statusMsg) {
                Log.d("response","onReportStatus : "+statusMsg);
            }

            @Override
            public void onResponseTimeout(int noResponseTime) {

            }
        });
        if(testInfoRepo==null){
            testInfoRepo=new TestInfoRepo(application);
        }
        if(testDtlRepo==null){
            testDtlRepo=new TestDtlRepo(application);
        }
        if(gpsInfoRepo==null){
            gpsInfoRepo=new GPSInfoRepo(application);
        }

        testResultMutableLiveData = new MutableLiveData<>();
        testRoundNumMutableLiveData = new MutableLiveData<>();
        testResultListMutableLiveData = new MutableLiveData<>();
        testStatusMutableLiveData = new MutableLiveData<>();
        cancelTestProcess = new MutableLiveData<>();
        doManualTestMutableLiveData = new MutableLiveData<>();
    }

    public void handleError(String errMsg){

        if(errMsg.equals("Command Timeout")){
            Log.d("response", lastSendCommand);
            if(testContorParams.PaulserType) {
                timerCheckStop();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if(lastSendCommand.equals("StartManualTest")) {
                cancelTestProcess.postValue(2);
                finishTest(true);
            }
            if(lastSendCommand.equals("ReadTestResult")) {
                String a = "1234";
            }
        }

        if(errMsg.equals("Transfer Layer is Disconnected")) {
            numErrorConnection++;
            if (numErrorConnection > 10) {
                testPause = true;
                cancelTestProcess.postValue(1);
            }
        }
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



    private void timerCheckStop() {
        if(timerCheck!=null){
            timerCheck.cancel();
            timerCheck.purge();
            timerCheck=null;
        }
    }

    public void readTestResultFromMeter(){
        if(testPause) return;
        TestResult testResult=null;

        try {
            int paulseCounter = metertester.ReadPaulseCounter();
            numErrorConnection=0;
            if(paulseCounter==lastPaulseCounter){
                numErrorPulseNum++;
                if(numErrorPulseNum >100) {
                    testPause=true;
                    cancelTestProcess.postValue(1);
                }
                Log.d("num_duplicate_paulse", numErrorPulseNum + "");
            }else{
                numErrorPulseNum = 0;
            }

            if(paulseCounter>=metertester.maxRoundTest) finishTest(true);
            testRoundNumMutableLiveData.postValue(paulseCounter);

            if(paulseCounter>0) {
                if(lastPaulseCounter<paulseCounter) {

                    for (int pl_cnt = lastPaulseCounter + 1; pl_cnt <= paulseCounter; pl_cnt++) {
                        testResult = metertester.ReadTestResult(pl_cnt, testContorParams);
                        testResultList.add(testResult);
                        Log.d("response_miss_round", pl_cnt + "");
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
                    //testResultMutableLiveData.postValue(String.format("%.2f,%s_%s_%s", ErrPercAvr/myPaulseCounter, testResult.MeterEnergy_Period1_A, testResult.MeterEnergy_Period1_B, testResult.MeterEnergy_Period1_C));
                    lastPaulseCounter=paulseCounter;
                }
            }else{
                Log.d("else paulse", paulseCounter + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void newManualTestCommand(){
        lastSendCommand = "StartManualTest";
        if(!metertester.geWaitForResponseStatus()) {
            String result = metertester.SendTestCommand(MT.TestCommands.StartManualTest);
            if (!result.isEmpty()) {
                doManualTestMutableLiveData.postValue(true);
            }
        }
    }

    public void readTestResultFromMeterManual(int manualPaulseNum){
        try {

            if(manualPaulseNum>0) {
                lastSendCommand = "ReadTestResult";
                TestResult testResult = metertester.ReadTestResult(manualPaulseNum, testContorParams);
                testResultList.add(testResult);
                ErrPercAvr += testResult.ErrPerc;
            }

            if(manualPaulseNum>0) testResultMutableLiveData.postValue(String.format("%.2f", ErrPercAvr/testResultList.size()));
            //testResultMutableLiveData.postValue(String.format("%.2f,%s_%s_%s", ErrPercAvr/myPaulseCounter, testResult.MeterEnergy_Period1_A, testResult.MeterEnergy_Period1_B, testResult.MeterEnergy_Period1_C));
            //lastPaulseCounter=paulseCounter;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void changeTestPauseStatus(boolean status){
        testPause=status;
        numErrorPulseNum = 0;
    }

    public void checkTestStatus(){

        MT.TestCommands startTestStatus =  metertester.ReadTestCommand();
        testStatusMutableLiveData.postValue(startTestStatus);
    }

    public void startTestOperation(){
        testStatusMutableLiveData.postValue(MT.TestCommands.StartTest);

        metertester.SendTestCommand(MT.TestCommands.StartTest);
        lastPaulseCounter = 0;
        myPaulseCounter = 0;
        numErrorPulseNum = 0;
        numErrorConnection = 0;
        ErrPercAvr = 0;
        setTimer = false;
        testResultList = new ArrayList<>();

        if(testContorParams.PaulserType){
            timerSetIntervalStart(250,1500);
        }
    }

    public void finishTest(Boolean sendFinishCommand){

        metertester.AbortOperation();
        if(sendFinishCommand) metertester.SendTestCommand(MT.TestCommands.FinishTest);
        testStatusMutableLiveData.postValue(MT.TestCommands.FinishTest);

        timerSetIntervalStop();
        if(handler!=null) handler.removeCallbacksAndMessages(null);

        timerCheckStop();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        showResult();
    }

    private void showResult(){
        testResultListMutableLiveData.postValue(testResultList);
    }

    private void timerSetIntervalStart(long prd,long delay) {
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
        timer.schedule(task, delay, prd);
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

    public void insertGpsInfo(GPSInfo gpsInfo){
         gpsInfoRepo.insertGPSInfo(gpsInfo);
    }

    public Long insertTestInfo(TestInfo testInfo){
        return testInfoRepo.insertTestInfo(testInfo);
    }

    public List<TestAllInfo> getTestAllInfos(Long clientId,Integer sendId){
        return testDtlRepo.getTestAllInfoWithSendId(clientId,sendId);
    }
    public TestAllInfo getTestAllInfo(Long clientId,Integer sendId,Integer testId){
        return testDtlRepo.getTestAllInfoWithTestId(clientId,sendId,testId);
    }

    public void deleteTestInfo(){
        testInfoRepo.deleteAllWithOutBlockId();
    }

    public void deleteTestdtl(){
        testDtlRepo.deleteAll();
    }
    public List<TestAllInfo> getTestAllInfoWithClientIdWithoutBlockId(Long ClientId){
        return testDtlRepo.getTestAllInfoWithClientIdWithoutBlockId(ClientId);
    }

    public void deleteAllTestInfo(int TestInfoId,int TestDtlId){
        testDtlRepo.deleteTestDtlById(TestDtlId);
        testInfoRepo.deleteTestInfoById(TestInfoId);
    }
    public LiveData<List<TestResultItemDisplay>> getTestAllInfoWithClientIDLiveData(Long ClientId,Integer sendId){
        TestResultItemDisplay testResultItemDisplay=null;

        TestContorFieldName testContorFieldName=new TestContorFieldName();
        MutableLiveData<List<TestResultItemDisplay>> listMutableLiveData=new MutableLiveData<>();
        List<TestResultItemDisplay> listMutableLiveDataTemp=new ArrayList<>();
        List<TestInfo> testInfos=testInfoRepo.getTestInfoWithClientId(ClientId,sendId);
        String DateTimeTest="";
        for (TestInfo testInfo:testInfos) {
            if (testInfo.BlockID == 0){
                DateTimeTest = String.valueOf(testInfo.TestDate) + String.format("%04d", Integer.parseInt(String.valueOf(testInfo.TestTime)));
            ;

            List<TestDtl> testDtls = testDtlRepo.getTestDtlByTestInfoId(testInfo.TestInfoID);
            testResultItemDisplay = new TestResultItemDisplay();
            for (TestDtl testDtl : testDtls) {


                if (testDtl.TestID != 0) {
                    switch (testContorFieldName.getTestFieldName(testDtl.TestID)) {
                        case "ContorConst":
                            testResultItemDisplay.ContorConst = Integer.valueOf(testDtl.TestValue);
                            break;
                        case "CTCoeff":
                            testResultItemDisplay.CTCoeff = Integer.valueOf(testDtl.TestValue);
                            break;
                        case "SensorRatio":
                            testResultItemDisplay.SensorRatio = Integer.valueOf(testDtl.TestValue);
                            break;
                        case "testContorParams_RoundNum":
                            testResultItemDisplay.RoundNumForTest = Integer.valueOf(testDtl.TestValue);
                            break;
                        case "ErrPerc":
                            testResultItemDisplay.ErrPerc = Double.valueOf(String.format("%.2f", Double.parseDouble(testDtl.TestValue)));

                            break;
                        case "PF_A":
                            testResultItemDisplay.PF_A = Double.valueOf(String.format("%.3f", Double.parseDouble(testDtl.TestValue)));
                            break;
                        case "PF_B":
                            testResultItemDisplay.PF_B = Double.valueOf(String.format("%.3f", Double.parseDouble(testDtl.TestValue)));
                            break;
                        case "PF_C":
                            testResultItemDisplay.PF_C = Double.valueOf(String.format("%.3f", Double.parseDouble(testDtl.TestValue)));
                            break;
                        case "RoundNum":
                            testResultItemDisplay.RoundNum = Integer.valueOf(testDtl.TestValue);
                            break;
                        case "AIRMS_Period1":
                            testResultItemDisplay.AIRMS_Period1 = testDtl.TestValue;
                            break;
                        case "BIRMS_Period1":
                            testResultItemDisplay.BIRMS_Period1 = testDtl.TestValue;
                            break;
                        case "Q_A":
                            testResultItemDisplay.Q_A = Double.valueOf(String.format("%.2f", Double.parseDouble(testDtl.TestValue)));
                            break;
                        case "Q_B":
                            testResultItemDisplay.Q_B = Double.valueOf(String.format("%.2f", Double.parseDouble(testDtl.TestValue)));
                            break;
                        case "Q_C":
                            testResultItemDisplay.Q_C = Double.valueOf(String.format("%.2f", Double.parseDouble(testDtl.TestValue)));
                            ;
                            break;
                        case "Pow_A":
                            testResultItemDisplay.Pow_A = Double.valueOf(String.format("%.2f", Double.parseDouble(testDtl.TestValue)));
                            break;
                        case "Pow_B":
                            testResultItemDisplay.Pow_B = Double.valueOf(String.format("%.2f", Double.parseDouble(testDtl.TestValue)));
                            break;
                        case "Pow_C":
                            testResultItemDisplay.Pow_C = Double.valueOf(String.format("%.2f", Double.parseDouble(testDtl.TestValue)));
                            ;
                            break;
                        case "S_A":
                            testResultItemDisplay.S_A = Double.valueOf(String.format("%.2f", Double.parseDouble(testDtl.TestValue)));
                            break;
                        case "S_B":
                            testResultItemDisplay.S_B = Double.valueOf(String.format("%.2f", Double.parseDouble(testDtl.TestValue)));
                            break;
                        case "S_C":
                            testResultItemDisplay.S_C = Double.valueOf(String.format("%.2f", Double.parseDouble(testDtl.TestValue)));
                            break;
                        case "CIRMS_Period1":
                            testResultItemDisplay.CIRMS_Period1 = testDtl.TestValue;
                            break;
                        case "NIRMS_Period1":
                            testResultItemDisplay.NIRMS_Period1 = testDtl.TestValue;
                            ;
                            break;
                        case "AVRMS_Period1":
                            testResultItemDisplay.AVRMS_Period1 = testDtl.TestValue;
                            break;
                        case "BVRMS_Period1":
                            testResultItemDisplay.BVRMS_Period1 = testDtl.TestValue;
                            break;
                        case "CVRMS_Period1":
                            testResultItemDisplay.CVRMS_Period1 = testDtl.TestValue;
                            break;
                        case "Period_Period1_A":
                            testResultItemDisplay.Period_Period1_A = testDtl.TestValue;
                            break;
                        case "Time_Period1":
                            testResultItemDisplay.Time_Period1 = testDtl.TestValue;
                            break;
                    }
                }


            }

            testResultItemDisplay.Active = testInfo.TestTypeID == 1 ? true : false;
            testResultItemDisplay.SinglePhase = testInfo.ContorTypeID == 1 ? true : false;
            testResultItemDisplay.FisrtTest = testInfo.TestCount == 1 ? true : false;
            testResultItemDisplay.DateTime = String.format("%s/%s/%s %s:%s", DateTimeTest.substring(0, 4), DateTimeTest.substring(4, 6)
                    , DateTimeTest.substring(6, 8), DateTimeTest.substring(8, 10), DateTimeTest.substring(10, 12));


            listMutableLiveDataTemp.add(testResultItemDisplay);

        }
        }
        listMutableLiveData.postValue(listMutableLiveDataTemp);
        return listMutableLiveData;
    }

    public List<TestInfo> getTestInfoWithBlockId(Long clientId,Integer SendId){
        return testInfoRepo.getTestInfoWithBlockId(clientId,SendId);
    }
    public void deleteTestInfoById(int TestInfoId){
        testInfoRepo.deleteTestInfoById(TestInfoId);
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        timerSetIntervalStop();
        if(handler!=null) handler.removeCallbacksAndMessages(null);
    }

}
