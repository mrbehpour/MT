package ir.saa.android.mt.repositories.model.entities;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(foreignKeys = {@ForeignKey(entity = Polomp.class,parentColumns = "PolompID",childColumns = "polomp_id"),
                       @ForeignKey(entity = PolompGroup.class,parentColumns = "PolompGroupID",childColumns = "polompgroup_id"),
                        @ForeignKey(entity = MasterGroupDetail.class,parentColumns = "MasterGroupDtlID",childColumns = "mastergroupdetail_id")})
public class PolompGroupingFormat  {

    @PrimaryKey
    @NonNull
    public Integer ID ;

    @ColumnInfo(name = "polomp_id")
    public Integer PolompID ;

    @ColumnInfo(name = "polompgroup_id")
    public Integer PolompGroupID ;

    public Integer PolompOrder ;

    @ColumnInfo(name = "mastergroupdetail_id")
    public Integer MasterGroupDetailID ;


}
