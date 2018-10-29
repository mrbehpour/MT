package ir.saa.android.mt.uicontrollers.activities;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
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
import ir.saa.android.mt.model.entities.RelUser;
import ir.saa.android.mt.viewmodels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    LoginViewModel loginViewModel;
    Spinner spinner;
    EditText edtPassword;
    List<String> spinnerArray;
    ArrayAdapter<String> adapter;
    TextView tvSanjesh;
    HashMap<Integer, Integer> spinnerMap = new HashMap<Integer, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        //loginViewModel.initializerUser();
        spinner = findViewById(R.id.spnUserName);
        edtPassword = findViewById(R.id.edtPassword);
        spinnerArray = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.al_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(R.layout.al_spinner_dropdown_item);
        tvSanjesh = findViewById(R.id.tvSanjesh);
        tvSanjesh.setText(tvSanjesh.getText()+"\n"+ getDeviceIMEI());
        G.setPref("DeviceId",getDeviceIMEI());
        spinner.setAdapter(adapter);

        adapterInit();

        findViewById(R.id.btnLogin).setOnClickListener(v -> {
            boolean isLoginValid = loginViewModel.IsLoginValid(spinnerMap.get(spinner.getSelectedItemPosition()), edtPassword.getText().toString());
            if (isLoginValid) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            } else
                Toast.makeText(this, getResources().getText(R.string.LoginFail), Toast.LENGTH_SHORT).show();
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

    public String getDeviceIMEI() {
        String deviceUniqueIdentifier = null;
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            deviceUniqueIdentifier = telephonyManager.getDeviceId();

        }
        return deviceUniqueIdentifier;
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
