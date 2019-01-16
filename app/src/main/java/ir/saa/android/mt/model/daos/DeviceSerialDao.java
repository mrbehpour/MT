package ir.saa.android.mt.model.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import ir.saa.android.mt.model.entities.DeviceSerial;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface DeviceSerialDao {

    @Query("Select * from DeviceSerial")
    LiveData<List<DeviceSerial>> getDeviceSerials();

    @Query("Select * from DeviceSerial where SerialId= :SerialID")
    LiveData<DeviceSerial> getDeviceSerialByIdLiveData(String SerialID);

    @Query("Select * from DeviceSerial where SerialId= :SerialID")
    DeviceSerial getDeviceSerialById(String SerialID);

    @Insert(onConflict = IGNORE)
    void insertDeviceSerial(DeviceSerial deviceSerial);

    @Update
    void updateDeviceSerial(DeviceSerial deviceSerial);

    @Delete
    void deleteDeviceSerial(DeviceSerial deviceSerial);
}
