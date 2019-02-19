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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.adapters.testresult.TestResultAdapter;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.components.MyDialog;
import ir.saa.android.mt.components.Tarikh;
import ir.saa.android.mt.enums.BundleKeysEnum;
import ir.saa.android.mt.enums.FragmentsEnum;
import ir.saa.android.mt.enums.SharePrefEnum;
import ir.saa.android.mt.model.entities.GPSInfo;
import ir.saa.android.mt.model.entities.TestDtl;
import ir.saa.android.mt.model.entities.TestInfo;
import ir.saa.android.mt.repositories.bluetooth.Bluetooth;
import ir.saa.android.mt.repositories.metertester.ElectericalParams;
import ir.saa.android.mt.repositories.metertester.MT;
import ir.saa.android.mt.repositories.metertester.TestResult;
import ir.saa.android.mt.uicontrollers.pojos.TestContor.TestContorFieldName;
import ir.saa.android.mt.uicontrollers.pojos.TestContor.TestContorParams;
import ir.saa.android.mt.viewmodels.AmaliyatViewModel;
import ir.saa.android.mt.viewmodels.LocationViewModel;
import ir.saa.android.mt.viewmodels.SanjeshViewModel;

public class SanjeshFragment extends Fragment {

    SanjeshViewModel sanjeshViewModel = null;
    List<ElectericalParams> sanjeshResult;
    AlertDialog ad;

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
                        showSanjeshResult(sanjeshResult);
                    }
                }
        );

        sanjeshViewModel.connectionStateMutableLiveData.observe(this, new Observer<Boolean>() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onChanged(@Nullable Boolean b) {
                        if(b){
                            HideProgressDialog();
                            btnReconnect.setVisibility(View.INVISIBLE);
                        }else{
                            btnReconnect.setVisibility(View.VISIBLE);
                        }
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        connectToModule();
        return rootView;
    }

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

    private void showSanjeshResult(List<ElectericalParams> sanjeshResult) {
        tvVR.setText(sanjeshResult.get(0).AVRMS);
        tvIR.setText(sanjeshResult.get(0).AIRMS);
        tvPR.setText(sanjeshResult.get(0).AWATTHR);
        tvQR.setText(sanjeshResult.get(0).AVARHR);
        tvSR.setText(sanjeshResult.get(0).AVAHR);
        tvPFR.setText(String.format("%.2f", Math.cos(Double.valueOf(sanjeshResult.get(0).ANGLE0))));

        tvVS.setText(sanjeshResult.get(1).AVRMS);
        tvIS.setText(sanjeshResult.get(1).AIRMS);
        tvPS.setText(sanjeshResult.get(1).AWATTHR);
        tvQS.setText(sanjeshResult.get(1).AVARHR);
        tvSS.setText(sanjeshResult.get(1).AVAHR);
        tvPFS.setText(String.format("%.2f", Math.cos(Double.valueOf(sanjeshResult.get(1).ANGLE0))));

        tvVT.setText(sanjeshResult.get(2).AVRMS);
        tvIT.setText(sanjeshResult.get(2).AIRMS);
        tvPT.setText(sanjeshResult.get(2).AWATTHR);
        tvQT.setText(sanjeshResult.get(2).AVARHR);
        tvST.setText(sanjeshResult.get(2).AVAHR);
        tvPFT.setText(String.format("%.2f", Math.cos(Double.valueOf(sanjeshResult.get(2).ANGLE0))));
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