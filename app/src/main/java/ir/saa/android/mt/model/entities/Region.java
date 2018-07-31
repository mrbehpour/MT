package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;



@Entity
        //(foreignKeys = {@ForeignKey(entity = Company.class,parentColumns = "FldID",childColumns = "company_Id")})
public class Region  {

    @PrimaryKey
    @NonNull
    public Integer RegionID ;

    @ColumnInfo(name = "company_Id")
    public Integer CompanyID ;

    public String RegionDes ;

    public String RegionName ;

    public Integer SinglePhaseTestPeriodIndex ;

    public Integer MultiphaseTestPeriodIndex ;

    public Integer DemandTestPeriodIndex ;

    public String ADDRESS ;

    public Integer TEL ;


}
