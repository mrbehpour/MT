package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity
        //(foreignKeys = {@ForeignKey(entity = Region.class,parentColumns = "RegionID",childColumns = "region_id")})
public class City  {

    @PrimaryKey
    @NonNull
    public int CityCode;

    public String CityName;

    @ColumnInfo(name = "region_id")
    public int RegionID ;


}
