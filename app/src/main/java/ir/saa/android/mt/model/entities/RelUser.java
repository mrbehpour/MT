package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class RelUser {

    @PrimaryKey
    @NonNull
    public int UserID ;

    public Short RegionID ;

    public Byte RoleID ;

    public String FirstName ;

    public String LastName ;

    public String ContractorName ;

    public String Address ;

    public String Phone ;

    public String CellPhone ;

    public String HandheldPass;

    public Boolean IsDeleted ;

    public Short UserType ;


}
