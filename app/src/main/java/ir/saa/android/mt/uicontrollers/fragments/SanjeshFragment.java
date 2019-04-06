package ir.saa.android.mt.uicontrollers.fragments;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.SharePrefEnum;
import ir.saa.android.mt.repositories.metertester.ElectericalParams;
import ir.saa.android.mt.viewmodels.SanjeshViewModel;

public class SanjeshFragment extends Fragment {

    SanjeshViewModel sanjeshViewModel = null;
    List<ElectericalParams> sanjeshResult;
    //List<ElectericalParams> sanjeshResultAvrage = new ArrayList<>(3);
    List<List<ElectericalParams>> sanjeshResultAvrage = new ArrayList<>(3);

    AlertDialog ad;

    boolean firstSample=true;

    com.github.angads25.toggle.LabeledSwitch switchPlayPause;
    Button btnReconnect;
    TextView tvVR,tvVS,tvVT;
    TextView tvIR,tvIS,tvIT;
    TextView tvPR,tvPS,tvPT;
    TextView tvQR,tvQS,tvQT;
    TextView tvSR,tvSS,tvST;
    TextView tvPFR,tvPFS,tvPFT;

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
        View rootView = inflater.inflate(R.layout.fragment_sanjesh, container, false);
        sanjeshViewModel = ViewModelProviders.of(this).get(SanjeshViewModel.class);
        initObjects(rootView);

        sanjeshViewModel.sanjeshResultMutableLiveData.observe(this, new Observer<List<ElectericalParams>>() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onChanged(@Nullable List<ElectericalParams> sanjeshResult) {
                    if(switchPlayPause.isOn()) {
                        calAvrageOfElectricalParams(sanjeshResult);
                        if (firstSample) {
                            showSanjeshResult(sanjeshResult);
                            firstSample = false;
                        }
                    }
                }
            }
        );

        sanjeshViewModel.connectionStateMutableLiveData.observe(this, new Observer<Boolean>() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onChanged(@Nullable Boolean b) {
                    Log.d("response disconnect",b + "");
                    if(b){
                        HideProgressDialog();
                        sanjeshViewModel.readSanjeshResultFromMeter();
                    }
                }
            }
        );

        sanjeshViewModel.abortOperationMutableLiveData.observe(this, new Observer<Boolean>() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onChanged(@Nullable Boolean b) {
                    if (b) {
                        btnReconnect.setVisibility(View.VISIBLE);
                    }else{
                        btnReconnect.setVisibility(View.INVISIBLE);
                    }
                }
            }
        );


        connectToModule();
        return rootView;
    }

    private void EnableBT(){
        Intent intentBtEnabled = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        // The REQUEST_ENABLE_BT constant passed to startActivityForResult() is a locally defined integer (which must be greater than 0), that the system passes back to you in your onActivityResult()
        // implementation as the requestCode parameter.
        int REQUEST_ENABLE_BT = 1;
        startActivityForResult(intentBtEnabled, REQUEST_ENABLE_BT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                //String result=data.getStringExtra("result");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

    private void connectToModule(){
        showEmptyResult();
        sanjeshViewModel.initTranseferLayer();
        connectToModuleDialog();

    }

    private void initObjects(View rootView){
        btnReconnect = rootView.findViewById(R.id.btnReconnect);
        btnReconnect.setVisibility(View.INVISIBLE);
        btnReconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectToModule();
            }
        });

        switchPlayPause = rootView.findViewById(R.id.switchPlayPause);
