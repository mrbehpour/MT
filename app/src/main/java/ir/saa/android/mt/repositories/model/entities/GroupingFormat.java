package ir.saa.android.mt.repositories.model.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(foreignKeys = {@ForeignKey(entity = RemarkGroup.class ,parentColumns = "FldID",childColumns = "remarkgroup_id"),
                       @ForeignKey(entity = MasterGroupDetail.class,parentColumns = "MasterGroupDtlID",childColumns = "mastergroupdetail_id")})
public class GroupingFormat  {

    @PrimaryKey
    @NonNull
    public Integer GroupingFormatID;

    @ColumnInfo(name="remarkgroup_id")
    public Integer RemarkGroupID;

    public Integer RemarkID;

    public  Integer RemarkOrder;

    @ColumnInfo(name = "mastergroupdetail_id")
    public  Integer MasterGroupDetailID;


}
