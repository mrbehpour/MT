package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.RemarkDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.Remark;
import ir.saa.android.mt.model.entities.RemarkGroupingFormat;

public class RemarkRepo  {

    private RemarkDao remarkDao;
    private LiveData<List<Remark>> Remarks;

    public RemarkRepo(Application application){
        MTDatabase db= MTDatabase.getDatabase(application);
        remarkDao=db.remarkModel();
        Remarks=remarkDao.getRemarks();
    }

    public LiveData<List<Remark>> getRemarks() {
        return Remarks;
    }


    public LiveData<Remark> getRemarkById(int Id) {
        return remarkDao.getRemarkById(Id);
    }


    public LiveData<List<Remark>> getRemarkByPropertyId(int Id) {
        return remarkDao.getRemarkByPropertyId(Id);
    }

    public LiveData<List<Remark>> getManehTestAndBazrasi(String KeyValue){
        return remarkDao.getManehTestAndBazrasi(KeyValue);
    }

    public LiveData<List<Remark>> getRemarkByAnswerGroupId(int Id) {
        return getRemarkByAnswerGroupId(Id);
    }


    public LiveData<List<Remark>> getRemarkByRemarkTypeId(int Id) {
        return remarkDao.getRemarkByRemarkTypeId(Id);
    }


    public void deleteAll() {
        remarkDao.deleteAll();
    }


    public void deleteById(int Id) {
        remarkDao.deleteById(Id);
    }


    public void insertRemark(Remark remark) {
        remarkDao.insertRemark(remark);
    }


    public List<Long> insertRemarks(List<Remark> remarks) {
        return remarkDao.insertRemarks(remarks);
    }


    public void updateRemark(Remark remark) {
        remarkDao.updateRemark(remark);

    }


    public void deleteRemark(Remark remark) {
        remarkDao.deleteRemark(remark);
    }

    public LiveData<List<RemarkGroupingFormat>> getRemarkGroupingFormat(Integer Id, Integer remarkTypeId){
        return remarkDao.getRemarkGroupingFormat(Id,remarkTypeId);
    }

    public LiveData<List<RemarkGroupingFormat>> getRemarkGroupingFormatForMoshahedat(Integer Id, Integer remarkTypeId){
        return remarkDao.getRemarkGroupingFormatForMoshahedat(Id,remarkTypeId);
    }
}
