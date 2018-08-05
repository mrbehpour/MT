package ir.saa.android.mt.uicontrollers.fragments;


import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

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
import android.widget.Toast;

import ir.saa.android.mt.R;

public class SettingFragment extends Fragment {


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
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.ll1).setOnClickListener(v->{
            ((SwitchCompat)view.findViewById(R.id.sw1)).setChecked(!(((SwitchCompat) view.findViewById(R.id.sw1)).isChecked()));
        });
        view.findViewById(R.id.ll2).setOnClickListener(v->{
            ((SwitchCompat)view.findViewById(R.id.sw2)).setChecked(!(((SwitchCompat) view.findViewById(R.id.sw2)).isChecked()));
        });
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