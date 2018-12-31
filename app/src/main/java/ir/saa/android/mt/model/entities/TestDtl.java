package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity
public class TestDtl {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public Integer TestDtlID ;

    public int ReadTypeID ;

    public int TestID ;

    public Integer TestInfoID ;

    public String TestValue;

    public Integer AgentID ;


}
