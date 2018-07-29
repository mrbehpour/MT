package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.GroupingFormat;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface GroupingFormatDao {

    @Query("select * from GroupingFormat")
    LiveData<List<GroupingFormat>> getGroupingFormats();

    @Query("select * from GroupingFormat where GroupingFormatID= :Id")
    LiveData<GroupingFormat> getGroupingFormatById(Integer Id);

    @Query("select * from GroupingFormat where mastergroupdetail_id= :Id")
    LiveData<List<GroupingFormat>>  getGroupingFormatByMasterGroupId(int Id);

    @Query("select * from GroupingFormat where remark_id= :Id")
    LiveData<List<GroupingFormat>> getGroupingFormatByRemarkId(int Id);


    @Query("Select * from GroupingFormat where remarkgroup_id= :Id")
    LiveData<List<GroupingFormat>> getGroupingFormatByRemarkgroupId(int Id);

    @Query("Delete from GroupingFormat")
    void deleteAll();

    @Query("Delete from GroupingFormat where GroupingFormatID= :Id")
    void deleteById(int Id);

    @Insert(onConflict = IGNORE)
    void insertGroupingFormat(GroupingFormat groupingFormat);

    @Update
    void updateGroupingFormat(GroupingFormat groupingFormat);

    @Delete
    void deleteGroupingFormat(GroupingFormat groupingFormat);
}
