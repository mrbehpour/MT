package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.bluetooth.BluetoothAdapter;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import ir.saa.android.mt.repositories.bluetooth.Bluetooth;

public class ModuleViewModel extends AndroidViewModel {

    private Bluetooth bluetooth;
    private BluetoothAdapter bluetoothAdapter;
    private MutableLiveData<Boolean> isEnableBluetooth;
    public MutableLiveData<List<String>> listBluetoothName;
    public ModuleViewModel(@NonNull Application application) {
        super(application);
        bluetooth = Bluetooth.getInstance();
        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        if(isEnableBluetooth==null)
            isEnableBluetooth=new MutableLiveData<>();
        if(listBluetoothName==null)
            listBluetoothName=new MutableLiveData<>();

    }

    private List<String> getPairedDevice(){
        return bluetooth.getPairedDevice();
    }

    public LiveData<Boolean> bluetoothIsEnable(){
        final BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        isEnableBluetooth.postValue(bluetoothAdapter.isEnabled());
        return isEnableBluetooth;
    }

    public void setBluetoothEnable(Boolean state)  {

        if(state){
            bluetoothAdapter.enable();
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }

                listBluetoothName.postValue(getPairedDevice());
            }).start();

        }else{
            bluetoothAdapter.disable();
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }

                listBluetoothName.postValue(new ArrayList<>());
            }).start();

        }

    }
}
