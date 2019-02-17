package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.SharePrefEnum;
import ir.saa.android.mt.repositories.bluetooth.Bluetooth;
import ir.saa.android.mt.repositories.metertester.ElectericalParams;
import ir.saa.android.mt.repositories.metertester.IMTCallback;
import ir.saa.android.mt.repositories.metertester.MT;

public class TempViewModel extends AndroidViewModel {

    public MutableLiveData<Boolean> connectionStateMutableLiveData;

    public TempViewModel(@NonNull Application application) {
        super(application);
        connectionStateMutableLiveData = new MutableLiveData<>();
    }

    public void testLiveData(){

            connectionStateMutableLiveData.postValue(true);

    }


    @Override
    protected void onCleared() {
        super.onCleared();
    }

}
