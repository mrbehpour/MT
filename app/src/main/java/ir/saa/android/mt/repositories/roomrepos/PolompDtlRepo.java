package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.PolompDtlDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.PolompAllInfo;
import ir.saa.android.mt.model.entities.PolompDtl;

public class PolompDtlRepo  {

    private PolompDtlDao polompDtlDao;
    private LiveData<List<PolompDtl>> polompDtls;

    public PolompDtlRepo(Application application){
        MTDatabase db= MTDatabase.getDatabase(application);
        polompDtlDao=db.polompDtlModel();
        polompDtls=polompDtlDao.getPolompDtls();
    }

    public LiveData<List<PolompDtl>> getPolompDtls() {
        return polompDtls;
    }


    public LiveData<PolompDtl> getPolompDtlById(Integer Id) {
        return polompDtlDao.getPolompDtlById(Id);
    }


    public LiveData<List<PolompDtl>> getPolompDtlByPolompInfoId(Integer Id) {
        return polompDtlDao.getPolompDtlByPolompInfoId(Id);
    }


    public void deleteAll() {
        polompDtlDao.deleteAll();
    }


    public void deletePolompDtl(Integer Id) {
        polompDtlDao.deletePolompDtlById(Id);
    }


    public void deletePolompDtl(PolompDtl polompDtl) {
        polompDtlDao.deletePolompDtl(polompDtl);
    }


    public List<Long> insertPolompDtls(List<PolompDtl> polompDtls) {
        return polompDtlDao.insertPolompDtls(polompDtls);
    }


    public Long insertPolompDtl(PolompDtl polompDtl) {
        return polompDtlDao.insertPolompDtl(polompDtl);
    }


    public void updatePolompDtl(PolompDtl polompDtl) {
        polompDtlDao.updatePolompDtl(polompDtl);
    }

    public LiveData<PolompAllInfo> getPolompAllInfoLiveData(Long clientId, Integer polompId){
        return polompDtlDao.getPolompAllInfoLiveData(clientId,polompId);
    }

    public PolompAllInfo getPolompAllInfo(Long clientId, Integer polompId){
        return polompDtlDao.getPolompAllInfo(clientId,polompId);
    }

    public  List<PolompAllInfo> getPolompAllInfoWithSendId(Long ClientId, Integer SendId){
        return polompDtlDao.getPolompAllInfoWithSendId(ClientId,SendId);
    }

    public  List<PolompAllInfo> getPolompAllInfoWithClientId(Long ClientId){
        return polompDtlDao.getPolompAllInfoWithClientId(ClientId);
    }
}
