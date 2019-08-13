package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

import ir.saa.android.mt.R;
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
    Boolean showConnectionError = false;

    public MutableLiveData<Boolean> connectionStateMutableLiveData;
    public MutableLiveData<Boolean> turnBluetoothOnMutableLiveData;

    public TestContorViewModel(@NonNull Application application) {
        super(application);
        metertester = MT.getInstance();
        connectionStateMutableLiveData = new MutableLiveData<>();
        turnBluetoothOnMutableLiveData = new MutableLiveData<>();
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
                if(showConnectionError) checkConnectionError(errMsg);

                Log.d("response","onConnectionError : "+errMsg);
            }

            @Override
            public void onReportStatus(String statusMsg) {
                Log.d("response","onReportStatus : "+statusMsg);
            }

            @Override
            public void onResponseTimeout(int noResponseTime) {

            }
        });
    }

    private void checkConnectionError(String errMsg){
        String showError=errMsg;
        if(errMsg.equals("Transfer Layer is Disconnected")){
            showError = String.format("%s:\n%s",errMsg,G.context.getResources().getText(R.string.CheckDeviceIsOn_msg));
        }else if(errMsg.equals("Can Not Connect To Device! : Bluetooth Is Off Or Permission Is Denided!")){
            showError = String.format("%s:\n%s",errMsg,G.context.getResources().getText(R.string.TurnBluetoothOn_msg));
            turnBluetoothOnMutableLiveData.postValue(true);
        }else if(errMsg.startsWith("length=0")) {
            showError = "";
        }

        if(showError!="") {
            //Toast.makeText(G.context, String.format("%s:\n%s",G.context.getResources().getText(R.string.ErrorInConnect),errMsg), Toast.LENGTH_SHORT).show();
            Toast fancyToast = FancyToast.makeText(G.context, showError, FancyToast.LENGTH_SHORT, FancyToast.ERROR, false);
            fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            fancyToast.show();
        }
    }



    public void initTranseferLayer(){
        String BluetoothDeviceName = G.getPref(SharePrefEnum.ModuleBluetoothName);
        if(BluetoothDeviceName==null) BluetoothDeviceName="";
        if(BluetoothDeviceName.equals("")) connectionStateMutableLiveData.postValue(false);
        bluetooth.disconnnect();
        showConnectionError=true;
        bluetooth.init(BluetoothDeviceName);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

}
