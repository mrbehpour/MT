package ir.saa.android.mt.uicontrollers.activities;


import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.components.MyDialog;
import ir.saa.android.mt.enums.SharePrefEnum;
import ir.saa.android.mt.model.entities.DeviceSerial;
import ir.saa.android.mt.model.entities.ImiRegisterInput;
import ir.saa.android.mt.model.entities.Region;
import ir.saa.android.mt.viewmodels.DeviceSerialViewModel;

public class SendSerialActivity extends AppCompatActivity {

    Spinner spinnerRegion;
    List<String> spinnerArray;
    ArrayAdapter<String> adapter;
    TextView tvSanjesh;
    Button btnConfirm;
    DeviceSerialViewModel deviceSerialViewModel=null;
    HashMap<Integer,Integer> spinnerMapRegion = new HashMap<Integer, Integer>();
    public  void adjustFontScale(Configuration configuration, Float fontSize) {

        configuration.fontScale = (float) fontSize;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);

    }

    public String getDeviceIMEI() {
        String deviceUniqueIdentifier = null;
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            deviceUniqueIdentifier = telephonyManager.getDeviceId();

        }
        return deviceUniqueIdentifier;
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    public boolean isInternetAvailable() {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendserial);
        deviceSerialViewModel= ViewModelProviders.of(this).get(DeviceSerialViewModel.class);
        if(G.getPref(SharePrefEnum.FontSize)!=null) {
            adjustFontScale(getResources().getConfiguration(), Float.parseFloat(G.getPref(SharePrefEnum.FontSize)));
        }

        if(G.getPref(SharePrefEnum.AddressServer)==null){
            MyDialog myDialog=new MyDialog(this);
            myDialog.addContentXml(R.layout.dialog_address);
            EditText edtAddress=myDialog.getDialog().findViewById(R.id.edtAddress);
            myDialog.setTitle(getResources().getText(R.string.TitleCaption).toString());
            myDialog.addButton(getResources().getText(R.string.OkButton).toString(), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(edtAddress.getText().toString().equals("-")){
                       return;
                    }
                    G.setPref(SharePrefEnum.AddressServer,edtAddress.getText().toString());
                    myDialog.dismiss();
                }
            });
            myDialog.show();

        }

        if(isNetworkConnected()){
            if(isInternetAvailable()) {
                deviceSerialViewModel.getRegionFromServer();
            }else{
                Toast.makeText(this, getResources().getText(R.string.MessagAccessMessage), Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, getResources().getText(R.string.MessageConntection), Toast.LENGTH_SHORT).show();
            return;
        }

        G.setPref(SharePrefEnum.DeviceId,getDeviceIMEI());
        tvSanjesh=(TextView)findViewById(R.id.tvSerial);
        tvSanjesh.setText(tvSanjesh.getText()+"\n"+ G.getPref(SharePrefEnum.DeviceId));

        spinnerRegion=(Spinner)findViewById(R.id.spnOmoor);
        spinnerArray=new ArrayList<>();
        adapter=new ArrayAdapter<>(this,R.layout.al_polomp_save_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(R.layout.al_polomp_save_spinner_dropdown);
        spinnerRegion.setAdapter(adapter);
        adapterInit();

        deviceSerialViewModel.getRegion().observe(this, new Observer<List<Region>>() {
            @Override
            public void onChanged(@Nullable List<Region> regions) {
                spinnerArray.clear();
                spinnerMapRegion.clear();
                for (int i = 0; i < regions.size(); i++) {
                    spinnerArray.add(regions.get(i).RegionName);
                    spinnerMapRegion.put(i,regions.get(i).RegionID);
                }
                adapter.notifyDataSetChanged();
            }
        });
        deviceSerialViewModel.IsRegisterImi.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    Intent intent=new Intent(SendSerialActivity.this,LoginActivity.class);
                    startActivity(intent);
                    SendSerialActivity.this.finish();
                }
            }
        });
        deviceSerialViewModel.getDeviceSerialLiveData(G.getPref(SharePrefEnum.DeviceId)).observe(this, new Observer<DeviceSerial>() {
            @Override
            public void onChanged(@Nullable DeviceSerial deviceSerial) {
                if(deviceSerial!=null){
                    Intent intent=new Intent(SendSerialActivity.this,LoginActivity.class);
                    startActivity(intent);
                    SendSerialActivity.this.finish();
                }

            }
        });

        btnConfirm=(Button)findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImiRegisterInput  imiRegisterInput=new ImiRegisterInput();
                imiRegisterInput.handHeldSerial=G.getPref(SharePrefEnum.DeviceId);
                imiRegisterInput.regionId= Short.valueOf(spinnerMapRegion.get(spinnerRegion.getSelectedItemPosition()).toString());
                deviceSerialViewModel.registerImi(imiRegisterInput);

            }
        });

        DeviceSerial deviceSerial=  deviceSerialViewModel.getDeviceSerial(G.getPref(SharePrefEnum.DeviceId));

        if(deviceSerial!=null) {
            if(deviceSerial.isActive) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                this.finish();
            }
        }
    }

    private void adapterInit() {
        spinnerArray.clear();
        if (deviceSerialViewModel.getRegion().getValue() != null) {
            for (Region region : deviceSerialViewModel.getRegion().getValue()) {
                spinnerArray.add(region.RegionName);
            }
        }

    }

    @Override
    public void onBackPressed() {
                SendSerialActivity.this.finish();
                System.exit(0);
    }

}
