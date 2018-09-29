package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.TestAllInfo;
import ir.saa.android.mt.model.entities.TestDtl;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface TestDtlDao {
    @Query("Select * From TestDtl")
    LiveData<List<TestDtl>> getTestDtls();

    @Query("Select * from TestDtl where TestDtlID=:Id")
    LiveData<TestDtl> getTestDtlById(Integer Id);

    @Query("Select * from TestDtl where TestInfoID=:Id")
    LiveData<List<TestDtl>> getTestDtlByTestInfoId(Integer Id);

    @Query("Delete From TestDtl")
    void deleteAll();

    @Query("Delete From TestDtl Where TestDtlID=:Id")
    void deleteTestDtlById(Integer Id);

    @Delete
    void deleteTestDtl(TestDtl testDtl);

    @Insert(onConflict = IGNORE)
    List<Long> insertTestDtls(List<TestDtl> testDtls);

    @Insert(onConflict = IGNORE)
    Long insertTestDtl(TestDtl testDtl);

    @Update
    void updateTestDtl(TestDtl testDtl);

    @Query("Select * from TestInfo " +
            "Inner Join TestDtl on TestDtl.TestInfoID=TestInfo.TestInfoID " +
            "where TestInfo.ClientID=:ClientId and TestInfo.SendID=:SendId")
    List<TestAllInfo> getTestAllInfoWithSendId(Long ClientId,Integer SendId);

    @Query("Select * from TestInfo " +
            "Inner Join TestDtl on TestDtl.TestInfoID=TestInfo.TestInfoID " +
            "where TestInfo.ClientID=:ClientId")
    List<TestAllInfo> getTestAllInfoWithClientId(Long ClientId);

    @Query("Select * from TestInfo " +
            "Inner Join TestDtl on TestDtl.TestInfoID=TestInfo.TestInfoID " +
            "where TestInfo.ClientID=:ClientId and TestInfo.SendID=:sendId and TestDtl.TestID=:testId")
    TestAllInfo getTestAllInfoWithTestId(Long ClientId,Integer sendId,Integer testId);
}
