package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.MasterGroupInfo;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface MasterGroupInfoDao {

    @Query("select * from MasterGroupInfo")
    LiveData<List<MasterGroupInfo>> getMasterGroupInfos();

    @Delete
    void deleteMasterGroupInfo(MasterGroupInfo masterGroupInfo);

    @Update
    void updateMasterGroupInfo(MasterGroupInfo masterGroupInfo);

    @Insert(onConflict = IGNORE)
    void insertMasterGroupInfo(MasterGroupInfo masterGroupInfo);


    @Query("select * from MasterGroupInfo where MasterGroupID= :Id")
    LiveData<MasterGroupInfo> getMasterGroupInfoById(int Id);

    @Query("Delete From MasterGroupInfo")
    void deleteAll();


    @Query("Delete from MasterGroupInfo where MasterGroupID= :Id")
    void deleteById(int Id);

}
