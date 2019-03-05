package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.TariffAllInfo;
import ir.saa.android.mt.model.entities.TariffDtl;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface TariffDtlDao {

    @Query("Select * From TariffDtl")
    LiveData<List<TariffDtl>> getTariffDtls();

    @Query("Select * from TariffDtl where TariffDtlID=:Id")
    LiveData<TariffDtl> getTariffDtlById(Integer Id);

    @Query("Select * from TariffDtl where TariffInfoID=:Id")
    LiveData<List<TariffDtl>> getTariffDtlByTariffInfoId(Integer Id);

    @Query("Delete From TariffDtl")
    void deleteAll();

    @Query("Delete From TariffDtl Where TariffDtlID=:Id")
    void deleteTariffDtlById(Integer Id);

    @Delete
    void deleteTariffDtl(TariffDtl tariffDtl);

    @Insert(onConflict = IGNORE)
    List<Long> insertTariffDtls(List<TariffDtl> tariffDtls);

    @Insert(onConflict = IGNORE)
    void insertTariffDtl(TariffDtl tariffDtl);

    @Update
    void updateTariffDtl(TariffDtl tariffDtl);

    @Query("Select * from Tariffinfo inner join tariffdtl " +
            "on Tariffinfo.TariffInfoID=tariffdtl.TariffInfoID  " +
            "where Tariffinfo.ClientID=:clientId and TariffInfo.SendID=:sendId"+
            " order by TariffInfo.TariffInfoID"
    )
    List<TariffAllInfo> getTariffAllInfo(Long clientId, Integer sendId);

}
