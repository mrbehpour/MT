package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.Polomp;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface PolompDao {

    @Query("select * from Polomp")
    LiveData<List<Polomp>> getPolomps();

    @Query("select * from Polomp where PolompID= :Id")
    LiveData<Polomp> getPolompById(int Id);

    @Query("Delete From Polomp")
    void deleteAll();

    @Query("Delete from Polomp where PolompID= :Id")
    void deleteById(int Id);

    @Insert(onConflict = IGNORE)
    void insertPolomp(Polomp polomp);

    @Insert(onConflict = IGNORE)
    List<Integer> insertPolomps(List<Polomp> polomps);
    @Delete
    void deletePolomp(Polomp polomp);

    @Update
    void updatePolomp(Polomp polomp);

}
