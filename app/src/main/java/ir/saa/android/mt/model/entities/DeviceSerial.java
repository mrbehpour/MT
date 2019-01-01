package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class DeviceSerial {

    @PrimaryKey
    public String SerialId;
    public boolean isActive;
}
