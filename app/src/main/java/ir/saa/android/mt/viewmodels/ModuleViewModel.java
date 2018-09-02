package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import java.util.List;

import ir.saa.android.mt.repositories.bluetooth.Bluetooth;

public class ModuleViewModel extends AndroidViewModel {

    private Bluetooth bluetooth;
    public ModuleViewModel(@NonNull Application application) {
        super(application);
        bluetooth = Bluetooth.getInstance();
    }

    public List<String> getPairedDevice(){
        return bluetooth.getPairedDevice();
    }
}
