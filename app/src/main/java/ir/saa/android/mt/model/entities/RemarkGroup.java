package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity
public class RemarkGroup  {

    @PrimaryKey
    @NonNull
    private Integer FldID;

    private String FldDes;

    private String FldName;

    private Boolean HHGrouping;

    private Boolean IsFixed;

    private String KeyName;

    //private RealmList<ir.saa.android.mt.database.contracts.GroupingFormat> GroupingFormat;


}