package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.DigitalMetersDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.DeviceSerial;
import ir.saa.android.mt.model.entities.DigitalMeters;
import ir.saa.android.mt.repositories.meterreader.MeterUtility;

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

    public List<DigitalMeters> getDigitalMeters() {
        return listDigitalMeters;
    }

    public DigitalMeters getDigitalMetersByMeterSummaryName(String MeterString) {
        return digitalMetersDao.getDigitalMetersByMeterSummaryName(MeterString);
    }

    public List<MeterUtility.MeterStringInfo> getAllDigitalMetersString() {
        return digitalMetersDao.getAllDigitalMetersString();
    }

    public int getDigitalMetersCount() {
        return digitalMetersDao.getDigitalMetersCount();
    }

    public void insertDigitalMeters(DigitalMeters digitalMeters) {
        digitalMetersDao.insertDigitalMeters(digitalMeters);
    }

    public void updateDigitalMeters(DigitalMeters digitalMeters) {

    }

    public void deleteDigitalMeters(DigitalMeters digitalMeters) {

    }

    public void deleteAllDigitalMeters() {
        digitalMetersDao.deleteAllDigitalMeters();
    }
}
