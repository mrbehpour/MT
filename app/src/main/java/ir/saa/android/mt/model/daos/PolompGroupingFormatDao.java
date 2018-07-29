package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.PolompGroupingFormat;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface PolompGroupingFormatDao {

    @Query("select * from PolompGroupingFormat")
    LiveData<List<PolompGroupingFormat>> getPolompGroupingFormats();

    @Query("select * from PolompGroupingFormat where ID= :Id")
    LiveData<PolompGroupingFormat> getPolompGroupingFormatById(int Id);

    @Query("select * from PolompGroupingFormat where polompgroup_id= :Id")
    LiveData<List<PolompGroupingFormat>> getPolompGroupingFormatByPolompGroupId(int Id);

    @Query("select * from PolompGroupingFormat where polomp_id= :Id")
    LiveData<List<PolompGroupingFormat>> getPolompGroupingFormatByPolompId(int Id);

    @Query("select * from PolompGroupingFormat where mastergroupdetail_id= :Id")
    LiveData<List<PolompGroupingFormat>> getPolompGroupingFormatByMasterGroupdtlId(int Id);

    @Query("Delete From PolompGroupingFormat ")
    void  deleteAll();

    @Query("Delete From PolompGroupingFormat where ID= :Id")
    void deleteById(int Id);

    @Delete
    void deletePolompGroupingFormat(PolompGroupingFormat polompGroupingFormat);

    @Insert(onConflict = IGNORE)
    void insertPolompGroupingFormat(PolompGroupingFormat polompGroupingFormat);

    @Insert(onConflict = IGNORE)
    List<Integer> insertPolompGroupingFormats(List<PolompGroupingFormat> polompGroupingFormats);
    @Update
    void updatePolompGroupingFormat(PolompGroupingFormat polompGroupingFormat);



}
