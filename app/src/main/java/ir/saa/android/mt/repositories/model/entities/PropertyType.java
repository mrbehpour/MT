package ir.saa.android.mt.repositories.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity
public class PropertyType  {


    @PrimaryKey
    @NonNull
    public Integer PropertyTypeID ;

    public String Description ;

    public  Boolean HasLength ;

    public Boolean HasPrecision;

    public Boolean HasTypeInfo ;

    public String KeyName ;

    public String PropertyTypeName ;

}
