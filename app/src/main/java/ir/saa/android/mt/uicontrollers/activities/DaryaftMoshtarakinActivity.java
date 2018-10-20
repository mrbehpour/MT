package ir.saa.android.mt.uicontrollers.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.daimajia.numberprogressbar.NumberProgressBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
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
    HashMap<Integer, Integer> spinnerMap = new HashMap<Integer, Integer>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getmoshtarkin);

        isDownloadClient = false;

        pbMoshtarakin = findViewById(R.id.pbMoshtarakin);
        pbMoshtarakin.setMax(100);

        llMoshtarkin = (LinearLayout) findViewById(R.id.llMoshtarakin);

        ivMoshtarakin = (ImageView) findViewById(R.id.ivMoshtarakin);

        baseInfoViewModel = ViewModelProviders.of(this).get(BaseInfoViewModel.class);

        spinnerRegion = findViewById(R.id.spnOmoor);

        spinnerArray = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.al_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(R.layout.al_spinner_dropdown_item);

        spinnerRegion.setAdapter(adapter);

        adapterInit();


        baseInfoViewModel.getRegion().observe(this, new Observer<List<Region>>() {
            @Override
            public void onChanged(@Nullable List<Region> regions) {
                spinnerArray.clear();
                spinnerMap.clear();
                for (int i = 0; i < regions.size(); i++) {
                    spinnerMap.put(i, regions.get(i).RegionID);
                    spinnerArray.add(regions.get(i).RegionName);
                }
                adapter.notifyDataSetChanged();
            }
        });



        llMoshtarkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isDownloadClient == false) {
                    isDownloadClient = true;
                    getClientInput = new GetClientInput();
                    getClientInput.handHeldSerial = G.getPref("DeviceId");
                    getClientInput.agentId = Integer.valueOf(G.getPref("UserID"));
                    getClientInput.regionId = Integer.valueOf(G.getPref("RegionID"));
                    baseInfoViewModel.getClientFromServer(getClientInput);
                }
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
