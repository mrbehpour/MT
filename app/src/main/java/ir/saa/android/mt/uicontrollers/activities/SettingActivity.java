package ir.saa.android.mt.uicontrollers.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;


public class SettingActivity extends AppCompatActivity {

    EditText edtServerAddress;
    Button btnSave;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_settings);
        edtServerAddress=(EditText)findViewById(R.id.edtServerAddress);
        btnSave=(Button)findViewById(R.id.btnSave);

        edtServerAddress.setText(G.getPref("ServerAddress"));


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G.setPref("ServerAddress",edtServerAddress.getText().toString());
                Toast.makeText(G.context,getResources().getText(R.string.MessageSuccess),Toast.LENGTH_SHORT).show();
            }
        });
//        findViewById(R.id.ll1).setOnClickListener(v->{
////            ((SwitchCompat)findViewById(R.id.sw1)).setChecked(!(((SwitchCompat) findViewById(R.id.sw1)).isChecked()));
//        });
////        findViewById(R.id.ll2).setOnClickListener(v->{
////            ((SwitchCompat)findViewById(R.id.sw2)).setChecked(!(((SwitchCompat) findViewById(R.id.sw2)).isChecked()));
//        });

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