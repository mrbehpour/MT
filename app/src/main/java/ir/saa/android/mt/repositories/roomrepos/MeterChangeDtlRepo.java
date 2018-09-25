package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.MeterChangeDtlDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.MeterChangeDtl;

public class MeterChangeDtlRepo  {

    private MeterChangeDtlDao meterChangeDtlDao;
    private LiveData<List<MeterChangeDtl>> meterChangeDtls;

    public MeterChangeDtlRepo(Application application){
        MTDatabase db= MTDatabase.getDatabase(application);
        meterChangeDtlDao=db.meterChangeDtlModel();
        meterChangeDtls=meterChangeDtlDao.getMeterChangeDtl();
    }


    public LiveData<List<MeterChangeDtl>> getMeterChangeDtl() {
        return meterChangeDtls;
    }


    public LiveData<MeterChangeDtl> getMeterChangeDtlById(Integer Id) {
        return meterChangeDtlDao.getMeterChangeDtlById(Id);
    }


    public LiveData<List<MeterChangeDtl>> getMeterChangeDtlByMeterChangeInfoId(Integer Id) {
        return meterChangeDtlDao.getMeterChangeDtlByMeterChangeInfoId(Id);
    }


    public LiveData<List<MeterChangeDtl>> getMeterChangeDtlByMeterChangeID(Integer Id) {
        return meterChangeDtlDao.getMeterChangeDtlByMeterChangeID(Id);
    }


    public void deleteAll() {
        meterChangeDtlDao.deleteAll();
    }


    public void deleteMeterChangeDtlById(Integer Id) {
        meterChangeDtlDao.deleteMeterChangeDtlById(Id);
    }


    public void deleteMeterChangeDtl(MeterChangeDtl meterChangeDtl) {
        meterChangeDtlDao.deleteMeterChangeDtl(meterChangeDtl);
    }


    public List<Long> insertMeterChangeDtls(List<MeterChangeDtl> meterChangeDtls) {
        return meterChangeDtlDao.insertMeterChangeDtls(meterChangeDtls);
    }


    public void insertMeterChangeDtl(MeterChangeDtl meterChangeDtl) {
        meterChangeDtlDao.insertMeterChangeDtl(meterChangeDtl);
    }


    public void updateMeterChangeDtl(MeterChangeDtl meterChangeDtl) {
        meterChangeDtlDao.updateMeterChangeDtl(meterChangeDtl);
    }
}
