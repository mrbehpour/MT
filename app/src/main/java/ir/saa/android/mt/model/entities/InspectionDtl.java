package ir.saa.android.mt.model.entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity
public class InspectionDtl {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int InspectionDtlID;

    public int InspectionInfoID;

    public int ReadTypeID;

    public int RemarkID;

    public boolean Status;

    public boolean HasCost;

    public boolean CostConfirmation;

    public int InspectionDtlFaultID;

    public String RemarkValue;

    public int AddedCount;

    public int RemovedCount;

}
