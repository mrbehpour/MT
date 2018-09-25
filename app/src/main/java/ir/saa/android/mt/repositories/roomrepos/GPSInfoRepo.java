package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.GPSInfoDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.GPSInfo;

public class GPSInfoRepo  {

    private GPSInfoDao gpsInfoDao;
    private LiveData<List<GPSInfo>> gPSInfos;

    public GPSInfoRepo(Application application){
        MTDatabase db= MTDatabase.getDatabase(application);
        gpsInfoDao=db.gpsInfoModel();
        gPSInfos=gpsInfoDao.getGPSInfos();
    }

    public LiveData<List<GPSInfo>> getGPSInfos() {
        return gPSInfos;
    }


    public LiveData<GPSInfo> getGPSInfoById(Integer Id) {
        return gpsInfoDao.getGPSInfoById(Id);
    }


    public void deleteGPSInfo() {
        gpsInfoDao.deleteAll();
    }


    public void deleteGPSInfoById(Integer Id) {
        gpsInfoDao.deleteGPSInfoById(Id);
    }


    public void deleteGPSInfo(GPSInfo gpsInfo) {
        gpsInfoDao.deleteGPSInfo(gpsInfo);
    }


    public List<Long> insertGPSInfos(List<GPSInfo> gpsInfos) {
        return gpsInfoDao.insertGPSInfos(gpsInfos);
    }


    public void insertGPSInfo(GPSInfo gpsInfo) {
        gpsInfoDao.insertGPSInfo(gpsInfo);
    }


    public void updateGPSInfo(GPSInfo gpsInfo) {
        gpsInfoDao.updateGPSInfo(gpsInfo);
    }
}
