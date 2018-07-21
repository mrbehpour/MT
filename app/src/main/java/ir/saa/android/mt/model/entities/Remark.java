package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(foreignKeys = {@ForeignKey(entity = AnswerGroup.class,parentColumns = "AnswerGroupID",childColumns = "answergroup_id"),
                       @ForeignKey(entity = PropertyType.class ,parentColumns = "PropertyTypeID",childColumns = "propertytype_id"),
                        @ForeignKey(entity = RemarkType.class,parentColumns = "FldID",childColumns = "remarktype_id")})
public class Remark  {

    @PrimaryKey
    @NonNull
    public Integer RemarkID;


    @ColumnInfo(name = "answergroup_id")
    public  Integer AnswerGroupID;

    public Integer BillingCode;

    public String Description;

    //public GroupingFormat GroupingFormat;

    //public RealmList<InspectionDtl> InspectionDtl;

    public  Boolean IsEditable;

    public  String KeyName;

    public  Integer Length;

    public Integer Precision;

    @ColumnInfo(name = "propertytype_id")
    public Integer PropertyTypeID;

    //public RealmList<RelRemarkUnit> RelRemarkUnit;

    public String RemarkName;

    //public RemarkType RemarkType;

    public Boolean IsDeleted;

    @ColumnInfo(name = "remarktype_id")
    public  Integer RemarkTypeID;


}
