package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.adapters.bazdid.ClientItem;
import ir.saa.android.mt.model.entities.Client;
import ir.saa.android.mt.model.entities.InspectionAllInfo;
import ir.saa.android.mt.model.entities.PolompAllInfo;
import ir.saa.android.mt.model.entities.TestAllInfo;
import ir.saa.android.mt.model.entities.TestDtl;
import ir.saa.android.mt.repositories.roomrepos.ClientRepo;
import ir.saa.android.mt.repositories.roomrepos.InspectionDtlRepo;
import ir.saa.android.mt.repositories.roomrepos.PolompDtlRepo;
import ir.saa.android.mt.repositories.roomrepos.TestDtlRepo;

public class BazdidViewModel extends AndroidViewModel {

    private ClientRepo clientRepo;
    private Boolean isTest;
    private Boolean isBazrasi;
    private Boolean isPolomp;
    private InspectionDtlRepo inspectionDtlRepo;
    private PolompDtlRepo polompDtlRepo;
    private TestDtlRepo testDtlRepo;

    public BazdidViewModel(@NonNull Application application) {
        super(application);
        if(clientRepo==null)
            clientRepo = new ClientRepo(application);
        if(polompDtlRepo==null){
            polompDtlRepo=new PolompDtlRepo(application);
        }
        if(testDtlRepo==null){
            testDtlRepo=new TestDtlRepo(application);
        }
        if(inspectionDtlRepo==null){
            inspectionDtlRepo=new InspectionDtlRepo(application);
        }
        if(isBazrasi==null){
            isBazrasi=false;
        }
        if(isTest==null){
            isTest=false;
        }
        if(isPolomp==null){
            isPolomp=false;
        }

//        Client client=new Client();
//        client.Active1=1;
//        client.Active2=2;
//        client.Name="مهدی کاظمی";
//        client.SubScript=Long.valueOf(102030);
//        client.ClientID=Long.valueOf(102030);
//        client.Address="زنجان";
//        client.SendId=326;
//        client.MeterNumActive= Long.valueOf(203040);
//        clientRepo.insertClient(client);
        //clientRepo.deleteAll();


    }

    public LiveData<List<Client>> getClientsLiveData(){

        return clientRepo.getClientsLiveData();
    }

    public LiveData<List<Client>> getClientsWithRegionIdLiveData(Integer regionId){
        return clientRepo.getClientsWithRegionIdLiveData(regionId);
    }



    public Boolean getInspectionValue(Long clientId) {
        List<InspectionAllInfo> inspectionAllInfos=new ArrayList<>();
        inspectionAllInfos= inspectionDtlRepo.getInspectionAllInfoWithClientId(clientId);
        if(inspectionAllInfos.size()!=0){
            isBazrasi=true;
        }else {
            isBazrasi=false;
        }
        return isBazrasi;
    }

    public Boolean getPolompValue(Long clientId) {
        List<PolompAllInfo> polompAllInfos=new ArrayList<>();
        polompAllInfos= polompDtlRepo.getPolompAllInfoWithClientId(clientId);
        if(polompAllInfos.size()!=0){
            isPolomp=true;
        }else{
            isPolomp=false;
        }
        return isPolomp;
    }

    public Boolean getTestValue(Long clientId) {
        List<TestAllInfo> testAllInfos=new ArrayList<>();
        testAllInfos=testDtlRepo.getTestAllInfoWithClientId(clientId);
        if(testAllInfos.size()!=0){
            isTest=true;
        }else{
            isTest=false;
        }
        return isTest;
    }


}
