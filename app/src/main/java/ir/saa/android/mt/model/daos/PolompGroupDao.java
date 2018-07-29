package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.PolompGroup;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface PolompGroupDao {

    @Query("select * from PolompGroup")
    LiveData<List<PolompGroup>> getPolompGroups();

    @Query("Select * from PolompGroup where PolompGroupID= :Id")
    LiveData<PolompGroup> getPolompGroupById(int Id);

    @Query("Delete From PolompGroup")
    void deleteAll();

    @Query("Delete from PolompGroup where PolompGroupID= :Id")
    void deleteById(int Id);

    @Insert(onConflict = IGNORE)
    void insertPolompGroup(PolompGroup polompGroup);

    @Insert(onConflict = IGNORE)
    List<Integer> insertPolompGroups(List<PolompGroup> polompGroups);

    @Update
    void updatePolompGroup(PolompGroup polompGroup);

    @Delete
    void deletePolompGroup(PolompGroup polompGroup);

}
