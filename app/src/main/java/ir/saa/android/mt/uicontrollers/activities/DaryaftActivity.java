package ir.saa.android.mt.uicontrollers.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.daimajia.numberprogressbar.*;
import com.shashank.sony.fancytoastlib.FancyToast;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;

import ir.saa.android.mt.enums.SharePrefEnum;
import ir.saa.android.mt.model.entities.DeviceSerial;
import ir.saa.android.mt.viewmodels.BaseInfoViewModel;


public class DaryaftActivity extends AppCompatActivity {

    //LinearLayout llMoshtarkin;
    LinearLayout llTanzimat;
    LinearLayout llPayeh;
    LinearLayout llKarbaran;
    NumberProgressBar pbDaryaftEtelatPayeh;
    NumberProgressBar pbKarbaran;
    //NumberProgressBar pbMoshtarakin;
    NumberProgressBar pbTanzimat;
    ImageView ivTanzimat;
    ImageView ivDaryaftEtelatPayeh;
    ImageView ivKarbaran;
    //ImageView ivMoshtarakin;
    Boolean isDownloadBaseInfo;
    Boolean isDownloadUser;
    Boolean isDownloadSetting;
    AppCompatButton btnNextLevel;

    //Boolean isDownloadClient;
    private DrawerLayout mDrawerLayout;

    TextView tvLabelDarhaleDaryaftEtelatPayeh;
    TextView tvLabelDarhaleDaryaftTanzimat;
    TextView tvLabelDarhaleDaryaftUsers;


    BaseInfoViewModel baseInfoViewModel = null;
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
        setContentView(R.layout.activity_baseinfo);

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
        isDownloadBaseInfo=false;
        isDownloadUser=false;
        isDownloadSetting=false;
        //isDownloadClient=false;

        baseInfoViewModel=ViewModelProviders.of(this).get(BaseInfoViewModel.class);
        pbDaryaftEtelatPayeh =  findViewById(R.id.pbDaryaftEtelatPayeh);
        pbDaryaftEtelatPayeh.setMax(100);

        tvLabelDarhaleDaryaftEtelatPayeh=findViewById(R.id.tvLabelDarhaleDaryaftEtelatPayeh);
        tvLabelDarhaleDaryaftTanzimat=findViewById(R.id.tvLabelDarhaleDaryaftTanzimat);
        tvLabelDarhaleDaryaftUsers=findViewById(R.id.tvLabelDarhaleDaryaftUsers);

        pbKarbaran =  findViewById(R.id.pbKarbaran);
        pbKarbaran.setMax(100);

//        pbMoshtarakin =  findViewById(R.id.pbMoshtarakin);
//        pbMoshtarakin.setMax(100);

        pbTanzimat =  findViewById(R.id.pbTanzimat);
        pbTanzimat.setMax(100);


        llTanzimat=(LinearLayout)findViewById(R.id.llTanzimat);
        //llMoshtarkin=(LinearLayout)findViewById(R.id.llMoshtarakin);
        llPayeh=(LinearLayout)findViewById(R.id.llPayeh);
        llKarbaran=(LinearLayout)findViewById(R.id.llKarbaran);

        ivTanzimat=(ImageView)findViewById(R.id.ivTanzimat);
        //ivMoshtarakin=(ImageView)findViewById(R.id.ivMoshtarakin);
        ivKarbaran=(ImageView)findViewById(R.id.ivKarbaran);
        ivDaryaftEtelatPayeh=(ImageView)findViewById(R.id.ivPayeh);

        llTanzimat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isDownloadSetting==false){
                    isDownloadSetting=true;
                    baseInfoViewModel.getSettingFromServer();
                    tvLabelDarhaleDaryaftTanzimat.setVisibility(View.VISIBLE);
                }



            }
        });
        baseInfoViewModel.settingProgressPercentLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer){
                pbTanzimat.setProgress(integer);
                if(integer==100){
                    isDownloadSetting=false;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ivTanzimat.setImageTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green_A400));
                    }
                }

            }
        });

        llKarbaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isDownloadUser==false){
                    isDownloadUser=true;
                    baseInfoViewModel.getUserFromServer();
                    tvLabelDarhaleDaryaftUsers.setVisibility(View.VISIBLE);
                }

            }
        });

        baseInfoViewModel.UsersProgressPercentLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                pbKarbaran.setProgress(integer);
                if(integer==100){
                    isDownloadUser=false;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ivKarbaran.setImageTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green_A400));
                    }
                }
            }
        });

        llPayeh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isDownloadBaseInfo==false){
                    isDownloadBaseInfo=true;
                    baseInfoViewModel.getBaseInfoFromServer();
                    tvLabelDarhaleDaryaftEtelatPayeh.setVisibility(View.VISIBLE);
                }

            }
        });
        baseInfoViewModel.messageErrorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                isDownloadBaseInfo=false;
                //isDownloadClient=false;
                isDownloadSetting=false;
                isDownloadUser=false;

                tvLabelDarhaleDaryaftEtelatPayeh.setVisibility(View.INVISIBLE);
                tvLabelDarhaleDaryaftTanzimat.setVisibility(View.INVISIBLE);
                tvLabelDarhaleDaryaftUsers.setVisibility(View.INVISIBLE);

                //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                Toast fancyToast= FancyToast.makeText(getApplicationContext(),s,FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false);
                fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                fancyToast.show();

            }
        });
        baseInfoViewModel.baseinfoProgressPercentLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                pbDaryaftEtelatPayeh.setProgress(integer);
                if(integer==100){
                    isDownloadBaseInfo=false;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ivDaryaftEtelatPayeh.setImageTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green_A400));
                    }
                }
            }
        });


//        llMoshtarkin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(isDownloadClient==false) {
//                    isDownloadClient=true;
//                    GetClientInput getClientInput = new GetClientInput();
//                    getClientInput.handHeldSerial = G.getPref("DeviceId");
//                    getClientInput.agentId = Integer.valueOf(G.getPref("UserID"));
//
//                    getClientInput.regionId = Integer.valueOf(G.getPref("RegionID"));
//                    baseInfoViewModel.getClientFromServer(getClientInput);
//                }
//            }
//        });
//        baseInfoViewModel.clientProgressPercentLiveData.observe(this, new Observer<Integer>() {
//            @Override
//            public void onChanged(@Nullable Integer integer) {
//                pbMoshtarakin.setProgress(integer);
//                if(integer==100){
//                    isDownloadClient=false;
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        ivMoshtarakin.setImageTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.green_A400));
//                    }
//                }
//            }
//        });
    }



    @Override
    public void onBackPressed() {
        if(isDownloadBaseInfo  || isDownloadSetting || isDownloadUser){
           // Toast.makeText(DaryaftActivity.this,R.string.backMessage,Toast.LENGTH_SHORT).show();
            Toast fancyToast= FancyToast.makeText(DaryaftActivity.this, String.valueOf(getResources().getText(R.string.backMessage)),FancyToast.LENGTH_SHORT,FancyToast.INFO,false);
            fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            fancyToast.show();
            return;
        }


        super.onBackPressed();
    }
}
