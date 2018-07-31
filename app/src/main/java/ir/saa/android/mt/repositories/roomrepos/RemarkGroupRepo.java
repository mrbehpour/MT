package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.RemarkGroupDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.RemarkGroup;

public class RemarkGroupRepo  {

    private RemarkGroupDao remarkGroupDao;
    private LiveData<List<RemarkGroup>> RemarkGrous;

    RemarkGroupRepo(Application application){
        MTDatabase db=MTDatabase.getDatabase(application);
        remarkGroupDao=db.remarkGroupModel();
        RemarkGrous=remarkGroupDao.getRemarkGroups();
    }
    public LiveData<List<RemarkGroup>> getRemarkGroups() {
        return RemarkGrous;
    }


    public LiveData<RemarkGroup> getRemarkGroupById(int Id) {
        return remarkGroupDao.getRemarkGroupById(Id);
    }


    public void deleteAll() {
        remarkGroupDao.deleteAll();
    }


    public void deleteById(int Id) {
        remarkGroupDao.deleteById(Id);
    }


    public void insertRemarkGroup(RemarkGroup remarkGroup) {
        remarkGroupDao.insertRemarkGroup(remarkGroup);
    }


    public void updateRemarkGroup(RemarkGroup remarkGroup) {
        remarkGroupDao.updateRemarkGroup(remarkGroup);
    }


    public void deleteRemarkGroup(RemarkGroup remarkGroup) {
        remarkGroupDao.updateRemarkGroup(remarkGroup);
    }

    public List<Long> insertremarkGroups(List<RemarkGroup> remarkGroups){
        return remarkGroupDao.insertRemarkGroups(remarkGroups);
    }
}
