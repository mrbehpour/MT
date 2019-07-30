package ir.saa.android.mt.uicontrollers.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;


import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


import com.google.gson.Gson;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ir.saa.android.mt.R;
import ir.saa.android.mt.adapters.bazdid.BazdidAdapter;
import ir.saa.android.mt.adapters.bazdid.ClientItem;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.components.MyCheckList;
import ir.saa.android.mt.components.MyCheckListItem;
import ir.saa.android.mt.components.MyDialog;
import ir.saa.android.mt.enums.SharePrefEnum;
import ir.saa.android.mt.model.entities.AddedClientInput;
import ir.saa.android.mt.model.entities.Client;
import ir.saa.android.mt.model.entities.ClientWithAction;
import ir.saa.android.mt.viewmodels.AddedClientViewModel;
import ir.saa.android.mt.viewmodels.BazdidViewModel;

public class BazdidFragment extends Fragment
{
    BazdidViewModel bazdidViewModel;
    BazdidAdapter adapter;
    List<ClientItem> clientItems;
    Boolean isTest=false;
    Boolean isPolomp=false;
    Boolean isBazrasi=false;
    Boolean isTariff=false;
    FloatingActionButton floatingBtnAdd;
    MutableLiveData<List<Client>> clientsLiveData;
    MyDialog myDialog;
    EditText etEshterak;
    EditText etFileId;
    EditText etRamz;
    EditText etSerialContor;
    EditText etShenasai;
    AddedClientViewModel addedClientViewModel;
    MyCheckList myCheckList;
    ProgressDialog progressDialog;
    MyDialog myDialogForClient;
    private Handler handler = new Handler();

    public BazdidFragment() {
    }

    private void connectToModuleDialog(){

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage(getResources().getText(R.string.PleaseWait_msg));
        progressDialog.setTitle(getResources().getText(R.string.GetClientInProgress_msg));
        progressDialog.setCancelable(true);
        progressDialog.show();

    }

