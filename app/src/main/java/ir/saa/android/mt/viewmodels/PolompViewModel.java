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

import ir.saa.android.mt.adapters.polomp.PolompItem;
import ir.saa.android.mt.model.entities.Polomp;
import ir.saa.android.mt.model.entities.PolompAllInfo;
import ir.saa.android.mt.model.entities.PolompColor;
import ir.saa.android.mt.model.entities.PolompDtl;
import ir.saa.android.mt.model.entities.PolompInfo;
import ir.saa.android.mt.model.entities.PolompType;
import ir.saa.android.mt.model.entities.Setting;
import ir.saa.android.mt.repositories.roomrepos.PolompColorRepo;
import ir.saa.android.mt.repositories.roomrepos.PolompDtlRepo;
import ir.saa.android.mt.repositories.roomrepos.PolompInfoRepo;
import ir.saa.android.mt.repositories.roomrepos.PolompRepo;
import ir.saa.android.mt.repositories.roomrepos.PolompTypeRepo;
import ir.saa.android.mt.repositories.roomrepos.SettingRepo;
import ir.saa.android.mt.uicontrollers.pojos.Polomp.PolompParams;
import ir.saa.android.mt.uicontrollers.pojos.Polomp.PolompParams;

public class PolompViewModel extends AndroidViewModel {

    private MutableLiveData<List<PolompItem>> PolompItemsLiveData;
    PolompRepo polompRepo = null;
    PolompColorRepo polompColorRepo=null;
    PolompTypeRepo polompTypeRepo=null;
    PolompInfoRepo polompInfoRepo=null;
    PolompDtlRepo polompDtlRepo=null;
    SettingRepo settingRepo=null;

    public PolompViewModel(@NonNull Application application) {
        super(application);

        PolompItemsLiveData=new MutableLiveData<>();

        if(polompRepo==null){
            polompRepo=new PolompRepo(application);
        }
        if(settingRepo==null){
            settingRepo=new SettingRepo(application);
        }
        if(polompColorRepo==null){
            polompColorRepo=new PolompColorRepo(application);
        }
        if(polompTypeRepo==null){
            polompTypeRepo=new PolompTypeRepo(application);
        }
        if(polompInfoRepo==null){
            polompInfoRepo=new PolompInfoRepo(application);
        }
        if(polompDtlRepo==null){
            polompDtlRepo=new PolompDtlRepo(application);
        }
    }

    public LiveData<List<PolompItem>> getPolompList(Long ClientId){
        //initialize
        if(polompRepo.getPolomps().getValue()!=null){
            List<PolompItem> polompItems=new ArrayList<>();
            for (Polomp polomp:polompRepo.getPolomps().getValue()) {
                polompItems.add(new PolompItem(polomp.PolompID,polomp.PolompName,ClientId));

            }
            PolompItemsLiveData.postValue(polompItems);
        }

        //observe data for any changes
        polompRepo.getPolomps().observeForever(polomps -> {
            List<PolompItem> polompItems=new ArrayList<>();
            for (Polomp polomp:polomps) {
                polompItems.add(new PolompItem(polomp.PolompID,polomp.PolompName,ClientId));

            }
            PolompItemsLiveData.postValue(polompItems);
        });
        return  PolompItemsLiveData;
    }

    public LiveData<List<PolompColor>> getPolompColor(){
        return polompColorRepo.getPolompColors();
    }

    public LiveData<List<PolompType>> getPolompType(){
        return polompTypeRepo.getPolompTypes();
    }

    public Long insertPolompInfo(PolompInfo polompInfo){

        return polompInfoRepo.insertPolompInfo(polompInfo);
    }

    public Long insertPolompDtl(PolompDtl polompDtl){
        return polompDtlRepo.insertPolompDtl(polompDtl);
    }

    public LiveData<PolompAllInfo> getPolompDataLiveData(PolompParams polompParams){
        return polompDtlRepo.getPolompAllInfoLiveData(polompParams.ClientId,polompParams.PolompId);
    }

    public PolompAllInfo getPolompData(PolompParams polompParams){
        return polompDtlRepo.getPolompAllInfo(polompParams.ClientId,polompParams.PolompId);
    }

    public void deleteAllPolomp(Integer polompInfoID,Integer polompDtlId){
        polompInfoRepo.deletePolompInfoById(polompInfoID);
        polompDtlRepo.deletePolompDtl(polompDtlId);
    }

    public void updatePolompDtl(PolompDtl polompDtl){
        polompDtlRepo.updatePolompDtl(polompDtl);
    }

    public LiveData<Setting> getSettingByKey(String settingKey){
        return settingRepo.getSettingByKey(settingKey);
    }


}
