package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.TariffType;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface TariffTypeDao {

    @Query("select * from TariffType")
    LiveData<List<TariffType>> getTariffTypes();

    @Query("Delete from TariffType")
    void deleteAll();

    @Query("select * from TariffType where FldID= :Id")
    LiveData<TariffType> getTariffTypeById(int Id);

    @Query("Delete from TariffType where FldID= :Id")
    void deleteById(int Id);

    @Insert(onConflict = IGNORE)
    void insertTariffType(TariffType tariffType);

    @Update
    void updateTariffType(TariffType tariffType);

    @Delete
    void deleteTariffType(TariffType tariffType);
}
