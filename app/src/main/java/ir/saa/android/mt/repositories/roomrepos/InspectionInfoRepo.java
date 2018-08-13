package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.InspectionInfoDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.InspectionInfo;

public class InspectionInfoRepo  {

    private InspectionInfoDao inspectionInfoDao;
    private LiveData<List<InspectionInfo>> inspectionInfos;

    public InspectionInfoRepo(Application application){
        MTDatabase db=MTDatabase.getDatabase(application);
        inspectionInfoDao=db.inspectionInfoModel();
        inspectionInfos=inspectionInfoDao.getInspectionInfos();
    }

    public LiveData<List<InspectionInfo>> getInspectionInfos() {
        return inspectionInfos;
    }


    public LiveData<InspectionInfo> getInspectionInfoById(Integer Id) {
        return inspectionInfoDao.getInspectionInfoById(Id);
    }


    public void deleteAll() {
        inspectionInfoDao.deleteAll();
    }


    public void deleteInspectionInfoById(Integer Id) {
        inspectionInfoDao.deleteInspectionInfoById(Id);

    }


    public void insertInspectionInfo(InspectionInfo inspectionInfo) {
        inspectionInfoDao.insertInspectionInfo(inspectionInfo);
    }


    public List<Long> insertInspectionInfos(List<InspectionInfo> inspectionInfos) {
        return inspectionInfoDao.insertInspectionInfos(inspectionInfos);
    }


    public void updateInspectionInfo(InspectionInfo inspectionInfo) {
        inspectionInfoDao.updateInspectionInfo(inspectionInfo);
    }


    public void deleteInspectionInfo(InspectionInfo inspectionInfo) {
        inspectionInfoDao.deleteInspectionInfo(inspectionInfo);
    }
}
