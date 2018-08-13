package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.InspectionDtl;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface InspectionDtlDao {
    @Query("Select * from InspectionDtl")
    LiveData<List<InspectionDtl>> getInspectionDtls();

    @Query("Select * from InspectionDtl where InspectionDtlID=:Id")
    LiveData<InspectionDtl> getInspectionDtlById(Integer Id);

    @Query("Select * from InspectionDtl where InspectionInfoID=:Id")
    LiveData<List<InspectionDtl>> getInspectionDtlByInspectionInfoId(Integer Id);

    @Query("Delete From InspectionDtl")
    void deleteAll();

    @Query("Delete From InspectionDtl where InspectionDtlID=:Id")
    void deleteInspectionDtlById(Integer Id);

    @Delete
    void deleteInspectionDtl(InspectionDtl inspectionDtl);

    @Insert(onConflict = IGNORE)
    List<Integer> insertInspectionDtls(List<InspectionDtl> inspectionDtls);

    @Insert(onConflict = IGNORE)
    void insertInspectionDtl(InspectionDtl inspectionDtl);

    @Update
    void updateInspectionDtl(InspectionDtl inspectionDtl);
}
