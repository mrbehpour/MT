package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.City;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface CityDao {

    @Query("select * from City")
    LiveData<List<City>> getCities();

    @Insert(onConflict = IGNORE)
    void insertCity(City city);

    @Insert(onConflict = IGNORE)
    List<Long> insertCities(List<City> cities);

    @Update
    void updateCity(City city);


    @Delete
    void deleteCity(City city);

    @Query("select * from City where CityCode= :cityCode")
    LiveData<City> getCityById(int cityCode);

    @Query("Delete From City")
    void deleteAll();

    @Query("Delete from City where CityCode= :cityCode")
    void deleteById(int cityCode);

    @Query("select * from City where region_id= :regionId")
    LiveData<List<City>> getCityByRegionId(int regionId);
}
