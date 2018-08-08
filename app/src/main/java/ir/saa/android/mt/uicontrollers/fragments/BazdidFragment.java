package ir.saa.android.mt.uicontrollers.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ir.saa.android.mt.R;
import ir.saa.android.mt.adapters.bazdid.BazdidAdapter;
import ir.saa.android.mt.adapters.bazdid.ClientItem;
import ir.saa.android.mt.viewmodels.BazdidViewModel;

public class BazdidFragment extends Fragment
{
//    BaseInfoViewModel baseInfoViewModel;
    BazdidViewModel bazdidViewModel;
    //List<ClientItem> datasourceList;
    BazdidAdapter adapter;

    public BazdidFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        baseInfoViewModel = ViewModelProviders.of(getActivity()).get(BaseInfoViewModel.class);
        bazdidViewModel = ViewModelProviders.of(getActivity()).get(BazdidViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bazdid, container, false);
        SearchView svBazdidMoshtarakin = rootView.findViewById(R.id.svBazdidMoshtarakin);
//        GetClientInput getClientInput = new GetClientInput();
//        getClientInput.handHeldSerial = "3103103103";
//        getClientInput.agentId = 1079;
//        getClientInput.regionId = 50;
//        baseInfoViewModel.getClientFromServer(getClientInput);
        setUpRecyclerView(rootView);
        svBazdidMoshtarakin.setSubmitButtonEnabled(true);
        svBazdidMoshtarakin.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return false;
            }
        });


        return rootView;
    }

    private void setUpRecyclerView(View view) {

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvBazdidMoshtarakin);
        //datasourceList = new ArrayList<>();
//        if(bazdidViewModel.ClientItemsLiveData.getValue()!=null){
//            if(datasourceList.size()==0)
//                datasourceList.addAll(bazdidViewModel.ClientItemsLiveData.getValue());
//        }
        adapter = new BazdidAdapter(getActivity(), bazdidViewModel.ClientItemsLiveData.getValue()==null?new ArrayList<>():bazdidViewModel.ClientItemsLiveData.getValue());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bazdidViewModel.ClientItemsLiveData.observe(this, clientItems -> {
            adapter.clearDataSet();
            adapter.addAll(clientItems);
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
