package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import ir.saa.android.mt.adapters.bazdid.ClientItem;

public class BazdidViewModel extends AndroidViewModel {
    public LiveData<List<ClientItem>> ClientItemsLiveData;
    public BazdidViewModel(@NonNull Application application) {
        super(application);
    }


}
