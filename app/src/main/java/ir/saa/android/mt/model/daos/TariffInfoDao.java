package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.TariffInfo;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface TariffInfoDao {

    @Query("Select * from TariffInfo")
    LiveData<List<TariffInfo>> getTariffInfos();

    @Query("Select * From TariffInfo where TariffInfoID=:Id")
    LiveData<TariffInfo> getTariffInfoById(Integer Id);

    @Query("Delete From TariffInfo")
    void deleteAll();

    @Query("Delete From TariffInfo Where TariffInfoID=:Id")
    void deleteTariffInfoById(Integer Id);

    @Delete
    void deleteTariffInfo(TariffInfo tariffInfo);

    @Insert(onConflict = IGNORE)
    List<Long> insertTariffInfos(List<TariffInfo> tariffInfos);

    @Insert(onConflict = IGNORE)
    Long insertTariffInfo(TariffInfo tariffInfo);

    @Update
    void updateTariffInfo(TariffInfo tariffInfo);


}
