package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity
public class Polomp  {

    @PrimaryKey
    @NonNull
    public Integer PolompID;

    public String Description;

    public String KeyName;

    //public RealmList<ir.saa.android.mt.database.contracts.PolompDtl> PolompDtl;



    public String PolompName;

    public Boolean IsEditable;

    public Boolean SendToHH;

    public Integer Order;

   // public RealmList<RelMasterGroupPolomp> RelMasterGroupPolomp;



}
