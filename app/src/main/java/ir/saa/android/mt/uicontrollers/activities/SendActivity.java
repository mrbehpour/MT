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
import pl.droidsonroids.gif.GifTextView;

public class SendActivity extends AppCompatActivity {

    AppCompatButton btnSend;

    SendViewModel sendViewModel;
    GifTextView  imgSending;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        sendViewModel= ViewModelProviders.of(this).get(SendViewModel.class);


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
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
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
