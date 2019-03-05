package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.DigitalMetersDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.DeviceSerial;
import ir.saa.android.mt.model.entities.DigitalMeters;

public class DigitalMetersRepo  {

    private DigitalMetersDao digitalMetersDao;
    private LiveData<List<DigitalMeters>> listLiveData;
    private List<DigitalMeters> listDigitalMeters;

    public DigitalMetersRepo(Application application){
        MTDatabase db=MTDatabase.getDatabase(application);
        digitalMetersDao=db.digitalMetersModel();
        //listLiveData=digitalMetersDao.getDigitalMeters();
        listDigitalMeters=digitalMetersDao.getDigitalMeters();
    }

//    public LiveData<List<DigitalMeters>> getDigitalMeters() {
//        return listLiveData;
//    }

    public List<DigitalMeters> getDigitalMeters() {
        return listDigitalMeters;
    }

    public LiveData<DigitalMeters> getDigitalMetersByMeterStringLiveData(String MeterString) {
        return digitalMetersDao.getDigitalMetersByMeterStringLiveData(MeterString);
    }

    public void insertDigitalMeters(DigitalMeters digitalMeters) {
        digitalMetersDao.insertDigitalMeters(digitalMeters);
    }

    public void updateDigitalMeters(DigitalMeters digitalMeters) {

    }

    public void deleteDigitalMeters(DigitalMeters digitalMeters) {

    }
}
