package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.BazdidDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.Bazdid;

public class BazdidRepo  {

    private BazdidDao bazdidDao;
    private LiveData<List<Bazdid>> listLiveData;

    public BazdidRepo(Application application){
        MTDatabase db=MTDatabase.getDatabase(application);
        bazdidDao=db.bazdidModel();
        listLiveData=bazdidDao.getBazdids();

    }
    public LiveData<List<Bazdid>> getBazdids() {
        return listLiveData;
    }


    public Long insertBazdid(Bazdid bazdid) {
        return bazdidDao.insertBazdid(bazdid);
    }


    public void updateBazdid(Bazdid bazdid) {
          bazdidDao.updateBazdid(bazdid);
    }


    public Bazdid getBazdid(Long iD) {
        return bazdidDao.getBazdid(iD);
    }


    public void deleteBazdid(Bazdid bazdid) {
          bazdidDao.deleteBazdid(bazdid);
    }


    public void deleteAll() {
       bazdidDao.deleteAll();
    }


    public void deleteBazdidById(Long iD) {
             bazdidDao.deleteBazdidById(iD);
    }
}
