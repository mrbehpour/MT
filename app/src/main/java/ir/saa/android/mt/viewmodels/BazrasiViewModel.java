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
import ir.saa.android.mt.model.entities.InspectionAllInfo;
import ir.saa.android.mt.model.entities.InspectionDtl;
import ir.saa.android.mt.model.entities.InspectionInfo;
import ir.saa.android.mt.model.entities.Remark;
import ir.saa.android.mt.model.entities.RemarkGroupingFormat;
import ir.saa.android.mt.repositories.roomrepos.AnswerGroupDtlRepo;
import ir.saa.android.mt.repositories.roomrepos.InspectionDtlRepo;
import ir.saa.android.mt.repositories.roomrepos.InspectionInfoRepo;
import ir.saa.android.mt.repositories.roomrepos.RemarkRepo;

public class BazrasiViewModel extends AndroidViewModel {

    private MutableLiveData<List<RemarkItem>> RemarkItemLiveData;
    private RemarkRepo remarkRepo;
    private  AnswerGroupDtlRepo answerGroupDtlRepo;
    private InspectionInfoRepo inspectionInfoRepo;
    private InspectionDtlRepo inspectionDtlRepo;

    public BazrasiViewModel(@NonNull Application application) {
        super(application);

        RemarkItemLiveData=new MutableLiveData<>();

       if(remarkRepo==null){
           remarkRepo=new RemarkRepo(application);
       }
       if(answerGroupDtlRepo==null){
           answerGroupDtlRepo=new AnswerGroupDtlRepo(application);
       }
       if(inspectionInfoRepo==null){
           inspectionInfoRepo=new InspectionInfoRepo(application);
       }
       if(inspectionDtlRepo==null){
           inspectionDtlRepo=new InspectionDtlRepo(application);
       }

//       inspectionInfoRepo.deleteAll();
//       inspectionDtlRepo.deleteAll();;
       }

       public LiveData<List<RemarkItem>> getRemarks(Integer Id){

           if(remarkRepo.getRemarkGroupingFormat(Id,3).getValue()!=null){
               List<RemarkItem> remarkInit=new ArrayList<>();
               for (RemarkGroupingFormat remark:remarkRepo.getRemarkGroupingFormat(Id,3).getValue()) {
                   InspectionAllInfo inspectionAllInfo1=inspectionDtlRepo.getInspectionAllInfo(G.clientInfo.ClientId,remark.RemarkID);
                   if(inspectionAllInfo1==null){
                       remarkInit.add(new RemarkItem(remark.RemarkID,remark.RemarkName,remark.AnswerGroupID,"-1"));
                   }else{
                       remarkInit.add(new RemarkItem(remark.RemarkID,remark.RemarkName,remark.AnswerGroupID,inspectionAllInfo1.RemarkValue));
                   }
               }
               RemarkItemLiveData.postValue( remarkInit);
           }

           remarkRepo.getRemarkGroupingFormat(G.clientInfo.GroupId,3).observeForever(remarks -> {
               List<RemarkItem> remarkItems=new ArrayList<>();
               InspectionAllInfo inspectionAllInfo=new InspectionAllInfo();
               for (RemarkGroupingFormat remark:remarks) {
                   InspectionAllInfo inspectionAllInfo1=inspectionDtlRepo.getInspectionAllInfo(G.clientInfo.ClientId,remark.RemarkID);
                   if(inspectionAllInfo1==null){
                       remarkItems.add(new RemarkItem(remark.RemarkID,remark.RemarkName,remark.AnswerGroupID,"-1"));
                   }else{
                       remarkItems.add(new RemarkItem(remark.RemarkID,remark.RemarkName,remark.AnswerGroupID,inspectionAllInfo1.RemarkValue));
                   }

               }
               RemarkItemLiveData.postValue(remarkItems);
           });
           return RemarkItemLiveData;
       }

       public LiveData<List<AnswerGroupDtl>> getAnswerGroupDtls(Integer Id){
          return answerGroupDtlRepo.getAnswerGroupDtlByAnswerGroupId(Id);
       }

       public Long insertInspectionInfo(InspectionInfo inspectionInfo){

            return  inspectionInfoRepo.insertInspectionInfo(inspectionInfo);
       }
       public long insertInspectionDtl(InspectionDtl inspectionDtl){

            return inspectionDtlRepo.insertInspectionDtl(inspectionDtl);
       }

       public int updateInspectionDtl(InspectionDtl inspectionDtl){
        return  inspectionDtlRepo.updateInspectionDtl(inspectionDtl);
       }
       public int updateInspectionInfo(InspectionInfo inspectionInfo){
        return  inspectionInfoRepo.updateInspectionInfo(inspectionInfo);
       }
       public InspectionAllInfo getInspectionAllInfo(Long clientId,Integer remarkID){
        return inspectionDtlRepo.getInspectionAllInfo(clientId,remarkID);
       }


}
