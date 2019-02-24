package ir.saa.android.mt.uicontrollers.fragments;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.SharePrefEnum;
import ir.saa.android.mt.repositories.meterreader.MeterUtility;
import ir.saa.android.mt.repositories.meterreader.StatusReport;
import ir.saa.android.mt.viewmodels.ReadmeterViewModel;

public class ReadmeterFragment extends Fragment {

    ReadmeterViewModel readmeterViewModel = null;
    AlertDialog ad;
    TextView txtMeterCompany;
    TextView txtMeterType;
    TextView txtMeterString;
    TextView txtLog;
    TextView txtRead;
    NumberProgressBar progReadMeter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_readmeter, container, false);

        readmeterViewModel = ViewModelProviders.of(this).get(ReadmeterViewModel.class);
        txtMeterCompany = rootView.findViewById(R.id.tvMeterCompany);
        txtMeterType = rootView.findViewById(R.id.tvMeterType);
        txtMeterString = rootView.findViewById(R.id.tvMeterString);

        txtRead = rootView.findViewById(R.id.tvReaddata);
        txtLog = rootView.findViewById(R.id.tvReadLog);

        progReadMeter = rootView.findViewById(R.id.progressBarReadMeter);

        Button btnRead = rootView.findViewById(R.id.btnRead);
        Bundle args = getArguments();

        readmeterViewModel.getStatusMutableLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if(StatusReport.checkProgressString(s)) {
                    StatusReport.progressItem progressItem = StatusReport.getProgressItem(s);
                    switch (progressItem.progMode){
                        case setMax:
                            progReadMeter.setMax(progressItem.progValue);
                            updateResponseTest(progressItem.progressText);
                            break;
                        case setValue:
                            progReadMeter.setProgress(progressItem.progValue);
                            updateResponseTest(progressItem.progressText);
                            break;
                        case resetValue:
                            progReadMeter.setProgress(0);
                            break;
                        case reportStatus:
                            updateResponseTest(progressItem.progressText);
                            break;
                    }


                }else{
                    updateResponseTest(s);
                }

            }
        });

        readmeterViewModel.readMeterMutableLiveData.observe(this, new Observer<MeterUtility.MeterInfo>()  {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onChanged(@Nullable MeterUtility.MeterInfo meterInfo) {
                    showMeterInfo(meterInfo);
                }
            }
        );

        readmeterViewModel.readMeterResultMutableLiveData.observe(this, new Observer<MeterUtility.ReadData>(){
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onChanged(@Nullable MeterUtility.ReadData readResult) {
                    showReadResult(readResult);
                }
            }
        );

        readmeterViewModel.connectionStateMutableLiveData.observe(this, new Observer<Boolean>() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onChanged(@Nullable Boolean b) {
                        if(b){
                            HideProgressDialog();
                            //btnReconnect.setVisibility(View.INVISIBLE);
                        }else{
                            //btnReconnect.setVisibility(View.VISIBLE);
                        }
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        readmeterViewModel.recieveDataMutableLiveData.observe(this, new Observer<Integer>() {
                    @Override
                    public void onChanged(@Nullable Integer Int) {
                        txtRead.append(String.valueOf(Int));
                        Log.d("meter_response","OK");

                    }
                }
        );

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readmeterViewModel.StartConnectionWithMeter();
            }
        });

        readmeterViewModel.initTranseferLayer();
        connectToModuleDialog();

        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void updateResponseTest(String readResult) {

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {

                handler.post(new Runnable(){
                    public void run() {
                        Log.d("meter_response",readResult);
                        txtLog.append(readResult + "\r\n");
                    }
                });
            }

        };
        new Thread(runnable).start();
    }

    private void updateProgressValue(int progStr){
        progReadMeter.setProgress(progStr);
    }

    private void showMeterInfo(MeterUtility.MeterInfo meterInfo){
        txtMeterCompany.setText(meterInfo.MeterCompany);
        txtMeterType.setText(meterInfo.MeterType);
        txtMeterString.setText(meterInfo.MeterString);
    }


    private void showReadResult(MeterUtility.ReadData readResult) {
        String str = String.format ("ACT1 =%s\nACT2 =%s\nACT3 =%s\nReACT =%s\nDimand =%s\nSN =%s\n",
                readResult.Active1,
                readResult.Active2,
                readResult.Active3,
                readResult.Reactive,
                readResult.Dimand,
                readResult.SerialNum1);

        updateResponseTest(str);
    }

    private void connectToModuleDialog(){
        String BluetoothDeviceName = G.getPref(SharePrefEnum.ModuleBluetoothNameRead);
        ad = new AlertDialog.Builder(this.getContext()).create();
        ad.setCancelable(true);
        ad.setTitle(String.format("%s %s", getResources().getText(R.string.ValidationConnect),BluetoothDeviceName));
        ad.setMessage(getResources().getText(R.string.Wait_Connect));
        new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };
        ad.show();
    }

    public void HideProgressDialog(){
        if(ad!=null) ad.dismiss();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }



}