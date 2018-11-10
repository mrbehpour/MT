package ir.saa.android.mt.uicontrollers.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.SharePrefEnum;
import ir.saa.android.mt.viewmodels.ModuleViewModel;

public class ModuleFragment extends Fragment
{
    ModuleViewModel moduleViewModel;
    Spinner spinner;
    SwitchCompat swBluetooth;
    List<String> spinnerArray;
    public ModuleFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_module, container, false);

        moduleViewModel = ViewModelProviders.of(getActivity()).get(ModuleViewModel.class);
        swBluetooth=rootView.findViewById(R.id.swBluetooth);
        spinnerArray=new ArrayList<>();
        spinner = rootView.findViewById(R.id.spnPaired);
        moduleViewModel.listBluetoothName.observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                spinnerArray=strings;
                if(strings.size()!=0) {
                    fillPairedDeviceSpinner();
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

    private void fillPairedDeviceSpinner(){


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), R.layout.al_majol_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
