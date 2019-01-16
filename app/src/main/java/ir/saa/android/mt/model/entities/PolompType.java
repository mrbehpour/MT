package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity
public class PolompType {

    @PrimaryKey
    @NonNull
    public Integer PolompTypeID ;

    public String KeyName ;

    public String PolompTypeName ;

    public Boolean IsDefault;
}
