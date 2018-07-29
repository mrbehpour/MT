package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity
public class RemarkGroup  {

    @PrimaryKey
    @NonNull
    public Integer FldID;

    public String FldDes;

    public String FldName;

    public Boolean HHGrouping;

    public Boolean IsFixed;

    public String KeyName;




}
