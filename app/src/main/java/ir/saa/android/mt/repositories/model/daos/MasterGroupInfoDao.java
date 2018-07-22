package ir.saa.android.mt.repositories.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import ir.saa.android.mt.repositories.model.entities.MasterGroupInfo;

@Dao
public interface MasterGroupInfoDao {

    @Query("select * from MasterGroupInfo")
    LiveData<List<MasterGroupInfo>> getMasterGroupInfos();

}
