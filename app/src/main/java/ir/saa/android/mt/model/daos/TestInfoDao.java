package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.TestInfo;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface TestInfoDao {

    @Query("Select * from TestInfo")
    LiveData<List<TestInfo>> getTestInfos();

    @Query("Select * From TestInfo where TestInfoID=:Id")
    LiveData<TestInfo> getTestInfoById(Integer Id);

    @Query("Select * From TestInfo where ClientID=:clientId and SendID=:sendId")
    List<TestInfo> getTestInfoByClientId(Long clientId,Integer sendId);

    @Query("Select * From TestInfo where ClientID=:clientId and SendID=:sendId " +
            "and TestInfo.BlockID is not null " +
            "order by TestInfoID Desc")
    List<TestInfo> getTestInfoByClientIdWithBlockId(Long clientId,Integer sendId);

    @Query("Delete From TestInfo")
    void deleteAll();

    @Query("Delete From TestInfo where TestInfo.BlockID is null ")
    void deleteAllWithOutBlockId();

    @Query("Delete From TestInfo Where TestInfoID=:Id")
    void deleteTestInfoById(Integer Id);

    @Delete
    void deleteTestInfo(TestInfo testInfo);

    @Insert(onConflict = IGNORE)
    List<Long> insertTestInfos(List<TestInfo> testInfos);

    @Insert(onConflict = IGNORE)
    Long insertTestInfo(TestInfo testInfo);

    @Update
    void updateTestInfo(TestInfo testInfo);
}
