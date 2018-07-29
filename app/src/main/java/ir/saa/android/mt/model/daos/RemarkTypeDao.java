package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.RemarkType;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface RemarkTypeDao {

    @Query("select * from RemarkType")
    LiveData<List<RemarkType>> getRemarkTypes();

    @Query("Delete from RemarkType")
    void deleteAll();

    @Query("select * from RemarkType where FldID= :Id")
    LiveData<RemarkType> getRemarkTypeById(int Id);

    @Query("Delete From RemarkType where FldID= :Id")
    void deleteById(int Id);

    @Insert(onConflict = IGNORE)
    void insertRemarkType(RemarkType remarkType);

    @Update
    void updateRemarkType(RemarkType remarkType);

    @Delete
    void deleteRemarkType(RemarkType remarkType);
}
