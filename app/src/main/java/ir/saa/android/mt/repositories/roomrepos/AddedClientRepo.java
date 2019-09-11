package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.AddedClientDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.AddedClient;
import ir.saa.android.mt.repositories.retrofit.MTApi;

public class AddedClientRepo  {

    private AddedClientDao addedClientDao;
    private  LiveData<List<AddedClient>> listLiveData;
    public AddedClientRepo(Application application){
        MTDatabase db = MTDatabase.getDatabase(application);
        addedClientDao= db.addedClientModel();
        listLiveData=addedClientDao.getAddedClients();

    }

    public LiveData<List<AddedClient>> getAddedClients() {
        return listLiveData;
    }


    public long insertAddedClient(AddedClient addedClient) {
        return addedClientDao.insertAddedClient(addedClient);
    }
}
