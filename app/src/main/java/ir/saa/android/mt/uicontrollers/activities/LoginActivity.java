package ir.saa.android.mt.uicontrollers.activities;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

import ir.saa.android.mt.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

    }
}
