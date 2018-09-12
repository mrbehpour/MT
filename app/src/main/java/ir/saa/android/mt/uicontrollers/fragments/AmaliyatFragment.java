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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.adapters.testresult.TestItem;
import ir.saa.android.mt.adapters.testresult.TestResultAdapter;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.BundleKeysEnum;
import ir.saa.android.mt.repositories.metertester.MT;
import ir.saa.android.mt.repositories.metertester.TestResult;
import ir.saa.android.mt.uicontrollers.pojos.TestContor.TestContorParams;
import ir.saa.android.mt.viewmodels.AmaliyatViewModel;

public class AmaliyatFragment extends Fragment {

    AmaliyatViewModel amaliyatViewModel = null;
    TestContorParams testContorParams;
    List<TestResult> lastTestResultList;
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

        Button btnStartTest = rootView.findViewById(R.id.btnStartTest);
        Button btnFinishTest = rootView.findViewById(R.id.btnFinishTest);
        Button btnSaveResult = rootView.findViewById(R.id.btnSaveTestResult);

        Bundle args = getArguments();
        if (args != null) {
            testContorParams = (TestContorParams)args.getSerializable(BundleKeysEnum.TestContorParams);
        }

        tvErrPerc = rootView.findViewById(R.id.tvErrPerc);
        amaliyatViewModel.checkTestStatus();

        amaliyatViewModel.testResultMutableLiveData.observe(this, new Observer<String>() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onChanged(@Nullable String testResult) {
                        tvErrPerc.setText(testResult);
                    }
                }
        );

        amaliyatViewModel.testStatusMutableLiveData.observe(this, new Observer<MT.TestCommands>() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onChanged(@Nullable MT.TestCommands testCommands) {
                        switch(testCommands){
                            case StartTest:
                                btnStartTest.setVisibility(View.GONE);
                                btnFinishTest.setVisibility(View.VISIBLE);
                                btnSaveResult.setVisibility(View.GONE);
                                tvErrPerc.setText("");
                                tvRoundNum.setText("");
                                break;

                            case FinishTest:
                                btnStartTest.setVisibility(View.VISIBLE);
                                btnFinishTest.setVisibility(View.GONE);
                                btnSaveResult.setVisibility(View.VISIBLE);
                                break;

                            case DisableTest:
                                btnStartTest.setVisibility(View.VISIBLE);
                                btnFinishTest.setVisibility(View.GONE);
                                btnSaveResult.setVisibility(View.GONE);
                                break;
                        }
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
                        if(testResultList!=null && testResultList.size()>0) {
                            setUpRecyclerView(rootView, testResultList);
                            lastTestResultList=testResultList;
                            btnSaveResult.setVisibility(View.VISIBLE);
                        }
                        else{
                            btnSaveResult.setVisibility(View.GONE);
                            Toast.makeText(G.context,"هیچ نتیجه تستی وجود ندارد.",Toast.LENGTH_SHORT ).show();
                        }
                    }
                }
        );


        btnStartTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<TestResult> testResults=new ArrayList<>();
                setUpRecyclerView(rootView,testResults);

                amaliyatViewModel.setTestContorParams(testContorParams);
                amaliyatViewModel.startTest();
                //btnStartTest.setEnabled(false);
            }
        });

        btnFinishTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amaliyatViewModel.finishTest();
            }
        });

        btnSaveResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(G.context,"نتایج تست با موفقیت ذخیره شد.",Toast.LENGTH_SHORT ).show();
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