package ir.saa.android.mt.uicontrollers.fragments;

import android.app.ProgressDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.adapters.bazrasi.BazrasiAdapter;
import ir.saa.android.mt.adapters.bazrasi.RemarkItem;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.components.MyCheckList;
import ir.saa.android.mt.components.MyCheckListItem;
import ir.saa.android.mt.components.MyCheckListMode;
import ir.saa.android.mt.components.MyDialog;
import ir.saa.android.mt.model.entities.AnswerGroupDtl;
import ir.saa.android.mt.model.entities.InspectionWithAnswerGroup;
import ir.saa.android.mt.model.entities.MasterGroupDetail;
import ir.saa.android.mt.services.GPSTracker;
import ir.saa.android.mt.services.ILocationCallBack;
import ir.saa.android.mt.viewmodels.BazrasiViewModel;
import ir.saa.android.mt.viewmodels.LocationViewModel;

public class BazrasiFragment extends Fragment  {

    MyCheckList myCheckListForCore;
    BazrasiViewModel bazrasiViewModel;
    BazrasiAdapter adapter;
    CheckBox cbSelectAll;
    LocationViewModel locationViewModel=null;
    ArrayList<Object> objects = new ArrayList<>();
    MyCheckList myCheckList;
    Location location;
    Context context;
    ProgressDialog progressDialog;
    GPSTracker gpsTracker;
    RecyclerView recyclerView;
    public BazrasiFragment() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void connectToModuleDialog(){

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage(getResources().getText(R.string.PleaseWait_msg));
        progressDialog.setTitle(getResources().getText(R.string.GetLocationInProgress_msg));
        progressDialog.setCancelable(true);
        progressDialog.show();

    }

