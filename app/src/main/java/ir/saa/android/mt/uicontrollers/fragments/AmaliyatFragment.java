package ir.saa.android.mt.uicontrollers.fragments;


import android.annotation.TargetApi;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.adapters.testresult.TestItem;
import ir.saa.android.mt.adapters.testresult.TestResultAdapter;
import ir.saa.android.mt.enums.BundleKeysEnum;
import ir.saa.android.mt.repositories.metertester.TestResult;
import ir.saa.android.mt.uicontrollers.pojos.TestContor.TestContorParams;
import ir.saa.android.mt.viewmodels.AmaliyatViewModel;

public class AmaliyatFragment extends Fragment {

    AmaliyatViewModel amaliyatViewModel = null;
    TestContorParams testContorParams;
    TestResultAdapter adapter;
    TextView tvRoundNum;
    TextView tvErrPerc;
    RecyclerView recyclerView;

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
        View rootView = inflater.inflate(R.layout.fargment_amaliyat, container, false);
        amaliyatViewModel = ViewModelProviders.of(this).get(AmaliyatViewModel.class);

        Bundle args = getArguments();
        if (args != null) {
            testContorParams = (TestContorParams)args.getSerializable(BundleKeysEnum.TestContorParams);
        }

        tvErrPerc = rootView.findViewById(R.id.tvErrPerc);
        amaliyatViewModel.testResultMutableLiveData.observe(this, new Observer<String>() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onChanged(@Nullable String testResult) {
                        tvErrPerc.setText(testResult);
                    }
                }
        );

        tvRoundNum = rootView.findViewById(R.id.tvTestRoundNum);
        amaliyatViewModel.testRoundNumMutableLiveData.observe(this, new Observer<Integer>() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onChanged(@Nullable Integer testRoundNum) {
                        tvRoundNum.setText( String.valueOf(testRoundNum));
                    }
                }
        );

        amaliyatViewModel.testResultListMutableLiveData.observe(this, new Observer<List<TestResult>>() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onChanged(@Nullable List<TestResult> testResultList) {
                        setUpRecyclerView(rootView,testResultList);
                    }
                }
        );

        Button btnStartTest = rootView.findViewById(R.id.btnStart);
        btnStartTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<TestResult> testResults=new ArrayList<>();
                setUpRecyclerView(rootView,testResults);

                amaliyatViewModel.setTestContorParams(testContorParams);
                amaliyatViewModel.startTest();
            }
        });

        Button btnTaeed = rootView.findViewById(R.id.btnTaeed);
        btnTaeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amaliyatViewModel.finishTest();
            }
        });

        return rootView;
    }

    private void setUpRecyclerView(View view,List<TestResult> testResultList) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rvKhata);

        adapter = new TestResultAdapter(getActivity(), testResultList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter.clearDataSet();
        adapter.addAll(testResultList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onPause() {
        super.onPause();
        amaliyatViewModel.finishTest();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}