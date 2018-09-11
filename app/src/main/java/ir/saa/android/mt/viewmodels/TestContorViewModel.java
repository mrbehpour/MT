package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.SharePrefEnum;
import ir.saa.android.mt.repositories.bluetooth.Bluetooth;
import ir.saa.android.mt.repositories.metertester.IMTCallback;
import ir.saa.android.mt.repositories.metertester.MT;
import ir.saa.android.mt.uicontrollers.pojos.TestContor.TestContorParams;

public class TestContorViewModel extends AndroidViewModel {
    Bluetooth bluetooth;
    MT metertester;
    TestContorParams testContorParams;

    public MutableLiveData<Boolean> connectionStateMutableLiveData;

    public TestContorViewModel(@NonNull Application application) {
        super(application);
        metertester = MT.getInstance();
        connectionStateMutableLiveData = new MutableLiveData<>();
        bluetooth = Bluetooth.getInstance();
        metertester.setTransferLayer(bluetooth);
        metertester.setMTCallback(new IMTCallback() {
            @Override
            public void onConnected() {
                connectionStateMutableLiveData.postValue(true);
                Log.d("response","onConnected");
            }

            @Override
            public void onDisConnected() {
                Log.d("response","onDisConnected");
            }

            @Override
            public void onConnectionError(String errMsg) {
                connectionStateMutableLiveData.postValue(false);
                Toast.makeText(G.context, String.format("%s:\n%s","خطا در اتصال به دستگاه",errMsg), Toast.LENGTH_SHORT).show();
                Log.d("response","onConnectionError : "+errMsg);
            }

            @Override
            public void onReportStatus(String statusMsg) {
                Log.d("response","onReportStatus : "+statusMsg);
            }
        });
    }

    public void initTranseferLayer(){
        String BluetoothDeviceName = G.getPref(SharePrefEnum.ModuleBluetoothName);
        if(BluetoothDeviceName==null) BluetoothDeviceName="";
        if(BluetoothDeviceName.equals("")) connectionStateMutableLiveData.postValue(false);
        bluetooth.disconnnect();
        bluetooth.init(BluetoothDeviceName);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
