package ir.saa.android.mt.uicontrollers.fragments;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.angads25.toggle.LabeledSwitch;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.adapters.Mane.ManeAdapter;
import ir.saa.android.mt.adapters.Mane.ManeItem;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.components.MyCheckList;
import ir.saa.android.mt.components.MyCheckListItem;
import ir.saa.android.mt.components.PersianCalendar;
import ir.saa.android.mt.enums.BundleKeysEnum;
import ir.saa.android.mt.enums.FragmentsEnum;
import ir.saa.android.mt.enums.SharePrefEnum;
import ir.saa.android.mt.model.entities.BlockTest;
import ir.saa.android.mt.model.entities.Remark;
import ir.saa.android.mt.model.entities.TestAllInfo;
import ir.saa.android.mt.model.entities.TestInfo;
import ir.saa.android.mt.uicontrollers.pojos.TestContor.TestContorParams;
import ir.saa.android.mt.viewmodels.AmaliyatViewModel;
import ir.saa.android.mt.viewmodels.BazrasiViewModel;
import ir.saa.android.mt.viewmodels.LocationViewModel;
import ir.saa.android.mt.viewmodels.TestContorViewModel;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class TestContorFragment extends Fragment
{
    TestContorViewModel testContorViewModel;
    TextView edtCTCoeff;
    TextView edtContorConst;
    TextView edtSensorRatio;
    TextView edtRoundNum;
    ArrayAdapter<String> adapter;
    BazrasiViewModel bazrasiViewModel=null;
    AmaliyatViewModel amaliyatViewModel=null;
    LinearLayout llManeh;
    LinearLayout llTexts;
    LinearLayout llRcyclerView;
    Spinner spnManehTest;
    ManeAdapter maneAdapter;
    List<ManeItem> maneItems;
    ScrollView svManeh;
    LinearLayout llsManeh;
    MyCheckList myCheckList;
    LocationViewModel locationViewModel;
    Location location;
    ProgressDialog progressDialog;
    boolean waitForLocation=true;
    boolean isTest;
    android.support.v7.app.AlertDialog finalBasic_reg;

    com.github.angads25.toggle.LabeledSwitch switchTestType;
    com.github.angads25.toggle.LabeledSwitch switchPhase;
    com.github.angads25.toggle.LabeledSwitch switchTestNum;
    com.github.angads25.toggle.LabeledSwitch switchPaulserType;

    AlertDialog ad;
    Bundle bundle;

    public void HideProgressDialogLoction(){
        if(progressDialog!=null) progressDialog.dismiss();
    }

    private void connectToModuleDialogLoction(){

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage(getResources().getText(R.string.PleaseWait_msg));
        progressDialog.setTitle(getResources().getText(R.string.GetLocationInProgress_msg));
        progressDialog.setCancelable(true);
        progressDialog.show();
        waitForLocation=true;
    }

    public TestContorFragment() {
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_test_contor, container, false);
        testContorViewModel= ViewModelProviders.of(this).get(TestContorViewModel.class);
        bazrasiViewModel=ViewModelProviders.of(this).get(BazrasiViewModel.class);
        amaliyatViewModel=ViewModelProviders.of(this).get(AmaliyatViewModel.class);
        locationViewModel=ViewModelProviders.of(this).get(LocationViewModel.class);

        edtCTCoeff = rootView.findViewById(R.id.edtZaribCT);
        edtContorConst = rootView.findViewById(R.id.edtSabeteKontor);
        edtSensorRatio = rootView.findViewById(R.id.edtNesbatKontor);
        edtRoundNum = rootView.findViewById(R.id.edtTedadDor);
        spnManehTest=rootView.findViewById(R.id.spnManehTest);
        svManeh=rootView.findViewById(R.id.svManeh);
        llsManeh=rootView.findViewById(R.id.llsManeh);
        llManeh=rootView.findViewById(R.id.llManeh);
        llTexts=rootView.findViewById(R.id.llTexts);
        llRcyclerView=rootView.findViewById(R.id.llRcyclerView);
        List<String> spinnerArray=new ArrayList<>();
        spinnerArray.add((String) getText(R.string.TestAsli));
        spinnerArray.add((String) getText(R.string.Maneh ));

        adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_dropdown,spinnerArray );

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spnManehTest.setAdapter(adapter);
        maneItems = new ArrayList<>();

        List<TestAllInfo> testAllInfos= amaliyatViewModel.getTestAllInfoWithClientIdWithoutBlockIdTest(G.clientInfo.ClientId);

        if(testAllInfos.size()!=0){
            isTest=true;
        }else{
            isTest=false;
        }


        switchTestType = rootView.findViewById(R.id.switchTestType);
        switchPhase = rootView.findViewById(R.id.switchPhase);
        switchTestNum = rootView.findViewById(R.id.switchTestNum);
        switchPaulserType = rootView.findViewById(R.id.switchPaulserType);
        maneAdapter=new ManeAdapter(getActivity(), maneItems);

