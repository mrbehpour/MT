package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.MasterGroupDetailDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.MasterGroupDetail;

public class MasterGroupDetailRepo  {

    private MasterGroupDetailDao masterGroupDetailDao;

    private LiveData<List<MasterGroupDetail>> MasterGroupDetails;

    public MasterGroupDetailRepo(Application application){
        MTDatabase db=MTDatabase.getDatabase(application);
        masterGroupDetailDao=db.masterGroupDetailModel();
        MasterGroupDetails=masterGroupDetailDao.getMasterGroupDetails();
    }

    public LiveData<List<MasterGroupDetail>> getMasterGroupDetails() {
        return MasterGroupDetails;
    }


    public void insertMasterGroupDetail(MasterGroupDetail masterGroupDetail) {
        masterGroupDetailDao.insertMasterGroupDetail(masterGroupDetail);
    }


    public List<Long> insertMasterGroupDetails(List<MasterGroupDetail> masterGroupDetails) {
        return masterGroupDetailDao.insertMasterGroupDetails(masterGroupDetails);
    }


    public void updateMasterGroupDetail(MasterGroupDetail masterGroupDetail) {
        masterGroupDetailDao.updateMasterGroupDetail(masterGroupDetail);
    }


    public void deleteMasterGroupDetail(MasterGroupDetail masterGroupDetail) {
        masterGroupDetailDao.deleteMasterGroupDetail(masterGroupDetail);
    }


    public LiveData<MasterGroupDetail> getMasterGroupDetailById(int Id) {
        return masterGroupDetailDao.getMasterGroupDetailById(Id);
    }


    public LiveData<List<MasterGroupDetail>> getMasterGroupDetailByMasterGroupInfoId(int Id) {
        return masterGroupDetailDao.getMasterGroupDetailByMasterGroupInfoId(Id);
    }


    public void deleteMasterGroupDetailById(int Id) {
        masterGroupDetailDao.deleteMasterGroupDetailById(Id);
    }


    public void deleteAll() {
        masterGroupDetailDao.deleteAll();
    }
}
