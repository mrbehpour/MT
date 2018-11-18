package ir.saa.android.mt.viewmodels;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.model.entities.AddedClient;
import ir.saa.android.mt.model.entities.Client;
import ir.saa.android.mt.model.entities.ClientAllInfo;
import ir.saa.android.mt.model.entities.GPSInfo;
import ir.saa.android.mt.model.entities.InspectionAllInfo;
import ir.saa.android.mt.model.entities.InspectionDtl;
import ir.saa.android.mt.model.entities.InspectionInfo;
import ir.saa.android.mt.model.entities.MeterChangeDtl;
import ir.saa.android.mt.model.entities.MeterChangeInfo;
import ir.saa.android.mt.model.entities.PolompAllInfo;
import ir.saa.android.mt.model.entities.PolompDtl;
import ir.saa.android.mt.model.entities.PolompInfo;
import ir.saa.android.mt.model.entities.RecordeSummary;
import ir.saa.android.mt.model.entities.TariffDtl;
import ir.saa.android.mt.model.entities.TariffInfo;
import ir.saa.android.mt.model.entities.TestDtl;
import ir.saa.android.mt.model.entities.TestInfo;
import ir.saa.android.mt.repositories.retrofit.RetrofitMT;
import ir.saa.android.mt.repositories.roomrepos.ClientRepo;
import ir.saa.android.mt.repositories.roomrepos.InspectionDtlRepo;
import ir.saa.android.mt.repositories.roomrepos.InspectionInfoRepo;
import ir.saa.android.mt.repositories.roomrepos.PolompDtlRepo;
import ir.saa.android.mt.repositories.roomrepos.PolompInfoRepo;
import ir.saa.android.mt.repositories.roomrepos.TestDtlRepo;
import ir.saa.android.mt.repositories.roomrepos.TestInfoRepo;

public class SendViewModel extends AndroidViewModel {

    RetrofitMT retrofitMT=null;
    PolompInfoRepo polompInfoRepo=null;
    PolompDtlRepo polompDtlRepo=null;
    InspectionInfoRepo inspectionInfoRepo=null;
    InspectionDtlRepo inspectionDtlRepo=null;
    TestDtlRepo testDtlRepo=null;
    TestInfoRepo testInfoRepo=null;
    ClientRepo clientRepo=null;
   public MutableLiveData<Integer> sendAllDataProgress=new MutableLiveData<>();
    public MutableLiveData<String> messageErrorLiveData=new MutableLiveData<>();

    public SendViewModel(@NonNull Application application) {
        super(application);

        if(retrofitMT==null){
            retrofitMT=RetrofitMT.getInstance();
        }
        if(polompDtlRepo==null){
            polompDtlRepo=new PolompDtlRepo(application);
        }
        if(polompInfoRepo==null){
            polompInfoRepo=new PolompInfoRepo(application);
        }
        if(inspectionInfoRepo==null){
            inspectionInfoRepo=new InspectionInfoRepo(application);
        }
        if(inspectionDtlRepo==null){
            inspectionDtlRepo=new InspectionDtlRepo(application);
        }
        if(testInfoRepo==null){
            testInfoRepo=new TestInfoRepo(application);
        }
        if(testDtlRepo==null){
            testDtlRepo=new TestDtlRepo(application);
        }
        if(clientRepo==null){
            clientRepo=new ClientRepo(application);
        }

        if(sendAllDataProgress==null){
            sendAllDataProgress.setValue(0);
        }
        if(messageErrorLiveData==null)
            messageErrorLiveData.setValue("");
    }

