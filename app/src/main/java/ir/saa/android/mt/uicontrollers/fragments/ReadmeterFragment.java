package ir.saa.android.mt.uicontrollers.fragments;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.components.Utilities;
import ir.saa.android.mt.enums.SharePrefEnum;
import ir.saa.android.mt.model.entities.DigitalMeters;
import ir.saa.android.mt.model.entities.TariffAllInfo;
import ir.saa.android.mt.model.entities.TestInfo;
import ir.saa.android.mt.repositories.meterreader.MeterUtility;
import ir.saa.android.mt.repositories.meterreader.StatusReport;
import ir.saa.android.mt.viewmodels.AmaliyatViewModel;
import ir.saa.android.mt.viewmodels.LocationViewModel;
import ir.saa.android.mt.viewmodels.ReadmeterViewModel;

import static ir.saa.android.mt.repositories.meterreader.MeterUtility.*;

public class ReadmeterFragment extends Fragment {

    ReadmeterViewModel readmeterViewModel = null;
    AmaliyatViewModel amaliyatViewModel=null;
    AlertDialog ad;
    TextView txtMeterCompany;
    TextView txtMeterType;
    TextView txtMeterString;
    TextView txtLog;
    EditText tvAct1,tvAct2,tvAct3,tvRAct,tvDim;
    TextView tvSN,tvActSum,tvReverseEnergy,tvDate,tvTime;
    TextView tvVR,tvVS,tvVT;
    TextView tvIR,tvIS,tvIT;

    Button btnProbRead,btnManualRead,btnCancelManualRead,btnSaveManualRead,btnSaveProbRead;

    LocationViewModel locationViewModel=null;
    Location location;
    ProgressDialog progressDialog;
    ReadData mainReadData;
    Button btnRead;
    //int CountGherat;
    NumberProgressBar progReadMeter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    private void connectToModuleDialogLocation(){

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage(getResources().getText(R.string.PleaseWait_msg));
        progressDialog.setTitle(getResources().getText(R.string.GetLocationInProgress_msg));
        progressDialog.setCancelable(true);
        progressDialog.show();

    }

    public void HideProgressDialogLocataion(){
        if(progressDialog!=null) progressDialog.dismiss();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_readmeter, container, false);
        locationViewModel=ViewModelProviders.of(this).get(LocationViewModel.class);
        amaliyatViewModel=ViewModelProviders.of(this).get(AmaliyatViewModel.class);
        //CountGherat=0;
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
        btnSaveProbRead = rootView.findViewById(R.id.btnSaveProbRead);
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

        readmeterViewModel.readMeterResultMutableLiveData.observe(this, new Observer<ReadData>(){
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onChanged(@Nullable ReadData readResult) {

                    btnProbRead.setVisibility(View.GONE);
                    btnSaveProbRead.setVisibility(View.VISIBLE);
                    showReadResult(readResult,true);
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

        readmeterViewModel.turnBluetoothOnMutableLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean b) {
                if(b){
                    requestForTurnOnBT();
                }
            }
        });

