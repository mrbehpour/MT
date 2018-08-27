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

import org.w3c.dom.Text;

import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.adapters.bazdid.BazdidAdapter;
import ir.saa.android.mt.adapters.bazdid.ClientItem;
import ir.saa.android.mt.adapters.testresult.TestItem;
import ir.saa.android.mt.adapters.testresult.TestResultAdapter;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.model.entities.Client;
import ir.saa.android.mt.repositories.metertester.EnergiesState;
import ir.saa.android.mt.viewmodels.AmaliyatViewModel;
import ir.saa.android.mt.viewmodels.TestEnergyViewModel;

public class AmaliyatFragment extends Fragment {

    AmaliyatViewModel amaliyatViewModel = null;
    TestResultAdapter adapter;
    List<TestItem> testItems;
    TextView tvRoundNum;
    TextView tvErrPerc;

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

        Button btnStartTest = rootView.findViewById(R.id.btnStart);
        btnStartTest.setOnClickListener(new View.OnClickListener() {
        @Override
            public void onClick(View view) {
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

    private void setUpRecyclerView(View view) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvKhata);
        adapter = new TestResultAdapter(getActivity(), testItems);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        testItems.clear();
//        for(TestItem ti:testItems){
//            clientItems.add(new ClientItem(client.ClientID,client.Name,client.Address,"اشتراک : ",client.CustId, R.drawable.account));
//        }

        adapter.clearDataSet();
        adapter.addAll(testItems);
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