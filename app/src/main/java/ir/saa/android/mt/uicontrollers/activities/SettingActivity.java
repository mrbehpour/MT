package ir.saa.android.mt.uicontrollers.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.SwitchCompat;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.SharePrefEnum;
import ir.saa.android.mt.model.entities.DeviceSerial;
import ir.saa.android.mt.model.entities.Setting;
import ir.saa.android.mt.repositories.retrofit.RetrofitMT;
import ir.saa.android.mt.viewmodels.DeviceSerialViewModel;
import ir.saa.android.mt.viewmodels.ModuleViewModel;


public class SettingActivity extends AppCompatActivity {

    EditText edtServerAddress;
    Button btnSave;
    ModuleViewModel moduleViewModel;
    Spinner spinner;
    Spinner spinnerRead;
    SwitchCompat swBluetooth;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapterRead;
    RadioButton rbtSmall;
    RadioButton rbtNormal;
    RadioButton rbtLarge;
    RadioButton rbtHuge;
    RetrofitMT retrofitMT=null;
    DeviceSerialViewModel deviceSerialViewModel=null;
    int myInt;

    HashMap<String, Integer> spinnerMap = new HashMap<String, Integer>();
    public  void adjustFontScale(Configuration configuration, Float fontSize) {

        configuration.fontScale = (float) fontSize;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) G.context.getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getResources().updateConfiguration(configuration, metrics);

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings);
        deviceSerialViewModel= ViewModelProviders.of(this).get(DeviceSerialViewModel.class);
        DeviceSerial deviceSerial=  deviceSerialViewModel.getDeviceSerial(G.getPref(SharePrefEnum.DeviceId));

        if(deviceSerial!=null) {

                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                this.finish();
                return;

        }


        edtServerAddress=(EditText) findViewById(R.id.edtServerAddress);
        btnSave=(AppCompatButton)findViewById(R.id.btnSave);
        //-Fontsize
        rbtSmall=(RadioButton)findViewById(R.id.rbtSmall);
        rbtNormal=(RadioButton)findViewById(R.id.rbtNormal);
        rbtLarge=(RadioButton)findViewById(R.id.rbtLarge);
        rbtHuge=(RadioButton)findViewById(R.id.rbtHuge);
        if(G.getPref(SharePrefEnum.FontSize)!=null) {
            switch (G.getPref(SharePrefEnum.FontSize)) {
                case "0.85":
                    rbtSmall.setChecked(true);
                    break;
                case "1":
                    rbtNormal.setChecked(true);
                    break;
                case "1.15":
                    rbtLarge.setChecked(true);
                    break;
                case "1.3":
                    rbtHuge.setChecked(true);
                    break;
            }
        }

        rbtSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G.setPref(SharePrefEnum.FontSize,"0.85");
            }
        });

        rbtNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G.setPref(SharePrefEnum.FontSize,"1");
            }
        });
        rbtLarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G.setPref(SharePrefEnum.FontSize,"1.15");
            }
        });

        rbtHuge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G.setPref(SharePrefEnum.FontSize,"1.3");
            }
        });
        if(G.getPref(SharePrefEnum.FontSize)!=null) {
            adjustFontScale(getResources().getConfiguration(), Float.parseFloat(G.getPref(SharePrefEnum.FontSize)));
        }
        //-Address
        if(G.getPref(SharePrefEnum.AddressServer)!=null) {
            edtServerAddress.setText(G.getPref(SharePrefEnum.AddressServer));
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G.setPref(SharePrefEnum.AddressServer,edtServerAddress.getText().toString());
                if(G.getPref(SharePrefEnum.FontSize)!=null) {
                    adjustFontScale(getResources().getConfiguration(), Float.parseFloat(G.getPref(SharePrefEnum.FontSize)));
                }

                if(retrofitMT==null){
                   retrofitMT=RetrofitMT.getInstance();
                }

                //Toast.makeText(G.context,getResources().getText(R.string.MessageSuccess),Toast.LENGTH_SHORT).show();
                Toast fancyToast = FancyToast.makeText(G.context, (String) getResources().getText(R.string.SaveOperationSuccess_msg), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false);
                fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                fancyToast.show();
                Intent intent=new Intent(SettingActivity.this, SendSerialActivity.class);
                startActivity(intent);
                SettingActivity.this.finish();

            }
        });
        //-BlueTooth
        moduleViewModel = ViewModelProviders.of(this).get(ModuleViewModel.class);
        swBluetooth=(SwitchCompat)findViewById(R.id.swBluetooth);
        //spinnerArray=new ArrayList<>();
        spinner = (Spinner) findViewById(R.id.spnPaired);
        spinnerRead=(Spinner)findViewById(R.id.spnPairedRead);
        moduleViewModel.listBluetoothName.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {

                if(strings.size()!=0) {

                    fillPairedDeviceSpinner(strings);
                    fillPairedDeviceSpinnerRead(strings);
                }
            }
        });
        moduleViewModel.bluetoothIsEnable().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                swBluetooth.setChecked(aBoolean);


            }
        });

        swBluetooth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    moduleViewModel.setBluetoothEnable(true);

                }else{
                    moduleViewModel.setBluetoothEnable(false);

                }
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

            @Override
            public void onItemSelected(AdapterView adapter, View v, int i, long lng) {
                String SelectedItem =  adapter.getItemAtPosition(i).toString();
                G.setPref(SharePrefEnum.ModuleBluetoothName,SelectedItem);
                //or this can be also right: selecteditem = level[i];
            }
        });
        spinnerRead.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapter, View view, int i, long l) {
                String SelectedItem =  adapter.getItemAtPosition(i).toString();
                G.setPref(SharePrefEnum.ModuleBluetoothNameRead,SelectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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

    private void fillPairedDeviceSpinner(List<String> spinnerArray){



        adapter = new ArrayAdapter<>(this, R.layout.al_majol_spinner_item,spinnerArray );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        for(int i=0;i<spinnerArray.size();i++){
            spinnerMap.put(spinnerArray.get(i).toString(),i);
        }
        if( G.getPref(SharePrefEnum.ModuleBluetoothName)!=null && spinnerMap.size()!=0) {
            if(spinnerMap.get(G.getPref(SharePrefEnum.ModuleBluetoothName))!=null) {
                spinner.setSelection(spinnerMap.get(G.getPref(SharePrefEnum.ModuleBluetoothName)));
            }

        }

    }
    private void fillPairedDeviceSpinnerRead(List<String> spinnerArray){

        adapterRead=new ArrayAdapter<>(this, R.layout.al_majol_spinner_item_read,spinnerArray );
        adapterRead.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRead.setAdapter(adapterRead);
        for(int i=0;i<spinnerArray.size();i++){
            spinnerMap.put(spinnerArray.get(i).toString(),i);
        }
        if( G.getPref(SharePrefEnum.ModuleBluetoothNameRead)!=null && spinnerMap.size()!=0) {
            if(spinnerMap.get(G.getPref(SharePrefEnum.ModuleBluetoothNameRead))!=null) {
                spinnerRead.setSelection(spinnerMap.get(G.getPref(SharePrefEnum.ModuleBluetoothNameRead)));
            }

        }

    }
}