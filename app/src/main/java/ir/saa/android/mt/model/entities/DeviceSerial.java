package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity
public class DeviceSerial {


    @PrimaryKey
    @android.support.annotation.NonNull
    public String SerialId;

    public short regionId;

    public boolean isActive;
}
