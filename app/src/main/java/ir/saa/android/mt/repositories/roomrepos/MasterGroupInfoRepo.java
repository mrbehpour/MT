package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.MasterGroupInfoDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.MasterGroupInfo;

public class MasterGroupInfoRepo {

    private MasterGroupInfoDao masterGroupInfoDao;
    private LiveData<List<MasterGroupInfo>> MasterGroupInfos;

    public MasterGroupInfoRepo(Application application){
        MTDatabase db= MTDatabase.getDatabase(application);
        masterGroupInfoDao=db.masterGroupInfoModel();
        MasterGroupInfos =masterGroupInfoDao.getMasterGroupInfos();
    }

    public LiveData<List<MasterGroupInfo>> getMasterGroupInfos() {
        return MasterGroupInfos;
    }


    public void deleteMasterGroupInfo(MasterGroupInfo masterGroupInfo) {
        masterGroupInfoDao.deleteMasterGroupInfo(masterGroupInfo);
    }


    public void updateMasterGroupInfo(MasterGroupInfo masterGroupInfo) {
        masterGroupInfoDao.updateMasterGroupInfo(masterGroupInfo);
    }


    public void insertMasterGroupInfo(MasterGroupInfo masterGroupInfo) {
        masterGroupInfoDao.insertMasterGroupInfo(masterGroupInfo);
    }


    public List<Long> insertMasterGroupInfos(List<MasterGroupInfo> masterGroupInfos) {
        return masterGroupInfoDao.insertMasterGroupInfos(masterGroupInfos);
    }


    public LiveData<MasterGroupInfo> getMasterGroupInfoById(int Id) {
        return masterGroupInfoDao.getMasterGroupInfoById(Id);
    }


    public void deleteAll() {
        masterGroupInfoDao.deleteAll();
    }


    public void deleteById(int Id) {
        masterGroupInfoDao.deleteById(Id);

    }
}
