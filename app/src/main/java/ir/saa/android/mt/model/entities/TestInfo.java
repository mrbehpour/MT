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

    public Long ClientID ;

    public Integer ContorTypeID ;

    public String Des ;

    public Integer SendID ;

    public Integer TestCount ;

    public Integer TestDate ;

    public Integer TestTime ;

    public Integer TestTypeID ;

    public Integer BlockID ;

}
