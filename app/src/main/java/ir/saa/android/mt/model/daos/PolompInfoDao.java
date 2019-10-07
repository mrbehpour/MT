package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.PolompInfo;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface PolompInfoDao {

    @Query("Select * from PolompInfo")
    LiveData<List<PolompInfo>> getPolompInfos();

    @Query("Select * from PolompInfo Where PolompInfoID=:Id")
    LiveData<PolompInfo> getPolompInfoById(Integer Id);

    @Query("Delete From PolompInfo")
    void deleteAll();

    @Query("Delete From PolompInfo where PolompInfoID=:Id")
    void deletePolompInfoById(Integer Id);

    @Delete
    void deletePolompInfo(PolompInfo polompInfo);

    @Insert(onConflict = IGNORE)
    List<Long> insertPolompInfos(List<PolompInfo> polompInfos);

    @Insert(onConflict = IGNORE)
    Long insertPolompInfo(PolompInfo polompInfo);

    @Query("Delete from PolompInfo Where PolompInfo.ClientID=:ClientId")
    void deleteByClientId(Long ClientId);

    @Update
    void updatePolompInfo(PolompInfo polompInfo);

}
