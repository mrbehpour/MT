package ir.saa.android.mt.uicontrollers.activities;


import android.Manifest;
import android.app.ProgressDialog;
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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
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
    LinearLayout laySettings;
    Button btnConfirm;
    AppCompatButton btnGetRegion;
    ProgressDialog progressDialog;
    DeviceSerialViewModel deviceSerialViewModel=null;
    private static final int MY_PERMISSIONS_REQUEST=0;
    HashMap<Integer,Integer> spinnerMapRegion = new HashMap<Integer, Integer>();
    public  void adjustFontScale(Configuration configuration, Float fontSize) {

        configuration.fontScale = (float) fontSize;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);

    }

    private void connectToModuleDialog(){

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage(getResources().getText(R.string.PleaseWait_msg));
        progressDialog.setTitle(getResources().getText(R.string.GetRegionFromServer_msg));
        progressDialog.setCancelable(true);
        progressDialog.show();

    }

    public void HideProgressDialog(){
        if(progressDialog!=null) progressDialog.dismiss();
    }
    public String getDeviceIMEI() {
        String deviceUniqueIdentifier = null;
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            deviceUniqueIdentifier = telephonyManager.getDeviceId();

        }
        return deviceUniqueIdentifier;
    }





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendserial);
        if(G.getPref(SharePrefEnum.FontSize)!=null) {
            adjustFontScale(getResources().getConfiguration(), Float.parseFloat(G.getPref(SharePrefEnum.FontSize)));

        }

        deviceSerialViewModel= ViewModelProviders.of(this).get(DeviceSerialViewModel.class);
        DeviceSerial deviceSerial=  deviceSerialViewModel.getDeviceSerial(G.getPref(SharePrefEnum.DeviceId));

        if(deviceSerial!=null) {
            if(deviceSerial.isActive) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                this.finish();
                return;
            }
        }
        checkAndRequestPermissions();
        laySettings=(LinearLayout)findViewById(R.id.laySettings);

        laySettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent=new Intent(SendSerialActivity.this,SettingActivity.class);
               startActivity(intent);

            }
        });


        if(G.getPref(SharePrefEnum.AddressServer)!=null) {
            if (isNetworkConnected()) {
                if (isInternetAvailable()) {
                    if(Ping(G.getPref(SharePrefEnum.AddressServer).substring(7).split(":")[0])) {
                        deviceSerialViewModel.getRegionFromServer();
                        connectToModuleDialog();

                    }
                } else {
                    //Toast.makeText(SendSerialActivity.this, getResources().getText(R.string.MessagAccessMessage), Toast.LENGTH_SHORT).show();
                    Toast fancyToast = FancyToast.makeText(SendSerialActivity.this, (String) getResources().getText(R.string.AccessServerError_msg), FancyToast.LENGTH_LONG, FancyToast.INFO, false);
                    fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    fancyToast.show();
                }
            } else {
                //Toast.makeText(SendSerialActivity.this, getResources().getText(R.string.MessageConntection), Toast.LENGTH_SHORT).show();
                Toast fancyToast = FancyToast.makeText(SendSerialActivity.this, (String) getResources().getText(R.string.ConntecInternet_msg), FancyToast.LENGTH_LONG, FancyToast.INFO, false);
                fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                fancyToast.show();
                return;
            }
        }


            deviceSerialViewModel.IsCompleted.observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(@Nullable Boolean aBoolean) {
                    if(aBoolean){
                        HideProgressDialog();
                    }else{
                        HideProgressDialog();
                    }
                }
            });




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


        G.setPref(SharePrefEnum.DeviceId,getDeviceIMEI());
        tvSanjesh=(TextView)findViewById(R.id.tvSerial);
        tvSanjesh.setText(tvSanjesh.getText()+"\n"+ G.getPref(SharePrefEnum.DeviceId));

        spinnerRegion=(Spinner)findViewById(R.id.spnOmoor);
        spinnerArray=new ArrayList<>();
        adapter=new ArrayAdapter<>(this,R.layout.al_polomp_save_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(R.layout.al_polomp_save_spinner_dropdown);
        spinnerRegion.setAdapter(adapter);

        deviceSerialViewModel.IsRegisterImi.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){

                    Intent intent=new Intent(SendSerialActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
//        deviceSerialViewModel.getDeviceSerialLiveData(G.getPref(SharePrefEnum.DeviceId)).observe(this, new Observer<DeviceSerial>() {
//            @Override
//            public void onChanged(@Nullable DeviceSerial deviceSerial) {
//                if(deviceSerial!=null){
//                    Intent intent=new Intent(SendSerialActivity.this,LoginActivity.class);
//                    startActivity(intent);
//                    SendSerialActivity.this.finish();
//                }
//
//            }
//        });

        btnConfirm=(Button)findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImiRegisterInput  imiRegisterInput=new ImiRegisterInput();
                imiRegisterInput.handHeldSerial=G.getPref(SharePrefEnum.DeviceId);
                if(spinnerRegion.getSelectedItemPosition()!=-1) {
                    imiRegisterInput.regionId = Short.valueOf(spinnerMapRegion.get(spinnerRegion.getSelectedItemPosition()).toString());
                    deviceSerialViewModel.registerImi(imiRegisterInput);
                }

            }
        });


    }


    private  void checkAndRequestPermissions() {


        String [] permissions=new String[]{
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
				android.Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.INTERNET,
				android.Manifest.permission.BLUETOOTH,
                android.Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                //Manifest.permission.WRITE_SECURE_SETTINGS,
                Manifest.permission.ACCESS_COARSE_LOCATION,
        };

        List<String> listPermissionsNeeded = new ArrayList<>();

        for (String permission:permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }

        if (!listPermissionsNeeded.isEmpty()) {
            requestPermissions(listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MY_PERMISSIONS_REQUEST);
        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public boolean isInternetAvailable() {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
    private boolean Ping(String IP){
        System.out.println("executeCommand");
        Runtime runtime = Runtime.getRuntime();
        try
        {
            Process  mIpAddrProcess = runtime.exec("/system/bin/ping -c 1 " + IP);
            int mExitValue = mIpAddrProcess.waitFor();
            System.out.println(" mExitValue "+mExitValue);
            if(mExitValue==0){
                return true;
            }else{
                return false;
            }
        }
        catch (InterruptedException ignore)
        {
            ignore.printStackTrace();
            System.out.println(" Exception:"+ignore);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println(" Exception:"+e);
        }
        return false;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        Log.e("Req Code", "" + requestCode);

        switch (requestCode){
            case MY_PERMISSIONS_REQUEST:
                adapterInit();
        }
    }

    private void adapterInit() {
        //spinnerArray.clear();
        if (deviceSerialViewModel.getRegion().getValue() != null) {
            for (Region region : deviceSerialViewModel.getRegion().getValue()) {
                spinnerArray.add(region.RegionName);
            }
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
