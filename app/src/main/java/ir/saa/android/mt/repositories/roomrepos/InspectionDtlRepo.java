package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.InspectionDtlDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.InspectionAllInfo;
import ir.saa.android.mt.model.entities.InspectionDtl;
import ir.saa.android.mt.model.entities.InspectionWithAnswerGroup;

public class InspectionDtlRepo  {

    private InspectionDtlDao inspectionDtlDao;
    private LiveData<List<InspectionDtl>> inspectionDtls;

    public InspectionDtlRepo(Application application){
        MTDatabase db= MTDatabase.getDatabase(application);
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


    public Long insertInspectionDtl(InspectionDtl inspectionDtl) {
        return inspectionDtlDao.insertInspectionDtl(inspectionDtl);
    }


    public int updateInspectionDtl(InspectionDtl inspectionDtl) {
       return inspectionDtlDao.updateInspectionDtl(inspectionDtl);
    }

    public InspectionWithAnswerGroup getInspectionAllInfo(Long clientId, Integer remarkId, Integer answerGroupId){
            return inspectionDtlDao.getInspectionAllInfo( clientId ,remarkId,answerGroupId);
    }

    public List<InspectionAllInfo> getInspectionAllInfoWithSendId(Long ClientId, Integer SendId){
        return inspectionDtlDao.getInspectionAllInfoWithSendId(ClientId,SendId);
    }

    public List<InspectionAllInfo> getInspectionAllInfoWithClientId(Long ClientId){
        return inspectionDtlDao.getInspectionAllInfoWithClientId(ClientId);
    }


}
