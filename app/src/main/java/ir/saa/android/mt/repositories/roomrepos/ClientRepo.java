package ir.saa.android.mt.repositories.roomrepos;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

import ir.saa.android.mt.model.daos.ClientDao;
import ir.saa.android.mt.model.database.MTDatabase;
import ir.saa.android.mt.model.entities.Client;

public class ClientRepo {

    private ClientDao clientDao;
    private LiveData<List<Client>> Clients;

    ClientRepo(Application application){
        MTDatabase db=MTDatabase.getDatabase(application);
        clientDao=db.clientModel();
        Clients=clientDao.getClients();
    }

    public LiveData<List<Client>> getClients() {
        return Clients;
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


    public LiveData<Client> getClientById(long clientId) {
        return clientDao.getClientById(clientId);
    }


    public void deleteAll() {
        clientDao.deleteAll();
    }


    public void deleteById(long clientId) {
        clientDao.deleteById(clientId);
    }
}
