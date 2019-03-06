package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;

import java.util.List;

import ir.saa.android.mt.model.daos.MetersObisDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.MetersObis;

public class MetersObisRepo {

    private MetersObisDao metersObisDao;
    private List<MetersObis> lstMetersObis;

    public MetersObisRepo(Application application){
        MTDatabase db= MTDatabase.getDatabase(application);
        metersObisDao = db.metersObisModel();
        //listLiveData=digitalMetersDao.getDigitalMeters();
        lstMetersObis = metersObisDao.getMetersObis();
    }

    public List<MetersObis> getDigitalMeters() {
        return metersObisDao.getMetersObis();
    }

    public MetersObis getMetersObisByMeterSummaryNameLiveData(String MeterSummaryName) {
        return null;
    }

    public void insertDigitalMeters(MetersObis metersObis) {

    }

    public void updateDigitalMeters(MetersObis metersObis) {

    }

    public void deleteDigitalMeters(MetersObis metersObis) {

    }
}
