package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import ir.saa.android.mt.model.entities.PolompColor;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface PolompColorDao {
    @Query("select * from PolompColor")
    LiveData<List<PolompColor>> getPolompColors();

    @Insert(onConflict = IGNORE)
    void insertPolompColor(PolompColor polompColor);

    @Query("delete from PolompColor")
    void deleteAll();
}
