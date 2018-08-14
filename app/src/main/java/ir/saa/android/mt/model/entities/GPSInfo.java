package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity
public class GPSInfo {

    @PrimaryKey
    @NonNull
    public Integer GPSInfoID ;

    public Long ClientID ;

    public Integer SendID ;

    public Integer RecieveID ;

    public Integer GPSDate ;

    public Integer GPSTime ;

    public Long FollowUpCode ;

    public String Long ;

    public String Lat ;
}
