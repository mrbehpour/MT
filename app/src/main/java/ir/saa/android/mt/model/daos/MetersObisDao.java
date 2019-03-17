package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

import java.util.List;

import ir.saa.android.mt.model.entities.MetersObis;

@Dao
public interface MetersObisDao {
    @Query("Select * from MetersObis")
//    LiveData<List<DigitalMeters>> getDigitalMeters();
    List<MetersObis> getMetersObis();

    @Query("Select * from MetersObis where MeterSummaryName= :MeterSummaryName")
    MetersObis getMetersObisByMeterSummaryName(String MeterSummaryName);

    @Insert(onConflict = IGNORE)
    void insertMetersObis(MetersObis metersObis);

    @Query("Select Count(MeterSummaryName) from MetersObis ")
    int getMetersObisCount();

    @Update
    void updateMetersObis(MetersObis metersObis);

    @Delete
    void deleteMetersObis(MetersObis metersObis);

    @Query("DELETE FROM MetersObis")
    void deleteAllMetersObis();
}
