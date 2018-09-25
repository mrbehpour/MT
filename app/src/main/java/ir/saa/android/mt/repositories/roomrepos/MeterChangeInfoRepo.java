package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.MeterChangeInfoDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.MeterChangeInfo;

public class MeterChangeInfoRepo implements MeterChangeInfoDao {

    private MeterChangeInfoDao meterChangeInfoDao;
    private LiveData<List<MeterChangeInfo>> meterChangeInfos;

    public MeterChangeInfoRepo(Application application){
        MTDatabase db= MTDatabase.getDatabase(application);
        meterChangeInfoDao=db.meterChangeInfoModel();
        meterChangeInfos=meterChangeInfoDao.getMeterChangeInfos();
    }
    @Override
    public LiveData<List<MeterChangeInfo>> getMeterChangeInfos() {
        return meterChangeInfos;
    }

    @Override
    public LiveData<MeterChangeInfo> getMeterChangeInfo(Integer Id) {
        return meterChangeInfoDao.getMeterChangeInfo(Id);
    }

    @Override
    public void deleteAll() {
        meterChangeInfoDao.deleteAll();
    }

    @Override
    public void deleteMeterChangeInfoById(Integer Id) {
        meterChangeInfoDao.deleteMeterChangeInfoById(Id);
    }

    @Override
    public void deleteMeterChangeInfo(MeterChangeInfo meterChangeInfo) {
        meterChangeInfoDao.deleteMeterChangeInfo(meterChangeInfo);
    }

    @Override
    public List<Long> insertMeterChangeInfos(List<MeterChangeInfo> meterChangeInfos) {
        return meterChangeInfoDao.insertMeterChangeInfos(meterChangeInfos);
    }

    @Override
    public void insertMeterChangeInfo(MeterChangeInfo meterChangeInfo) {
        meterChangeInfoDao.insertMeterChangeInfo(meterChangeInfo);
    }

    @Override
    public void updateMeterChangeInfo(MeterChangeInfo meterChangeInfo) {
        meterChangeInfoDao.updateMeterChangeInfo(meterChangeInfo);

    }
}