//        rvManeh.setAdapter(maneAdapter);
//        rvManeh.setLayoutManager(new LinearLayoutManager(getActivity()));

        locationViewModel.locationMutableLiveData.observe((LifecycleOwner) getContext(), new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location locationObserve) {
                if(locationObserve!=null){
                    location =locationObserve;

                    HideProgressDialogLoction();

                    if(waitForLocation){

                        waitForLocation=false;
                    }

                }
            }
        });

        switchTestType.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(LabeledSwitch labeledSwitch, boolean isOn) {

                String TypeTest="";
                if(isOn){
                    TypeTest="ActiveBlock";

                }else{
                    TypeTest="ReactiveBlock";

                }

                bazrasiViewModel.getManehTestAndBazrasiLiveData(TypeTest).observe(getActivity(), new Observer<List<Remark>>() {
                    @Override
                    public void onChanged(@Nullable List<Remark> remarks) {
//
                         myCheckList=new MyCheckList(getActivity(),
                                new MyCheckListItem("مانع 1", 1),
                                new MyCheckListItem("مانع 2", 2));

                        myCheckList.setOrientation(LinearLayout.VERTICAL);
                        myCheckList.setCheckedBackgroundColor(getResources().getColor(R.color.orange_500 ));
                        if(remarks.size()!=0) {
                            myCheckList.removeAllCkeckItems();
//                                    final RadioButton[] rb = new RadioButton[remarks.size()];
//                                    RadioGroup rg = rootView.findViewById(R.id.rgManeh); //create the RadioGroup
//                                    rg.setLayoutParams(params);
//                                    rg.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL
//                                    rg.removeAllViews();
                            for (int i = 0; i < remarks.size(); i++) {
//                                        rb[i] = new RadioButton(getActivity());
//                                        rb[i].setLayoutParams(params);
//                                        rb[i].setText(remarks.get(i).RemarkName);
//                                        rb[i].setId(remarks.get(i).RemarkID);
//                                        rg.addView(rb[i]);
                                MyCheckListItem myCheckListItem=new MyCheckListItem(remarks.get(i).RemarkName, remarks.get(i).RemarkID);
                                myCheckList.addCheckItem(myCheckListItem);
                            }



                            svManeh.removeAllViews();
                            svManeh.addView(myCheckList);//
                            //svManeh.addView(rg);//
                        }
                        List<TestInfo> testInfos=amaliyatViewModel.getTestInfoWithBlockId(G.clientInfo.ClientId,G.clientInfo.SendId);
                        if(testInfos.size()!=0){
                            List<BlockTest> blockTests=amaliyatViewModel.getBlockTestByBlockIdAndClientId(testInfos.get(0).BlockID
                                    ,G.clientInfo.ClientId );
                            myCheckList.setSelectionByValue(blockTests.get(0).BlockId);
                        }
                        myCheckList.setOnCheckListItemClickListener(new MyCheckList.OnCheckListItemClickListener() {
                            @Override
                            public void onCheckListItemClick(MyCheckListItem selectedCheckListItem, Boolean isChecked) {
                                if(isChecked){
                                        android.support.v7.app.AlertDialog basic_reg = null;
                                        if(location==null) {
                                            if (locationViewModel.isGpsEnable() == false) {
                                                location = null;
                                                locationViewModel.trunOnGps(getActivity());
                                                return;
                                            } else {
                                                connectToModuleDialogLoction();
                                                locationViewModel.getLocation(getActivity());
                                                return;
                                            }
                                        }
                                        if(isTest){
                                            TextView txtDialogTitle;
                                            TextView tvMessage;
                                            android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(getActivity());
                                            View v = getLayoutInflater().inflate(R.layout.custom_alretdialog, null);
                                            builder.setView(v);
                                            builder.setCancelable(false);
                                            builder.create();
                                            basic_reg=builder.show();
                                            txtDialogTitle=(TextView)v.findViewById(R.id.txtDialogTitle);
                                            tvMessage=(TextView)v.findViewById(R.id.tvMessage);
                                            txtDialogTitle.setText(getText(R.string.msg));
                                            tvMessage.setText(getText(R.string.msg_Save_Maneh_test));
                                            Button btnCancel=(Button)v.findViewById(R.id.btnCancel);
                                            Button btnRegister=(Button)v.findViewById(R.id.btnRegister);
                                            finalBasic_reg = basic_reg;
                                            btnRegister.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    for(TestAllInfo testAllInfo:testAllInfos){
                                                        amaliyatViewModel.deleteAllTestInfo(testAllInfo.TestInfoID
                                                                , testAllInfo.TestDtlID);
                                                    }
                                                    TestInfo testInfo=new TestInfo();
                                                    BlockTest blockTest=new BlockTest();
                                                    if(location!=null) {
                                                        blockTest.BlockId=Integer.parseInt(selectedCheckListItem.Value.toString());
                                                        blockTest.ClientId=G.clientInfo.ClientId;
                                                        amaliyatViewModel.insertBlockTest(blockTest);

                                                        testInfo.AgentID = Integer.valueOf(G.getPref("UserID"));
                                                        testInfo.TestDate = Integer.valueOf(PersianCalendar.getCurrentSimpleShamsiDate());
                                                        testInfo.TestTime = Integer.valueOf(PersianCalendar.getCurrentSimpleTime());
                                                        testInfo.SendID = G.clientInfo.SendId;
                                                        testInfo.FollowUpCode = G.clientInfo.FollowUpCode;
                                                        testInfo.ClientID = G.clientInfo.ClientId;
                                                        testInfo.Lat = String.valueOf(location.getLatitude());
                                                        testInfo.Long = String.valueOf(location.getLongitude());
                                                        testInfo.BlockID = Integer.parseInt(selectedCheckListItem.Value.toString());
                                                        Long testInfoId = amaliyatViewModel.insertTestInfo(testInfo);

                                                        if (testInfoId != null) {
                                                            Toast fancyToast = FancyToast.makeText(getContext(), (String) getResources().getText(R.string.SaveOperationSuccess_msg), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false);
                                                            fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                                            fancyToast.show();
                                                            finalBasic_reg.dismiss();
                                                            return;
                                                        }
                                                    }
                                                }
                                            });
                                            btnCancel.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    finalBasic_reg.dismiss();
                                                    return;
                                                }
                                            });

                                        }else {
                                            if (location != null) {
                                                TestInfo testInfo = new TestInfo();
                                                BlockTest blockTest = new BlockTest();
                                                if (location != null) {
                                                    blockTest.BlockId = Integer.parseInt(selectedCheckListItem.Value.toString());
                                                    blockTest.ClientId = G.clientInfo.ClientId;
                                                    amaliyatViewModel.insertBlockTest(blockTest);

                                                    testInfo.AgentID = Integer.valueOf(G.getPref("UserID"));
                                                    testInfo.TestDate = Integer.valueOf(PersianCalendar.getCurrentSimpleShamsiDate());
                                                    testInfo.TestTime = Integer.valueOf(PersianCalendar.getCurrentSimpleTime());
                                                    testInfo.SendID = G.clientInfo.SendId;
                                                    testInfo.FollowUpCode = G.clientInfo.FollowUpCode;
                                                    testInfo.ClientID = G.clientInfo.ClientId;
                                                    testInfo.Lat = String.valueOf(location.getLatitude());
                                                    testInfo.Long = String.valueOf(location.getLongitude());
                                                    testInfo.BlockID = Integer.parseInt(selectedCheckListItem.Value.toString());
                                                    Long testInfoId = amaliyatViewModel.insertTestInfo(testInfo);

                                                    if (testInfoId != null) {
                                                        Toast fancyToast = FancyToast.makeText(getContext(), (String) getResources().getText(R.string.SaveOperationSuccess_msg), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false);
                                                        fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                                        fancyToast.show();

                                                    }
                                                }

                                            }
                                        }
                                }
                            }
                        });
                    }

                });


            }
        });

        spnManehTest.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i){
                    case 0:
                        llTexts.setVisibility(View.VISIBLE);
                        llManeh.setVisibility(View.VISIBLE);
                        llRcyclerView.setVisibility(View.GONE);
                        break;
                    case 1:


                        llTexts.setVisibility(View.GONE);
                        llManeh.setVisibility(View.GONE);
                        llRcyclerView.setVisibility(View.VISIBLE);
                        String TypeTest="";
                        if(switchTestType.isOn()){
                            TypeTest="ActiveBlock";

                        }else{
                            TypeTest="ReactiveBlock";

                        }

                        bazrasiViewModel.getManehTestAndBazrasiLiveData(TypeTest).observe(getActivity(), new Observer<List<Remark>>() {
                            @Override
                            public void onChanged(@Nullable List<Remark> remarks) {
//                                maneAdapter.clearDataSet();
//                                maneItems.clear();
//                                for (Remark remark:remarks){
//                                    maneItems.add(new ManeItem(remark.RemarkID,remark.RemarkName, G.clientInfo.ClientId));
//                                }
                                ScrollView.LayoutParams params=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT);



                                params.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                                 myCheckList=new MyCheckList(getActivity(),
                                        new MyCheckListItem("مانع 1", 1),
                                        new MyCheckListItem("مانع 2", 2));
                                myCheckList.setCheckedBackgroundColor(getResources().getColor(R.color.orange_500 ));
                                myCheckList.setOrientation(LinearLayout.VERTICAL);

                                if(remarks.size()!=0) {
                                    myCheckList.removeAllCkeckItems();
                                  for (int i = 0; i < remarks.size(); i++) {

                                        MyCheckListItem myCheckListItem=new MyCheckListItem(remarks.get(i).RemarkName, remarks.get(i).RemarkID);
                                        myCheckList.addCheckItem(myCheckListItem);
                                    }



                                    svManeh.removeAllViews();
                                    svManeh.addView(myCheckList);//



                                }
                                List<TestInfo> testInfos=amaliyatViewModel.getTestInfoWithBlockId(G.clientInfo.ClientId,G.clientInfo.SendId);
                                if(testInfos.size()!=0){
                                    List<BlockTest> blockTests=amaliyatViewModel.getBlockTestByBlockIdAndClientId(testInfos.get(0).BlockID
                                            ,G.clientInfo.ClientId );
                                    myCheckList.setSelectionByValue(blockTests.get(0).BlockId);
                                }
                                myCheckList.setOnCheckListItemClickListener(new MyCheckList.OnCheckListItemClickListener() {
                                    @Override
                                    public void onCheckListItemClick(MyCheckListItem selectedCheckListItem, Boolean isChecked) {
                                        if(isChecked){
                                            android.support.v7.app.AlertDialog basic_reg = null;
                                            if(location==null) {
                                                if (locationViewModel.isGpsEnable() == false) {
                                                    location = null;
                                                    locationViewModel.trunOnGps(getActivity());
                                                    return;
                                                } else {
                                                    connectToModuleDialogLoction();
                                                    locationViewModel.getLocation(getActivity());
                                                    return;
                                                }
                                            }
                                            if(isTest){
                                                TextView txtDialogTitle;
                                                TextView tvMessage;
                                                android.support.v7.app.AlertDialog.Builder builder=new android.support.v7.app.AlertDialog.Builder(getActivity());
                                                View v = getLayoutInflater().inflate(R.layout.custom_alretdialog, null);
                                                builder.setView(v);
                                                builder.setCancelable(false);
                                                builder.create();
                                                basic_reg=builder.show();
                                                txtDialogTitle=(TextView)v.findViewById(R.id.txtDialogTitle);
                                                tvMessage=(TextView)v.findViewById(R.id.tvMessage);
                                                txtDialogTitle.setText(getText(R.string.msg));
                                                tvMessage.setText(getText(R.string.msg_Save_Maneh_test));
                                                Button btnCancel=(Button)v.findViewById(R.id.btnCancel);
                                                Button btnRegister=(Button)v.findViewById(R.id.btnRegister);
                                                finalBasic_reg = basic_reg;
                                                btnRegister.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        for(TestAllInfo testAllInfo:testAllInfos){
                                                            amaliyatViewModel.deleteAllTestInfo(testAllInfo.TestInfoID
                                                                    , testAllInfo.TestDtlID);
                                                        }
                                                        TestInfo testInfo=new TestInfo();
                                                        BlockTest blockTest=new BlockTest();
                                                        if(location!=null) {
                                                            blockTest.BlockId=Integer.parseInt(selectedCheckListItem.Value.toString());
                                                            blockTest.ClientId=G.clientInfo.ClientId;
                                                            amaliyatViewModel.insertBlockTest(blockTest);

                                                            testInfo.AgentID = Integer.valueOf(G.getPref("UserID"));
                                                            testInfo.TestDate = Integer.valueOf(PersianCalendar.getCurrentSimpleShamsiDate());
                                                            testInfo.TestTime = Integer.valueOf(PersianCalendar.getCurrentSimpleTime());
                                                            testInfo.SendID = G.clientInfo.SendId;
                                                            testInfo.FollowUpCode = G.clientInfo.FollowUpCode;
                                                            testInfo.ClientID = G.clientInfo.ClientId;
                                                            testInfo.Lat = String.valueOf(location.getLatitude());
                                                            testInfo.Long = String.valueOf(location.getLongitude());
                                                            testInfo.BlockID = Integer.parseInt(selectedCheckListItem.Value.toString());
                                                            Long testInfoId = amaliyatViewModel.insertTestInfo(testInfo);

                                                            if (testInfoId != null) {
                                                                Toast fancyToast = FancyToast.makeText(getContext(), (String) getResources().getText(R.string.SaveOperationSuccess_msg), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false);
                                                                fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                                                fancyToast.show();
                                                                finalBasic_reg.dismiss();
                                                                return;
                                                            }
                                                        }
                                                    }
                                                });
                                                btnCancel.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        finalBasic_reg.dismiss();
                                                        return;
                                                    }
                                                });

                                            }else {
                                                if (location != null) {
                                                    TestInfo testInfo = new TestInfo();
                                                    BlockTest blockTest = new BlockTest();
                                                    if (location != null) {
                                                        blockTest.BlockId = Integer.parseInt(selectedCheckListItem.Value.toString());
                                                        blockTest.ClientId = G.clientInfo.ClientId;
                                                        amaliyatViewModel.insertBlockTest(blockTest);

                                                        testInfo.AgentID = Integer.valueOf(G.getPref("UserID"));
                                                        testInfo.TestDate = Integer.valueOf(PersianCalendar.getCurrentSimpleShamsiDate());
                                                        testInfo.TestTime = Integer.valueOf(PersianCalendar.getCurrentSimpleTime());
                                                        testInfo.SendID = G.clientInfo.SendId;
                                                        testInfo.FollowUpCode = G.clientInfo.FollowUpCode;
                                                        testInfo.ClientID = G.clientInfo.ClientId;
                                                        testInfo.Lat = String.valueOf(location.getLatitude());
                                                        testInfo.Long = String.valueOf(location.getLongitude());
                                                        testInfo.BlockID = Integer.parseInt(selectedCheckListItem.Value.toString());
                                                        Long testInfoId = amaliyatViewModel.insertTestInfo(testInfo);

                                                        if (testInfoId != null) {
                                                            Toast fancyToast = FancyToast.makeText(getContext(), (String) getResources().getText(R.string.SaveOperationSuccess_msg), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false);
                                                            fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                                            fancyToast.show();

                                                        }
                                                    }

                                                }
                                            }
                                        }
                                    }
                                });
                            }
                        });



                        break;
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        setDefaultValue();

        Button btnTaeed=rootView.findViewById(R.id.btnTaeed);
        btnTaeed.setOnClickListener(view -> {
            if(validateParams()) {
                TestContorParams testContorParams = new TestContorParams(switchTestType.isOn(),
                        switchPhase.isOn(),
                        switchTestNum.isOn(),
                        Integer.parseInt(String.valueOf(edtCTCoeff.getText())),
                        Integer.parseInt(String.valueOf(edtContorConst.getText())),
                        Integer.parseInt(String.valueOf(edtSensorRatio.getText())),

//                    Integer.parseInt(String.valueOf(edtRoundNum.getText()))
                        1,
                        switchPaulserType.isOn());

                bundle = new Bundle();
                bundle.putSerializable(BundleKeysEnum.TestContorParams, testContorParams);

                if(G.bundleHashMap.containsKey(BundleKeysEnum.TestContorParams)) G.bundleHashMap.remove(BundleKeysEnum.TestContorParams);
                G.bundleHashMap.put(BundleKeysEnum.TestContorParams,bundle);

                testContorViewModel.initTranseferLayer();

                connectToModuleDialog();
            }
        });

        testContorViewModel.connectionStateMutableLiveData.observe(this, new Observer<Boolean>() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onChanged(@Nullable Boolean b) {
                    if(b){
                        G.startFragment(FragmentsEnum.TestEnergyFragment, false, bundle);
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    HideProgressDialog();
                }
            }
        );

        testContorViewModel.turnBluetoothOnMutableLiveData.observe(this, new Observer<Boolean>() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onChanged(@Nullable Boolean b) {
                        if(b){
                            requestForTurnOnBT();
                        }
                    }
                }
        );
        if(G.getPref("DontShowHelpTestMeter","0").equals("0")){
            showManualTestHelp(rootView);
        }
        return rootView;
    }

    private void showManualTestHelp(View v){
        new MaterialTapTargetPrompt.Builder(getActivity())
                .setTarget(v.findViewById(R.id.switchTestType))
                .setPrimaryText((String) getResources().getText(R.string.Help))
                .setSecondaryText((String) getResources().getText(R.string.SwapForChange_msg))
                .setPromptStateChangeListener(new uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt.PromptStateChangeListener()
                {
                    @Override
                    public void onPromptStateChanged(uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt prompt, int state)
                    {
                        if (state == MaterialTapTargetPrompt.STATE_DISMISSED) {

                            G.setPref("DontShowHelpTestMeter","1");
                        }
                    }
                })
                .show();
    }

    private void setDefaultValue(){
        TestContorParams testContorParams;
        if(G.bundleHashMap.containsKey(BundleKeysEnum.TestContorParams)) {
            Bundle b = G.bundleHashMap.get(BundleKeysEnum.TestContorParams);
            testContorParams = (TestContorParams)b.getSerializable(BundleKeysEnum.TestContorParams);

            switchPhase.setOn(testContorParams.SinglePhase);
            switchTestNum.setOn(testContorParams.FisrtTest);
            switchTestType.setOn(testContorParams.Active);
            switchPaulserType.setOn(testContorParams.PaulserType);
            edtCTCoeff.setText(String.valueOf(testContorParams.CTCoeff));
            edtContorConst.setText(String.valueOf(testContorParams.ContorConst));
            edtSensorRatio.setText(String.valueOf(testContorParams.SensorRatio));
            edtRoundNum.setText(String.valueOf(testContorParams.RoundNum));
        }
        else {
            edtCTCoeff.setText("1");
            edtContorConst.setText("2000");
            edtSensorRatio.setText("1");
            edtRoundNum.setText("1");
        }

    }

    public void requestForTurnOnBT(){
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        getActivity().startActivity(enableBtIntent);
    }

    private void connectToModuleDialog(){
        String BluetoothDeviceName = G.getPref(SharePrefEnum.ModuleBluetoothName);
        ad = new AlertDialog.Builder(this.getContext()).create();
        ad.setCancelable(true);
        ad.setTitle(String.format("%s %s", getResources().getText(R.string.ConnectToTestModule),BluetoothDeviceName));
        ad.setMessage(getResources().getText(R.string.PleaseWait_msg));
        new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        };
        ad.show();
    }

    public void HideProgressDialog(){
        if(ad!=null) ad.dismiss();
    }

    private boolean validateParams(){
        boolean res=true;
        String selText="";

        selText = edtCTCoeff.getText().toString();
        res = ValidationText(selText,(String) getResources().getText(R.string.MeterCoeff));
        if(!res) return res;

        selText = edtContorConst.getText().toString();
        res = ValidationText(selText,(String) getResources().getText(R.string.MeterConst));
        if(!res) return res;

        selText = edtSensorRatio.getText().toString();
        res = ValidationText(selText,(String) getResources().getText(R.string.Sensor));
        if(!res) return res;

        selText = edtRoundNum.getText().toString();
        res = ValidationText(selText,(String) getResources().getText(R.string.RoundNum));
        if(!res) return res;

        return  res;
    }

    private boolean ValidationText(String selText,String ToastString){
        boolean res=true;
        if(selText.equals("") || selText.equals("0")) {
            Toast fancyToast = FancyToast.makeText(G.context, String.format((String) getResources().getText(R.string.PleaseEneter_msg),ToastString), FancyToast.LENGTH_SHORT, FancyToast.WARNING, false);
            fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            fancyToast.show();
            res = false;
        }
        return res;
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