        btnSaveProbRead.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                saveTariff();
            }
        });

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
                btnSaveProbRead.setVisibility(View.GONE);
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

        btnSaveManualRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createMainRead();
                saveTariff();
            }
        });

        locationViewModel.locationMutableLiveData.observe((LifecycleOwner) getContext(), new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location locationObserver) {
                if(locationObserver!=null) {
                    location = locationObserver;
//                    if(mainReadData!=null){
//                        saveTariff(mainReadData);
//                    }
//                    HideProgressDialogLocataion();
                }
            }
        });

        List<TariffAllInfo> tariffAllInfos=readmeterViewModel.getTariffAllInfo(G.clientInfo.ClientId,G.clientInfo.SendId);
        displayTariff(tariffAllInfos);

        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void resetUI(){

        btnProbRead.setVisibility(View.VISIBLE);
        btnManualRead.setVisibility(View.VISIBLE);
        btnSaveProbRead.setVisibility(View.GONE);
        btnCancelManualRead.setVisibility(View.GONE);
        btnSaveManualRead.setVisibility(View.GONE);
    }

    private void createMainRead(){
        mainReadData = new ReadData();
        if(Utilities.isNumeric(tvAct1.getText().toString())) mainReadData.Active1 = Double.valueOf(tvAct1.getText().toString());
        if(Utilities.isNumeric(tvAct2.getText().toString())) mainReadData.Active2 = Double.valueOf(tvAct2.getText().toString());
        if(Utilities.isNumeric(tvAct3.getText().toString())) mainReadData.Active3 = Double.valueOf(tvAct3.getText().toString());
        if(Utilities.isNumeric(tvRAct.getText().toString())) mainReadData.Reactive = Double.valueOf(tvRAct.getText().toString());
        if(Utilities.isNumeric(tvDim.getText().toString())) mainReadData.Dimand = Double.valueOf(tvDim.getText().toString());
    }

    private void displayTariff(List<TariffAllInfo> tariffAllInfos){
        ReadData readData=new ReadData();
        for (TariffAllInfo tariffAllInfo:tariffAllInfos) {
            switch (tariffAllInfo.TariffID) {
                case 17:
                    readData.Dimand=Double.valueOf(tariffAllInfo.TariffValue);
                    break;
                case 1:
                    readData.Active1=Double.valueOf(tariffAllInfo.TariffValue);
                    break;
                case 2:
                    readData.Active2=Double.valueOf(tariffAllInfo.TariffValue);
                    break;
                case 3:
                    readData.Active3=Double.valueOf(tariffAllInfo.TariffValue);
                    break;
                case 5:
                    readData.Reactive=Double.valueOf(tariffAllInfo.TariffValue);
                    break;
                case 11:
                    readData.Holiday=Double.valueOf(tariffAllInfo.TariffValue);
                    break;
                case 28:
                    readData.SerialNum1=tariffAllInfo.TariffValue;
                    break;
                case 21:
                    readData.ActiveSum=Double.valueOf(tariffAllInfo.TariffValue);
                    break;
                case 23:
                    readData.ReversEnerji=Double.valueOf(tariffAllInfo.TariffValue);
                    break;
                case 24:
                    readData.CurDate=tariffAllInfo.TariffValue;
                    break;
                case 25:
                    readData.CurTime=tariffAllInfo.TariffValue;
                    break;
            }
            showReadResult(readData,false);
        }
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
        if(meterInfo==null){
            txtMeterCompany.setText("");
            txtMeterType.setText(R.string.Unknown);
            txtMeterString.setText("");
            Toast fancyToast = FancyToast.makeText(getActivity(), (String) getResources().getText(R.string.MeterUnknown_msg), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false);
            fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            fancyToast.show();
        }else {
            txtMeterCompany.setText(meterInfo.MeterCompany);
            txtMeterType.setText(meterInfo.MeterType);
            txtMeterString.setText(meterInfo.MeterString);
        }
    }


    private void showReadResult(ReadData readResult,boolean State) {

        mainReadData=readResult;

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

    private void saveTariff(){
        if(mainReadData==null){
            return;
        }
        List<TestInfo> testInfos = amaliyatViewModel.getTestInfoWithBlockId(G.clientInfo.ClientId, G.clientInfo.SendId);
        if (testInfos.size() != 0) {
            AlertDialog basic_reg;
            TextView txtDialogTitle;
            TextView tvMessage;
            AlertDialog.Builder builder = new  AlertDialog.Builder(getContext());
            View v = getLayoutInflater().inflate(R.layout.custom_alretdialog, null);
            builder.setView(v);
            builder.setCancelable(false);
            builder.create();
            basic_reg = builder.show();
            txtDialogTitle = (TextView) v.findViewById(R.id.txtDialogTitle);
            tvMessage = (TextView) v.findViewById(R.id.tvMessage);
            txtDialogTitle.setText(getText(R.string.msg));
            tvMessage.setText(getText(R.string.msg_Save));
            Button btnCancel = (Button) v.findViewById(R.id.btnCancel);
            Button btnRegister = (Button) v.findViewById(R.id.btnRegister);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    basic_reg.dismiss();
                    return;
                }
            });
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(TestInfo testInfo:testInfos){
                        amaliyatViewModel.deleteTestInfoById(testInfo.TestInfoID);
                    }
                    location = locationViewModel.getLocation(getContext());

                    if (readmeterViewModel.saveTraiff(mainReadData, location)) {
                        Toast fancyToast = FancyToast.makeText(getActivity(), (String) getResources().getText(R.string.SaveOperationSuccess_msg), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false);
                        fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                        fancyToast.show();
                    } else {

                    }
                    basic_reg.dismiss();
                    resetUI();

                }
            });
        }else {
            location = locationViewModel.getLocation(this.getContext());

            if (readmeterViewModel.saveTraiff(mainReadData, location)) {
                Toast fancyToast = FancyToast.makeText(getActivity(), (String) getResources().getText(R.string.SaveOperationSuccess_msg), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false);
                fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                fancyToast.show();
            } else {

            }

            resetUI();
        }
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

    public void requestForTurnOnBT(){
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        getActivity().startActivity(enableBtIntent);
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


//                if(mainReadData!=null && btnRead.getText().toString().contains(getResources().getText(R.string.Save))){
//        saveTariff(mainReadData);
//
//    }else {
//        readmeterViewModel.StartConnectionWithMeter();
//    }
}