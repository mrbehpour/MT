package ir.saa.android.mt.repositories.model.entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity

public class ClientType  {

    @PrimaryKey
    @NonNull
    public byte FldID ;

    public String FldDes ;

    public String FldName ;
}
