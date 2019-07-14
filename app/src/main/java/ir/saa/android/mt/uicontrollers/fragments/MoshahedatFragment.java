package ir.saa.android.mt.uicontrollers.fragments;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.adapters.bazrasi.BazrasiAdapter;
import ir.saa.android.mt.adapters.moshahedat.MoshahedatAdapter;
import ir.saa.android.mt.adapters.moshahedat.MoshahedatItem;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.components.MyCheckList;
import ir.saa.android.mt.components.MyCheckListItem;
import ir.saa.android.mt.components.MyCheckListMode;
import ir.saa.android.mt.components.MyDialog;
import ir.saa.android.mt.model.entities.MasterGroupDetail;
import ir.saa.android.mt.services.GPSTracker;
import ir.saa.android.mt.viewmodels.BazrasiViewModel;
import ir.saa.android.mt.viewmodels.LocationViewModel;

public class MoshahedatFragment extends Fragment {
    MyCheckList myCheckListForCore;
    BazrasiViewModel bazrasiViewModel;
    MoshahedatAdapter adapter;
    CheckBox cbSelectAll;
    LocationViewModel locationViewModel=null;
    ArrayList<Object> objects = new ArrayList<>();
    Location location;
    Context context;
    ProgressDialog progressDialog;
    GPSTracker gpsTracker;
    RecyclerView recyclerView;

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_moshahedat, container, false);

        this.context=this.getContext();
        bazrasiViewModel = ViewModelProviders.of(this).get(BazrasiViewModel.class);
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvList);


        setUpRecyclerView(rootView);
        if(gpsTracker==null){
            gpsTracker=GPSTracker.getInstance(this.getContext());
        }


        return rootView;
    }

    public void setUpRecyclerView(View view) {


        recyclerView.setScrollbarFadingEnabled(false);
        recyclerView.setScrollBarSize(50);
        adapter = new MoshahedatAdapter(getActivity(), bazrasiViewModel.getRemarksForMoshahedat(G.clientInfo.GroupId).getValue()==null?new ArrayList<>(): bazrasiViewModel.getRemarksForMoshahedat(G.clientInfo.GroupId).getValue());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        bazrasiViewModel.getRemarksForMoshahedat(G.clientInfo.GroupId).observe(this, new Observer<List<MoshahedatItem>>() {
            @Override
            public void onChanged(@Nullable List<MoshahedatItem> moshahedatItems) {
                if(moshahedatItems.size()==0){
                    Toast fancyToast= FancyToast.makeText(getActivity(), (String) getResources().getText(R.string.RemarkDataNotFound_msg),FancyToast.LENGTH_SHORT,FancyToast.WARNING,false);
                    fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    fancyToast.show();
                    return;

                }
                adapter.clearDataSet();
                adapter.addAll(moshahedatItems);

                adapter.notifyDataSetChanged();
            }
        });
    }
}
