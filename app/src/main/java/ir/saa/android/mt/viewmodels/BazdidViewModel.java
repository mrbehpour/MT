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

    private ClientRepo clientRepo;
    public BazdidViewModel(@NonNull Application application) {
        super(application);
        if(clientRepo==null)
            clientRepo = new ClientRepo(application);

    }

    public LiveData<List<Client>> getClientsLiveData(){
        return clientRepo.getClients();
    }





}
