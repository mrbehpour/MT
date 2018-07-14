package ir.saa.android.mt;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;


public class Main2Activity extends AppCompatActivity {
    private VideoView vvLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.al_login);


    //Login
    // you need to have a list of data that you want the spinner to display
    List<String> spinnerArray =  new ArrayList<>();
    spinnerArray.add("علی علوی");
    spinnerArray.add("مجید مجیدی");
    spinnerArray.add("علی کاشانی راد");
    spinnerArray.add("میثم محمدی");
    spinnerArray.add("محمد مهدی صادقی عارف");
    spinnerArray.add("علیرضا خسروی");
    spinnerArray.add("مهران مدیری");
    spinnerArray.add("علی صادقی");
    spinnerArray.add("مجید محمدی نیا");

    ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this, R.layout.al_spinner_item, spinnerArray);
    adapter.setDropDownViewResource(R.layout.al_spinner_dropdown_item);
    Spinner sItems = findViewById(R.id.spnUserName);
    sItems.setAdapter(adapter);

    //set video background
    vvLogin = (VideoView) findViewById(R.id.vvLogin);
    Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.login);

    vvLogin.setVideoURI(uri);
    vvLogin.start();

    vvLogin.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            mediaPlayer.setLooping(true);
        }
    });
    }


//        Bazdid




    }


