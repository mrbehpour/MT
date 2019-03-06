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
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.SharePrefEnum;
import ir.saa.android.mt.model.entities.DigitalMeters;
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
    EditText tvAct1,tvAct2,tvAct3,tvRAct,tvDim;
    TextView tvSN,tvActSum,tvReverseEnergy,tvDate,tvTime;
    TextView tvVR,tvVS,tvVT;
    TextView tvIR,tvIS,tvIT;

    Button btnProbRead,btnManualRead,btnCancelManualRead,btnSaveManualRead;

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

        txtLog = rootView.findViewById(R.id.tvReadLog);

        tvAct1 = rootView.findViewById(R.id.tvAct1Val);
        tvAct2 = rootView.findViewById(R.id.tvAct2Val);
        tvAct3 = rootView.findViewById(R.id.tvAct3Val);
        tvRAct = rootView.findViewById(R.id.tvRActVal);
        tvDim = rootView.findViewById(R.id.tvDimVal);

        tvActSum = rootView.findViewById(R.id.tvActSumVal);
        tvReverseEnergy = rootView.findViewById(R.id.tvRererseEnergyVal);
        tvSN = rootView.findViewById(R.id.tvSNVal);
        tvDate = rootView.findViewById(R.id.tvDateVal);
        tvTime = rootView.findViewById(R.id.tvTimeVal);

        tvVR = rootView.findViewById(R.id.tvVolt1Val);
        tvVS = rootView.findViewById(R.id.tvVolt2Val);
        tvVT = rootView.findViewById(R.id.tvVolt3Val);

        tvIR = rootView.findViewById(R.id.tvAmp1Val);
        tvIS = rootView.findViewById(R.id.tvAmp2Val);
        tvIT = rootView.findViewById(R.id.tvAmp3Val);

        progReadMeter = rootView.findViewById(R.id.progressBarReadMeter);

        btnProbRead = rootView.findViewById(R.id.btnProbRead);
        btnManualRead = rootView.findViewById(R.id.btnManualRead);
        btnCancelManualRead = rootView.findViewById(R.id.btnCancelManualRead);
        btnSaveManualRead = rootView.findViewById(R.id.btnSaveManualRead);

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

        readmeterViewModel.readMeterMutableLiveData.observe(this, new Observer<DigitalMeters>()  {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onChanged(@Nullable DigitalMeters meterInfo) {
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
                            btnManualRead.setVisibility(View.INVISIBLE);
                            readmeterViewModel.StartConnectionWithMeter();
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

        btnProbRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readmeterViewModel.initTranseferLayer();
                connectToModuleDialog();
            }
        });

        btnManualRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnProbRead.setVisibility(View.GONE);
                btnManualRead.setVisibility(View.GONE);
                btnCancelManualRead.setVisibility(View.VISIBLE);
                btnSaveManualRead.setVisibility(View.VISIBLE);
            }
        });

        btnCancelManualRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetUI();
            }
        });



        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void resetUI(){

        btnProbRead.setVisibility(View.VISIBLE);
        btnManualRead.setVisibility(View.VISIBLE);
        btnCancelManualRead.setVisibility(View.GONE);
        btnSaveManualRead.setVisibility(View.GONE);
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

    private void showMeterInfo(DigitalMeters meterInfo){
        txtMeterCompany.setText(meterInfo.MeterCompany);
        txtMeterType.setText(meterInfo.MeterType);
        txtMeterString.setText(meterInfo.MeterString);
    }


    private void showReadResult(MeterUtility.ReadData readResult) {
//        String str = String.format ("ACT1 =%s\nACT2 =%s\nACT3 =%s\nReACT =%s\nDimand =%s\nSN =%s\n",
//                readResult.Active1,
//                readResult.Active2,
//                readResult.Active3,
//                readResult.Reactive,
//                readResult.Dimand,
//                readResult.SerialNum1);

        tvAct1.setText(String.valueOf(readResult.Active1));
        tvAct2.setText(String.valueOf(readResult.Active2));
        tvAct3.setText(String.valueOf(readResult.Active3));
        tvRAct.setText(String.valueOf(readResult.Reactive));
        tvDim.setText(String.valueOf(readResult.Dimand));

        tvActSum.setText(String.valueOf(readResult.ActiveSum));
        tvReverseEnergy.setText(String.valueOf(readResult.ReversEnerji));
        tvSN.setText(String.valueOf(readResult.SerialNum1));
        tvDate.setText(String.valueOf(readResult.CurDate));
        tvTime.setText(String.valueOf(readResult.CurTime));

        tvVR.setText(String.valueOf(readResult.CurVolt1));
        tvVS.setText(String.valueOf(readResult.CurVolt2));
        tvVT.setText(String.valueOf(readResult.CurVolt3));

        tvIR.setText(String.valueOf(readResult.Amp1));
        tvIS.setText(String.valueOf(readResult.Amp2));
        tvIT.setText(String.valueOf(readResult.Amp3));
    }

    private void connectToModuleDialog(){
        String BluetoothDeviceName = G.getPref(SharePrefEnum.ModuleBluetoothNameRead);
        ad = new AlertDialog.Builder(this.getContext()).create();
        ad.setCancelable(true);
        ad.setTitle(String.format("%s %s", getResources().getText(R.string.ConnectToOpticalProb),BluetoothDeviceName));
        ad.setMessage(getResources().getText(R.string.PleaseWait_msg));
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