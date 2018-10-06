package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity
public class MeterChangeInfo {

    @PrimaryKey
    @NonNull
    public int MChangeInfoID ;

    public int AgentID ;

    public int ChangeDate ;

    public short ChangeTime ;

    public long ClientID ;

    public int SendID ;
}
