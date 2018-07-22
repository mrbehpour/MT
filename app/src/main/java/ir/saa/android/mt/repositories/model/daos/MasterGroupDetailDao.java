package ir.saa.android.mt.repositories.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import ir.saa.android.mt.repositories.model.entities.MasterGroupDetail;

@Dao
public interface MasterGroupDetailDao {

    @Query("select * from MasterGroupDetail")
    LiveData<List<MasterGroupDetail>> getMasterGroupDetails();
}
