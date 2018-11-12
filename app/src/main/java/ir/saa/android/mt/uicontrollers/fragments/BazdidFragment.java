package ir.saa.android.mt.uicontrollers.fragments;

import android.arch.lifecycle.Observer;
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
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.model.entities.Client;
import ir.saa.android.mt.viewmodels.BazdidViewModel;

public class BazdidFragment extends Fragment
{
    BazdidViewModel bazdidViewModel;
    BazdidAdapter adapter;
    List<ClientItem> clientItems;
    Boolean isTest=false;
    Boolean isPolomp=false;
    Boolean isBazrasi=false;

    public BazdidFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bazdidViewModel = ViewModelProviders.of(getActivity()).get(BazdidViewModel.class);
        clientItems = new ArrayList<>();
    }

    @Override
    public void onResume() {



        super.onResume();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bazdid, container, false);
        SearchView svBazdidMoshtarakin = rootView.findViewById(R.id.svBazdidMoshtarakin);

        setUpRecyclerView(rootView,G.clientInfo.Postion);
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


    public void setUpRecyclerView(View view,Integer Postion) {
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvBazdidMoshtarakin);
        recyclerView.setScrollbarFadingEnabled(false);
        recyclerView.setScrollBarSize(50);
        adapter = new BazdidAdapter(getActivity(), clientItems);
        bazdidViewModel.getClientsWithRegionIdLiveData(Integer.valueOf(G.getPref("RegionID"))).observe(this, new Observer<List<Client>>() {
            @Override
            public void onChanged(@Nullable List<Client> clients) {
                clientItems.clear();
                for(Client client:clients){
                    isBazrasi=bazdidViewModel.getInspectionValue(client.ClientID);
                    isPolomp=bazdidViewModel.getPolompValue(client.ClientID);
                    isTest=bazdidViewModel.getTestValue(client.ClientID);
                    clientItems.add(new ClientItem(client.ClientID,client.Name,client.Address, (String) getResources().getText(R.string.UniqField),client.CustId, R.drawable.account,client.SendId
                            ,client.MasterGroupDtlID,isTest,isPolomp,isBazrasi,client.FollowUpCode ));
                }

                adapter.clearDataSet();
                adapter.addAll(clientItems);
                adapter.notifyDataSetChanged();
                if(Postion!=null) {
                    recyclerView.scrollToPosition(Postion);
                }
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));







    }

}
