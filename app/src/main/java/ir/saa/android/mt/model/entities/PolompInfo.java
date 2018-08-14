package ir.saa.android.mt.model.entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity
public class PolompInfo {

    @PrimaryKey
    @NonNull
    public Integer PolompInfoID ;

    public Integer AgentID ;

    public Integer ChangeDate ;

    public Integer ChangeTime ;

    public Long ClientID ;

    public Integer SendID ;

    public Integer BlockID ;

}
