package ir.saa.android.mt.uicontrollers.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.numberprogressbar.*;

import ir.saa.android.mt.R;
import ir.saa.android.mt.viewmodels.BaseInfoViewModel;

public class DaryaftActivity extends AppCompatActivity   {

    CardView cvMoshtarkin;
    CardView cvTanzimat;
    CardView cvPayeh;
    CardView cvKarbaran;
    NumberProgressBar pbDaryaftEtelatPayeh;
    NumberProgressBar pbKarbaran;
    NumberProgressBar pbMoshtarakin;
    NumberProgressBar pbTanzimat;
    ImageView ivTanzimat;
    ImageView ivDaryaftEtelatPayeh;
    ImageView ivKarbaran;
    ImageView ivMoshtarakin;

    BaseInfoViewModel baseInfoViewModel = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baseinfo);

        baseInfoViewModel=ViewModelProviders.of(this).get(BaseInfoViewModel.class);
        pbDaryaftEtelatPayeh =  findViewById(R.id.pbDaryaftEtelatPayeh);
        pbDaryaftEtelatPayeh.setMax(100);

        pbKarbaran =  findViewById(R.id.pbKarbaran);
        pbKarbaran.setMax(100);

        pbMoshtarakin =  findViewById(R.id.pbMoshtarakin);
        pbMoshtarakin.setMax(100);

        pbTanzimat =  findViewById(R.id.pbTanzimat);
        pbTanzimat.setMax(100);


        cvTanzimat=(CardView)findViewById(R.id.cvTanzimat);
        cvMoshtarkin=(CardView)findViewById(R.id.cvMoshtarakin);
        cvPayeh=(CardView)findViewById(R.id.cvPayeh);
        cvKarbaran=(CardView)findViewById(R.id.cvKarbaran);

        ivTanzimat=(ImageView)findViewById(R.id.ivTanzimat);
        ivMoshtarakin=(ImageView)findViewById(R.id.ivMoshtarakin);
        ivKarbaran=(ImageView)findViewById(R.id.ivKarbaran);
        ivDaryaftEtelatPayeh=(ImageView)findViewById(R.id.ivPayeh);

        cvTanzimat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baseInfoViewModel.getSettingFromServer();


            }
        });
        baseInfoViewModel.settingProgressPercentLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer){
                pbTanzimat.setProgress(integer);
                if(integer==100){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ivTanzimat.setImageTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green_A400));
                    }
                }

            }
        });

        cvKarbaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baseInfoViewModel.getUserFromServer();
            }
        });

        baseInfoViewModel.UsersProgressPercentLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                pbKarbaran.setProgress(integer);
                if(integer==100){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ivKarbaran.setImageTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green_A400));
                    }
                }
            }
        });

        cvPayeh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                baseInfoViewModel.getBaseInfoFromServer();
            }
        });

        baseInfoViewModel.baseinfoProgressPercentLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                pbDaryaftEtelatPayeh.setProgress(integer);
                if(integer==100){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ivDaryaftEtelatPayeh.setImageTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green_A400));
                    }
                }
            }
        });
    }


}
