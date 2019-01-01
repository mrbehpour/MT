package ir.saa.android.mt.uicontrollers.activities;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.SharePrefEnum;
import ir.saa.android.mt.model.entities.Bazdid;
import ir.saa.android.mt.model.entities.GetClientInput;
import ir.saa.android.mt.model.entities.Region;

import ir.saa.android.mt.viewmodels.BaseInfoViewModel;

public class DaryaftMoshtarakinActivity extends AppCompatActivity {

    LinearLayout llMoshtarkin;
    NumberProgressBar pbMoshtarakin;
    ImageView ivMoshtarakin;
    Boolean isDownloadClient;
    BaseInfoViewModel baseInfoViewModel = null;

    Spinner spinnerRegion;
    List<String> spinnerArray;
    GetClientInput getClientInput;
    ArrayAdapter<String> adapter;
    TextView tvLabelDarhaleDaryaftMoshtarakin;
    HashMap<Integer, Integer> spinnerMap = new HashMap<Integer, Integer>();
    HashMap<Integer, Integer> spinnerMapID = new HashMap<Integer, Integer>();
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
        setContentView(R.layout.activity_getmoshtarkin);
        if(G.getPref(SharePrefEnum.FontSize)!=null) {
            adjustFontScale(getResources().getConfiguration(), Float.parseFloat(G.getPref(SharePrefEnum.FontSize)));
        }
        android.support.v7.widget.Toolbar toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Your toolbar is now an action bar and you can use it like you always do, for example:
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getSupportActionBar().setTitle("");

        isDownloadClient = false;

        tvLabelDarhaleDaryaftMoshtarakin=(TextView) findViewById(R.id.tvLabelDarhaleDaryaftMoshtarakin);


        pbMoshtarakin = findViewById(R.id.pbMoshtarakin);
        pbMoshtarakin.setMax(100);

        llMoshtarkin = (LinearLayout) findViewById(R.id.llMoshtarakin);

        ivMoshtarakin = (ImageView) findViewById(R.id.ivMoshtarakin);

        baseInfoViewModel = ViewModelProviders.of(this).get(BaseInfoViewModel.class);

        spinnerRegion = findViewById(R.id.spnOmoor);

        spinnerArray = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.al_polomp_save_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(R.layout.al_polomp_save_spinner_dropdown);

        spinnerRegion.setAdapter(adapter);

        adapterInit();




        baseInfoViewModel.getRegion().observe(this, new Observer<List<Region>>() {
            @Override
            public void onChanged(@Nullable List<Region> regions) {
                spinnerArray.clear();
                spinnerMap.clear();
                spinnerMap.put(0,0);
                spinnerArray.add(0,"");
                for (int i = 0; i < regions.size(); i++) {
                    spinnerMap.put(i+1, regions.get(i).RegionID);
                    spinnerMapID.put(regions.get(i).RegionID,i+1);
                    spinnerArray.add(regions.get(i).RegionName);
                }
                adapter.notifyDataSetChanged();
                if(G.getPref("RegionID").equals("-1")==false) {
                    spinnerRegion.setSelection(spinnerMapID.get(Integer.valueOf(G.getPref("RegionID"))));
                    spinnerRegion.setEnabled(false);
                }else{
                    spinnerRegion.setEnabled(true);
                }

            }
        });



        llMoshtarkin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                List<Bazdid> bazdids=baseInfoViewModel.getBazdidIsSend(false);
                if(bazdids.size()!=0){
                    Toast.makeText(DaryaftMoshtarakinActivity.this,getResources().getText(R.string.Message_CheckSend),Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isDownloadClient == false) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ivMoshtarakin.setImageTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.ms_gray3));
                    }
                    pbMoshtarakin.setProgress(0);
                    tvLabelDarhaleDaryaftMoshtarakin.setVisibility(View.VISIBLE);
                    isDownloadClient = true;
                    getClientInput = new GetClientInput();
                    getClientInput.handHeldSerial = G.getPref("DeviceId");
                    getClientInput.agentId = Integer.valueOf(G.getPref("UserID"));
                    getClientInput.regionId = spinnerMap.get(spinnerRegion.getSelectedItemPosition());
                    baseInfoViewModel.getClientFromServer(getClientInput);
                }
            }
        });
        baseInfoViewModel.messageErrorLiveData.observe(this, new Observer<String>() {

            @Override
            public void onChanged(@Nullable String s) {
                tvLabelDarhaleDaryaftMoshtarakin.setVisibility(View.INVISIBLE);
                isDownloadClient=false;

                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();

            }
        });
        baseInfoViewModel.clientProgressPercentLiveData.observe(this, new Observer<Integer>() {

            @Override
            public void onChanged(@Nullable Integer integer) {
                pbMoshtarakin.setProgress(integer);
                if(integer==100){
                    isDownloadClient=false;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ivMoshtarakin.setImageTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green_A400));
                    }
                }

            }
        });
        try {
            Thread.sleep(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    private void adapterInit() {
        spinnerArray.clear();
        if (baseInfoViewModel.getRegion().getValue() != null) {
            for (Region region : baseInfoViewModel.getRegion().getValue()) {
                spinnerArray.add(region.RegionName);
            }
        }

    }
    }
