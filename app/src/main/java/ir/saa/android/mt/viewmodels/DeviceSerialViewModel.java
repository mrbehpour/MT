package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ir.saa.android.mt.model.entities.DeviceSerial;
import ir.saa.android.mt.model.entities.IMEI_RegisterInput;
import ir.saa.android.mt.model.entities.Region;
import ir.saa.android.mt.repositories.retrofit.DataClass;
import ir.saa.android.mt.repositories.retrofit.RetrofitMT;
import ir.saa.android.mt.repositories.roomrepos.DeviceSerialRepo;
import ir.saa.android.mt.repositories.roomrepos.RegionRepo;

public class DeviceSerialViewModel extends AndroidViewModel {
    DeviceSerialRepo deviceSerialRepo=null;
    RegionRepo regionRepo=null;
    RetrofitMT retrofitMT=null;
    public MutableLiveData<Integer> IsRegisterIMEI;
    public MutableLiveData<Boolean> IsValidIMEI;
    public MutableLiveData<Boolean> IsCompleted;

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

       if(IsRegisterIMEI ==null){
           IsRegisterIMEI =new MutableLiveData<>();
       }
       if(IsValidIMEI ==null){
           IsValidIMEI =new MutableLiveData<>();
       }
       if(IsCompleted==null){
           IsCompleted=new MutableLiveData<>();
       }

    }

    public void getRegionFromServer(){
            if(retrofitMT.getMtApi()!=null) {
                retrofitMT.getMtApi().GetRegions()
                        .subscribeOn(Schedulers.io())
                        .subscribeWith(new DisposableSingleObserver<DataClass<List<Region>>>() {
                            @Override
                            public void onSuccess(DataClass<List<Region>> listDataClass) {
                                if (listDataClass.Success) {
                                    for (int i = 0; i < listDataClass.Data.size(); i++) {
                                        regionRepo.insertRegion(listDataClass.Data.get(i));
                                    }
                                }
                                IsCompleted.postValue(true);
                            }

                            @Override
                            public void onError(Throwable e) {

                                IsCompleted.postValue(false);
                            }
                        });
            }else{
                retrofitMT=RetrofitMT.getInstance();
            }

    }

    public void registerIMEI(IMEI_RegisterInput IMEIRegisterInput){
//        String add= G.getPref(SharePrefEnum.AddressServer);
//        Gson gson=new Gson();
//        String val= gson.toJson(imiRegisterInput);
        retrofitMT.getMtApi().RegisterDeviceIMEI(IMEIRegisterInput)
                 .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<DataClass<Integer>>() {
                    @Override
                    public void onSuccess(DataClass<Integer> integerDataClass) {

                        DeviceSerial deviceSerial=new DeviceSerial();
                        deviceSerial.isActive=false;
                        deviceSerial.regionId= IMEIRegisterInput.regionId;
                        deviceSerial.SerialId= IMEIRegisterInput.handHeldSerial;
                        deviceSerialRepo.insertDeviceSerial(deviceSerial);

                        if(integerDataClass.Success && integerDataClass.Data==0){
                            //Save And Wait For Confirm
                            IsRegisterIMEI.postValue(0);
                        }else{
                            if (integerDataClass.Data>0) {
                                //Save Before
                                IsRegisterIMEI.postValue(1);
                            }else{
                                //Not Saved
                                IsRegisterIMEI.postValue(2);
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                      String s=e.getMessage();
                    }
                });
    }

    public void confirmIMEI(IMEI_RegisterInput IMEIRegisterInput) {
        retrofitMT.getMtApi().GetIMEIConfirmStatus(IMEIRegisterInput)
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<DataClass<Boolean>>() {
                    @Override
                    public void onSuccess(DataClass<Boolean> booleanDataClass) {
                        if(booleanDataClass.Success && booleanDataClass.Data){

                            DeviceSerial deviceSerial=new DeviceSerial();
                            deviceSerial.isActive=true;
                            deviceSerial.SerialId= IMEIRegisterInput.handHeldSerial;
                            deviceSerial.regionId= IMEIRegisterInput.regionId;
                            deviceSerialRepo.updateDeviceSerial(deviceSerial);
                            IsValidIMEI.postValue(true);

                        }else{
                            IsValidIMEI.postValue(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        String a = e.getMessage();
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
