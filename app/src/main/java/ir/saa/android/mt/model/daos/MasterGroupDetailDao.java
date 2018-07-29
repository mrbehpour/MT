package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.MasterGroupDetail;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface MasterGroupDetailDao {

    @Query("select * from MasterGroupDetail")
    LiveData<List<MasterGroupDetail>> getMasterGroupDetails();

    @Insert(onConflict = IGNORE)
    void insertMasterGroupDetail(MasterGroupDetail masterGroupDetail);

    @Insert(onConflict = IGNORE)
    List<Integer> insertMasterGroupDetails(List<MasterGroupDetail> masterGroupDetails);

    @Update
    void updateMasterGroupDetail(MasterGroupDetail masterGroupDetail);

    @Delete
    void deleteMasterGroupDetail(MasterGroupDetail masterGroupDetail);


    @Query("select * from MasterGroupDetail where MasterGroupDtlID= :Id")
    LiveData<MasterGroupDetail> getMasterGroupDetailById(int Id);

    @Query("select * from MasterGroupDetail where mastergroupinf_id= :Id")
    LiveData<List<MasterGroupDetail>> getMasterGroupDetailByMasterGroupInfoId(int Id);

    @Query("Delete from MasterGroupDetail where MasterGroupDtlID= :Id")
    void deleteMasterGroupDetailById(int Id);

    @Query("delete from MasterGroupDetail")
    void deleteAll();
}
