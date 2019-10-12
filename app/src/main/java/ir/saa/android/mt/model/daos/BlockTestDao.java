package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.BlockTest;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface BlockTestDao {

    @Query("Select * from  BlockTest")
    LiveData<List<BlockTest>> getBlockTestLiveData();

    @Query("Select * from  BlockTest")
    List<BlockTest> getBlockTest();

    @Query("Select * from BlockTest Where BlockTest.ClientId=:clientId ")
    List<BlockTest> getBlockTestByClientId(Long clientId);

    @Query("Select * from BlockTest Where BlockTest.ClientId=:clientId ")
    LiveData<List<BlockTest>> getBlockTestByClientIdLiveData(Long clientId);

    @Insert(onConflict = IGNORE)
    long insertBlockTest(BlockTest blockTest);

    @Update
    void updateBlockTest(BlockTest blockTest);

    @Delete
    void  deleteBlockTest(BlockTest blockTest);

    @Query("Delete From BlockTest")
    void deleteAll();

    @Query("Delete from BlockTest where BlockTest.BlockTestId=:id")
    void deleteById(int id);

    @Query("Delete from BlockTest where BlockTest.ClientId=:clientId")
    void deleteByClientId(Long clientId);

}
