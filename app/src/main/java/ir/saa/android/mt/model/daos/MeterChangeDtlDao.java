package ir.saa.android.mt.model.daos;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.MeterChangeDtl;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface MeterChangeDtlDao {

    @Query("Select * from MeterChangeDtl")
    LiveData<List<MeterChangeDtl>> getMeterChangeDtl();

    @Query("Select * from MeterChangeDtl where MChangeDtlID=:Id")
    LiveData<MeterChangeDtl> getMeterChangeDtlById(Integer Id);

    @Query("Select * from MeterChangeDtl Where MChangeInfoID=:Id")
    LiveData<List<MeterChangeDtl>> getMeterChangeDtlByMeterChangeInfoId(Integer Id);


    @Query("Select * from MeterChangeDtl where MeterChangeID=:Id")
    LiveData<List<MeterChangeDtl>> getMeterChangeDtlByMeterChangeID(Integer Id);

    @Query("Delete From MeterChangeDtl")
    void deleteAll();

    @Query("Delete from MeterChangeDtl where MChangeDtlID=:Id")
    void deleteMeterChangeDtlById(Integer Id);

    @Delete
    void deleteMeterChangeDtl(MeterChangeDtl meterChangeDtl);

    @Insert(onConflict = IGNORE)
    List<Long> insertMeterChangeDtls(List<MeterChangeDtl> meterChangeDtls);


    @Insert(onConflict = IGNORE)
    void insertMeterChangeDtl(MeterChangeDtl meterChangeDtl);


    @Update
    void updateMeterChangeDtl(MeterChangeDtl meterChangeDtl);




}
