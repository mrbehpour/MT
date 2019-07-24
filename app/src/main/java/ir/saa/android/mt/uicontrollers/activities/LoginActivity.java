package ir.saa.android.mt.uicontrollers.activities;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.SharePrefEnum;
import ir.saa.android.mt.model.entities.DeviceSerial;
import ir.saa.android.mt.model.entities.IMEI_RegisterInput;
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
//                .setTarget(findViewById(R.id.tvSerial))
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
        if(G.getPref(SharePrefEnum.DeviceId)==null) {
            G.setPref(SharePrefEnum.DeviceId, getDeviceIMEI());
        }
        tvSanjesh.setText(G.getPref(SharePrefEnum.DeviceId));

        spinner.setAdapter(adapter);


        adapterInit();

        findViewById(R.id.btnLogin).setOnClickListener(v -> {

            boolean isLoginValid = loginViewModel.IsLoginValid(spinnerMap.get(spinner.getSelectedItemPosition()), edtPassword.getText().toString());
            if (isLoginValid) {
                G.setPref(SharePrefEnum.EmpName,spinner.getSelectedItem().toString());
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                this.finish();
            } else {
                Toast fancyToast = FancyToast.makeText(G.context, (String) getResources().getText(R.string.UsernameOrPassword_msg), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false);
                fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                fancyToast.show();
            }
        });

        String regionId = G.getPref(SharePrefEnum.RegionId);

        if(regionId!=null ) {
            getUserByRegion(Integer.valueOf(regionId));
        }else{
            getAllUser();
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

    private void getAllUser(){

        if(spinnerMap.size()==0) {
            loginViewModel.getUsers().observe(this, relUsers -> {
                spinnerArray.clear();
                spinnerMap.clear();
                for (int i = 0; i < relUsers.size(); i++) {
                    spinnerMap.put(i, relUsers.get(i).UserID);
                    spinnerArray.add(relUsers.get(i).FirstName + " " + relUsers.get(i).LastName);
                }
                adapter.notifyDataSetChanged();
                setSpinnerLastIndex();

            });

        }
    }

    private void getUserByRegion(int regionId){
        loginViewModel.getUsersByRegion(Integer.valueOf(regionId)).observe(this, relUsers -> {
            spinnerArray.clear();
            spinnerMap.clear();

            if( relUsers.size()==0){
                getAllUser();
                return;
            }

            for (int i = 0; i < relUsers.size(); i++) {
                spinnerMap.put(i, relUsers.get(i).UserID);
                spinnerArray.add(relUsers.get(i).FirstName + " " + relUsers.get(i).LastName);
            }
            adapter.notifyDataSetChanged();
            setSpinnerLastIndex();

        });
    }

    private void setSpinnerLastIndex() {
        String empName = G.getPref(SharePrefEnum.EmpName);
        if (empName != null)
            for (int i = 0; i < spinner.getCount(); i++) {
                if (spinner.getItemAtPosition(i).equals(empName)) {
                    spinner.setSelection(i);
                }
            }
    }

    private void adapterInit() {
        spinnerArray.clear();
        if (loginViewModel.getUsers().getValue() != null) {
            for (RelUser user : loginViewModel.getUsers().getValue()) {
                spinnerArray.add(user.FirstName + " " + user.LastName);
            }
        }
    }


    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {

        if (G.fragmentNumStack.size() > 0) {
            Integer targetFragmentNum = G.fragmentNumStack.pop();
            G.startFragment(targetFragmentNum, true, null);
        } else {
            if (doubleBackToExitPressedOnce ) {
                G.currentFragmentNum=null;
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);

                finish();
                System.exit(0);
            }
            this.doubleBackToExitPressedOnce=true;

            Toast fancyToast = FancyToast.makeText(G.context, (String) getResources().getText(R.string.TapAgainForExit_msg), FancyToast.LENGTH_SHORT, FancyToast.INFO, false);
            fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
            fancyToast.show();
        }
    }

}
