package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.AccessAgentAndroidDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.AccessAgentAndroid;

public class AccessAgentAndroidRepo {
    private AccessAgentAndroidDao accessAgentAndroidDao;
    private LiveData<List<AccessAgentAndroid>> AccessAgentAndroids;

    public AccessAgentAndroidRepo(Application application){
        MTDatabase db = MTDatabase.getDatabase(application);
        accessAgentAndroidDao=db.accessAgentAndroidModel();
        AccessAgentAndroids=accessAgentAndroidDao.getAccessAgentAndroids();

    }

   public LiveData<List<AccessAgentAndroid>> getAccessAgentAndroids(){
        return AccessAgentAndroids;
    }


    public List<Long> insertAccessAgentAndroids(List<AccessAgentAndroid> accessAgentAndroids){
        return accessAgentAndroidDao.insertAccessAgentAndroids(accessAgentAndroids);
    }

    public void updateAccessAgentAndroid(AccessAgentAndroid accessAgentAndroid){
        accessAgentAndroidDao.updateAccessAgentAndroid(accessAgentAndroid);
    }

    public void deleteAllaccessAgentAndroid(){
        accessAgentAndroidDao.deleteAll();
    }

    public LiveData<AccessAgentAndroid>  getAccessAgentAndroidById(int Id){
        return  accessAgentAndroidDao.getAccessAgentAndroidById(Id);
    }

    public void deleteaccessAgentAndroidById(int Id){
        accessAgentAndroidDao.deleteById(Id);
    }

    public LiveData<List<AccessAgentAndroid>> getaccessAgentAndroidByUserid(int userId){
        return accessAgentAndroidDao.getAccessAgentAndroidByUserid(userId);
    }

}
