package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
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
import ir.saa.android.mt.model.entities.TariffAllInfo;
import ir.saa.android.mt.model.entities.TariffDtl;
import ir.saa.android.mt.model.entities.TariffInfo;
import ir.saa.android.mt.model.entities.DigitalMeters;
import ir.saa.android.mt.repositories.bluetooth.Bluetooth;
import ir.saa.android.mt.repositories.meterreader.MeterUtility;
import ir.saa.android.mt.repositories.meterreader.PROB;
import ir.saa.android.mt.repositories.meterreader.SplitData;
import ir.saa.android.mt.repositories.meterreader.StatusReport;
import ir.saa.android.mt.repositories.metertester.IMTCallback;
import ir.saa.android.mt.repositories.roomrepos.TariffDtlRepo;
import ir.saa.android.mt.repositories.roomrepos.TariffInfoRepo;
import ir.saa.android.mt.repositories.roomrepos.DigitalMetersRepo;
import ir.saa.android.mt.repositories.roomrepos.TariffDtlRepo;
import ir.saa.android.mt.repositories.roomrepos.TariffInfoRepo;

public class ReadmeterViewModel extends AndroidViewModel {
    Bluetooth bluetooth;
    PROB meterreader;
    MeterUtility.connectionStatus lastCnnStatus;
    boolean connectToMeter =false;

    public MutableLiveData<String> getStatusMutableLiveData;
    public MutableLiveData<DigitalMeters> readMeterMutableLiveData;
    public MutableLiveData<MeterUtility.ReadData> readMeterResultMutableLiveData;
    public MutableLiveData<Boolean> connectionStateMutableLiveData;
    public MutableLiveData<Integer> recieveDataMutableLiveData;
    TariffDtlRepo tariffDtlRepo=null;
    TariffInfoRepo tariffInfoRepo;

    private DigitalMetersRepo digitalMetersRepo;

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

