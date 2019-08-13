package ir.saa.android.mt.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.BundleKeysEnum;
import ir.saa.android.mt.enums.FragmentsEnum;
import ir.saa.android.mt.repositories.metertester.ElectericalParams;
import ir.saa.android.mt.repositories.metertester.EnergiesState;
import ir.saa.android.mt.repositories.metertester.MT;
import ir.saa.android.mt.uicontrollers.pojos.TestContor.TestContorParams;

public class TestEnergyViewModel extends AndroidViewModel {
    MT metertester;
    TestContorParams testContorParams;
    Timer timerCheck;

    public MutableLiveData<EnergiesState> energiesStateMutableLiveData;
    public MutableLiveData<int[]> checkClampMutableLiveData;
    public MutableLiveData<List<ElectericalParams>> sanjeshResultMutableLiveData;

    public TestEnergyViewModel(@NonNull Application application) {
        super(application);
        metertester = MT.getInstance();

        checkClampMutableLiveData = new MutableLiveData<>();
        energiesStateMutableLiveData = new MutableLiveData<>();
        sanjeshResultMutableLiveData = new MutableLiveData<>();

        timerCheckStart(2000);
    }

    public void timerCheckStart(long prd) {
        if(timerCheck != null) {
            return;
        }

        timerCheck = new Timer();
        TimerTask timerCheckTask = new TimerTask() {

            @Override
            public void run() {
                readEnergiesStateFromMeter();
                readElectricalParams();
            }

        };
        timerCheck.schedule(timerCheckTask, 0, prd);
    }

    private void timerCheckStop() {
        if(timerCheck!=null){
            timerCheck.cancel();
            timerCheck.purge();
            timerCheck=null;
        }
    }

    public void setTestContorParams(TestContorParams testContorParams){
        this.testContorParams=testContorParams;
    }

    public void writeTestContorParams(){
        try {
            String s =  metertester.SendTestContorParams(testContorParams);
            Log.d("response tcp",s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void CheckClampType(){
        int[] clampType = new int[3];
        try {
            clampType = metertester.ReadClampType();
            checkClampMutableLiveData.postValue(clampType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readEnergiesStateFromMeter(){

        EnergiesState energiesState;
        try {
            energiesState = metertester.ReadEnergiesState();
            energiesStateMutableLiveData.postValue(energiesState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readElectricalParams(){
        List<ElectericalParams> sanjeshResult;
        try {
            sanjeshResult = metertester.ReadAllElectericalParams();
            sanjeshResultMutableLiveData.postValue(sanjeshResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void confirmEnergies(){
        try{
            if ((energiesStateMutableLiveData.getValue().energy1AState && energiesStateMutableLiveData.getValue().energy2AState && energiesStateMutableLiveData.getValue().energy3AState) ||
                    (!energiesStateMutableLiveData.getValue().energy1AState && !energiesStateMutableLiveData.getValue().energy3AState && !energiesStateMutableLiveData.getValue().energy3AState)) {
                //go to next fragment
                timerCheckStop();
                writeTestContorParams();

                Bundle bundle = new Bundle();
                bundle.putSerializable(BundleKeysEnum.TestContorParams, testContorParams);

                G.startFragment(FragmentsEnum.AmaliyatFragment, false, bundle);

            } else {
                //toast.makeText(G.context, G.context.getResources().getText(R.string.Arrow_Clamp), Toast.LENGTH_SHORT).show();
                Toast fancyToast = FancyToast.makeText(G.context, (String) G.context.getResources().getText(R.string.ClampDirectionError_msg), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false);
                fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                fancyToast.show();
            }
        }
        catch (Exception ex){

        }
    }



    @Override
    protected void onCleared() {
        super.onCleared();
        timerCheckStop();
    }
}
