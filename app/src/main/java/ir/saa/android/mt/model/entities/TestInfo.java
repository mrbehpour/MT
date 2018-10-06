package ir.saa.android.mt.model.entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity
public class TestInfo  {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public Integer TestInfoID ;

    public Integer AgentID ;

    public long ClientID ;

    public Integer ContorTypeID ;

    public String Des ;

    public int SendID ;

    public int TestCount ;

    public int TestDate ;

    public int TestTime ;

    public int TestTypeID ;

    public int BlockID ;

}
