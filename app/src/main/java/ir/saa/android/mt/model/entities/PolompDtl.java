package ir.saa.android.mt.model.entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity
public class PolompDtl  {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int PolompDtlID ;

    public int CurrentColorID ;

    public String CurrentPolomp ;

    public int PolompID ;

    public long PolompInfoID ;

    public int PreviousColorID ;

    public String PreviousPolomp ;

    public byte ReadTypeID ;

    public boolean IsDuplicated ;

    public int PolompTypeID ;

    public int PreviousPolompTypeID ;

    public int StateOld;

    public int StateNew;

    public Integer AgentID ;
}
