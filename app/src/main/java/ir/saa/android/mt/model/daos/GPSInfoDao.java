package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.GPSInfo;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface GPSInfoDao {

    @Query("Select * from GPSInfo")
    LiveData<List<GPSInfo>> getGPSInfos();

    @Query("Select * from GPSInfo Where GPSInfoID=:Id")
    LiveData<GPSInfo> getGPSInfoById(Integer Id);

    @Query("Delete From GPSInfo")
    void deleteAll();

    @Query("Delete From GPSInfo where GPSInfoID=:Id")
    void deleteGPSInfoById(Integer Id);

    @Delete
    void deleteGPSInfo(GPSInfo gpsInfo);

    @Insert(onConflict = IGNORE)
    List<Long> insertGPSInfos(List<GPSInfo> gpsInfos);

    @Insert(onConflict = IGNORE)
    void insertGPSInfo(GPSInfo gpsInfo);

    @Update
    void updateGPSInfo(GPSInfo gpsInfo);


}
