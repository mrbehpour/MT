package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.TariffTypeDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.TariffType;

public class TariffTypeRepo  {

    private TariffTypeDao tariffTypeDao;
    private LiveData<List<TariffType>> TariffTypes;

    TariffTypeRepo(Application application){
        MTDatabase db=MTDatabase.getDatabase(application);
        tariffTypeDao=db.tariffTypeModel();
        TariffTypes=tariffTypeDao.getTariffTypes();
    }

    public LiveData<List<TariffType>> getTariffTypes() {
        return TariffTypes;
    }


    public void deleteAll() {
        tariffTypeDao.deleteAll();
    }


    public LiveData<TariffType> getTariffTypeById(int Id) {
        return tariffTypeDao.getTariffTypeById(Id);
    }


    public void deleteById(int Id) {
        tariffTypeDao.deleteById(Id);
    }


    public void insertTariffType(TariffType tariffType) {
        tariffTypeDao.insertTariffType(tariffType);
    }


    public List<Long> insertTariffTypes(List<TariffType> tariffTypes) {
        return tariffTypeDao.insertTariffTypes(tariffTypes);
    }


    public void updateTariffType(TariffType tariffType) {
        tariffTypeDao.updateTariffType(tariffType);
    }


    public void deleteTariffType(TariffType tariffType) {
        tariffTypeDao.deleteTariffType(tariffType);
    }
}
