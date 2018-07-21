package ir.saa.android.mt.model.entities;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class PolompGroup  {

    @PrimaryKey
    public Integer PolompGroupID ;

    public String PolompGroupName ;

    public String Description ;

    public String KeyName ;

    public Boolean IsForAndroid ;


}
