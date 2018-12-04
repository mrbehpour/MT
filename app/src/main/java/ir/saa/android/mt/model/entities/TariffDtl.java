package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity
public class TariffDtl {

    @PrimaryKey
    @NonNull
    public int TariffDtlID ;

    public int ReadTypeID ;

    public int TariffID ;

    public int TariffInfoID ;

    public String TariffValue ;

    public String Long ;

    public String Lat ;
}
