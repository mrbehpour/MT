package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;



@Entity
public class PropertyType  {


    @PrimaryKey
    public Integer PropertyTypeID ;

    public String Description ;

    public  Boolean HasLength ;

    public Boolean HasPrecision;

    public Boolean HasTypeInfo ;

    public String KeyName ;

    public String PropertyTypeName ;

}
