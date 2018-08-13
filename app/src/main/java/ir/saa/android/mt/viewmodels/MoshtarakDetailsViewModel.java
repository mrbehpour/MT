package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import ir.saa.android.mt.model.entities.AnswerGroupDtl;
import ir.saa.android.mt.model.entities.Client;
import ir.saa.android.mt.model.entities.ClientWithTarif;

import ir.saa.android.mt.repositories.roomrepos.ClientRepo;


public class MoshtarakDetailsViewModel extends AndroidViewModel {

    ClientRepo clientRepo = null;
    public MoshtarakDetailsViewModel(@NonNull Application application) {
        super(application);

        if(clientRepo==null)
            clientRepo = new ClientRepo(application);

    }

    public LiveData<ClientWithTarif> getDetailsClientWithTariff(Long idClient){

       return clientRepo.getClientWithTarif(idClient);
    }


}