    public void HideProgressDialog(){
        if(progressDialog!=null) progressDialog.dismiss();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addedClientViewModel=ViewModelProviders.of(getActivity()).get(AddedClientViewModel.class);
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

        floatingBtnAdd=(FloatingActionButton)rootView.findViewById(R.id.floatingBtnAdd);


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



        floatingBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialogForClient=new MyDialog(getContext());
                myDialog=new MyDialog(getContext())
                        .setTitle((String) getResources().getText(R.string.AddClientTitle))
                        .addContentXmlScorllView(R.layout.body_addclient)
                        .addButton((String) getResources().getText(R.string.Cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                myDialog.dismiss();
                            }
                        }).addButton((String) getResources().getText(R.string.Ok), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AddedClientInput addedClientInput = new AddedClientInput();
                                etEshterak = (EditText) myDialog.getDialog().findViewById(R.id.etEshterak);
                                etFileId = (EditText) myDialog.getDialog().findViewById(R.id.etFileId);
                                etRamz = (EditText) myDialog.getDialog().findViewById(R.id.etRamz);
                                etSerialContor = (EditText) myDialog.getDialog().findViewById(R.id.etSerialContor);
                                etShenasai = (EditText) myDialog.getDialog().findViewById(R.id.etShenasai);
                                if (G.isNetWorkConnection()) {
                                    addedClientInput.clientPass = etRamz.getText().toString().equals("") ? null : Long.parseLong(etRamz.getText().toString());
                                    addedClientInput.custId = etShenasai.getText().toString();
                                    addedClientInput.fileId = etFileId.getText().toString().equals("") ? null :
                                            Long.valueOf(etFileId.getText().toString());
                                    addedClientInput.subScript = etEshterak.getText().toString().equals("") ? null :
                                            Long.parseLong(etEshterak.getText().toString());
                                    addedClientInput.meterNumActive = etSerialContor.getText().toString().equals("") ? null :
                                            Long.parseLong(etSerialContor.getText().toString());
                                    Client client = bazdidViewModel.getClientByClientId(addedClientInput.subScript);
                                    if (client == null){
//                                    Gson gson = new Gson();
//                                    String ddf = gson.toJson(addedClientInput);
                                        addedClientViewModel.getClientFromServer(addedClientInput);
                                    connectToModuleDialog();
                                    addedClientViewModel.ClientListMultiLiveData.observe((LifecycleOwner) getContext(), new Observer<List<Client>>() {
                                        @Override
                                        public void onChanged(@Nullable List<Client> clients) {
                                            myCheckList = new MyCheckList(G.context, new MyCheckListItem("", ""),
                                                    new MyCheckListItem("", ""));

                                            if (clients != null) {
                                                HideProgressDialog();
                                                myDialogForClient.clearAllPanel();
                                                myCheckList.removeAllCkeckItems();
                                                for (Client client : clients) {
                                                    String Caption = String.format("نام : %s", client.Name) + "\n" +
                                                            String.format("آدرس : %s", client.Address) + "\n" +
                                                            String.format("بدنه کنتور : %s", client.MeterNumActive);
                                                    myCheckList.addCheckItem(new MyCheckListItem(Caption, client.ClientID));
                                                }
                                                myDialog.dismiss();


                                                myDialogForClient.setTitle((String) getResources().getText(R.string.ClientTitle));
                                                myDialogForClient.addContentView(myCheckList)
                                                        .addButton((String) getResources().getText(R.string.Cancel),
                                                                new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View view) {
                                                                        myDialogForClient.dismiss();
                                                                    }
                                                                })
                                                        .addButton((String) getResources().getText(R.string.Ok), new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {
                                                                if (myCheckList.getSelectedItemsValues().size() != 0) {

                                                                    Object o = myCheckList.getSelectedItemsValues().get(0);
                                                                    for (Client client : clients) {
                                                                        if (client.ClientID == Long.valueOf(o.toString())) {
                                                                            bazdidViewModel.insertClient(client);
                                                                            adapter.notifyDataSetChanged();
                                                                        }
                                                                    }
                                                                    //Fill up myList with your Data Points


                                                                }
                                                            }
                                                        }).show();


                                            } else {
                                                HideProgressDialog();
                                                Toast fancyToast = FancyToast.makeText(G.context, (String) getResources().getText(R.string.NoClientFind_msg), FancyToast.LENGTH_SHORT, FancyToast.INFO, false);
                                                fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                                fancyToast.show();
                                            }
                                        }
                                    });
                                }else{
                                        Toast fancyToast = FancyToast.makeText(G.context, (String) getResources().getText(R.string.ExistJoint_msg), FancyToast.LENGTH_SHORT, FancyToast.INFO, false);
                                        fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                        fancyToast.show();
                                        myDialog.dismiss();
                                    }
                                }else{
                                    Client client=new Client();
                                    client.ClientPass = etRamz.getText().toString().equals("") ? null : Long.parseLong(etRamz.getText().toString());
                                    client.CustId = etShenasai.getText().toString();
                                    client.FileID = etFileId.getText().toString().equals("") ? null :
                                            Long.valueOf(etFileId.getText().toString());
                                    client.SubScript = etEshterak.getText().toString().equals("") ? null :
                                            Long.parseLong(etEshterak.getText().toString());
                                    client.MeterNumActive = etSerialContor.getText().toString().equals("") ? null :
                                            Long.parseLong(etSerialContor.getText().toString());
                                   client.Name= (String) getResources().getText(R.string.AddClientTitle);
                                   client.ClientID=etEshterak.getText().toString().equals("") ? null :
                                           Long.parseLong(etEshterak.getText().toString());
                                   client.RegionID=Integer.valueOf(G.getPref(SharePrefEnum.RegionId));

                                   Client client1=bazdidViewModel.getClientByClientId(client.ClientID);

                                   if(client1==null) {
                                       bazdidViewModel.insertClient(client);
                                       Toast fancyToast = FancyToast.makeText(G.context, (String) getResources().getText(R.string.SaveOperationSuccess_msg), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false);
                                       fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                       fancyToast.show();
                                       myDialog.dismiss();
                                   }
                                }
                            }
                        })
                        .show();
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
                             Activity activity = getActivity();
                             activity = getActivity();
                             clientItems.clear();
                             Integer RowId=0;
                             String UniqField="";
                             if(activity != null && isAdded()) {
                                 UniqField= (String) getResources().getText(R.string.UniqeField);
                             }

                             for(ClientWithAction client:clients){

                                       isBazrasi=client.isBazrasi!=0?true:false;
                                       isPolomp=client.isPolomp!=0?true:false;
                                       isTest=client.isTest!=0?true:false;
                                       isTariff=client.isTariff!=0?true:false;
                                       RowId+=1;
                                       clientItems.add(new ClientItem(client.ClientID,client.Name,client.Address,UniqField ,String.valueOf(client.SubScript),
                                               R.drawable.account,client.SendId,client.MasterGroupDtlID,isTest,isPolomp,isBazrasi,isTariff,
                                               client.FollowUpCode,RowId,client.forcibleMasterGroup ));
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
