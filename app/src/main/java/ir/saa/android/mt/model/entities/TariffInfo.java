package ir.saa.android.mt.model.entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity
public class TariffInfo  {

    @PrimaryKey
    @NonNull
    public Integer TariffInfoID ;

    public Integer AgentID ;

    public Long ClientID ;

    public String DigitString ;

    public Integer SendID ;

    public Integer TariffDate ;

    public Integer TariffTime ;

    public Integer BlockID ;

}
