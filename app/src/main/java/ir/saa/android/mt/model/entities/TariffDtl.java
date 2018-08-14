package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity
public class TariffDtl {

    @PrimaryKey
    @NonNull
    public Integer TariffDtlID ;

    public Integer ReadTypeID ;

    public Integer TariffID ;

    public Integer TariffInfoID ;

    public String TariffValue ;
}
