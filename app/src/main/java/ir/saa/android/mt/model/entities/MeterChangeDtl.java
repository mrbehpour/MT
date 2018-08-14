package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity
public class MeterChangeDtl {

    @PrimaryKey
    @NonNull
    public Integer MChangeDtlID ;

    public String CurrentValue ;

    public Integer MChangeInfoID ;

    public Integer MeterChangeID ;

    public String PreviousValue ;

    public Integer ReadTypeID ;
}
