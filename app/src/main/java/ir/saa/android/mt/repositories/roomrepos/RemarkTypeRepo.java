package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.model.daos.RemarkDao;
import ir.saa.android.mt.model.daos.RemarkTypeDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.Remark;
import ir.saa.android.mt.model.entities.RemarkType;

public class RemarkTypeRepo  {
    private RemarkTypeDao remarkTypeDao;
    private LiveData<List<RemarkType>> RemarkTypes;

    RemarkTypeRepo(Application application){
        MTDatabase db=MTDatabase.getDatabase(application);
        remarkTypeDao=db.remarkTypeModel();
        RemarkTypes=remarkTypeDao.getRemarkTypes();
    }

    public LiveData<List<RemarkType>> getRemarkTypes() {
        return RemarkTypes;
    }


    public void deleteAll() {
        remarkTypeDao.deleteAll();
    }


    public LiveData<RemarkType> getRemarkTypeById(int Id) {
        return remarkTypeDao.getRemarkTypeById(Id);
    }


    public void deleteById(int Id) {
        remarkTypeDao.deleteById(Id);
    }


    public void insertRemarkType(RemarkType remarkType) {
        remarkTypeDao.insertRemarkType(remarkType);
    }


    public void updateRemarkType(RemarkType remarkType) {
        remarkTypeDao.updateRemarkType(remarkType);
    }


    public void deleteRemarkType(RemarkType remarkType) {
        remarkTypeDao.deleteRemarkType(remarkType);
    }

    public List<Long> insertRemarkTypes(List<RemarkType> remarkTypes){
        return  remarkTypeDao.insertRemarkTypes(remarkTypes);
    }
}