    @SuppressLint("CheckResult")
    public void sendData(){
        ArrayList<ClientAllInfo> clientinfolList =new ArrayList<>();
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                List<Client> clientList=new ArrayList<>();
                clientList=clientRepo.getClients();

                for (Integer i=0;i<clientList.size();i++){

                    ClientAllInfo ClientAllInfo=new ClientAllInfo();
//                    ClientAllInfo.AddedClient=new AddedClient();
//                    ClientAllInfo.TestDtlsList=new ArrayList<List<TestDtl>>() ;
//                    ClientAllInfo.RecordStringInfo="";
//                    ClientAllInfo.TestInfos=new ArrayList<TestInfo>();
//                    ClientAllInfo.InspectionDtls=new ArrayList<InspectionDtl>();
//                    ClientAllInfo.InspectionInfo=new InspectionInfo();
//                    ClientAllInfo.PolompInfo=new PolompInfo();
//                    ClientAllInfo.PolompDtls=new ArrayList<PolompDtl>();
//                    ClientAllInfo.MeterChangeInfo=new MeterChangeInfo();
//                    ClientAllInfo.MeterChangeDtls=new ArrayList<MeterChangeDtl>();
//                    ClientAllInfo.TariffInfo=new TariffInfo();
//                    ClientAllInfo.TariffDtls=new ArrayList<TariffDtl>();
//
//                    ClientAllInfo.GpsInfo=new GPSInfo();
                    ClientAllInfo.Client=clientList.get(i);
                    ClientAllInfo.Id=i;
                    ClientAllInfo.SendId= clientList.get(i).SendId;
                    //-----Inspection Send
                    List<InspectionAllInfo> inspectionAllInfos=inspectionDtlRepo.getInspectionAllInfoWithSendId(clientList.get(i).ClientID
                            ,clientList.get(i).SendId);
                    //Last InspectionInfo
                    Integer LastItem=inspectionAllInfos.size()-1;
                    if(inspectionAllInfos.size()!=0) {
                        InspectionInfo inspectionInfo=new InspectionInfo();
                        inspectionInfo.InspectionInfoID=null;
                        //inspectionInfo.RemarkID=inspectionAllInfos.get(0).RemarkID;
                        inspectionInfo.InspectionTime=inspectionAllInfos.get(LastItem).InspectionTime;
                        inspectionInfo.InspectionDate=inspectionAllInfos.get(LastItem).InspectionDate;
                        inspectionInfo.SendID=inspectionAllInfos.get(LastItem).SendID;
                        inspectionInfo.ClientID=inspectionAllInfos.get(LastItem).ClientID;
                        inspectionInfo.AgentID=inspectionAllInfos.get(LastItem).AgentID;
                        //inspectionInfo.RemarkID=inspectionAllInfos.get(0).RemarkID;
                        inspectionInfo.FollowUpCode=inspectionAllInfos.get(0).FollowUpCode;
                        inspectionInfo.BlockID=null;
                        ClientAllInfo.InspectionInfo =inspectionInfo;
                        ClientAllInfo.InspectionDtls=new ArrayList<>();
                        for (InspectionAllInfo inspectionAllInfo:inspectionAllInfos) {
                            InspectionDtl inspectionDtl=new InspectionDtl();
                            inspectionDtl.InspectionDtlID=null;
                            inspectionDtl.InspectionInfoID=null;
                            inspectionDtl.ReadTypeID=inspectionAllInfo.ReadTypeID;
                            inspectionDtl.RemarkValue=inspectionAllInfo.RemarkValue;
                            inspectionDtl.RemarkID=inspectionAllInfo.RemarkID;
                            inspectionDtl.AgentID=inspectionAllInfo.AgentID;

                            ClientAllInfo.InspectionDtls.add(inspectionDtl);

                        }
                    }

                    //-----Polomp Send
                    List<PolompAllInfo> polompAllInfos=polompDtlRepo.getPolompAllInfoWithSendId(clientList.get(i).ClientID
                            ,clientList.get(i).SendId);
                    //last polompInfo
                     LastItem=polompAllInfos.size()-1;
                    if(polompAllInfos.size()!=0){
                        PolompInfo polompInfo=new PolompInfo();

                        polompInfo.SendID=polompAllInfos.get(LastItem).SendID;
                        polompInfo.ClientID=polompAllInfos.get(LastItem).ClientID;
                        polompInfo.AgentID=polompAllInfos.get(LastItem).AgentID;
                        polompInfo.ChangeTime=polompAllInfos.get(LastItem).ChangeTime;
                        polompInfo.ChangeDate=polompAllInfos.get(LastItem).ChangeDate;
                        polompInfo.FollowUpCode=polompAllInfos.get(LastItem).FollowUpCode;
                        polompInfo.PolompInfoID=null;
                        ClientAllInfo.PolompInfo=polompInfo;
                        ClientAllInfo.PolompDtls=new ArrayList<>();
                        for (PolompAllInfo polompAllInfo:polompAllInfos) {
                            PolompDtl polompDtl=new PolompDtl();
                            polompDtl.CurrentPolomp=polompAllInfo.CurrentPolomp;
                            polompDtl.PolompTypeID=polompAllInfo.PolompTypeID;
                            polompDtl.PreviousColorID=polompAllInfo.PreviousColorID;
                            polompDtl.PreviousPolompTypeID=polompAllInfo.PreviousPolompTypeID;
                            polompDtl.CurrentColorID=polompAllInfo.CurrentColorID;
                            polompDtl.PolompDtlID=null;
                            polompDtl.PolompInfoID=null;
                            polompDtl.PolompID=polompAllInfo.PolompID;
                            polompDtl.AgentID=polompAllInfo.AgentID;
                            polompDtl.PreviousPolomp=polompAllInfo.PreviousPolomp;
                            polompDtl.ReadTypeID=polompAllInfo.ReadTypeID;
                            polompDtl.StatePolomp=polompAllInfo.StatePolomp;
                            polompDtl.IsDuplicated=false;

                            ClientAllInfo.PolompDtls.add(polompDtl);
                        }

                    }
//                    //------------Test
                    List<TestInfo> testInfos=testInfoRepo.getTestInfoWithClientId(clientList.get(i).ClientID,clientList.get(i).SendId);
                    ClientAllInfo.TestInfos=new ArrayList<>();
                    ClientAllInfo.TestDtlsList=new ArrayList<>();
                    for (TestInfo testInfo:testInfos) {


                        List<TestDtl> testDtls=new ArrayList<>();
                        for (TestDtl testDtl:testDtlRepo.getTestDtlByTestInfoId(testInfo.TestInfoID)){
                            testDtl.TestInfoID=null;
                            testDtl.TestDtlID = null;
                            Changevalue(testDtl);
                            testDtls.add(testDtl);
                        }
                        testInfo.TestInfoID=null;
                        ClientAllInfo.TestInfos.add(testInfo);

                        ClientAllInfo.TestDtlsList.add(testDtls);
                    }
                    if(testInfos.size()!=0 || inspectionAllInfos.size()!=0 || polompAllInfos.size()!=0){
                        clientinfolList.add(ClientAllInfo);

                    }

                    sendAllDataProgress.postValue(getPrecent(i+1,clientList.size())-1);

                }
                
                if(clientinfolList.size()!=0){
                   //Gson gson = new Gson();
                    //String val=gson.toJson(clientinfolList);
                    retrofitMT.getMtApi().SaveClientAllInfoAndroid(clientinfolList)
                            .subscribeOn(Schedulers.io())
                            .subscribeWith(new SingleObserver<List<RecordeSummary>>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }


                                @SuppressLint("StringFormatMatches")
                                @Override
                                public void onSuccess(List<RecordeSummary> recordeSummaries) {
                                    String ErrorMessageSite="";

                                    Integer countTrue=0;
                                    for(RecordeSummary recordeSummary:recordeSummaries){
                                        if(recordeSummary.Result){
                                            countTrue++;
                                        }else {
                                            ErrorMessageSite=recordeSummary.Description;
                                        }
                                    }
                                    if(recordeSummaries.size()==countTrue){
                                        sendAllDataProgress.postValue(100);
                                        messageErrorLiveData.postValue((String) G.context.getResources().getText(R.string.MessageSuccess));
                                    }else {
                                        sendAllDataProgress.postValue(-1);
                                        Integer countRegister=recordeSummaries.size()-countTrue;
                                        messageErrorLiveData.postValue(G.context.getResources().getString(R.string.MessageCount,countRegister.toString(),String.valueOf(recordeSummaries.size())));

                                    }
                                }

                                @Override
                                public void onError(Throwable e) {

                                    String error=e.getMessage();
                                    sendAllDataProgress.postValue(-1);
                                    messageErrorLiveData.postValue(e.getMessage());
                                }
                            });
                }

            }

            private void Changevalue(TestDtl testDtl) {
                switch (testDtl.TestID){
                    case 13:
                        testDtl.TestValue=String.format("%.3f",Double.parseDouble(testDtl.TestValue)) ;
                        break;
                    case 14:
                        testDtl.TestValue=String.format("%.3f",Double.parseDouble(testDtl.TestValue)) ;
                        break;
                    case 15:
                        testDtl.TestValue=String.format("%.3f",Double.parseDouble(testDtl.TestValue)) ;
                        break;
                    case 41:
                        testDtl.TestValue=String.format("%.2f",Double.parseDouble(testDtl.TestValue)) ;
                        break;
                    case 10:
                        testDtl.TestValue=String.format("%.2f",Double.parseDouble(testDtl.TestValue)) ;
                        break;
                    case 11:
                        testDtl.TestValue=String.format("%.2f",Double.parseDouble(testDtl.TestValue)) ;
                        break;
                    case 12:
                        testDtl.TestValue=String.format("%.2f",Double.parseDouble(testDtl.TestValue)) ;
                        break;
                    case 16:
                        testDtl.TestValue=String.format("%.2f",Double.parseDouble(testDtl.TestValue)) ;
                        break;
                    case 17:
                        testDtl.TestValue=String.format("%.2f",Double.parseDouble(testDtl.TestValue)) ;
                        break;
                    case 18:
                        testDtl.TestValue=String.format("%.2f",Double.parseDouble(testDtl.TestValue)) ;
                        break;
                    case 40:
                        testDtl.TestValue=String.format("%.2f",Double.parseDouble(testDtl.TestValue)) ;
                        break;

                }
            }
        }).subscribeOn(Schedulers.io())
                .subscribeWith(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });


    }
    private int getPrecent(int progress,int totalCount){
        return (progress * 100)/totalCount;
    }
}
