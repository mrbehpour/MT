package ir.saa.android.mt.model.entities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = Company.class,parentColumns = "FldID",childColumns = "company_id"),
                       @ForeignKey(entity = Region.class,parentColumns = "RegionID",childColumns = "region_id"),
                       @ForeignKey(entity = PropertyType.class,parentColumns = "PropertyTypeID",childColumns = "propertytype_id"),
                       @ForeignKey(entity = AnswerGroup.class,parentColumns = "AnswerGroupID",childColumns = "answergroup_id") })


public class Setting {

    @PrimaryKey
    public Integer SettingID ;

    @ColumnInfo(name = "company_id")
    public Integer CompanyID ;

    public Boolean IsEditable ;

    @ColumnInfo(name="region_id")
    public Integer RegionID ;

    public String SettingGroupKey ;

    public String SettingGroupName ;

    public String SettingKey ;

    public String SettingName ;

    public String SettingValue ;

    @ColumnInfo(name = "propertytype_id")
    public Integer PropertyTypeID ;

    @ColumnInfo(name="answergroup_id")
    public Integer AnswerGroupID ;



}
