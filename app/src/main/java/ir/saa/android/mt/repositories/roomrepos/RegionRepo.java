package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.RegionDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.Region;

public class RegionRepo  {

    private RegionDao regionDao;
    private LiveData<List<Region>> Regions;

    RegionRepo(Application application){
        MTDatabase db=MTDatabase.getDatabase(application);
        regionDao=db.regionModel();
        Regions=regionDao.getRegions();
    }

    public LiveData<List<Region>> getRegions() {
        return Regions;
    }


    public void deleteAll() {
        regionDao.deleteAll();
    }


    public LiveData<Region> getRegionById(int Id) {
        return regionDao.getRegionById(Id);
    }


    public LiveData<List<Region>> getRegionByCompanyId(int Id) {
        return regionDao.getRegionByCompanyId(Id);
    }


    public void deleteById(int Id) {
        regionDao.deleteById(Id);
    }


    public void deleteRegion(Region region) {
        regionDao.deleteRegion(region);
    }


    public void insertRegion(Region region) {
        regionDao.insertRegion(region);
    }


    public List<Integer> insertRegions(List<Region> regions) {
        return regionDao.insertRegions(regions);
    }


    public void updateRegion(Region region) {
        regionDao.updateRegion(region);

    }
}
