package ir.saa.android.mt.viewmodels;

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
    }

    public void sendData(){
        ArrayList<ClientAllInfo> clientinfolList =new ArrayList<>();
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                List<Client> clientList=new ArrayList<>();
                clientList=clientRepo.getClients();

                for (Integer i=0;i<clientList.size();i++){

                    ClientAllInfo ClientAllInfo=new ClientAllInfo();
                    ClientAllInfo.AddedClient=new AddedClient();
                    ClientAllInfo.TestDtlsList=new ArrayList<List<TestDtl>>() ;
                    ClientAllInfo.RecordStringInfo="";
                    ClientAllInfo.TestInfos=new ArrayList<TestInfo>();
                    ClientAllInfo.InspectionDtls=new ArrayList<InspectionDtl>();
                    ClientAllInfo.InspectionInfo=new InspectionInfo();
                    ClientAllInfo.PolompInfo=new PolompInfo();
                    ClientAllInfo.PolompDtls=new ArrayList<PolompDtl>();
                    ClientAllInfo.MeterChangeInfo=new MeterChangeInfo();
                    ClientAllInfo.MeterChangeDtls=new ArrayList<MeterChangeDtl>();
                    ClientAllInfo.TariffInfo=new TariffInfo();
                    ClientAllInfo.TariffDtls=new ArrayList<TariffDtl>();

                    ClientAllInfo.GpsInfo=new GPSInfo();
                    ClientAllInfo.Client=clientList.get(i);
                    ClientAllInfo.Id=i;
                    //-----Inspection Send
                    List<InspectionAllInfo> inspectionAllInfos=inspectionDtlRepo.getInspectionAllInfoWithSendId(clientList.get(i).ClientID
                            ,clientList.get(i).SendId);


                    if(inspectionAllInfos.size()!=0) {
                        InspectionInfo inspectionInfo=new InspectionInfo();
                        inspectionInfo.InspectionInfoID=inspectionAllInfos.get(0).InspectionInfoID;
                        inspectionInfo.RemarkID=inspectionAllInfos.get(0).RemarkID;
                        inspectionInfo.InspectionTime=inspectionAllInfos.get(0).InspectionTime;
                        inspectionInfo.InspectionDate=inspectionAllInfos.get(0).InspectionDate;
                        inspectionInfo.SendID=inspectionAllInfos.get(0).SendID;
                        inspectionInfo.ClientID=inspectionAllInfos.get(0).ClientID;
                        inspectionInfo.AgentID=inspectionAllInfos.get(0).AgentID;
                        inspectionInfo.RemarkID=inspectionAllInfos.get(0).RemarkID;
                        ClientAllInfo.InspectionInfo =inspectionInfo;
                        for (InspectionAllInfo inspectionAllInfo:inspectionAllInfos) {
                            InspectionDtl inspectionDtl=new InspectionDtl();
                            inspectionDtl.InspectionDtlID=inspectionAllInfo.InspectionDtlID;
                            inspectionDtl.InspectionInfoID=inspectionAllInfo.InspectionInfoID;
                            inspectionDtl.ReadTypeID=inspectionAllInfo.ReadTypeID;
                            inspectionDtl.RemarkValue=inspectionAllInfo.RemarkValue;
                            inspectionDtl.RemarkID=inspectionAllInfo.RemarkID;
                            ClientAllInfo.InspectionDtls=new ArrayList<>();
                            ClientAllInfo.InspectionDtls.add(inspectionDtl);

                        }
                    }

                    //-----Polomp Send
                    List<PolompAllInfo> polompAllInfos=polompDtlRepo.getPolompAllInfoWithSendId(clientList.get(i).ClientID
                            ,clientList.get(i).SendId);
                    if(polompAllInfos.size()!=0){
                        PolompInfo polompInfo=new PolompInfo();

                        polompInfo.SendID=polompAllInfos.get(0).SendID;
                        polompInfo.ClientID=polompAllInfos.get(0).ClientID;
                        polompInfo.AgentID=polompAllInfos.get(0).AgentID;
                        polompInfo.ChangeTime=polompAllInfos.get(0).ChangeTime;
                        polompInfo.ChangeDate=polompAllInfos.get(0).ChangeDate;
                        polompInfo.PolompInfoID=polompAllInfos.get(0).PolompInfoID;
                        ClientAllInfo.PolompInfo=polompInfo;
                        for (PolompAllInfo polompAllInfo:polompAllInfos) {
                            PolompDtl polompDtl=new PolompDtl();
                            polompDtl.CurrentPolomp=polompAllInfo.CurrentPolomp;
                            polompDtl.PolompTypeID=polompAllInfo.PolompTypeID;
                            polompDtl.PreviousColorID=polompAllInfo.PreviousColorID;
                            polompDtl.PreviousPolompTypeID=polompAllInfo.PreviousPolompTypeID;
                            polompDtl.CurrentColorID=polompAllInfo.CurrentColorID;
                            polompDtl.PolompDtlID=polompAllInfo.PolompDtlID;
                            polompDtl.PolompInfoID=Long.valueOf(polompAllInfo.PolompInfoID);
                            polompDtl.PolompID=polompAllInfo.PolompID;
                            polompDtl.PreviousPolomp=polompAllInfo.PreviousPolomp;
                            polompDtl.ReadTypeID=polompAllInfo.ReadTypeID;
                            polompDtl.IsDuplicated=false;
                            ClientAllInfo.PolompDtls=new ArrayList<>();
                            ClientAllInfo.PolompDtls.add(polompDtl);
                        }

                    }
//                    //------------Test
                    List<TestInfo> testInfos=testInfoRepo.getTestInfoWithClientId(clientList.get(i).ClientID,clientList.get(i).SendId);
                    for (TestInfo testInfo:testInfos) {
                        ClientAllInfo.TestInfos=new ArrayList<>();
                        ClientAllInfo.TestInfos.add(testInfo);
                        List<TestDtl> testDtls=new ArrayList<>();
                        for (TestDtl testDtl:testDtlRepo.getTestDtlByTestInfoId(testInfo.TestInfoID)){
                            testDtls.add(testDtl);
                        }
                        ClientAllInfo.TestDtlsList=new ArrayList<>();
                        ClientAllInfo.TestDtlsList.add(testDtls);
                    }
                    if(testInfos.size()!=0 || inspectionAllInfos.size()!=0 || polompAllInfos.size()!=0){
                        clientinfolList.add(ClientAllInfo);

                    }

                    sendAllDataProgress.postValue(getPrecent(i+1,clientList.size()));

                }
                
                if(clientinfolList.size()!=0){
                    Gson gson = new GsonBuilder().create();
                    JsonArray myCustomArray = gson.toJsonTree(clientinfolList).getAsJsonArray();
                    retrofitMT.getMtApi().SaveClientAllInfoAndroid(clientinfolList)
                            .subscribeOn(Schedulers.io())
                            .subscribeWith(new SingleObserver<List<RecordeSummary>>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onSuccess(List<RecordeSummary> recordeSummaries) {
                                    String s=recordeSummaries.get(0).Description;
                                    sendAllDataProgress.postValue(100);
                                }

                                @Override
                                public void onError(Throwable e) {

                                    String error=e.getMessage();
                                    sendAllDataProgress.postValue(-1);
                                }
                            });
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
