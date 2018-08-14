package ir.saa.android.mt.model.entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity
public class PolompDtl  {

    @PrimaryKey
    @NonNull
    public Integer PolompDtlID ;

    public Integer CurrentColorID ;

    public String CurrentPolomp ;

    public byte PolompID ;

    public Integer PolompInfoID ;

    public Integer PreviousColorID ;

    public String PreviousPolomp ;

    public byte ReadTypeID ;

    public Boolean IsDuplicated ;

    public Integer PolompTypeID ;

    public short PreviousPolompTypeID ;
}
