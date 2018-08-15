package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import ir.saa.android.mt.application.G;
import ir.saa.android.mt.repositories.bluetooth.Bluetooth;
import ir.saa.android.mt.repositories.metertester.IMTCallback;
import ir.saa.android.mt.repositories.metertester.MT;
import ir.saa.android.mt.repositories.metertester.EnergiesState;
import ir.saa.android.mt.uicontrollers.pojos.TestContor.TestContorParams;

public class TestEnergyViewModel extends AndroidViewModel {
    Bluetooth bluetooth;
    MT metertester;
    TestContorParams testContorParams;
    public MutableLiveData<EnergiesState> energiesStateMutableLiveData;

    public TestEnergyViewModel(@NonNull Application application) {
        super(application);
        metertester = MT.getInstance();
        bluetooth = Bluetooth.getInstance();
        metertester.setTransferLayer(bluetooth);

        metertester.setMTCallback(new IMTCallback() {
            @Override
            public void onConnected() {
                Log.d("response","onConnected");
                bluetooth.init("BTM0604C2P");
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


        energiesStateMutableLiveData = new MutableLiveData<>();

        readEnergiesStateFromMeter();
    }

    public void setTestContorParams(TestContorParams tcp){
        testContorParams=tcp;
    }

    private void readEnergiesStateFromMeter(){
        EnergiesState energiesState;
        try {
            energiesState = metertester.ReadEnergiesState();
            energiesStateMutableLiveData.setValue(energiesState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void confirmEnergies(){
        if((energiesStateMutableLiveData.getValue().energy1AState && energiesStateMutableLiveData.getValue().energy2AState && energiesStateMutableLiveData.getValue().energy3AState) ||
                (!energiesStateMutableLiveData.getValue().energy1AState && !energiesStateMutableLiveData.getValue().energy3AState && !energiesStateMutableLiveData.getValue().energy3AState)   ){
            //go to next fragment
            Toast.makeText(G.context,"okkkkkkk",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(G.context,"جهت کلمپ ها یکسان نیست",Toast.LENGTH_SHORT).show();
        }
    }
}
