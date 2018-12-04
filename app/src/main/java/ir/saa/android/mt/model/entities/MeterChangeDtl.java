package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity
public class MeterChangeDtl {

    @PrimaryKey
    @NonNull
    public int MChangeDtlID ;

    public String CurrentValue ;

    public int MChangeInfoID ;

    public int MeterChangeID ;

    public int PreviousValue ;

    public int ReadTypeID ;

    public String Long ;

    public String Lat ;
}
