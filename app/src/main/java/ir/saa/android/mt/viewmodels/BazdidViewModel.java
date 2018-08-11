package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.adapters.bazdid.ClientItem;
import ir.saa.android.mt.model.entities.Client;
import ir.saa.android.mt.repositories.roomrepos.ClientRepo;

public class BazdidViewModel extends AndroidViewModel {
    public MutableLiveData<List<ClientItem>> ClientItemsLiveData;

    private ClientRepo clientRepo;
    public BazdidViewModel(@NonNull Application application) {
        super(application);
        ClientItemsLiveData = new MutableLiveData<>();
        if(clientRepo==null)
            clientRepo = new ClientRepo(application);
        //initialize
        if(clientRepo.getClients().getValue()!=null) {
            List<ClientItem> clientItemsInit = new ArrayList<>();
            for (Client client : clientRepo.getClients().getValue()) {
                clientItemsInit.add(new ClientItem(client.ClientID, client.Name, client.Address, "", client.CustId, R.drawable.account));
            }
            ClientItemsLiveData.postValue(clientItemsInit);
        }
        //observe data for any changes
        clientRepo.getClients().observeForever(clients -> {
            List<ClientItem> clientItems = new ArrayList<>();
            for(Client client:clients){
                clientItems.add(new ClientItem(client.ClientID,client.Name,client.Address,"اشتراک : ",client.CustId, R.drawable.account));
            }
            ClientItemsLiveData.setValue(clientItems);
        });
    }





}
