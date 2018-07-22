package ir.saa.android.mt.repositories.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;



@Entity
public class Company  {

    public String FldDes ;

    @PrimaryKey
    @NonNull
    public Integer FldID ;

    public String FldName ;

}
