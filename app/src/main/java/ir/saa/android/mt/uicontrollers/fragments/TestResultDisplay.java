package ir.saa.android.mt.uicontrollers.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.saa.android.mt.R;
import ir.saa.android.mt.adapters.testresult.TestResultAdapter;

public class TestResultDisplay extends Fragment {

    public TestResultDisplay(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_test_result, container, false);
        return rootView;
    }
}
