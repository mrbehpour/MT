package ir.saa.android.mt.uicontrollers.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.components.MyDialog;

import ir.saa.android.mt.enums.SharePrefEnum;
import ir.saa.android.mt.viewmodels.SendViewModel;
import pl.droidsonroids.gif.GifTextView;

public class SendActivity extends AppCompatActivity {

    AppCompatButton btnSend;

    SendViewModel sendViewModel;
    Context context;
    GifTextView  imgSending;
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
        setContentView(R.layout.activity_send);
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

        sendViewModel= ViewModelProviders.of(this).get(SendViewModel.class);
        context=this;

        btnSend=(AppCompatButton)findViewById(R.id.btnStartUploading);
        imgSending=(GifTextView)findViewById(R.id.imgSending);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                imgSending.setBackgroundResource(R.drawable.sending);
                sendViewModel.sendData();
            }
        });

        sendViewModel.messageErrorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                final MyDialog dialog=new MyDialog(context) ;
                //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
                dialog.clearAllPanel();
                dialog.clearButtonPanel();

                dialog.addButton(G.context.getResources().getString(R.string.Save), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.addBodyText(s,15).setTitle((String) context.getResources().getText(R.string.Error_msg)).show();


            }
        });
        sendViewModel.sendAllDataProgress.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {


                if(integer==-1){

                    imgSending.setBackgroundResource(R.drawable.sending_failed);
                }
                else if(integer==100){
                    imgSending.setBackgroundResource(R.drawable.sending_successful);

                }




            }
        });
    }
}
