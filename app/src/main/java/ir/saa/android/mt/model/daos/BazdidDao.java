package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import java.util.List;

import ir.saa.android.mt.model.entities.Bazdid;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface BazdidDao {

    @Query("Select * from Bazdid")
    LiveData<List<Bazdid>> getBazdids();


    @Insert(onConflict = IGNORE)
    Long insertBazdid(Bazdid  bazdid);

    @Update
    void updateBazdid(Bazdid bazdid);


    @Query("Select * from Bazdid where ClientId= :iD")
    Bazdid getBazdid(Long iD);

    @Delete
    void deleteBazdid(Bazdid bazdid);

    @Query("Delete from Bazdid")
    void deleteAll();

    @Query("Delete from Bazdid where ClientId= :iD")
    void deleteBazdidById(Long iD);


    @Query("Select * from Bazdid where isSend= :hasSend")
    List<Bazdid> getBazdidIsSend(Boolean hasSend);



}