//        switchPlayPause.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//            }
//        });

        tvVR = rootView.findViewById(R.id.tvRowV_R);
        tvVS = rootView.findViewById(R.id.tvRowV_S);
        tvVT = rootView.findViewById(R.id.tvRowV_T);

        tvIR = rootView.findViewById(R.id.tvRowI_R);
        tvIS = rootView.findViewById(R.id.tvRowI_S);
        tvIT = rootView.findViewById(R.id.tvRowI_T);

        tvPR = rootView.findViewById(R.id.tvRowP_R);
        tvPS = rootView.findViewById(R.id.tvRowP_S);
        tvPT = rootView.findViewById(R.id.tvRowP_T);

        tvQR = rootView.findViewById(R.id.tvRowQ_R);
        tvQS = rootView.findViewById(R.id.tvRowQ_S);
        tvQT = rootView.findViewById(R.id.tvRowQ_T);

        tvSR = rootView.findViewById(R.id.tvRowS_R);
        tvSS = rootView.findViewById(R.id.tvRowS_S);
        tvST = rootView.findViewById(R.id.tvRowS_T);

        tvPFR = rootView.findViewById(R.id.tvRowPF_R);
        tvPFS = rootView.findViewById(R.id.tvRowPF_S);
        tvPFT = rootView.findViewById(R.id.tvRowPF_T);
    }


    private void calAvrageOfElectricalParams(List<ElectericalParams> sanjeshResult) {
        double[] vals =  new double[18];
        sanjeshResultAvrage.add(sanjeshResult);

        if(sanjeshResultAvrage.size()==10){
            for (List<ElectericalParams> lstEP : sanjeshResultAvrage) {

                vals[0] += Double.valueOf(lstEP.get(0).AVRMS);
                vals[1] += Double.valueOf(lstEP.get(0).AIRMS);
                vals[2] += Double.valueOf(lstEP.get(0).AWATT);
                vals[3] += Double.valueOf(lstEP.get(0).AVAR);
                vals[4] += Double.valueOf(lstEP.get(0).AVA);
                vals[5] += Double.valueOf(lstEP.get(0).ANGLE0);

                vals[6] += Double.valueOf(lstEP.get(1).AVRMS);
                vals[7] += Double.valueOf(lstEP.get(1).AIRMS);
                vals[8] += Double.valueOf(lstEP.get(1).AWATT);
                vals[9] += Double.valueOf(lstEP.get(1).AVAR);
                vals[10] += Double.valueOf(lstEP.get(1).AVA);
                vals[11] += Double.valueOf(lstEP.get(1).ANGLE0);

                vals[12] += Double.valueOf(lstEP.get(2).AVRMS);
                vals[13] += Double.valueOf(lstEP.get(2).AIRMS);
                vals[14] += Double.valueOf(lstEP.get(2).AWATT);
                vals[15] += Double.valueOf(lstEP.get(2).AVAR);
                vals[16] += Double.valueOf(lstEP.get(2).AVA);
                vals[17] += Double.valueOf(lstEP.get(2).ANGLE0);

            }

            List<ElectericalParams> sanjeshResultFinal = new ArrayList<>();
            for(int i=0;i<3;i++) {
                ElectericalParams epAvg = new ElectericalParams();

                epAvg.AVRMS = String.format("%.2f",(vals[0+i*6] / 10));
                epAvg.AIRMS = String.format("%.2f",(vals[1+i*6] / 10));

                epAvg.AWATT = String.format("%.2f",(vals[2+i*6] / 10));
                epAvg.AVAR = String.format("%.2f",(vals[3+i*6] / 10));
                epAvg.AVA = String.format("%.2f",(vals[4+i*6] / 10));

                epAvg.ANGLE0 = String.format("%.2f",(vals[5+i*6] / 10));

                sanjeshResultFinal.add(epAvg);
            }

            sanjeshResultAvrage.clear();
            showSanjeshResult(sanjeshResultFinal);
            //int i=sanjeshResultFinal.size();
        }


//        ElectericalParams epAvg = new ElectericalParams() ;
//
//        if(sanjeshResultAvrage.isEmpty()){
//            sanjeshResultAvrage.add(sanjeshResult.get(0));
//            sanjeshResultAvrage.add(sanjeshResult.get(1));
//            sanjeshResultAvrage.add(sanjeshResult.get(2));
//        }else {
//            for (int i = 0; i < 3; i++) {
//
//                epAvg.AVRMS = String.valueOf((Double.valueOf(epAvg.AVRMS) + Double.valueOf(sanjeshResult.get(i).AVRMS)) / 2);
//                epAvg.AIRMS = String.valueOf((Double.valueOf(epAvg.AIRMS) + Double.valueOf(sanjeshResult.get(i).AIRMS)) / 2);
//
//                epAvg.AWATT = String.valueOf((Double.valueOf(epAvg.AWATT) + Double.valueOf(sanjeshResult.get(i).AWATT)) / 2);
//                epAvg.AVAR = String.valueOf((Double.valueOf(epAvg.AVAR) + Double.valueOf(sanjeshResult.get(i).AVAR)) / 2);
//                epAvg.AVA = String.valueOf((Double.valueOf(epAvg.AVA) + Double.valueOf(sanjeshResult.get(i).AVA)) / 2);
//
//                sanjeshResultAvrage.set(i, epAvg);
//            }
//        }
//
//
//        if(numGetSample>6) {
//            showSanjeshResult(sanjeshResultAvrage);
//            numGetSample++;
//        }else{
//            numGetSample=0;
//        }
    }

    private void showSanjeshResult(List<ElectericalParams> sanjeshResult) {
        tvVR.setText(sanjeshResult.get(0).AVRMS);
        tvIR.setText(sanjeshResult.get(0).AIRMS);
        tvPR.setText(sanjeshResult.get(0).AWATT);
        tvQR.setText(sanjeshResult.get(0).AVAR);
        tvSR.setText(sanjeshResult.get(0).AVA);
        tvPFR.setText(sanjeshResult.get(0).ANGLE0);
        //tvPFR.setText(String.format("%.2f", Double.valueOf(sanjeshResult.get(0).ANGLE0)));

        tvVS.setText(sanjeshResult.get(1).AVRMS);
        tvIS.setText(sanjeshResult.get(1).AIRMS);
        tvPS.setText(sanjeshResult.get(1).AWATT);
        tvQS.setText(sanjeshResult.get(1).AVAR);
        tvSS.setText(sanjeshResult.get(1).AVA);
        tvPFS.setText(sanjeshResult.get(1).ANGLE0);
        //tvPFS.setText(String.format("%.2f", Double.valueOf(sanjeshResult.get(1).ANGLE0)));

        tvVT.setText(sanjeshResult.get(2).AVRMS);
        tvIT.setText(sanjeshResult.get(2).AIRMS);
        tvPT.setText(sanjeshResult.get(2).AWATT);
        tvQT.setText(sanjeshResult.get(2).AVAR);
        tvST.setText(sanjeshResult.get(2).AVA);
        tvPFT.setText(sanjeshResult.get(2).ANGLE0);
        //tvPFT.setText(String.format("%.2f", Double.valueOf(sanjeshResult.get(2).ANGLE0)));
    }

    private void showEmptyResult() {
        String emptyTxt = "-";
        tvVR.setText(emptyTxt);
        tvIR.setText(emptyTxt);
        tvPR.setText(emptyTxt);
        tvQR.setText(emptyTxt);
        tvSR.setText(emptyTxt);
        tvPFR.setText(emptyTxt);

        tvVS.setText(emptyTxt);
        tvIS.setText(emptyTxt);
        tvPS.setText(emptyTxt);
        tvQS.setText(emptyTxt);
        tvSS.setText(emptyTxt);
        tvPFS.setText(emptyTxt);

        tvVT.setText(emptyTxt);
        tvIT.setText(emptyTxt);
        tvPT.setText(emptyTxt);
        tvQT.setText(emptyTxt);
        tvST.setText(emptyTxt);
        tvPFT.setText(emptyTxt);
    }

    private void connectToModuleDialog(){
        String BluetoothDeviceName = G.getPref(SharePrefEnum.ModuleBluetoothName);
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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