package ir.saa.android.mt.model.entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class MasterGroupInfo  {

    @PrimaryKey
    @NonNull
    public byte MasterGroupID ;

    public String MasterGroupDes ;

    public String MasterGroupName ;
}
