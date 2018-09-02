package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import ir.saa.android.mt.model.entities.PolompType;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface PolompTypeDao {

    @Query("Select * from PolompType")
    LiveData<List<PolompType>> getPolompTypes();


    @Insert(onConflict = IGNORE)
    void insertPolompType(PolompType polompType);

    @Query("Delete From PolompType")
    void deleteAll();

}
