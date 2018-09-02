package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.PolompTypeDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.PolompType;

public class PolompTypeRepo  {

    private PolompTypeDao polompTypeDao;
    private LiveData<List<PolompType>> PolompTypes;

    public PolompTypeRepo(Application application){
        MTDatabase db= MTDatabase.getDatabase(application);
        polompTypeDao=db.polompTypeModel();
        PolompTypes=polompTypeDao.getPolompTypes();

    }

    public LiveData<List<PolompType>> getPolompTypes() {
        return PolompTypes;
    }


    public void insertPolompType(PolompType polompType) {
        polompTypeDao.insertPolompType(polompType);
    }


    public void deleteAll() {
        polompTypeDao.deleteAll();
    }
}
