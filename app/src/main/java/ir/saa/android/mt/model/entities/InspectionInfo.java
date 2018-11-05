package ir.saa.android.mt.model.entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity
public class InspectionInfo  {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public Integer InspectionInfoID;

    public int AgentID;

    public long ClientID;

    public int InspectionDate;

    public int InspectionTime;

    public int SendID;

    public Integer BlockID;

    //public int RemarkID;

    public Long FollowUpCode;

}
