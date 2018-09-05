package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ir.saa.android.mt.adapters.bazrasi.RemarkItem;
import ir.saa.android.mt.model.entities.Remark;
import ir.saa.android.mt.repositories.roomrepos.RemarkRepo;

public class BazrasiViewModel extends AndroidViewModel {

    private MutableLiveData<List<RemarkItem>> RemarkItemLiveData;
    private RemarkRepo remarkRepo;

    public BazrasiViewModel(@NonNull Application application) {
        super(application);

        RemarkItemLiveData=new MutableLiveData<>();

       if(remarkRepo==null){
           remarkRepo=new RemarkRepo(application);
       }

       }

       public LiveData<List<RemarkItem>> getRemarks(){
           if(remarkRepo.getRemarks().getValue()!=null){
               List<RemarkItem> remarkInit=new ArrayList<>();
               for (Remark remark:remarkRepo.getRemarks().getValue()) {
                   remarkInit.add(new RemarkItem(remark.RemarkID,remark.RemarkName));
               }
               RemarkItemLiveData.postValue( remarkInit);
           }

           remarkRepo.getRemarks().observeForever(remarks -> {
               List<RemarkItem> remarkItems=new ArrayList<>();
               for (Remark remark:remarks) {
                   remarkItems.add(new RemarkItem(remark.RemarkID,remark.RemarkName));
               }
               RemarkItemLiveData.postValue(remarkItems);
           });
           return RemarkItemLiveData;
       }
}
