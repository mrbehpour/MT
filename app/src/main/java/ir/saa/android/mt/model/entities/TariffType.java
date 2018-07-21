package ir.saa.android.mt.model.entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class TariffType  {
    @PrimaryKey
    public byte FldID ;

    public String FldDes ;

    public String FldName ;
}
