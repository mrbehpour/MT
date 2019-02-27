package ir.saa.android.mt.uicontrollers.fragments;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.lang.reflect.Field;
import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.components.MyDialog;
import ir.saa.android.mt.components.Tarikh;
import ir.saa.android.mt.enums.SharePrefEnum;
import ir.saa.android.mt.model.entities.TariffAllInfo;
import ir.saa.android.mt.model.entities.TariffDtl;
import ir.saa.android.mt.model.entities.TariffInfo;
import ir.saa.android.mt.repositories.meterreader.MeterUtility;
import ir.saa.android.mt.repositories.meterreader.StatusReport;
import ir.saa.android.mt.uicontrollers.pojos.MeterReader.MeterReaderFieldName;
import ir.saa.android.mt.viewmodels.LocationViewModel;
import ir.saa.android.mt.viewmodels.ReadmeterViewModel;

public class ReadmeterFragment extends Fragment {

    ReadmeterViewModel readmeterViewModel = null;
    AlertDialog ad;
    TextView txtMeterCompany;
    TextView txtMeterType;
    TextView txtMeterString;
    TextView txtLog;
    TextView tvAct1,tvAct2,tvAct3,tvRAct,tvDim,tvSN,tvActSum,tvReverseEnergy,tvDate,tvTime;
    TextView tvVR,tvVS,tvVT;
    TextView tvIR,tvIS,tvIT;
    LocationViewModel locationViewModel=null;
    Location location;
    ProgressDialog progressDialog;
    MeterUtility.ReadData mainReadData;


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
                            saveTariff(mainReadData);
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

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                readmeterViewModel.StartConnectionWithMeter();

            }
        });

        readmeterViewModel.initTranseferLayer();
        connectToModuleDialog();

        locationViewModel.locationMutableLiveData.observe((LifecycleOwner) getContext(), new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location locationObserver) {
                if(locationObserver!=null) {
                    location = locationObserver;
                    HideProgressDialogLocataion();
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

    private void displayTariff(List<TariffAllInfo> tariffAllInfos){
        MeterUtility.ReadData readData=new MeterUtility.ReadData();
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

    private void showMeterInfo(MeterUtility.MeterInfo meterInfo){
        txtMeterCompany.setText(meterInfo.MeterCompany);
        txtMeterType.setText(meterInfo.MeterType);
        txtMeterString.setText(meterInfo.MeterString);
    }


    private void showReadResult(MeterUtility.ReadData readResult,boolean State) {
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

        if(State) {
            mainReadData=new MeterUtility.ReadData();
            mainReadData=readResult;
            MyDialog dialog=new MyDialog(this.getActivity());

            //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            dialog.clearAllPanel();
            dialog.clearButtonPanel();

            dialog.addButton(getResources().getString(R.string.Cancel), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    return;
                }
            });
            dialog.addButton(getResources().getString(R.string.Save), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (locationViewModel.isGpsEnable()) {
                        if (location == null) {
                            connectToModuleDialogLocation();

                        }
                    } else {
                        location = null;
                        locationViewModel.trunOnGps(getContext());
                    }
                    saveTariff(readResult);
                    dialog.dismiss();

                }
            });

            dialog.addBodyText((String) getResources().getText(R.string.Confirm_msg),15).setTitle((String) getResources().getText(R.string.Title_msg)).show();


        }
    }

    private void saveTariff(MeterUtility.ReadData result){
        if(result==null){
            return;
        }
        List<TariffAllInfo> tariffAllInfos=readmeterViewModel.getTariffAllInfo(G.clientInfo.ClientId,G.clientInfo.SendId);
        location=locationViewModel.getLocation(this.getContext());
        if(location != null) {
            TariffInfo tariffInfo = new TariffInfo();

            tariffInfo.AgentID = Integer.valueOf(G.getPref("UserID"));
            tariffInfo.TariffDate = Integer.valueOf(Tarikh.getCurrentShamsidatetimeWithoutSlash().substring(0, 8));
            tariffInfo.TariffTime = Integer.valueOf(Tarikh.getTimeWithoutColon());
            tariffInfo.ClientID = G.clientInfo.ClientId;
            tariffInfo.SendID = G.clientInfo.SendId;
            tariffInfo.Lat = String.valueOf(location.getLatitude());
            tariffInfo.Long = String.valueOf(location.getLongitude());
            tariffInfo.FollowUpCode = G.clientInfo.FollowUpCode == null ? 0 : G.clientInfo.FollowUpCode;
            Long tariffInfoId = Long.valueOf(0);
            if (tariffAllInfos.size() == 0) {
                tariffInfoId = readmeterViewModel.insertTariffInfo(tariffInfo);


            } else {
                tariffInfoId = Long.valueOf(tariffAllInfos.get(0).TariffInfoID);
                readmeterViewModel.updateTariffInfo(tariffInfo);
            }

            TariffDtl tariffDtl = new TariffDtl();
            Field[] triffDtlFiels = MeterUtility.ReadData.class.getFields();
            for (Field field : triffDtlFiels) {
                Integer tariffId = MeterReaderFieldName.ReadMeterId(field.getName());
                if (tariffId != null) {
                    tariffDtl.ReadTypeID = 1;
                    tariffDtl.TariffInfoID = Integer.valueOf(tariffInfoId.toString());
                    tariffDtl.TariffID = tariffId;
                    Object value = null;

                    try {
                        value = field.get(result);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if (value != null) {
                        tariffDtl.TariffValue = value.toString();
                    }
                    readmeterViewModel.insertTariffDtl(tariffDtl);
                }

            }
            Toast fancyToast = FancyToast.makeText(getActivity(), (String) getResources().getText(R.string.SaveOperationSuccess_msg), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false);
            fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            fancyToast.show();
        }else{
            locationViewModel.trunOnGps(getContext());
        }


    }

    private void connectToModuleDialog(){
        String BluetoothDeviceName = G.getPref(SharePrefEnum.ModuleBluetoothNameRead);
        ad = new AlertDialog.Builder(this.getContext()).create();
        ad.setCancelable(true);
        ad.setTitle(String.format("%s %s", getResources().getText(R.string.ConnectToTestModule),BluetoothDeviceName));
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