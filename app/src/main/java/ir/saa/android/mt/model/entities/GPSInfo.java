package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity
public class GPSInfo {


    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int GPSInfoID ;

    public long ClientID ;

    public int SendID ;

    public int RecieveID ;

    public int GPSDate ;

    public int GPSTime ;

    public long FollowUpCode ;

    public String Long ;

    public String Lat ;

    public String InspectionLat;

    public String InspectionLong;

    public String PolompLat;

    public String PolompLong;

    public String TariffLat;

    public String TariffLong;

    public String MeterChangeLat;

    public String MeterChangeLong;
}
