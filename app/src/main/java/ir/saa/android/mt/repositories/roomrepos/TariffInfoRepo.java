package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.TariffInfoDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.TariffInfo;

public class TariffInfoRepo  {

    private TariffInfoDao tariffInfoDao;
    private LiveData<List<TariffInfo>> tariffInfos;

    public TariffInfoRepo(Application application){
        MTDatabase db=MTDatabase.getDatabase(application);
        tariffInfoDao=db.tariffInfoModel();
        tariffInfos=tariffInfoDao.getTariffInfos();
    }

    public LiveData<List<TariffInfo>> getTariffInfos() {
        return tariffInfos;
    }


    public LiveData<TariffInfo> getTariffInfoById(Integer Id) {
        return tariffInfoDao.getTariffInfoById(Id);
    }


    public void deleteAll() {
        tariffInfoDao.deleteAll();
    }


    public void deleteTariffInfoById(Integer Id) {
        tariffInfoDao.deleteTariffInfoById(Id);
    }


    public void deleteTariffInfo(TariffInfo tariffInfo) {
        tariffInfoDao.deleteTariffInfo(tariffInfo);
    }


    public List<Long> insertTariffInfos(List<TariffInfo> tariffInfos) {
        return tariffInfoDao.insertTariffInfos(tariffInfos);
    }


    public void insertTariffInfo(TariffInfo tariffInfo) {
        tariffInfoDao.insertTariffInfo(tariffInfo);
    }


    public void updateTariffInfo(TariffInfo tariffInfo) {
        tariffInfoDao.updateTariffInfo(tariffInfo);

    }
}
