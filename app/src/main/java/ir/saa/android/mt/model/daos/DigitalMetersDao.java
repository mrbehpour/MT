package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.DigitalMeters;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface DigitalMetersDao {

    @Query("Select * from DigitalMeters")
//    LiveData<List<DigitalMeters>> getDigitalMeters();
    List<DigitalMeters> getDigitalMeters();

    @Query("Select * from DigitalMeters where MeterString= :MeterString")
    LiveData<DigitalMeters> getDigitalMetersByMeterStringLiveData(String MeterString);

    @Insert(onConflict = IGNORE)
    void insertDigitalMeters(DigitalMeters digitalMeters);

    @Update
    void updateDigitalMeters(DigitalMeters digitalMeters);

    @Delete
    void deleteDigitalMeters(DigitalMeters digitalMeters);

}
