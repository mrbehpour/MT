package ir.saa.android.mt.uicontrollers.fragments;


import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.SharePrefEnum;
import ir.saa.android.mt.viewmodels.ModuleViewModel;

import static android.content.Context.WINDOW_SERVICE;

public class SettingFragment extends Fragment {


    EditText edtServerAddress;
    Button btnSave;
    ModuleViewModel moduleViewModel;
    Spinner spinner;
    SwitchCompat swBluetooth;
    ArrayAdapter<String> adapter;
    RadioButton rbtSmall;
    RadioButton rbtNormal;
    RadioButton rbtLarge;
    RadioButton rbtHuge;

    HashMap<String, Integer> spinnerMap = new HashMap<String, Integer>();


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    public  void adjustFontScale(Configuration configuration, Float fontSize) {

        configuration.fontScale = (float) fontSize;
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) G.context.getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
       getResources().updateConfiguration(configuration, metrics);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        edtServerAddress=rootView.findViewById(R.id.edtServerAddress);
        btnSave=rootView.findViewById(R.id.btnSave);
        //-Fontsize
        rbtSmall=rootView.findViewById(R.id.rbtSmall);
        rbtNormal=rootView.findViewById(R.id.rbtNormal);
        rbtLarge=rootView.findViewById(R.id.rbtLarge);
        rbtHuge=rootView.findViewById(R.id.rbtHuge);
    if(G.getPref(SharePrefEnum.FontSize)!=null) {
    switch (G.getPref(SharePrefEnum.FontSize)) {
        case "0.85":
            rbtSmall.setChecked(true);
            break;
        case "1":
            rbtNormal.setChecked(true);
            break;
        case "1.15":
            rbtLarge.setChecked(true);
            break;
        case "1.3":
            rbtHuge.setChecked(true);
            break;
    }
}

        rbtSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G.setPref(SharePrefEnum.FontSize,"0.85");
            }
        });

        rbtNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G.setPref(SharePrefEnum.FontSize,"1");
            }
        });
        rbtLarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G.setPref(SharePrefEnum.FontSize,"1.15");
            }
        });

        rbtHuge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G.setPref(SharePrefEnum.FontSize,"1.3");
            }
        });
        if(G.getPref(SharePrefEnum.FontSize)!=null) {
            adjustFontScale(getResources().getConfiguration(), Float.parseFloat(G.getPref(SharePrefEnum.FontSize)));
        }
        //-Address
        if(G.getPref(SharePrefEnum.AddressServer)!=null) {
            edtServerAddress.setText(G.getPref(SharePrefEnum.AddressServer));
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G.setPref(SharePrefEnum.AddressServer,edtServerAddress.getText().toString());
                adjustFontScale(getResources().getConfiguration(),Float.parseFloat(G.getPref(SharePrefEnum.FontSize)));

                Toast.makeText(G.context,getResources().getText(R.string.MessageSuccess),Toast.LENGTH_SHORT).show();
                G.startFragment(G.fragmentNumStack.pop(), true, null);
            }
        });
        //-BlueTooth
        moduleViewModel = ViewModelProviders.of(getActivity()).get(ModuleViewModel.class);
        swBluetooth=rootView.findViewById(R.id.swBluetooth);
        //spinnerArray=new ArrayList<>();
        spinner = rootView.findViewById(R.id.spnPaired);

        moduleViewModel.listBluetoothName.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {

                if(strings.size()!=0) {

                    fillPairedDeviceSpinner(strings);

                }
            }
        });
        moduleViewModel.bluetoothIsEnable().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                swBluetooth.setChecked(aBoolean);


            }
        });

        swBluetooth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    moduleViewModel.setBluetoothEnable(true);

                }else{
                    moduleViewModel.setBluetoothEnable(false);

                }
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

            @Override
            public void onItemSelected(AdapterView adapter, View v, int i, long lng) {
                String SelectedItem =  adapter.getItemAtPosition(i).toString();
                G.setPref(SharePrefEnum.ModuleBluetoothName,SelectedItem);
                //or this can be also right: selecteditem = level[i];
            }
        });

        return rootView;
    }
    private void fillPairedDeviceSpinner(List<String> spinnerArray){



        adapter = new ArrayAdapter<>(this.getActivity(), R.layout.al_majol_spinner_item,spinnerArray );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        for(int i=0;i<spinnerArray.size();i++){
            spinnerMap.put(spinnerArray.get(i).toString(),i);
        }
        if( G.getPref(SharePrefEnum.ModuleBluetoothName)!=null && spinnerMap.size()!=0) {
            if(spinnerMap.get(G.getPref(SharePrefEnum.ModuleBluetoothName))!=null) {
                spinner.setSelection(spinnerMap.get(G.getPref(SharePrefEnum.ModuleBluetoothName)));
            }

        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        view.findViewById(R.id.ll1).setOnClickListener(v->{
//            ((SwitchCompat)view.findViewById(R.id.sw1)).setChecked(!(((SwitchCompat) view.findViewById(R.id.sw1)).isChecked()));
//        });
//        view.findViewById(R.id.ll2).setOnClickListener(v->{
//            ((SwitchCompat)view.findViewById(R.id.sw2)).setChecked(!(((SwitchCompat) view.findViewById(R.id.sw2)).isChecked()));
//        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}