    public void HideProgressDialog(){
        if(progressDialog!=null) progressDialog.dismiss();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bazrasi, container, false);
        this.context=this.getContext();
        bazrasiViewModel = ViewModelProviders.of(this).get(BazrasiViewModel.class);
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvSoal);
        cbSelectAll=rootView.findViewById(R.id.cbSelectAll);
        cbSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){

                    location=locationViewModel.getLocation(getContext());
                    if(locationViewModel.isGpsEnable()){
                        if(location==null){
                            connectToModuleDialog();
                        }
                    }else{
                        location=null;
                    }

                    if (location != null) {
                        if(bazrasiViewModel.isEqualAnswerGroupDtl(G.clientInfo.GroupId)) {
                            final MyDialog dialog = new MyDialog(getContext());
                            //InspectionWithAnswerGroup inspectionAllInfo = bazrasiViewModel.getInspectionAllInfo(G.clientInfo.ClientId, 1, 1);

                            //if (current.answerGroupId != null) {
                            bazrasiViewModel.getAnswerGroupDtlsLiveData(11).observe((AppCompatActivity) getContext(), new Observer<List<AnswerGroupDtl>>() {
                                @Override
                                public void onChanged(@Nullable List<AnswerGroupDtl> answerGroupDtls) {


                                    dialog.clearAllPanel();

                                    myCheckList =
                                            new MyCheckList(G.context
                                                    , new MyCheckListItem(answerGroupDtls.get(0).AnswerGroupDtlName, answerGroupDtls.get(0).AnswerGroupDtlID),
                                                    new MyCheckListItem(answerGroupDtls.get(1).AnswerGroupDtlName, answerGroupDtls.get(1).AnswerGroupDtlID));
                                    for (Integer i = 2; i < answerGroupDtls.size(); i++) {
                                        myCheckList.addCheckItem(new MyCheckListItem(answerGroupDtls.get(i).AnswerGroupDtlName,
                                                answerGroupDtls.get(i).AnswerGroupDtlID));

                                    }
                                    dialog.clearButtonPanel();

                                    myCheckList
                                            .setCheckListOrientation(LinearLayout.VERTICAL)
                                            .setSelectionMode(MyCheckListMode.SingleSelection)
                                            .setCheckItemsHeight(80);

                                    //myCheckList.setSelectionByValue(Integer.valueOf(current.remarkValue));


                                    dialog.addButton(G.context.getResources().getString(R.string.Save), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            objects = myCheckList.getSelectedItemsValues();
                                            Object objectAnswer;
                                            if(objects.size()==0){
                                                objectAnswer=null;
                                            }else {
                                                objectAnswer=objects.get(0);
                                            }
                                            List<RemarkItem> remarkItems=bazrasiViewModel.getRemarks(G.clientInfo.GroupId).getValue();

                                            for(RemarkItem renarkItem:remarkItems){
                                                bazrasiViewModel.saveBazrasi(renarkItem,objectAnswer,location);
                                            }
                                            if(objectAnswer!=null) {
                                                //Toast.makeText(getContext(), getResources().getText(R.string.MessageSuccess), Toast.LENGTH_SHORT).show();
                                                Toast fancyToast = FancyToast.makeText(getContext(), (String) getResources().getText(R.string.SaveOperationSuccess_msg), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false);
                                                fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                                fancyToast.show();
                                            }
                                            dialog.dismiss();
                                        }
                                    });

                                    dialog.setTitle("")

                                            .addContentView(myCheckList)
                                            .setLeftImageTitle(G.context.getResources().getDrawable(R.drawable.account), new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                }
                                            })

                                            .show();

                                }
                            });
                            //}
                        }else{

                        }
                    }else{
                        cbSelectAll.setChecked(false);
                        locationViewModel.trunOnGps(getContext());



                    }

                }else{

                }
            }
        });
        locationViewModel.locationMutableLiveData.observe(this, new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location locationViewModel) {
                if(locationViewModel!=null){
                    location=locationViewModel;
                    HideProgressDialog();
                }
            }
        });
        setUpRecyclerView(rootView);
        if(gpsTracker==null){
            gpsTracker=GPSTracker.getInstance(this.getContext());
        }

        if(G.clientInfo.forcibleMasterGroup && recyclerView.getChildCount()!=0){
            bazrasiViewModel.getMasterGroupDetail(G.clientInfo.GroupId).observe(getActivity(), new Observer<MasterGroupDetail>() {
                @Override
                public void onChanged(@Nullable MasterGroupDetail masterGroupDetail) {
                    final MyDialog myDialog=new MyDialog(getActivity());
                    myDialog.clearAllPanel();
                    myDialog.clearButtonPanel();

                    myDialog.addButton((String) getResources().getText(R.string.No), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            final MyDialog dialogforTypeCore=new MyDialog(getActivity());
                            dialogforTypeCore.clearButtonPanel();
                            dialogforTypeCore.clearAllPanel();


                            bazrasiViewModel.getMasterGroupDetail().observe(getActivity(), new Observer<List<MasterGroupDetail>>() {
                                @Override
                                public void onChanged(@Nullable List<MasterGroupDetail> masterGroupDetails) {
                                    myCheckListForCore=new MyCheckList(getActivity()
                                            ,new MyCheckListItem(masterGroupDetails.get(0).ChoiceValue ,masterGroupDetails.get(0).ChoiceID)
                                            ,new MyCheckListItem( masterGroupDetails.get(1).ChoiceValue ,masterGroupDetails.get(1).ChoiceID));
                                    for(int i=2;i<masterGroupDetails.size();i++){
                                        myCheckListForCore.addCheckItem(
                                                new MyCheckListItem(masterGroupDetails.get(i).ChoiceValue
                                                ,masterGroupDetails.get(i).ChoiceID));
                                    }
                                    myCheckListForCore.setCheckListOrientation(LinearLayout.VERTICAL)
                                            .setSelectionMode(MyCheckListMode.SingleSelection)
                                            .setCheckItemsHeight(70);
                                    dialogforTypeCore.addContentView(myCheckListForCore);
                                }
                            });
                            dialogforTypeCore.show();
                            dialogforTypeCore.addButton((String) getResources().getText(R.string.Cancel), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialogforTypeCore.dismiss();
                                }
                            });
                            dialogforTypeCore.addButton((String) getResources().getText(R.string.Ok), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ArrayList<Object> objectArrayList=new ArrayList<>();
                                    objectArrayList = myCheckListForCore.getSelectedItemsValues();
                                    if(objectArrayList.size()>0) {
                                        G.clientInfo.GroupId = Integer.valueOf(objectArrayList.get(0).toString());
                                        setUpRecyclerView(rootView);
                                        adapter.notifyDataSetChanged();
                                        dialogforTypeCore.dismiss();
                                    }
                                }
                            });





                           myDialog.dismiss();
                        }
                    });

                    myDialog.addButton((String) getResources().getText(R.string.Yes), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            myDialog.dismiss();
                        }
                    });

                    myDialog.addBodyText(String.format( (String) getResources().getText(R.string.GroupName_msg)
                            ,masterGroupDetail.ChoiceValue),13).show();
                   // Toast.makeText(getActivity(),masterGroupDetail.ChoiceValue,Toast.LENGTH_SHORT).show();
                }
            });
        }

        gpsTracker.isHasLocation.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    location=gpsTracker.getLocation();
                    HideProgressDialog();
                }
            }
        });

        return rootView;
    }

    public void setUpRecyclerView(View view) {


        recyclerView.setScrollbarFadingEnabled(false);
        recyclerView.setScrollBarSize(50);
        adapter = new BazrasiAdapter(getActivity(), bazrasiViewModel.getRemarks(G.clientInfo.GroupId).getValue()==null?new ArrayList<>(): bazrasiViewModel.getRemarks(G.clientInfo.GroupId).getValue());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        bazrasiViewModel.getRemarks(G.clientInfo.GroupId).observe(this, remarkItems -> {
            if(remarkItems.size()==0){
                Toast fancyToast= FancyToast.makeText(getActivity(), (String) getResources().getText(R.string.RemarkDataNotFound_msg),FancyToast.LENGTH_SHORT,FancyToast.WARNING,false);
                fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                fancyToast.show();
                return;

            }
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
