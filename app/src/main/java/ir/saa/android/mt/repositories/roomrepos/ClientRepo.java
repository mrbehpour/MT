package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Query;

import java.util.List;

import ir.saa.android.mt.application.G;
import ir.saa.android.mt.model.daos.ClientDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.Client;
import ir.saa.android.mt.model.entities.ClientWithAction;
import ir.saa.android.mt.model.entities.ClientWithTarif;

public class ClientRepo {

    private ClientDao clientDao;
    private LiveData<List<Client>> Clients;

    public ClientRepo(Application application){
        MTDatabase db= MTDatabase.getDatabase(application);
        //String backupDBPath = db.getDatabase(G.context).getOpenHelper().getWritableDatabase().getPath();
        clientDao=db.clientModel();
        Clients=clientDao.getClientsLiveData();
    }

    public LiveData<List<Client>> getClientsLiveData() {
        return Clients;
    }


    public List<Client> getClients() {
        return clientDao.getClients();
    }

    public LiveData<List<ClientWithAction>> getClientWithActionLiveData(){
        return clientDao.getClientsWithActionLiveData();
    }

    public LiveData<List<ClientWithAction>> getClientWithActionWithRegionIdLiveData(Integer regionId){
        return clientDao.getClientsWithActionWithRegionIdLiveData(regionId);
    }

    public List<Client> getClientsWithRegionId(Integer regionId){
        return clientDao.getClientsWithRegionId(regionId);
    }

    public LiveData<List<Client>> getClientsWithRegionIdLiveData(Integer regionId){
        return clientDao.getClientsWithRegionIdLiveData(regionId);
    }

    public void updateClient(Client client) {
        clientDao.updateClient(client);
    }


    public void insertClient(Client client) {
        clientDao.insertClient(client);
    }


    public List<Long> insertClients(List<Client> clients) {
        return clientDao.insertClients(clients);
    }


    public void deleteClient(Client client) {
        clientDao.deleteClient(client);
    }


    public Client getClientById(long clientId) {
        return clientDao.getClientById(clientId);
    }

    public LiveData<ClientWithTarif> getClientWithTarif(long clientId) {
        return clientDao.getClientWithTarif(clientId);
    }


    public void deleteAll() {
        clientDao.deleteAll();
    }


    public void deleteById(long clientId) {
        clientDao.deleteById(clientId);
    }
}
