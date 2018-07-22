package ir.saa.android.mt.repositories.model.entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class PolompGroup  {

    @PrimaryKey
    @NonNull
    public Integer PolompGroupID ;

    public String PolompGroupName ;

    public String Description ;

    public String KeyName ;

    public Boolean IsForAndroid ;


}
