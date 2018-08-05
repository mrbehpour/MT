package ir.saa.android.mt.uicontrollers.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.saa.android.mt.R;
import ir.saa.android.mt.repositories.roomrepos.ReluserRepo;
import ir.saa.android.mt.viewmodels.BaseInfoViewModel;
import ir.saa.android.mt.viewmodels.LoginViewModel;

public class BaseInfoFragment extends Fragment
{
    BaseInfoViewModel baseInfoViewModel = null;
    public BaseInfoFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bazdid, container, false);

        baseInfoViewModel = ViewModelProviders.of(this).get(BaseInfoViewModel.class);
        baseInfoViewModel.getUserFromServer();

        baseInfoViewModel.UsersProgressPercent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {

            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
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
