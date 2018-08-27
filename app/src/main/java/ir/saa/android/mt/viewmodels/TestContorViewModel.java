package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.FragmentsEnum;
import ir.saa.android.mt.repositories.bluetooth.Bluetooth;
import ir.saa.android.mt.repositories.metertester.EnergiesState;
import ir.saa.android.mt.repositories.metertester.IMTCallback;
import ir.saa.android.mt.repositories.metertester.MT;
import ir.saa.android.mt.uicontrollers.pojos.TestContor.TestContorParams;

public class TestContorViewModel extends AndroidViewModel {
    Bluetooth bluetooth;
    MT metertester;
    TestContorParams testContorParams;

    public TestContorViewModel(@NonNull Application application) {
        super(application);
        metertester = MT.getInstance();
        bluetooth = Bluetooth.getInstance();
        metertester.setTransferLayer(bluetooth);
        metertester.setMTCallback(new IMTCallback() {
            @Override
            public void onConnected() {
                Log.d("response","onConnected");
            }

            @Override
            public void onDisConnected() {
                Log.d("response","onDisConnected");
            }

            @Override
            public void onConnectionError(String errMsg) {
                Log.d("response","onConnectionError : "+errMsg);
            }

            @Override
            public void onReportStatus(String statusMsg) {
                Log.d("response","onReportStatus : "+statusMsg);
            }
        });

    }

}
