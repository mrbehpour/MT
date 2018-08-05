package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.ClientTypeDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.ClientType;

public class ClientTypeRepo  {

    private ClientTypeDao clientTypeDao;
    private LiveData<List<ClientType>> ClientTypes;

    public ClientTypeRepo(Application application){
        MTDatabase db=MTDatabase.getDatabase(application);
        clientTypeDao=db.clientTypeModel();
        ClientTypes=clientTypeDao.getClientTypes();
    }

    public LiveData<List<ClientType>> getClientTypes() {
        return ClientTypes;
    }


    public void updateClientType(ClientType clientType) {
        clientTypeDao.updateClientType(clientType);
    }


    public void insertClientType(ClientType clientType) {
        clientTypeDao.insertClientType(clientType);
    }


    public List<Long> insertClientTypes(List<ClientType> clientTypes) {
        return clientTypeDao.insertClientTypes(clientTypes);
    }


    public void deleteClientType(ClientType clientType) {
        clientTypeDao.deleteClientType(clientType);
    }


    public void deleteAll() {
        clientTypeDao.deleteAll();
    }


    public LiveData<ClientType> getClientTypeById(byte Id) {
        return clientTypeDao.getClientTypeById(Id);
    }


    public void deleteClientTypeById(byte Id) {
        clientTypeDao.deleteClientTypeById(Id);
    }
}
