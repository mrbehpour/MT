package ir.saa.android.mt.uicontrollers.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.net.ConnectivityManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.content.Context;
import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.components.MyDialog;
import ir.saa.android.mt.enums.SharePrefEnum;
import ir.saa.android.mt.model.entities.DeviceSerial;
import ir.saa.android.mt.viewmodels.DeviceSerialViewModel;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.net.InetAddress;

public class SplashScreenActivity extends AppCompatActivity {

    // Duration of wait
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    Context context = SplashScreenActivity.this;
    LinearLayout flRect;
    pl.droidsonroids.gif.GifTextView ivLogo;
    DeviceSerialViewModel deviceSerialViewModel=null;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splashscreen);
        deviceSerialViewModel= ViewModelProviders.of(this).get(DeviceSerialViewModel.class);
        flRect=(LinearLayout)findViewById(R.id.flRect);

        // New Handler to start the Login-Activity
        // and close this Splash-Screen after some seconds
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                // Create an Intent that will start the Login-Activity
//                Intent mainIntent = new Intent(SplashScreenActivity.this,LoginActivity.class);
//                SplashScreenActivity.this.startActivity(mainIntent);
//                SplashScreenActivity.this.finish();
                goToNextActivity(R.anim.slide_in_from_left, R.anim.fade_out);
            }
        }, SPLASH_DISPLAY_LENGTH);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                ivLogo = findViewById(R.id.ivLogo);
                ivLogo.setBackgroundResource(R.drawable.splach1);
            }
        }, 500);

        Animation myanim = AnimationUtils.loadAnimation(this,R.anim.splashanimation);
        flRect.startAnimation(myanim);


    }


    private void goToNextActivity(int animationIn, int animationOut) {
        Intent intent = new Intent(context, SendSerialActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        overridePendingTransition(animationIn, animationOut);
        SplashScreenActivity.this.finish();
    }



}





