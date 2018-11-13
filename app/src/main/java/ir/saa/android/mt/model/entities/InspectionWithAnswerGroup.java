package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.ColumnInfo;

public class InspectionWithAnswerGroup {
    public Integer InspectionInfoID;

    public Integer AgentID;

    public Long ClientID;

    public Integer InspectionDate;

    public Integer InspectionTime;

    public Integer SendID;

    public Integer BlockID;

    public Integer RemarkID;

    public Integer InspectionDtlID;

    public Integer ReadTypeID;

    public Boolean Status;

    public Boolean HasCost;

    public Boolean CostConfirmation;

    public int InspectionDtlFaultID;

    public String RemarkValue;

    public Integer AddedCount;

    public Integer RemovedCount;

    public Long FollowUpCode;

    public int AnswerGroupDtlID ;

    public String AnswerGroupDtlName ;

    @ColumnInfo(name = "answergroup_id")
    public int AnswerGroupID ;

    public String Description ;
}
