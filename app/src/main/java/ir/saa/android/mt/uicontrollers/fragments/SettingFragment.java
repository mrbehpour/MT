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
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.SharePrefEnum;
import ir.saa.android.mt.viewmodels.ModuleViewModel;

public class SettingFragment extends Fragment {


    EditText edtServerAddress;
    Button btnSave;
    ModuleViewModel moduleViewModel;
    Spinner spinner;
    SwitchCompat swBluetooth;
    ArrayAdapter<String> adapter;
    HashMap<String, Integer> spinnerMap = new HashMap<String, Integer>();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        edtServerAddress=rootView.findViewById(R.id.edtServerAddress);
        btnSave=rootView.findViewById(R.id.btnSave);
//-Address
        edtServerAddress.setText(G.getPref("ServerAddress"));


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G.setPref("ServerAddress",edtServerAddress.getText().toString());
                Toast.makeText(G.context,getResources().getText(R.string.MessageSuccess),Toast.LENGTH_SHORT).show();
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
            spinner.setSelection(spinnerMap.get(G.getPref(SharePrefEnum.ModuleBluetoothName)));

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