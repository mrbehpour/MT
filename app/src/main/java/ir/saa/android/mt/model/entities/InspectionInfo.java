package ir.saa.android.mt.model.entities;


import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

public class InspectionInfo  {

    @PrimaryKey
    @NonNull
    public Integer InspectionInfoID;

    public Integer AgentID;

    public Long ClientID;

    public Integer InspectionDate;

    public Integer InspectionTime;

    public Integer SendID;

    public Integer BlockID;

    public Integer RemarkID;

}