        if(tariffDtlRepo==null){
            tariffDtlRepo=new TariffDtlRepo(application);
        }
        if(tariffInfoRepo==null){
            tariffInfoRepo=new TariffInfoRepo(application);
        }
        if(digitalMetersRepo==null){
            digitalMetersRepo=new DigitalMetersRepo(application);
        }
    }

    public void InsertDigitlMetersToDB(){

        //String jsonArray = "{\"DigitalMeters\":[{\"MeterID\":2,\"MeterCompany\":\"Elester\",\"MeterType\":\"A1350\",\"MeterSummaryName\":\"Elester_A1350\",\"MeterString\":\"ABB5\\\\@V5\",\"ReadMode\":\"0\",\"ValidationRegex\":\"^[FC0-9]{1,2}[.][FC0-9]{1,2}[.]\\\\d{1,2}[(].*[)]$\",\"SetDateTime\":false,\"NeedPassForRead\":false,\"ReadObisType\":\"Obis_Prnts_Semi\",\"R_Command\":\"R5\",\"MakePassAlgorithm\":null,\"Pass1\":\"00000000\",\"Pass2\":null,\"Pass3\":null},{\"MeterID\":3,\"MeterCompany\":\"Elester\",\"MeterType\":\"A1500\",\"MeterSummaryName\":\"Elester_A1500\",\"MeterString\":\"ABB5\\\\@V4\",\"ReadMode\":\"0\",\"ValidationRegex\":\"^[FC0-9]{1,2}[.][FC0-9]{1,2}[.]\\\\d{1,2}[(].*[)]$\",\"SetDateTime\":false,\"NeedPassForRead\":false,\"ReadObisType\":\"Obis_Prnts_Semi\",\"R_Command\":\"R5\",\"MakePassAlgorithm\":null,\"Pass1\":\"00000000\",\"Pass2\":null,\"Pass3\":null},{\"MeterID\":4,\"MeterCompany\":\"Elester\",\"MeterType\":\"A1440\",\"MeterSummaryName\":\"Elester_A1440\",\"MeterString\":\"ABB5\\\\@V9\",\"ReadMode\":\"0\",\"ValidationRegex\":\"^[FC0-9]{1,2}[.][FC0-9]{1,2}[.]\\\\d{1,2}[(].*[)]$\",\"SetDateTime\":false,\"NeedPassForRead\":false,\"ReadObisType\":\"Obis_Prnts_Semi\",\"R_Command\":\"R5\",\"MakePassAlgorithm\":null,\"Pass1\":\"00000000\",\"Pass2\":null,\"Pass3\":null},{\"MeterID\":6,\"MeterCompany\":\"Elester\",\"MeterType\":\"A220\",\"MeterSummaryName\":\"Elester_A220\",\"MeterString\":\"ABB5\\\\@V7\",\"ReadMode\":\"1\",\"ValidationRegex\":\"^[FC0-9]{1,2}[.][FC0-9]{1,2}[.]\\\\d{1,2}[(].*[)]$\",\"SetDateTime\":false,\"NeedPassForRead\":false,\"ReadObisType\":\"Obis_Prnts_Semi\",\"R_Command\":\"R5\",\"MakePassAlgorithm\":null,\"Pass1\":\"00000000\",\"Pass2\":null,\"Pass3\":null},{\"MeterID\":15,\"MeterCompany\":\"AMPY\",\"MeterType\":\"5194E\",\"MeterSummaryName\":\"AMPY_E\",\"MeterString\":\"AMP2519\",\"ReadMode\":\"0\",\"ValidationRegex\":\"^AMP\\\\\\\\d{3}[(].*[)]$\",\"SetDateTime\":false,\"NeedPassForRead\":false,\"ReadObisType\":\"Just_Obis\",\"R_Command\":\"R1\",\"MakePassAlgorithm\":null,\"Pass1\":\"KERM\",\"Pass2\":\"AZAR\",\"Pass3\":null},{\"MeterID\":30,\"MeterCompany\":\"AfzarAzma\",\"MeterType\":\"JAM300\",\"MeterSummaryName\":\"JAM_300\",\"MeterString\":\"EAA5JAM3\",\"ReadMode\":\"0\",\"ValidationRegex\":\"^[FC0-9]{1,2}[.][FC0-9]{1,2}[.]\\\\\\\\d{1,2}[(].*[)]$\",\"SetDateTime\":false,\"NeedPassForRead\":false,\"ReadObisType\":\"Just_Obis\",\"R_Command\":\"R1\",\"MakePassAlgorithm\":null,\"Pass1\":null,\"Pass2\":null,\"Pass3\":null}]}";
//        String jsonArray = "[{\"MeterID\":2,\"MeterCompany\":\"Elester\",\"MeterType\":\"A1350\",\"MeterSummaryName\":\"Elester_A1350\",\"MeterString\":\"ABB5\\\\@V5\",\"ReadMode\":\"0\",\"ValidationRegex\":\"^[FC0-9]{1,2}[.][FC0-9]{1,2}[.]\\\\d{1,2}[(].*[)]$\",\"SetDateTime\":false,\"NeedPassForRead\":false,\"ReadObisType\":\"Obis_Prnts_Semi\",\"R_Command\":\"R5\",\"MakePassAlgorithm\":null,\"Pass1\":\"00000000\",\"Pass2\":null,\"Pass3\":null},{\"MeterID\":3,\"MeterCompany\":\"Elester\",\"MeterType\":\"A1500\",\"MeterSummaryName\":\"Elester_A1500\",\"MeterString\":\"ABB5\\\\@V4\",\"ReadMode\":\"0\",\"ValidationRegex\":\"^[FC0-9]{1,2}[.][FC0-9]{1,2}[.]\\\\d{1,2}[(].*[)]$\",\"SetDateTime\":false,\"NeedPassForRead\":false,\"ReadObisType\":\"Obis_Prnts_Semi\",\"R_Command\":\"R5\",\"MakePassAlgorithm\":null,\"Pass1\":\"00000000\",\"Pass2\":null,\"Pass3\":null},{\"MeterID\":4,\"MeterCompany\":\"Elester\",\"MeterType\":\"A1440\",\"MeterSummaryName\":\"Elester_A1440\",\"MeterString\":\"ABB5\\\\@V9\",\"ReadMode\":\"0\",\"ValidationRegex\":\"^[FC0-9]{1,2}[.][FC0-9]{1,2}[.]\\\\d{1,2}[(].*[)]$\",\"SetDateTime\":false,\"NeedPassForRead\":false,\"ReadObisType\":\"Obis_Prnts_Semi\",\"R_Command\":\"R5\",\"MakePassAlgorithm\":null,\"Pass1\":\"00000000\",\"Pass2\":null,\"Pass3\":null},{\"MeterID\":6,\"MeterCompany\":\"Elester\",\"MeterType\":\"A220\",\"MeterSummaryName\":\"Elester_A220\",\"MeterString\":\"ABB5\\\\@V7\",\"ReadMode\":\"1\",\"ValidationRegex\":\"^[FC0-9]{1,2}[.][FC0-9]{1,2}[.]\\\\d{1,2}[(].*[)]$\",\"SetDateTime\":false,\"NeedPassForRead\":false,\"ReadObisType\":\"Obis_Prnts_Semi\",\"R_Command\":\"R5\",\"MakePassAlgorithm\":null,\"Pass1\":\"00000000\",\"Pass2\":null,\"Pass3\":null},{\"MeterID\":15,\"MeterCompany\":\"AMPY\",\"MeterType\":\"5194E\",\"MeterSummaryName\":\"AMPY_E\",\"MeterString\":\"AMP2519\",\"ReadMode\":\"0\",\"ValidationRegex\":\"^AMP\\\\\\\\d{3}[(].*[)]$\",\"SetDateTime\":false,\"NeedPassForRead\":false,\"ReadObisType\":\"Just_Obis\",\"R_Command\":\"R1\",\"MakePassAlgorithm\":null,\"Pass1\":\"KERM\",\"Pass2\":\"AZAR\",\"Pass3\":null},{\"MeterID\":30,\"MeterCompany\":\"AfzarAzma\",\"MeterType\":\"JAM300\",\"MeterSummaryName\":\"JAM_300\",\"MeterString\":\"EAA5JAM3\",\"ReadMode\":\"0\",\"ValidationRegex\":\"^[FC0-9]{1,2}[.][FC0-9]{1,2}[.]\\\\\\\\d{1,2}[(].*[)]$\",\"SetDateTime\":false,\"NeedPassForRead\":false,\"ReadObisType\":\"Just_Obis\",\"R_Command\":\"R1\",\"MakePassAlgorithm\":null,\"Pass1\":null,\"Pass2\":null,\"Pass3\":null}]";
//        List<DigitalMeters> digitalMetersList = null;
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        //Set pretty printing of json
//        objectMapper.setSerializationInclusion (JsonInclude.Include.NON_NULL);
//        objectMapper.setSerializationInclusion (JsonInclude.Include.NON_EMPTY);
//        objectMapper.setSerializationInclusion (JsonInclude.Include.NON_DEFAULT);
//
//        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//
//        //Gson gson=new Gson();
//        try {
//            digitalMetersList = objectMapper.readValue(String.valueOf(jsonArray), new TypeReference<List<DigitalMeters>>(){});
//            //Data data=gson.fromJson(String.valueOf(jsonArray),Data.class );
//            //digitalMetersList=data.digitalMeters;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//
//        for (DigitalMeters dm:digitalMetersList) {
//            digitalMetersRepo.insertDigitalMeters(dm);
//        }

        List<DigitalMeters> digitalMetersList = digitalMetersRepo.getDigitalMeters();
        int a=digitalMetersList.size();
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
                                //String ms=meterString[0].substring(1,9);
                                DigitalMeters meterInfo = GetMeterInfo(meterString[0]);

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

    private void ReadOutMeter(DigitalMeters meterInfo){
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

    private void GetP0FromMeter(DigitalMeters meterInfo){
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

    private void SendPasswordMeter(DigitalMeters meterInfo,String P0String){
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

    private void ProgrammingReadMeter(DigitalMeters meterInfo){
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

    private void CheckNoResponseOperation(int noResponseTime){
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

    private DigitalMeters GetMeterInfo(String meterString){
        String meterSummaryName="";
        List<MeterUtility.MeterStringInfo> meterStringInfos = digitalMetersRepo.getAllDigitalMetersString();
        for (MeterUtility.MeterStringInfo mui:meterStringInfos) {
            if(meterString.contains(mui.MeterString)){
                meterSummaryName=mui.MeterSummaryName;
                break;
            }
        }

        DigitalMeters meterInfo = digitalMetersRepo.getDigitalMetersByMeterSummaryName(meterSummaryName);
        return  meterInfo;
    }

    public void initTranseferLayer(){
        String BluetoothDeviceName = G.getPref(SharePrefEnum.ModuleBluetoothNameRead);
        if(BluetoothDeviceName==null) BluetoothDeviceName="";
        if(BluetoothDeviceName.equals("")) connectionStateMutableLiveData.postValue(false);
        bluetooth.disconnnect();
        bluetooth.init(BluetoothDeviceName);
    }

    public  Long insertTariffInfo(TariffInfo tariffInfo) {
            return tariffInfoRepo.insertTariffInfo(tariffInfo);
    }

    public void insertTariffDtl(TariffDtl tariffDtl){
        tariffDtlRepo.insertTariffDtl(tariffDtl);
    }

    public List<TariffAllInfo> getTariffAllInfo(Long clientId, Integer sendId){
        return tariffDtlRepo.getTariffAllInfo(clientId,sendId);
    }
    public void updateTariffInfo(TariffInfo tariffInfo){
        tariffInfoRepo.updateTariffInfo(tariffInfo);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public class Data{
        private List<DigitalMeters> digitalMeters;
    }
}
