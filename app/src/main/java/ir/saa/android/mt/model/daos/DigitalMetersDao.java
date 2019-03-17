package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.DigitalMeters;
import ir.saa.android.mt.repositories.meterreader.MeterUtility;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface DigitalMetersDao {

    @Query("Select * from DigitalMeters")
//    LiveData<List<DigitalMeters>> getDigitalMeters();
    List<DigitalMeters> getDigitalMeters();

//    @Query("Select * from DigitalMeters where MeterString= :MeterString")
//    DigitalMeters getDigitalMetersByMeterStringLiveData(String MeterString);

    @Query("Select * from DigitalMeters where MeterSummaryName = :MeterString ")
    DigitalMeters getDigitalMetersByMeterSummaryName(String MeterString);

    @Query("Select MeterSummaryName,MeterString from DigitalMeters ")
    List<MeterUtility.MeterStringInfo> getAllDigitalMetersString();

    @Query("Select Count(MeterSummaryName) from DigitalMeters ")
    int getDigitalMetersCount();

    @Insert(onConflict = IGNORE)
    void insertDigitalMeters(DigitalMeters digitalMeters);

    @Update
    void updateDigitalMeters(DigitalMeters digitalMeters);

    @Delete
    void deleteDigitalMeters(DigitalMeters digitalMeters);

    @Query("DELETE FROM DigitalMeters")
    void deleteAllDigitalMeters();
}
