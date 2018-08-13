package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.MeterChangeInfo;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface MeterChangeInfoDao {

    @Query("Select * from MeterChangeInfo")
    LiveData<List<MeterChangeInfo>> getMeterChangeInfos();

    @Query("Select * from MeterChangeInfo where MChangeInfoID=:Id")
    LiveData<MeterChangeInfo> getMeterChangeInfo(Integer Id);

    @Query("Delete From MeterChangeInfo")
    void deleteAll();

    @Query("Delete from MeterChangeInfo where MChangeInfoID=:Id")
    void deleteMeterChangeInfoById(Integer Id);

    @Delete
    void deleteMeterChangeInfo(MeterChangeInfo meterChangeInfo);

    @Insert(onConflict = IGNORE)
    List<Long> insertMeterChangeInfos(List<MeterChangeInfo> meterChangeInfos);

    @Insert(onConflict = IGNORE)
    void insertMeterChangeInfo(MeterChangeInfo meterChangeInfo);

    @Update
    void updateMeterChangeInfo(MeterChangeInfo meterChangeInfo);


}
