package ir.saa.android.mt.model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import io.reactivex.annotations.NonNull;

@Entity
public class DigitalMeters {
    @PrimaryKey
    public int MeterID;
    public String MeterCompany;
    public String MeterType;
    @NonNull
    public String MeterSummaryName;
    @NonNull
    public String MeterString;
    public String ReadMode;
    public String ValidationRegex;
    public boolean SetDateTime;
    public boolean NeedPassForRead;
    public String ReadObisType;
    public String R_Command;
    public int MakePassAlgorithm;
    public String Pass1;
    public String Pass2;
    public String Pass3;
}
