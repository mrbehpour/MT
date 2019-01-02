package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import ir.saa.android.mt.model.entities.Region;
import ir.saa.android.mt.repositories.retrofit.DataClass;
import ir.saa.android.mt.repositories.retrofit.RetrofitMT;
import ir.saa.android.mt.repositories.roomrepos.DeviceSerialRepo;
import ir.saa.android.mt.repositories.roomrepos.RegionRepo;

public class DeviceSerialViewModel extends AndroidViewModel {
    DeviceSerialRepo deviceSerialRepo=null;
    RegionRepo regionRepo=null;
    RetrofitMT retrofitMT=null;

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


    }

    public void getRegionFromServer(){
        retrofitMT.getMtApi().GetRegions()
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<DataClass<Region>>() {
                    @Override
                    public void onSuccess(DataClass<Region> regionDataClass) {
                        if(regionDataClass.Success){
                            for(int i=0;i<regionDataClass.Data.size();i++){
                                regionRepo.insertRegion(regionDataClass.Data.get(i));
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
    public LiveData<List<Region>> getRegion(){
        return regionRepo.getRegions();
    }



}
