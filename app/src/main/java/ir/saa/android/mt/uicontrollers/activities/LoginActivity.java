package ir.saa.android.mt.uicontrollers.activities;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.SharePrefEnum;
import ir.saa.android.mt.model.entities.DeviceSerial;
import ir.saa.android.mt.model.entities.ImiRegisterInput;
import ir.saa.android.mt.model.entities.RelUser;

import ir.saa.android.mt.viewmodels.DeviceSerialViewModel;
import ir.saa.android.mt.viewmodels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    LoginViewModel loginViewModel;
    DeviceSerialViewModel deviceSerialViewModel;
    Spinner spinner;
    EditText edtPassword;
    List<String> spinnerArray;
    ArrayAdapter<String> adapter;
    TextView tvSanjesh;
    HashMap<Integer, Integer> spinnerMap = new HashMap<Integer, Integer>();

    public  void adjustFontScale( Configuration configuration,Float fontSize) {

        configuration.fontScale = (float) fontSize;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(G.getPref(SharePrefEnum.FontSize)!=null) {
            adjustFontScale(getResources().getConfiguration(), Float.parseFloat(G.getPref(SharePrefEnum.FontSize)));
        }


        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        deviceSerialViewModel=ViewModelProviders.of(this).get(DeviceSerialViewModel.class);
        //loginViewModel.initializerUser();
        spinner = findViewById(R.id.spnUserName);
        edtPassword = findViewById(R.id.edtPassword);
        spinnerArray = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.al_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(R.layout.al_spinner_dropdown_item);
        tvSanjesh = findViewById(R.id.tvSanjesh);
//        new uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt.Builder(LoginActivity.this)
//                .setTarget(findViewById(R.id.tvSanjesh))
//                .setPrimaryText("شماره سریال دستگاه")
//                .setSecondaryText("جهت نمایش سریال دستگاه حتما باید دسترسی خواندن وضعیت تلفن به برنامه داده شود.")
//                .setPromptStateChangeListener(new uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt.PromptStateChangeListener()
//                {
//                    @Override
//                    public void onPromptStateChanged(uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt prompt, int state)
//                    {
//                        if (state == uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)
//                        {
//
//                        }
//                    }
//                })
//                .show();
        tvSanjesh.setText(tvSanjesh.getText()+"\n"+ G.getPref(SharePrefEnum.DeviceId));

        spinner.setAdapter(adapter);

        adapterInit();

        deviceSerialViewModel.IsValidImi.observe(this, new android.arch.lifecycle.Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    boolean isLoginValid = loginViewModel.IsLoginValid(spinnerMap.get(spinner.getSelectedItemPosition()), edtPassword.getText().toString());
                    if (isLoginValid) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else
                        Toast.makeText(LoginActivity.this, getResources().getText(R.string.LoginFail), Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btnLogin).setOnClickListener(v -> {
            boolean isLoginValid = loginViewModel.IsLoginValid(spinnerMap.get(spinner.getSelectedItemPosition()), edtPassword.getText().toString());
            if (isLoginValid) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            } else
                Toast.makeText(LoginActivity.this, getResources().getText(R.string.LoginFail), Toast.LENGTH_SHORT).show();
            //DeviceSerial deviceSerial=deviceSerialViewModel.getDeviceSerial(G.getPref(SharePrefEnum.DeviceId));
           // if(deviceSerial.isActive==false){
//                ImiRegisterInput imiRegisterInput=new ImiRegisterInput();
//                imiRegisterInput.regionId=deviceSerial.regionId;
//                imiRegisterInput.handHeldSerial=deviceSerial.SerialId;
//                deviceSerialViewModel.confirmImi(imiRegisterInput);

//            }else{
//                boolean isLoginValid = loginViewModel.IsLoginValid(spinnerMap.get(spinner.getSelectedItemPosition()), edtPassword.getText().toString());
//                if (isLoginValid) {
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                } else
//                    Toast.makeText(LoginActivity.this, getResources().getText(R.string.LoginFail), Toast.LENGTH_SHORT).show();
//            }

        });

        loginViewModel.getUsers().observe(this, relUsers -> {
            spinnerArray.clear();
            spinnerMap.clear();
            for (int i = 0; i < relUsers.size(); i++) {
                spinnerMap.put(i, relUsers.get(i).UserID);
                spinnerArray.add(relUsers.get(i).FirstName + " " + relUsers.get(i).LastName);
            }
            adapter.notifyDataSetChanged();
        });

    }

    private void adapterInit() {
        spinnerArray.clear();
        if (loginViewModel.getUsers().getValue() != null) {
            for (RelUser user : loginViewModel.getUsers().getValue()) {
                spinnerArray.add(user.FirstName + " " + user.LastName);
            }
        }
    }


Boolean doubleBackToExitPressedOnce = false;
     @Override
    public void onBackPressed() {
        if(G.fragmentNumStack.size()>0){
            Integer targetFragmentNum = G.fragmentNumStack.pop();
            G.startFragment(targetFragmentNum, true, null);
        }else{
            if (doubleBackToExitPressedOnce) {

                LoginActivity.this.finish();

                System.exit(0);
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(G.context,getResources().getText(R.string.Exit_Back), Toast.LENGTH_SHORT).show();
            new android.os.Handler().postDelayed(() -> doubleBackToExitPressedOnce=false, 1500);
        }
    }
}
