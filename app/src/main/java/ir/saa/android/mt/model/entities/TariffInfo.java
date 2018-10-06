package ir.saa.android.mt.model.entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity
public class TariffInfo  {

    @PrimaryKey
    @NonNull
    public int TariffInfoID ;

    public int AgentID ;

    public long ClientID ;

    public String DigitString ;

    public int SendID ;

    public int TariffDate ;

    public int TariffTime ;

    public int BlockID ;

}
