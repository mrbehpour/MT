package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity
public class MetersObis {
    @PrimaryKey
    public int MeterID;
    @NonNull
    public String MeterSummaryName;
    public String MeterObisStr;
}
