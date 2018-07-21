package ir.saa.android.mt.model.entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class MasterGroupInfo  {

    @PrimaryKey
    public byte MasterGroupID ;

    public String MasterGroupDes ;

    public String MasterGroupName ;
}
