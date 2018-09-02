package ir.saa.android.mt.repositories.roomrepos;


import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.PolompColorDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.PolompColor;


public class PolompColorRepo  {

    private PolompColorDao polompColorDao;
    private LiveData<List<PolompColor>> PolompColors;

    public PolompColorRepo(Application application){
        MTDatabase db= MTDatabase.getDatabase(application);

        polompColorDao=db.polompColorModel();

        PolompColors=polompColorDao.getPolompColors();
    }

    public LiveData<List<PolompColor>> getPolompColors() {
        return PolompColors;
    }


    public void insertPolompColor(PolompColor polompColor) {
        polompColorDao.insertPolompColor(polompColor);
    }


    public void deleteAll() {
        polompColorDao.deleteAll();
    }
}
