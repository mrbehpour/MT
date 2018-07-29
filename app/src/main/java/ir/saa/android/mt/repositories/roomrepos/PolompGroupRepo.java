package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.PolompGroupDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.PolompGroup;

public class PolompGroupRepo {

    private PolompGroupDao polompGroupDao;
    private LiveData<List<PolompGroup>> PolompGroups;

    PolompGroupRepo(Application application){
        MTDatabase db=MTDatabase.getDatabase(application);
        polompGroupDao=db.polompGroupModel();
        PolompGroups=polompGroupDao.getPolompGroups();
    }

    public LiveData<List<PolompGroup>> getPolompGroups() {
        return PolompGroups;
    }


    public LiveData<PolompGroup> getPolompGroupById(int Id) {
        return polompGroupDao.getPolompGroupById(Id);
    }


    public void deleteAll() {
        polompGroupDao.deleteAll();
    }


    public void deleteById(int Id) {
        polompGroupDao.deleteById(Id);
    }


    public void insertPolompGroup(PolompGroup polompGroup) {
        polompGroupDao.insertPolompGroup(polompGroup);
    }


    public List<Integer> insertPolompGroups(List<PolompGroup> polompGroups) {
        return polompGroupDao.insertPolompGroups(polompGroups);
    }


    public void updatePolompGroup(PolompGroup polompGroup) {
        polompGroupDao.updatePolompGroup(polompGroup);
    }


    public void deletePolompGroup(PolompGroup polompGroup) {
        polompGroupDao.deletePolompGroup(polompGroup);
    }
}
