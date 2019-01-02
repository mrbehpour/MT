package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;


import java.util.List;

import ir.saa.android.mt.model.daos.DeviceSerialDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.DeviceSerial;

public class DeviceSerialRepo  {

    private DeviceSerialDao deviceSerialDao;
    private LiveData<List<DeviceSerial>> listLiveData;

    public DeviceSerialRepo(Application application){
        MTDatabase db=MTDatabase.getDatabase(application);
        deviceSerialDao=db.deviceSerialModel();
        listLiveData=deviceSerialDao.getDeviceSerials();
    }
    public LiveData<List<DeviceSerial>> getDeviceSerials() {
        return listLiveData;
    }


    public LiveData<DeviceSerial> getDeviceSerialById(String SerialID) {
        return deviceSerialDao.getDeviceSerialById(SerialID);
    }


    public void insertDeviceSerial(DeviceSerial deviceSerial) {
         deviceSerialDao.insertDeviceSerial(deviceSerial);
    }


    public void updateDeviceSerial(DeviceSerial deviceSerial) {
        deviceSerialDao.updateDeviceSerial(deviceSerial);
    }


    public void deleteDeviceSerial(DeviceSerial deviceSerial) {
          deviceSerialDao.deleteDeviceSerial(deviceSerial);
    }
}
