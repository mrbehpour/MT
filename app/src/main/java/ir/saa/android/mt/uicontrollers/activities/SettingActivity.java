package ir.saa.android.mt.uicontrollers.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;

import ir.saa.android.mt.R;

public class SettingActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings);

        findViewById(R.id.ll1).setOnClickListener(v->{
            ((SwitchCompat)findViewById(R.id.sw1)).setChecked(!(((SwitchCompat) findViewById(R.id.sw1)).isChecked()));
        });
        findViewById(R.id.ll2).setOnClickListener(v->{
            ((SwitchCompat)findViewById(R.id.sw2)).setChecked(!(((SwitchCompat) findViewById(R.id.sw2)).isChecked()));
        });

//        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.JELLY_BEAN_MR1)
//            getListView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
//        getListView().setTextDirection(View.TEXT_DIRECTION_ANY_RTL);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            getListView().setForegroundGravity(Gravity.RIGHT);
//        }

//        getFragmentManager().beginTransaction()
//                .replace(android.R.id.content, new SettingFragment())
//                .commit();
    }


}