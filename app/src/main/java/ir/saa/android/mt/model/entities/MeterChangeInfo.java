package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity
public class MeterChangeInfo {

    @PrimaryKey
    @NonNull
    public int MChangeInfoID ;

    public Integer AgentID ;

    public Integer ChangeDate ;

    public short ChangeTime ;

    public long ClientID ;

    public Integer SendID ;
}
