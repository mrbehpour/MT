package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(foreignKeys ={@ForeignKey(entity = MasterGroupInfo.class,parentColumns = "MasterGroupID",childColumns = "mastergroupinf_id")})
public class MasterGroupDetail {

    @PrimaryKey
    @NonNull
    public Integer MasterGroupDtlID ;

    public Integer ChoiceID ;

    public String ChoiceValue ;





    @ColumnInfo(name = "mastergroupinf_id")
    public Integer MasterGroupInfID ;




}
