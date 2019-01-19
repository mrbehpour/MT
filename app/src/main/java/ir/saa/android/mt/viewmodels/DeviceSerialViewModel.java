package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.SharePrefEnum;
import ir.saa.android.mt.model.entities.DeviceSerial;
import ir.saa.android.mt.model.entities.ImiRegisterInput;
import ir.saa.android.mt.model.entities.Region;
import ir.saa.android.mt.repositories.retrofit.DataClass;
import ir.saa.android.mt.repositories.retrofit.RetrofitMT;
import ir.saa.android.mt.repositories.roomrepos.DeviceSerialRepo;
import ir.saa.android.mt.repositories.roomrepos.RegionRepo;

public class DeviceSerialViewModel extends AndroidViewModel {
    DeviceSerialRepo deviceSerialRepo=null;
    RegionRepo regionRepo=null;
    RetrofitMT retrofitMT=null;
    public MutableLiveData<Boolean> IsRegisterImi;
    public MutableLiveData<Boolean> IsValidImi;

    public DeviceSerialViewModel(@NonNull Application application) {
        super(application);

        if(retrofitMT==null){
            retrofitMT=RetrofitMT.getInstance();
        }
        if(regionRepo==null){
            regionRepo=new RegionRepo(application);
       }
       if(deviceSerialRepo==null){
            deviceSerialRepo=new DeviceSerialRepo(application);
       }

       if(IsRegisterImi==null){
           IsRegisterImi=new MutableLiveData<>();
       }
       if(IsValidImi==null){
           IsValidImi=new MutableLiveData<>();
       }


    }

    public void getRegionFromServer(){
        retrofitMT.getMtApi().GetRegions()
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<DataClass<List<Region>>>() {
                    @Override
                    public void onSuccess(DataClass<List<Region>> regionDataClass) {
                        if(regionDataClass.Success){
                            for(int i=0;i<regionDataClass.Data.size();i++){
                                regionRepo.insertRegion(regionDataClass.Data.get(i));
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        String s=e.getMessage();
                    }
                });
    }

    public void registerImi(ImiRegisterInput imiRegisterInput){
//        String add= G.getPref(SharePrefEnum.AddressServer);
//        Gson gson=new Gson();
//        String val= gson.toJson(imiRegisterInput);
        retrofitMT.getMtApi().RegisterDeviceImi(imiRegisterInput)
                 .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<DataClass<Integer>>() {
                    @Override
                    public void onSuccess(DataClass<Integer> integerDataClass) {
                        if(integerDataClass.Success && integerDataClass.Data==0){
                            DeviceSerial deviceSerial=new DeviceSerial();
                            deviceSerial.isActive=false;
                            deviceSerial.regionId=imiRegisterInput.regionId;
                            deviceSerial.SerialId=imiRegisterInput.handHeldSerial;
                            deviceSerialRepo.insertDeviceSerial(deviceSerial);
                            IsRegisterImi.postValue(true);
                        }else if (integerDataClass.Success==false && integerDataClass.Data>0){
                            DeviceSerial deviceSerial=new DeviceSerial();
                            deviceSerial.isActive=false;
                            deviceSerial.regionId=imiRegisterInput.regionId;
                            deviceSerial.SerialId=imiRegisterInput.handHeldSerial;
                            deviceSerialRepo.insertDeviceSerial(deviceSerial);
                            IsRegisterImi.postValue(true);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                      String s=e.getMessage();
                    }
                });
    }

    public void confirmImi(ImiRegisterInput imiRegisterInput) {
        retrofitMT.getMtApi().GetImiConfirmStatus(imiRegisterInput)
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<DataClass<Boolean>>() {
                    @Override
                    public void onSuccess(DataClass<Boolean> booleanDataClass) {
                        if(booleanDataClass.Success && booleanDataClass.Data){
                            IsValidImi.postValue(true);
                            DeviceSerial deviceSerial=new DeviceSerial();
                            deviceSerial.isActive=true;
                            deviceSerial.SerialId=imiRegisterInput.handHeldSerial;
                            deviceSerial.regionId=imiRegisterInput.regionId;
                            deviceSerialRepo.updateDeviceSerial(deviceSerial);

                        }else{
                            IsValidImi.postValue(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }
    public LiveData<DeviceSerial> getDeviceSerialLiveData(String SerialID){
        return deviceSerialRepo.getDeviceSerialByIdLiveData(SerialID);
    }

    public DeviceSerial getDeviceSerial(String SerialID){
        return deviceSerialRepo.getDeviceSerialById(SerialID);
    }

    public LiveData<List<Region>> getRegion(){
        return regionRepo.getRegions();
    }



}
