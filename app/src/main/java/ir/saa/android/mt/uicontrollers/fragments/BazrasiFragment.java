package ir.saa.android.mt.uicontrollers.fragments;

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

import ir.saa.android.mt.R;
import ir.saa.android.mt.adapters.bazrasi.BazrasiAdapter;
import ir.saa.android.mt.viewmodels.BazrasiViewModel;

public class BazrasiFragment extends Fragment {

    BazrasiViewModel bazrasiViewModel;
    BazrasiAdapter adapter;

    public BazrasiFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bazrasiViewModel = ViewModelProviders.of(getActivity()).get(BazrasiViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bazrasi, container, false);

        return rootView;
    }

    private void setUpRecyclerView(View view) {

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvSoal);

        adapter = new BazrasiAdapter(getActivity(), bazrasiViewModel.RemarkItemLiveData.getValue()==null?new ArrayList<>(): bazrasiViewModel.RemarkItemLiveData.getValue());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bazrasiViewModel.RemarkItemLiveData.observe(this, remarkItems -> {
            adapter.clearDataSet();
            adapter.addAll(remarkItems);
            adapter.notifyDataSetChanged();
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
