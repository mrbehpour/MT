package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;


import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.adapters.bazrasi.RemarkItem;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.components.Tarikh;
import ir.saa.android.mt.model.entities.AnswerGroupDtl;
import ir.saa.android.mt.model.entities.InspectionAllInfo;
import ir.saa.android.mt.model.entities.InspectionDtl;
import ir.saa.android.mt.model.entities.InspectionInfo;
import ir.saa.android.mt.model.entities.InspectionWithAnswerGroup;
import ir.saa.android.mt.model.entities.Remark;
import ir.saa.android.mt.model.entities.RemarkGroupingFormat;
import ir.saa.android.mt.repositories.roomrepos.AnswerGroupDtlRepo;
import ir.saa.android.mt.repositories.roomrepos.InspectionDtlRepo;
import ir.saa.android.mt.repositories.roomrepos.InspectionInfoRepo;
import ir.saa.android.mt.repositories.roomrepos.RemarkRepo;

public class BazrasiViewModel extends AndroidViewModel {

    private MutableLiveData<List<RemarkItem>> RemarkItemLiveData;
    private RemarkRepo remarkRepo;
    private AnswerGroupDtlRepo answerGroupDtlRepo;
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
               for (RemarkGroupingFormat remark:remarkRepo.getRemarkGroupingFormat(Id,0).getValue()) {
                   InspectionWithAnswerGroup inspectionAllInfo1=inspectionDtlRepo.getInspectionAllInfo(G.clientInfo.ClientId,remark.RemarkID,remark.AnswerGroupID);
                   if(inspectionAllInfo1==null){
                       remarkInit.add(new RemarkItem(remark.RemarkID,remark.RemarkName,remark.AnswerGroupID,"-1",""));
                   }else{
                       remarkInit.add(new RemarkItem(remark.RemarkID,remark.RemarkName,remark.AnswerGroupID,inspectionAllInfo1.RemarkValue,inspectionAllInfo1.AnswerGroupDtlName));
                   }
               }
               RemarkItemLiveData.postValue( remarkInit);
           }

           remarkRepo.getRemarkGroupingFormat(G.clientInfo.GroupId,0).observeForever(remarks -> {
               List<RemarkItem> remarkItems=new ArrayList<>();
               InspectionAllInfo inspectionAllInfo=new InspectionAllInfo();
               for (RemarkGroupingFormat remark:remarks) {
                   InspectionWithAnswerGroup inspectionAllInfo1=inspectionDtlRepo.getInspectionAllInfo(G.clientInfo.ClientId,remark.RemarkID,remark.AnswerGroupID);
                   if(inspectionAllInfo1==null){
                       remarkItems.add(new RemarkItem(remark.RemarkID,remark.RemarkName,remark.AnswerGroupID,"-1",""));
                   }else{
                       remarkItems.add(new RemarkItem(remark.RemarkID,remark.RemarkName,remark.AnswerGroupID,inspectionAllInfo1.RemarkValue,inspectionAllInfo1.AnswerGroupDtlName));
                   }

               }
               RemarkItemLiveData.postValue(remarkItems);
           });
           return RemarkItemLiveData;
       }

       public boolean isEqualAnswerGroupDtl(Integer iD){
           boolean hasOk=true;

           List<RemarkItem> remarkItems=getRemarks(iD).getValue();
           if(remarkItems.size()!=0) {
               Integer answerGroupId = remarkItems.get(0).answerGroupId;
               for (int i=1;i<remarkItems.size(); i++) {
                   if(remarkItems.get(i).answerGroupId!=answerGroupId){
                       hasOk=false;
                   }
               }
           }else{
               hasOk=false;
           }
           return hasOk;
       }

       public LiveData<List<AnswerGroupDtl>> getAnswerGroupDtls(Integer Id){
          return answerGroupDtlRepo.getAnswerGroupDtlByAnswerGroupId(Id);
       }

       public LiveData<AnswerGroupDtl> getAnswerGroupDtl(Integer Id){
        return  answerGroupDtlRepo.getAnswerGroupDtlById(Id);
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
       public InspectionWithAnswerGroup getInspectionAllInfo(Long clientId,Integer remarkID,Integer answerGroupId){
        return inspectionDtlRepo.getInspectionAllInfo(clientId,remarkID,answerGroupId);
       }
       public List<InspectionInfo> getInspectionInfo(Long clientId){
        return inspectionInfoRepo.getInspectionInfoByClientId(clientId);
       }
       public void deleteAll(InspectionInfo inspectionInfo,InspectionDtl inspectionDtl){
        inspectionInfoRepo.deleteInspectionInfo(inspectionInfo);
        inspectionDtlRepo.deleteInspectionDtl(inspectionDtl);

       }

       public AnswerGroupDtl getAnswerGroupDtlByIdAndAnswegroupId(int Id,int answergroupId){
        return answerGroupDtlRepo.getAnswerGroupDtl(Id,answergroupId);
       }


      public boolean saveBazrasi(RemarkItem currentItem, Object objectValue, Location location) {


        InspectionWithAnswerGroup inspectionAllInfo = getInspectionAllInfo(G.clientInfo.ClientId, currentItem.Id, currentItem.answerGroupId);
        if (inspectionAllInfo == null) {
            if (objectValue != null) {
                InspectionInfo inspectionInfo = new InspectionInfo();
                inspectionInfo.AgentID = Integer.valueOf(G.getPref("UserID"));
                inspectionInfo.ClientID = G.clientInfo.ClientId;
                inspectionInfo.SendID = G.clientInfo.SendId;

                inspectionInfo.FollowUpCode = G.clientInfo.FollowUpCode;

                inspectionInfo.Lat=String.valueOf(location.getLatitude());
                inspectionInfo.Long=String.valueOf(location.getLongitude());
                inspectionInfo.InspectionDate = Integer.valueOf(Tarikh.getCurrentShamsidatetimeWithoutSlash().substring(0, 8));
                inspectionInfo.InspectionTime = Integer.valueOf(Tarikh.getTimeWithoutColon());
                //inspectionInfo.RemarkID = currentItem.Id;
                Long inspectionInfoId = insertInspectionInfo(inspectionInfo);
                InspectionDtl inspectionDtl = new InspectionDtl();
                inspectionDtl.RemarkID = currentItem.Id;
                inspectionDtl.InspectionInfoID = Integer.valueOf(inspectionInfoId.toString());
                inspectionDtl.RemarkValue = String.valueOf(objectValue);
                inspectionDtl.ReadTypeID = 1;

                inspectionDtl.AgentID = Integer.valueOf(G.getPref("UserID"));
                Long inspectionDtlId = insertInspectionDtl(inspectionDtl);
                if (inspectionDtlId != null) {
                    return  true;
                }
            }
        } else {


            InspectionInfo inspectionInfo = new InspectionInfo();
            inspectionInfo.AgentID = inspectionAllInfo.AgentID;
            inspectionInfo.ClientID = inspectionAllInfo.ClientID;
            inspectionInfo.SendID = G.clientInfo.SendId;
            inspectionInfo.Lat=String.valueOf(location.getLatitude());
            inspectionInfo.Long=String.valueOf(location.getLongitude());
            inspectionInfo.InspectionDate = inspectionAllInfo.InspectionDate;
            inspectionInfo.InspectionTime = inspectionAllInfo.InspectionTime;
            //inspectionInfo.RemarkID=inspectionAllInfo.RemarkID;
            inspectionInfo.InspectionInfoID = inspectionAllInfo.InspectionInfoID;
            int inspectionInfoId = updateInspectionInfo(inspectionInfo);
            InspectionDtl inspectionDtl = new InspectionDtl();
            inspectionDtl.RemarkID = inspectionAllInfo.RemarkID;
            inspectionDtl.InspectionInfoID = inspectionAllInfo.InspectionInfoID;
            inspectionDtl.RemarkValue = String.valueOf(objectValue);
            inspectionDtl.ReadTypeID = 1;
            inspectionDtl.InspectionDtlID = inspectionAllInfo.InspectionDtlID;
            inspectionDtl.AgentID = Integer.valueOf(G.getPref("UserID"));
            int inspectionDtlId = updateInspectionDtl(inspectionDtl);
            if (objectValue == null) {
                deleteAll(inspectionInfo, inspectionDtl);
                return false;
            }
            if (inspectionDtlId != 0) {
               return true;
            }

        }
        return false;




    }


}
