package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

import ir.saa.android.mt.adapters.bazrasi.RemarkItem;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.model.entities.AnswerGroupDtl;
import ir.saa.android.mt.model.entities.Remark;
import ir.saa.android.mt.model.entities.RemarkGroupingFormat;
import ir.saa.android.mt.repositories.roomrepos.AnswerGroupDtlRepo;
import ir.saa.android.mt.repositories.roomrepos.RemarkRepo;

public class BazrasiViewModel extends AndroidViewModel {

    private MutableLiveData<List<RemarkItem>> RemarkItemLiveData;
    private RemarkRepo remarkRepo;
    private  AnswerGroupDtlRepo answerGroupDtlRepo;

    public BazrasiViewModel(@NonNull Application application) {
        super(application);

        RemarkItemLiveData=new MutableLiveData<>();

       if(remarkRepo==null){
           remarkRepo=new RemarkRepo(application);
       }
       if(answerGroupDtlRepo==null){
           answerGroupDtlRepo=new AnswerGroupDtlRepo(application);
       }

       }

       public LiveData<List<RemarkItem>> getRemarks(Integer Id){
           if(remarkRepo.getRemarkGroupingFormat(Id,3).getValue()!=null){
               List<RemarkItem> remarkInit=new ArrayList<>();
               for (RemarkGroupingFormat remark:remarkRepo.getRemarkGroupingFormat(Id,3).getValue()) {
                   remarkInit.add(new RemarkItem(remark.RemarkID,remark.RemarkName,remark.AnswerGroupID));
               }
               RemarkItemLiveData.postValue( remarkInit);
           }

           remarkRepo.getRemarkGroupingFormat(G.clientInfo.GroupId,3).observeForever(remarks -> {
               List<RemarkItem> remarkItems=new ArrayList<>();
               for (RemarkGroupingFormat remark:remarks) {
                   remarkItems.add(new RemarkItem(remark.RemarkID,remark.RemarkName,remark.AnswerGroupID));
               }
               RemarkItemLiveData.postValue(remarkItems);
           });
           return RemarkItemLiveData;
       }

       public LiveData<List<AnswerGroupDtl>> getAnswerGroupDtls(Integer Id){
          return answerGroupDtlRepo.getAnswerGroupDtlByAnswerGroupId(Id);
       }
}
