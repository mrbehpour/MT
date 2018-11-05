package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.Remark;
import ir.saa.android.mt.model.entities.RemarkGroupingFormat;
import retrofit2.http.DELETE;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface RemarkDao {

    @Query("select * from Remark")
    LiveData<List<Remark>> getRemarks();

    @Query("select * from Remark where RemarkID= :Id")
    LiveData<Remark> getRemarkById(int Id);

    @Query("select *  from Remark where PropertyTypeID= :Id")
    LiveData<List<Remark>> getRemarkByPropertyId(int Id);

    @Query("select * from Remark where AnswerGroupID= :Id")
    LiveData<List<Remark>> getRemarkByAnswerGroupId(int Id);

    @Query("select * from Remark where RemarkTypeID= :Id")
    LiveData<List<Remark>> getRemarkByRemarkTypeId(int Id);

    @Query("Delete From Remark")
    void deleteAll();

    @Query("Delete From Remark where RemarkID= :Id")
    void deleteById(int Id);

    @Insert(onConflict = IGNORE)
    void insertRemark(Remark remark);

    @Insert(onConflict = IGNORE)
    List<Long> insertRemarks(List<Remark> remarks);

    @Update
    void updateRemark(Remark remark);

    @Delete
    void deleteRemark(Remark remark);

    @Query("Select * from Remark" +
            " inner join GroupingFormat on" +
            " Remark.RemarkID=GroupingFormat.remark_id" +
            " Where GroupingFormat.mastergroupdetail_id=:Id and GroupingFormat.remarkgroup_id=:remarkgroupId")
    LiveData<List<RemarkGroupingFormat>> getRemarkGroupingFormat(Integer Id,Integer remarkgroupId);



}
