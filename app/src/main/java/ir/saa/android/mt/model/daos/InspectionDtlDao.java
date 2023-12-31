package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.InspectionAllInfo;
import ir.saa.android.mt.model.entities.InspectionDtl;
import ir.saa.android.mt.model.entities.InspectionInfo;
import ir.saa.android.mt.model.entities.InspectionWithAnswerGroup;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface InspectionDtlDao {
    @Query("Select * from InspectionDtl")
    LiveData<List<InspectionDtl>> getInspectionDtls();

    @Query("Select * from InspectionDtl where InspectionDtlID=:Id")
    LiveData<InspectionDtl> getInspectionDtlById(Integer Id);

    @Query("Select * from InspectionDtl where InspectionInfoID=:Id")
    LiveData<List<InspectionDtl>> getInspectionDtlByInspectionInfoId(Integer Id);

    @Query("Delete From InspectionDtl")
    void deleteAll();

    @Query("Delete From InspectionDtl where InspectionDtlID=:Id")
    void deleteInspectionDtlById(Integer Id);

    @Delete
    void deleteInspectionDtl(InspectionDtl inspectionDtl);

    @Insert(onConflict = IGNORE)
    List<Long> insertInspectionDtls(List<InspectionDtl> inspectionDtls);

    @Insert(onConflict = IGNORE)
    Long insertInspectionDtl(InspectionDtl inspectionDtl);

    @Update
    int updateInspectionDtl(InspectionDtl inspectionDtl);

    @Query("Select * from InspectionInfo " +
            "inner join InspectionDtl on " +
            "InspectionInfo.InspectionInfoID=InspectionDtl.InspectionInfoID " +
            "Inner Join Remark on "+
            "InspectionDtl.RemarkID=Remark.RemarkID and InspectionDtl.RemarkValue=AnswerGroupDtl.AnswerGroupDtlID " +
            "inner join  AnswerGroupDtl on " +
            " AnswerGroupDtl.answergroup_id=Remark.AnswerGroupID "+
            "Where AnswerGroupDtl.answergroup_id=:answerGroupId and InspectionInfo.ClientID=:clientId and Remark.RemarkID=:remarkId")
    InspectionWithAnswerGroup getInspectionAllInfo(Long clientId, Integer remarkId, Integer answerGroupId);

    @Query("Select * from InspectionInfo " +
            "inner join InspectionDtl on " +
            "InspectionInfo.InspectionInfoID=InspectionDtl.InspectionInfoID " +
            "Inner Join Remark on "+
            "InspectionDtl.RemarkID=Remark.RemarkID  " +
            "inner join  AnswerGroupDtl on " +
            "AnswerGroupDtl.answergroup_id=Remark.AnswerGroupID "+
            "Where AnswerGroupDtl.answergroup_id=:answerGroupId and InspectionInfo.ClientID=:clientId " +
            "and Remark.RemarkID=:remarkId and InspectionDtl.RemarkValue in (" +
            "Select InspectionDtl.RemarkValue from InspectionInfo "  +
            "inner join InspectionDtl on "+
            "InspectionInfo.InspectionInfoID=InspectionDtl.InspectionInfoID " +
            "Inner Join Remark on " +
            "InspectionDtl.RemarkID=Remark.RemarkID  " +
            "inner join  AnswerGroupDtl on " +
            " AnswerGroupDtl.answergroup_id=Remark.AnswerGroupID " +
            "Where AnswerGroupDtl.answergroup_id=:answerGroupId and InspectionInfo.ClientID=:clientId " +
            "and Remark.RemarkID=:remarkId)")
    InspectionWithAnswerGroup getInspectionAllInfoLiveData(Long clientId, Integer remarkId, Integer answerGroupId);

    @Query("Select * from InspectionInfo " +
            "inner join InspectionDtl on " +
            "InspectionInfo.InspectionInfoID=InspectionDtl.InspectionInfoID " +
            "Where InspectionInfo.ClientID=:clientId and InspectionInfo.SendID=:sendId")
    List<InspectionAllInfo> getInspectionAllInfoWithSendId(Long clientId,Integer sendId);

    @Query("Select * from InspectionInfo " +
            "inner join InspectionDtl on " +
            "InspectionInfo.InspectionInfoID=InspectionDtl.InspectionInfoID " +
            "Where InspectionInfo.ClientID=:clientId")
    List<InspectionAllInfo> getInspectionAllInfoWithClientId(Long clientId);
}
