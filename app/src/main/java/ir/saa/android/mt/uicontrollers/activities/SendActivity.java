package ir.saa.android.mt.uicontrollers.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Toast;

import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;

import ir.saa.android.mt.R;
import ir.saa.android.mt.viewmodels.SendViewModel;

public class SendActivity extends AppCompatActivity {

    AppCompatButton btnSend;
    AnimatedCircleLoadingView progerssSend;
    SendViewModel sendViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        sendViewModel= ViewModelProviders.of(this).get(SendViewModel.class);
        progerssSend=(AnimatedCircleLoadingView)findViewById(R.id.circle_loading_view);

        btnSend=(AppCompatButton)findViewById(R.id.btnStartUploading);
        btnSend.setEnabled(true);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                progerssSend.startDeterminate();
                btnSend.setEnabled(false);

                sendViewModel.sendData();
            }
        });

        sendViewModel.messageErrorLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            }
        });
        sendViewModel.sendAllDataProgress.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {

                progerssSend.setPercent(integer);
                if(integer==-1){
                    progerssSend.resetLoading();
                    progerssSend.stopFailure();
                }
                else if(integer==100){
                    progerssSend.stopOk();

                }




            }
        });
    }
}
