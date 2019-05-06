package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.Region;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface RegionDao {
    @Query("SELECT * FROM Region where RegionID<>-1")
    LiveData<List<Region>> getRegions();

    @Query("Delete From Region")
    void deleteAll();

    @Query("select * from Region where RegionID= :Id")
    Region getRegionById(int Id);

    @Query("select * from Region where RegionID= :Id")
    LiveData<Region> getRegionByIdLiveData(int Id);

    @Query("select * from Region where company_Id= :Id")
    LiveData<List<Region>> getRegionByCompanyId(int Id);

    @Query("Delete from Region where RegionID= :Id")
    void deleteById(int Id);

    @Delete
    void deleteRegion(Region region);

    @Insert(onConflict =IGNORE)
    void insertRegion(Region region);

    @Insert(onConflict =IGNORE)
    List<Long> insertRegions(List<Region> regions);

    @Update
    void updateRegion(Region region);
}

