package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity
public class PolompColor {

    @PrimaryKey
    @NonNull
    public Integer FldID ;

    public String FldDes ;

    public String FldName ;
}
