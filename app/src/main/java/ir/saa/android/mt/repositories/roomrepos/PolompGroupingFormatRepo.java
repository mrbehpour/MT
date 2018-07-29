package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.PolompGroupingFormatDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.PolompGroupingFormat;

public class PolompGroupingFormatRepo  {

    private PolompGroupingFormatDao polompGroupingFormatDao;
    private LiveData<List<PolompGroupingFormat>> PolompGroupingFormats;

    PolompGroupingFormatRepo(Application application){
        MTDatabase db=MTDatabase.getDatabase(application);
        polompGroupingFormatDao=db.polompGroupingFormatModel();
        PolompGroupingFormats=polompGroupingFormatDao.getPolompGroupingFormats();
    }

    public LiveData<List<PolompGroupingFormat>> getPolompGroupingFormats() {
        return PolompGroupingFormats;
    }


    public LiveData<PolompGroupingFormat> getPolompGroupingFormatById(int Id) {
        return polompGroupingFormatDao.getPolompGroupingFormatById(Id);
    }


    public LiveData<List<PolompGroupingFormat>> getPolompGroupingFormatByPolompGroupId(int Id) {
        return polompGroupingFormatDao.getPolompGroupingFormatByPolompGroupId(Id);
    }


    public LiveData<List<PolompGroupingFormat>> getPolompGroupingFormatByPolompId(int Id) {
        return polompGroupingFormatDao.getPolompGroupingFormatByPolompId(Id);
    }


    public LiveData<List<PolompGroupingFormat>> getPolompGroupingFormatByMasterGroupdtlId(int Id) {
        return polompGroupingFormatDao.getPolompGroupingFormatByPolompGroupId(Id);
    }


    public void deleteAll() {
        polompGroupingFormatDao.deleteAll();
    }


    public void deleteById(int Id) {
        polompGroupingFormatDao.deleteById(Id);
    }


    public void deletePolompGroupingFormat(PolompGroupingFormat polompGroupingFormat) {
        polompGroupingFormatDao.deletePolompGroupingFormat(polompGroupingFormat);
    }


    public void insertPolompGroupingFormat(PolompGroupingFormat polompGroupingFormat) {
        polompGroupingFormatDao.insertPolompGroupingFormat(polompGroupingFormat);
    }


    public List<Integer> insertPolompGroupingFormats(List<PolompGroupingFormat> polompGroupingFormats) {
        return polompGroupingFormatDao.insertPolompGroupingFormats(polompGroupingFormats);
    }


    public void updatePolompGroupingFormat(PolompGroupingFormat polompGroupingFormat) {
        polompGroupingFormatDao.updatePolompGroupingFormat(polompGroupingFormat);
    }
}
