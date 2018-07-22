package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import ir.saa.android.mt.model.entities.TariffType;

@Dao
public interface TariffTypeDao {

    @Query("select * from TariffTyp")
    LiveData<List<TariffType>> getTariffTypes();
}
