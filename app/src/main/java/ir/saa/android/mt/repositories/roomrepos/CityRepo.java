package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.CityDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.City;

public class CityRepo  {

    private CityDao cityDao;
    private LiveData<List<City>> Cities;

    public CityRepo(Application application){
        MTDatabase db=MTDatabase.getDatabase(application);
        cityDao=db.cityModel();
        Cities=cityDao.getCities();
    }

    public LiveData<List<City>> getCities() {
        return Cities;
    }


    public void insertCity(City city) {
        cityDao.insertCity(city);
    }


    public void updateCity(City city) {
        cityDao.updateCity(city);
    }


    public void deleteCity(City city) {
        cityDao.deleteCity(city);
    }


    public LiveData<City> getCityById(int cityCode) {
        return cityDao.getCityById(cityCode);
    }


    public void deleteAll() {
        cityDao.deleteAll();
    }


    public void deleteById(int cityCode) {
        cityDao.deleteById(cityCode);
    }


    public LiveData<List<City>> getCityByRegionId(int regionId) {
        return null;
    }

    public List<Long> insertCities(List<City> cities){
        return cityDao.insertCities(cities);
    }
}
