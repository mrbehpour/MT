package ir.saa.android.mt.uicontrollers.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.adapters.testdisplay.TestDisplayAdapter;
import ir.saa.android.mt.adapters.testdisplay.TestResultItemDisplay;
import ir.saa.android.mt.adapters.testresult.TestResultAdapter;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.viewmodels.AmaliyatViewModel;

public class TestResultDisplay extends Fragment {
    AmaliyatViewModel amaliyatViewModel;
    TestDisplayAdapter testDisplayAdapter;

    public TestResultDisplay(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        amaliyatViewModel= ViewModelProviders.of(this).get(AmaliyatViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_test_result, container, false);
        setUpRecyclerView(rootView);
        return rootView;
    }

    private void setUpRecyclerView(View view) {
        RecyclerView recyclerView=(RecyclerView)view.findViewById(R.id.rvTestResult);

        testDisplayAdapter =new TestDisplayAdapter(getActivity(),amaliyatViewModel.getTestAllInfoWithClientIDLiveData(G.clientInfo.ClientId,G.clientInfo.SendId).getValue()==null?new ArrayList<>(): amaliyatViewModel.getTestAllInfoWithClientIDLiveData(G.clientInfo.ClientId,G.clientInfo.SendId).getValue());
        recyclerView.setAdapter(testDisplayAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        amaliyatViewModel.getTestAllInfoWithClientIDLiveData(G.clientInfo.ClientId,G.clientInfo.SendId).observe(this, new Observer<List<TestResultItemDisplay>>() {
            @Override
            public void onChanged(@Nullable List<TestResultItemDisplay> testResultItemDisplays) {
                testDisplayAdapter.clearDataSet();
                testDisplayAdapter.addAll(testResultItemDisplays);
                testDisplayAdapter.notifyDataSetChanged();
            }
        });

    }
}
