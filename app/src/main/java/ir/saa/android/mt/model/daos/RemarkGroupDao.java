package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.RemarkGroup;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface RemarkGroupDao {

    @Query("Select * from RemarkGroup")
    LiveData<List<RemarkGroup>> getRemarkGroups();

    @Query("select * from RemarkGroup where FldID= :Id")
    LiveData<RemarkGroup> getRemarkGroupById(int Id);

    @Query("Delete From RemarkGroup")
    void deleteAll();

    @Query("Delete From RemarkGroup where FldID= :Id")
    void deleteById(int Id);

    @Insert(onConflict = IGNORE)
    void insertRemarkGroup(RemarkGroup remarkGroup);


    @Insert(onConflict = IGNORE)
    List<Long> insertRemarkGroups(List<RemarkGroup> remarkGroup);
    @Update
    void updateRemarkGroup(RemarkGroup remarkGroup);

    @Delete
    void deleteRemarkGroup(RemarkGroup remarkGroup);
}
