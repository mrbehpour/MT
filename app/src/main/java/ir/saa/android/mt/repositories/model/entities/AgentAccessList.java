package ir.saa.android.mt.repositories.model.entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity

public class AgentAccessList  {

    @PrimaryKey
    @NonNull
    public Integer AccessID ;

    public String AgentAccessName ;

    public Boolean IsVisible ;

    public Boolean PosID ;




}

