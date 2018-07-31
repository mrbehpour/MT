package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.PropertyType;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface PropertyTypeDao {
    @Query("select * from PropertyType")
    LiveData<List<PropertyType>> getPropertyTypes();

    @Query("Delete from PropertyType")
     void deleteAll();

    @Query("Select * from PropertyType where PropertyTypeID= :Id")
    LiveData<PropertyType> getPropertyTypeById(int Id);

    @Query("Delete From PropertyType where PropertyTypeID= :Id")
    void deleteById(int Id);

    @Delete
    void deletePropertyType(PropertyType propertyType);

    @Insert(onConflict = IGNORE)
    void insertPropertyType(PropertyType propertyType);

    @Insert(onConflict = IGNORE)
    List<Long> insertPropertyTypes(List<PropertyType> propertyTypes);

    @Update
    void updatePropertyType(PropertyType propertyType);
}
