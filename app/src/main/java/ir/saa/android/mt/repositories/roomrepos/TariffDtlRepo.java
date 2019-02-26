package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.TariffDtlDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.TariffAllInfo;
import ir.saa.android.mt.model.entities.TariffDtl;

public class TariffDtlRepo  {

    private TariffDtlDao tariffDtlDao;
    private LiveData<List<TariffDtl>> tariffDtls;

    public TariffDtlRepo(Application application){
        MTDatabase db= MTDatabase.getDatabase(application);
        tariffDtlDao=db.tariffDtlModel();
        tariffDtls=tariffDtlDao.getTariffDtls();
    }

    public LiveData<List<TariffDtl>> getTariffDtls() {
        return tariffDtls;
    }


    public LiveData<TariffDtl> getTariffDtl(Integer Id) {
        return tariffDtlDao.getTariffDtlById(Id);
    }


    public LiveData<List<TariffDtl>> getTariffDtlByTariffInfoId(Integer Id) {
        return tariffDtlDao.getTariffDtlByTariffInfoId(Id);
    }


    public void deleteAll() {
        tariffDtlDao.deleteAll();
    }


    public void deleteTariffDtlById(Integer Id) {
        tariffDtlDao.deleteTariffDtlById(Id);
    }


    public void deleteTariffDtl(TariffDtl tariffDtl) {
        tariffDtlDao.deleteTariffDtl(tariffDtl);
    }


    public List<Long> insertTariffDtls(List<TariffDtl> tariffDtls) {
        return tariffDtlDao.insertTariffDtls(tariffDtls);
    }


    public void insertTariffDtl(TariffDtl tariffDtl) {
        tariffDtlDao.insertTariffDtl(tariffDtl);
    }


    public void updateTariffDtl(TariffDtl tariffDtl) {
        tariffDtlDao.updateTariffDtl(tariffDtl);
    }

    public List<TariffAllInfo>  getTariffAllInfo(Long clientId, Integer sendId){
        return tariffDtlDao.getTariffAllInfo(clientId,sendId);
    }
}
