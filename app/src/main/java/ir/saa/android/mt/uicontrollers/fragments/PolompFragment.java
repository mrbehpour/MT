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
import ir.saa.android.mt.adapters.polomp.PolompAdapter;
import ir.saa.android.mt.adapters.polomp.PolompItem;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.BundleKeysEnum;
import ir.saa.android.mt.viewmodels.PolompViewModel;

public class PolompFragment extends Fragment {

    PolompAdapter polompAdapter;
    PolompViewModel polompViewModel;
    private Long clientId=null;

    public PolompFragment(){
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        polompViewModel= ViewModelProviders.of(getActivity()).get(PolompViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_polomp, container, false);
        if(clientId==null){
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                clientId = bundle.getLong(BundleKeysEnum.ClientID);
            }else {
                clientId = G.clientInfo.ClientId;
            }

        }
        setUpRecyclerView(rootView);

        return rootView;
    }
    private void setUpRecyclerView(View view) {

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvPolomp);
        recyclerView.setScrollbarFadingEnabled(false);
        recyclerView.setScrollBarSize(50);
        polompAdapter = new PolompAdapter(getActivity(),polompViewModel.getPolompList(clientId).getValue()==null?new ArrayList<>()
                :polompViewModel.getPolompList(clientId).getValue());
        recyclerView.setAdapter(polompAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        polompViewModel.getPolompList(clientId).observe(this, new Observer<List<PolompItem>>() {
            @Override
            public void onChanged(@Nullable List<PolompItem> polompItems) {
                polompAdapter.clearDataSet();
                polompAdapter.addAll(polompItems);
                polompAdapter.notifyDataSetChanged();
            }
        });
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
