package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.InspectionDtlDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.InspectionDtl;

public class InspectionDtlRepo implements InspectionDtlDao {

    private InspectionDtlDao inspectionDtlDao;
    private LiveData<List<InspectionDtl>> inspectionDtls;

    public InspectionDtlRepo(Application application){
        MTDatabase db=MTDatabase.getDatabase(application);
        inspectionDtlDao=db.inspectionDtlModel();
        inspectionDtls=inspectionDtlDao.getInspectionDtls();
    }

    public LiveData<List<InspectionDtl>> getInspectionDtls() {
        return inspectionDtls;
    }


    public LiveData<InspectionDtl> getInspectionDtlById(Integer Id) {
        return inspectionDtlDao.getInspectionDtlById(Id);
    }


    public LiveData<List<InspectionDtl>> getInspectionDtlByInspectionInfoId(Integer Id) {
        return inspectionDtlDao.getInspectionDtlByInspectionInfoId(Id);
    }


    public void deleteAll() {
        inspectionDtlDao.deleteAll();
    }


    public void deleteInspectionDtlById(Integer Id) {
        inspectionDtlDao.deleteInspectionDtlById(Id);
    }


    public void deleteInspectionDtl(InspectionDtl inspectionDtl) {
        inspectionDtlDao.deleteInspectionDtl(inspectionDtl);
    }


    public List<Long> insertInspectionDtls(List<InspectionDtl> inspectionDtls) {
        return inspectionDtlDao.insertInspectionDtls(inspectionDtls);
    }


    public void insertInspectionDtl(InspectionDtl inspectionDtl) {
        inspectionDtlDao.insertInspectionDtl(inspectionDtl);
    }


    public void updateInspectionDtl(InspectionDtl inspectionDtl) {
        inspectionDtlDao.updateInspectionDtl(inspectionDtl);
    }
}
