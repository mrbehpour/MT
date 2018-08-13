package ir.saa.android.mt.model.entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity
public class InspectionDtl {

    @PrimaryKey
    @NonNull
    public Integer InspectionDtlID;

    public Integer InspectionInfoID;

    public Integer ReadTypeID;

    public Integer RemarkID;

    public Boolean Status;

    public Boolean HasCost;

    public Boolean CostConfirmation;

    public int InspectionDtlFaultID;

    public String RemarkValue;

    public Integer AddedCount;

    public Integer RemovedCount;

}
