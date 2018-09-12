package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import ir.saa.android.mt.model.entities.Client;
import ir.saa.android.mt.model.entities.ClientAllInfo;
import ir.saa.android.mt.model.entities.InspectionAllInfo;
import ir.saa.android.mt.model.entities.InspectionDtl;
import ir.saa.android.mt.model.entities.InspectionInfo;
import ir.saa.android.mt.model.entities.PolompAllInfo;
import ir.saa.android.mt.model.entities.PolompDtl;
import ir.saa.android.mt.model.entities.PolompInfo;
import ir.saa.android.mt.model.entities.RecordeSummary;
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
        ArrayList<ClientAllInfo> clientAllInfos=new ArrayList<>();
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                List<Client> clientList=new ArrayList<>();
                clientList=clientRepo.getClients();

                for (Integer i=0;i<clientList.size();i++){
                    ClientAllInfo clientAllInfo=new ClientAllInfo();
                    clientAllInfo.Client=clientList.get(i);
                    //-----Inspection Send
                    List<InspectionAllInfo> inspectionAllInfos=inspectionDtlRepo.getInspectionAllInfoWithSendId(clientList.get(i).ClientID
                            ,clientList.get(i).SendId);
                    InspectionInfo inspectionInfo=new InspectionInfo();

                    if(inspectionAllInfos.size()!=0) {
                        inspectionInfo.InspectionInfoID=inspectionAllInfos.get(0).InspectionInfoID;
                        inspectionInfo.RemarkID=inspectionAllInfos.get(0).RemarkID;
                        inspectionInfo.InspectionTime=inspectionAllInfos.get(0).InspectionTime;
                        inspectionInfo.InspectionDate=inspectionAllInfos.get(0).InspectionDate;
                        inspectionInfo.SendID=inspectionAllInfos.get(0).SendID;
                        inspectionInfo.ClientID=inspectionAllInfos.get(0).ClientID;
                        inspectionInfo.AgentID=inspectionAllInfos.get(0).AgentID;
                        inspectionInfo.RemarkID=inspectionAllInfos.get(0).RemarkID;
                        clientAllInfo.InspectionInfo =inspectionInfo;
                        for (InspectionAllInfo inspectionAllInfo:inspectionAllInfos) {
                            InspectionDtl inspectionDtl=new InspectionDtl();
                            inspectionDtl.InspectionDtlID=inspectionAllInfo.InspectionDtlID;
                            inspectionDtl.InspectionInfoID=inspectionAllInfo.InspectionInfoID;
                            inspectionDtl.ReadTypeID=inspectionAllInfo.ReadTypeID;
                            inspectionDtl.RemarkValue=inspectionAllInfo.RemarkValue;
                            inspectionDtl.RemarkID=inspectionAllInfo.RemarkID;
                            clientAllInfo.InspectionDtls=new ArrayList<>();
                            clientAllInfo.InspectionDtls.add(inspectionDtl);

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
                        clientAllInfo.PolompInfo=polompInfo;
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
                            clientAllInfo.PolompDtls=new ArrayList<>();
                            clientAllInfo.PolompDtls.add(polompDtl);
                        }
                    }
                    clientAllInfos.add(clientAllInfo);
                    sendAllDataProgress.postValue(getPrecent(i+1,clientList.size()));
                }
                if(clientAllInfos.size()!=0){
                    retrofitMT.getMtApi().SaveClientAllInfoAndroid(clientAllInfos)
                            .subscribeOn(Schedulers.io())
                            .subscribeWith(new SingleObserver<List<RecordeSummary>>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onSuccess(List<RecordeSummary> recordeSummaries) {
                                    String s=recordeSummaries.get(0).Description;
                                }

                                @Override
                                public void onError(Throwable e) {
                                    String error=e.getMessage();
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
