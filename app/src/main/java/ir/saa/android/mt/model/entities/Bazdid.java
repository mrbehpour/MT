package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Bazdid {

    @PrimaryKey
    public Long ClientId;

    public boolean isBazdid;

    public boolean isSend;
}
