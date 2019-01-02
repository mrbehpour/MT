package ir.saa.android.mt.uicontrollers.activities;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.SharePrefEnum;
import ir.saa.android.mt.model.entities.Region;
import ir.saa.android.mt.viewmodels.DeviceSerialViewModel;

public class SendSerialActivity extends AppCompatActivity {

    Spinner spinnerRegion;
    List<String> spinnerArray;
    ArrayAdapter<String> adapter;
    Button btnConfirm;
    DeviceSerialViewModel deviceSerialViewModel=null;

    public  void adjustFontScale(Configuration configuration, Float fontSize) {

        configuration.fontScale = (float) fontSize;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);

    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendserial);
        if(G.getPref(SharePrefEnum.FontSize)!=null) {
            adjustFontScale(getResources().getConfiguration(), Float.parseFloat(G.getPref(SharePrefEnum.FontSize)));
        }
        deviceSerialViewModel= ViewModelProviders.of(this).get(DeviceSerialViewModel.class);
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
                for (int i = 0; i < regions.size(); i++) {
                    spinnerArray.add(regions.get(i).RegionName);
                }
                adapter.notifyDataSetChanged();
            }
        });

        btnConfirm=(Button)findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SendSerialActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });


//        Intent intent=new Intent(this,LoginActivity.class);
//        startActivity(intent);
//        this.finish();
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
