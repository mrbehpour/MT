package ir.saa.android.mt.uicontrollers.fragments;


import android.app.ProgressDialog;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;


import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;
import ir.saa.android.mt.R;
import ir.saa.android.mt.adapters.bazdid.BazdidAdapter;
import ir.saa.android.mt.adapters.bazdid.ClientItem;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.model.entities.Client;
import ir.saa.android.mt.model.entities.ClientWithAction;
import ir.saa.android.mt.viewmodels.BazdidViewModel;

public class BazdidFragment extends Fragment
{
    BazdidViewModel bazdidViewModel;
    BazdidAdapter adapter;
    List<ClientItem> clientItems;
    Boolean isTest=false;
    Boolean isPolomp=false;
    Boolean isBazrasi=false;
    MutableLiveData<List<Client>> clientsLiveData;

    private Handler handler = new Handler();

    public BazdidFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bazdidViewModel = ViewModelProviders.of(getActivity()).get(BazdidViewModel.class);
        clientItems = new ArrayList<>();
        if(clientsLiveData==null){
            clientsLiveData=new MutableLiveData<>();
        }

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
       bazdidViewModel.getClientsWithRegionIdLiveData(Integer.valueOf(G.getPref("RegionID"))).observeForever(new Observer<List<ClientWithAction>>() {
            @Override
                public void onChanged(@Nullable List<ClientWithAction> clients) {

                clientItems.clear();
                Integer RowId=0;
                for(ClientWithAction client:clients){
                    isBazrasi=client.isBazrasi!=0?true:false;
                    isPolomp=client.isPolomp!=0?true:false;
                    isTest=client.isTest!=0?true:false;
                    RowId+=1;
                    clientItems.add(new ClientItem(client.ClientID,client.Name,client.Address, (String) getResources().getText(R.string.UniqField),client.CustId, R.drawable.account,client.SendId
                            ,client.MasterGroupDtlID,isTest,isPolomp,isBazrasi,client.FollowUpCode,RowId ));
                }

                adapter.clearDataSet();
                adapter.addAll(clientItems);
                adapter.notifyDataSetChanged();

                if(Postion!=null) {
                    recyclerView.scrollToPosition(Postion);
                }
                }
       }
                );
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
   }

}
