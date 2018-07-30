package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.PolompDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.Polomp;

public class PolompRepo  {

    private PolompDao polompDao;
    private LiveData<List<Polomp>> Polomps;

    PolompRepo(Application application){
        MTDatabase db=MTDatabase.getDatabase(application);
        polompDao=db.polompModel();
        Polomps=polompDao.getPolomps();
    }

    public LiveData<List<Polomp>> getPolomps() {
        return Polomps;
    }


    public LiveData<Polomp> getPolompById(int Id) {
        return polompDao.getPolompById(Id);
    }


    public void deleteAll() {
        polompDao.deleteAll();
    }


    public void deleteById(int Id) {
        polompDao.deleteById(Id);
    }


    public void insertPolomp(Polomp polomp) {
        polompDao.insertPolomp(polomp);
    }


    public List<Long> insertPolomps(List<Polomp> polomps) {
        return polompDao.insertPolomps(polomps);
    }


    public void deletePolomp(Polomp polomp) {
        polompDao.deletePolomp(polomp);
    }


    public void updatePolomp(Polomp polomp) {
        polompDao.updatePolomp(polomp);
    }
}
