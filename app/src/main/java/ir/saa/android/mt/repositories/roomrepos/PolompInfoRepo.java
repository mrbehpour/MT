package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.PolompInfoDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.InspectionInfo;
import ir.saa.android.mt.model.entities.PolompInfo;

public class PolompInfoRepo  {

    private PolompInfoDao polompInfoDao;
    private LiveData<List<PolompInfo>> polompInfos;

    public PolompInfoRepo(Application application){
        MTDatabase db= MTDatabase.getDatabase(application);
        polompInfoDao=db.polompInfoModel();
        polompInfos=polompInfoDao.getPolompInfos();
    }

    public LiveData<List<PolompInfo>> getPolompInfos() {
        return polompInfos;
    }


    public LiveData<PolompInfo> getPolompInfoById(Integer Id) {
        return polompInfoDao.getPolompInfoById(Id);
    }


    public void deleteAll() {
        polompInfoDao.deleteAll();
    }


    public void deletePolompInfoById(Integer Id) {
        polompInfoDao.deletePolompInfoById(Id);
    }


    public void deletePolompInfo(PolompInfo polompInfo) {
        polompInfoDao.deletePolompInfo(polompInfo);
    }

    public void deleteByClientId(Long ClientId){
        polompInfoDao.deleteByClientId(ClientId);
    }


    public List<Long> insertPolompInfos(List<PolompInfo> polompInfos) {
        return polompInfoDao.insertPolompInfos(polompInfos);
    }


    public Long insertPolompInfo(PolompInfo polompInfo) {
        return polompInfoDao.insertPolompInfo(polompInfo);
    }


    public void updatePolompInfo(PolompInfo polompInfo) {
        polompInfoDao.updatePolompInfo(polompInfo);
    }


}
