package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.InspectionInfo;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface InspectionInfoDao {
    @Query("Select * from InspectionInfo")
    LiveData<List<InspectionInfo>> getInspectionInfos();

    @Query("Select * from InspectionInfo where InspectionInfoID=:Id")
    LiveData<InspectionInfo> getInspectionInfoById(Integer Id);

    @Query("Select * from InspectionInfo where ClientID=:clientId")
    List<InspectionInfo> getInspectionInfoByClientId(Long clientId);


    @Query("Delete  From InspectionInfo")
    void deleteAll();

    @Query("Delete from InspectionInfo where InspectionInfoID=:Id")
    void deleteInspectionInfoById(Integer Id);

    @Insert(onConflict = IGNORE)
    Long insertInspectionInfo(InspectionInfo inspectionInfo);

    @Insert(onConflict = IGNORE)
    List<Long> insertInspectionInfos(List<InspectionInfo> inspectionInfos);

    @Update
    int updateInspectionInfo(InspectionInfo inspectionInfo);

    @Delete
    void deleteInspectionInfo(InspectionInfo inspectionInfo);
}
