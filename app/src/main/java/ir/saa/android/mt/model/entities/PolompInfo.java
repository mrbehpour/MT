package ir.saa.android.mt.model.entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity
public class PolompInfo {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int PolompInfoID ;

    public int AgentID ;

    public int ChangeDate ;

    public int ChangeTime ;

    public long ClientID ;

    public int SendID ;

    public int BlockID ;

}
