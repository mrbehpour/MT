package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity
public class TestDtl {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public Integer TestDtlID ;

    public Integer ReadTypeID ;

    public Integer TestID ;

    public Integer TestInfoID ;

    public String TestValue;
}
