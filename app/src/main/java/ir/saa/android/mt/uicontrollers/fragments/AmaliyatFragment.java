package ir.saa.android.mt.uicontrollers.fragments;


import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.adapters.testresult.TestResultAdapter;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.components.MyDialog;
import ir.saa.android.mt.components.PersianCalendar;
import ir.saa.android.mt.enums.BundleKeysEnum;
import ir.saa.android.mt.model.entities.GPSInfo;
import ir.saa.android.mt.model.entities.TestDtl;
import ir.saa.android.mt.model.entities.TestInfo;
import ir.saa.android.mt.repositories.metertester.MT;
import ir.saa.android.mt.repositories.metertester.TestResult;
import ir.saa.android.mt.uicontrollers.pojos.TestContor.TestContorFieldName;
import ir.saa.android.mt.uicontrollers.pojos.TestContor.TestContorParams;
import ir.saa.android.mt.viewmodels.AmaliyatViewModel;
import ir.saa.android.mt.viewmodels.LocationViewModel;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class AmaliyatFragment extends Fragment {

    AmaliyatViewModel amaliyatViewModel = null;
    LocationViewModel locationViewModel = null;
    TestContorParams testContorParams;
    List<TestResult> lastTestResultList;
    TestResultAdapter adapter;
    RelativeLayout rlManualPaulser;
    TextView tvRoundNum;
    TextView tvErrPerc;
    RecyclerView recyclerView;
    private Boolean active;
    ImageView imgNewPaulse;
    TextView tvManualPaulseNum;
    //TextView tvEnergi;
    Location location;
    boolean isLocation;
    boolean paulserType;
    int manualPaulseNum =0;
    TestResult testResultLocation;
    int CountSaveTest;
    ProgressDialog progressDialog;
    boolean waitForLocation=false;

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
        waitForLocation=true;
    }

    public void HideProgressDialog(){
        if(progressDialog!=null) progressDialog.dismiss();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void showManualTestHelp(View v){
        new MaterialTapTargetPrompt.Builder(getActivity())
                .setTarget(v.findViewById(R.id.imgManualPaulse))
                .setPrimaryText((String) getResources().getText(R.string.ManualPaulser))
                .setSecondaryText((String) getResources().getText(R.string.ClickToStartManualTes_msg))
                .setPromptStateChangeListener(new uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt.PromptStateChangeListener()
                {
                    @Override
                    public void onPromptStateChanged(uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt prompt, int state)
                    {
                        if (state == uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)
                        {

                        }
                    }
                })
                .show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fargment_amaliyat, container, false);
        amaliyatViewModel = ViewModelProviders.of(this).get(AmaliyatViewModel.class);
        locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        CountSaveTest=0;
        testResultLocation=new TestResult();
        rlManualPaulser = rootView.findViewById(R.id.rlManualPaulser);
        Button btnStartTest = rootView.findViewById(R.id.btnStartTest);
        Button btnFinishTest = rootView.findViewById(R.id.btnFinishTest);
        Button btnSaveResult = rootView.findViewById(R.id.btnSaveTestResult);
        imgNewPaulse = rootView.findViewById(R.id.imgManualPaulse);
        tvManualPaulseNum =  rootView.findViewById(R.id.txtManualPaulseNum);
        //tvEnergi = rootView.findViewById(R.id.tvEnergi);
        isLocation = false;
        location=null;
        Bundle args = getArguments();
        if (args != null) {
            testContorParams = (TestContorParams) args.getSerializable(BundleKeysEnum.TestContorParams);
            paulserType = !testContorParams.PaulserType;
        }

        tvErrPerc = rootView.findViewById(R.id.tvErrPerc);
        amaliyatViewModel.checkTestStatus();

        amaliyatViewModel.testResultMutableLiveData.observe(this, new Observer<String>() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onChanged(@Nullable String testResult) {
                        tvErrPerc.setText(testResult);
                    }
                }
        );



        amaliyatViewModel.testStatusMutableLiveData.observe(this, new Observer<MT.TestCommands>() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onChanged(@Nullable MT.TestCommands testCommands) {
                        switch (testCommands) {
                            case StartTest:
                                btnStartTest.setVisibility(View.GONE);
                                btnFinishTest.setVisibility(View.VISIBLE);
                                btnSaveResult.setVisibility(View.GONE);
                                tvErrPerc.setText("");
                                tvRoundNum.setText("");
                                manualPaulseNum=0;
                                tvManualPaulseNum.setText(String.valueOf(manualPaulseNum));
                                if(paulserType) {
                                    rlManualPaulser.setVisibility(View.VISIBLE);
                                    showManualTestHelp(rootView);
                                }else{
                                    rlManualPaulser.setVisibility(View.GONE);
                                }
                                break;

                            case FinishTest:
                                btnStartTest.setVisibility(View.VISIBLE);
                                btnFinishTest.setVisibility(View.GONE);
                                btnSaveResult.setVisibility(View.VISIBLE);
                                rlManualPaulser.setVisibility(View.GONE);

                                break;

                            case DisableTest:
                                btnStartTest.setVisibility(View.VISIBLE);
                                btnFinishTest.setVisibility(View.GONE);
                                btnSaveResult.setVisibility(View.GONE);
                                rlManualPaulser.setVisibility(View.GONE);
                                break;
                        }
                    }
                }
        );

        tvRoundNum = rootView.findViewById(R.id.tvTestRoundNum);
        amaliyatViewModel.testRoundNumMutableLiveData.observe(this, new Observer<Integer>() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onChanged(@Nullable Integer testRoundNum) {
                        tvRoundNum.setText(String.valueOf(testRoundNum));
                    }
                }
        );

        amaliyatViewModel.testResultListMutableLiveData.observe(this, new Observer<List<TestResult>>() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onChanged(@Nullable List<TestResult> testResultList) {
                        if (testResultList != null && testResultList.size() > 0) {
                            setUpRecyclerView(rootView, testResultList);
                            lastTestResultList = testResultList;
                            btnSaveResult.setVisibility(View.VISIBLE);
                        } else {
                            btnSaveResult.setVisibility(View.GONE);
                            Toast.makeText(G.context, getResources().getText(R.string.TestResultNotExsist_msg), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        amaliyatViewModel.cancelTestProcess.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean) finishTestDialog();
            }
        });

        imgNewPaulse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvManualPaulseNum.setText(String.valueOf(manualPaulseNum));
                tvRoundNum.setText(String.valueOf(manualPaulseNum));
                amaliyatViewModel.readTestResultFromMeterManual(manualPaulseNum);

                manualPaulseNum++;
            }
        });


        btnStartTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<TestResult> testResults = new ArrayList<>();
                setUpRecyclerView(rootView, testResults);
                waitForLocation=false;
                amaliyatViewModel.setTestContorParams(testContorParams);
                amaliyatViewModel.startTestOperation();
                //btnStartTest.setEnabled(false);
            }
        });

        btnFinishTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amaliyatViewModel.finishTest(true);
            }
        });

        btnSaveResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MT meterTest = MT.getInstance();
                TestResult testResult = meterTest.prepareTestResultForSave(lastTestResultList);
                testResult.ErrPerc = Double.parseDouble((String) tvErrPerc.getText());
                testResultLocation=testResult;
                if(locationViewModel.isGpsEnable()==false) {
                    location=null;
                    locationViewModel.trunOnGps(getActivity());
                }else{
                    connectToModuleDialog();
                    locationViewModel.getLocation(getActivity());
                   return;
                }
                //saveTestResult(testResult);
            }
        });


        locationViewModel.locationMutableLiveData.observe((LifecycleOwner) getContext(), new Observer<Location>() {
        @Override
        public void onChanged(@Nullable Location locationObserve) {
            if(locationObserve!=null){
                location =locationObserve;

                HideProgressDialog();
                if(testResultLocation!=null){
                    if(waitForLocation){
                        saveTestResult(testResultLocation);
                        waitForLocation=false;
                    }
                }
            }
        }
        });


        return rootView;
    }



    private void saveTestResult(TestResult testResult) {
        CountSaveTest++;
        if (location != null) {

            GPSInfo gpsInfo = new GPSInfo();
            gpsInfo.ClientID = G.clientInfo.ClientId;
            gpsInfo.FollowUpCode = G.clientInfo.FollowUpCode == null ? 0 : G.clientInfo.FollowUpCode;
            gpsInfo.SendID = G.clientInfo.SendId;
            gpsInfo.GPSDate = Integer.valueOf(PersianCalendar.getCurrentSimpleShamsiDate());
            gpsInfo.GPSTime = Integer.valueOf(PersianCalendar.getCurrentSimpleTime());
            gpsInfo.Lat = String.valueOf(location.getLatitude());
            gpsInfo.Long = String.valueOf(location.getLongitude());
            amaliyatViewModel.insertGpsInfo(gpsInfo);
            isLocation = true;
        }else {
            locationViewModel.trunOnGps(getContext());
            isLocation=false;

        }

        if (isLocation) {
            TestInfo testInfo = new TestInfo();
            testInfo.AgentID = Integer.valueOf(G.getPref("UserID"));
            testInfo.TestDate = Integer.valueOf(PersianCalendar.getCurrentSimpleShamsiDate());
            testInfo.TestTime = Integer.valueOf(PersianCalendar.getCurrentSimpleTime());
            testInfo.SendID = G.clientInfo.SendId;
            testInfo.FollowUpCode = G.clientInfo.FollowUpCode;
            testInfo.ClientID = G.clientInfo.ClientId;
            testInfo.ContorTypeID = testContorParams.SinglePhase == true ? 1 : 3;
            testInfo.TestCount = testContorParams.FisrtTest == true ? 1 : 2;
            testInfo.TestTypeID = testContorParams.Active == true ? 1 : 2;
            Long testInfoId = amaliyatViewModel.insertTestInfo(testInfo);
            TestDtl testDtl = new TestDtl();
            testDtl.TestInfoID = Integer.valueOf(testInfoId.toString());
            TestContorFieldName testContorFieldName = new TestContorFieldName();
            Field[] fieldsTestcontorParam = TestContorParams.class.getFields();
            testDtl.ReadTypeID = 1;
            testDtl.AgentID = Integer.valueOf(G.getPref("UserID"));
            testDtl.TestID = testContorFieldName.getTestId("testContorParams_RoundNum");
            testDtl.TestValue = String.valueOf(testContorParams.RoundNum);
            amaliyatViewModel.insertTestDtl(testDtl);
            Field[] fieldstestDtl = TestResult.class.getFields();

            TestDtl testDetail = new TestDtl();
            for (Field field : fieldstestDtl) {
                Integer testID = testContorFieldName.getTestId(field.getName());
                if (testID != null) {
                    testDetail.ReadTypeID = 1;
                    testDetail.TestInfoID = Integer.valueOf(testInfoId.toString());

                    testDetail.TestID = testID;

                    Object value = null;

                    try {
                        value = field.get(testResult);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }


                    if (value != null) {
                        testDetail.TestValue = value.toString();
                    }
                    amaliyatViewModel.insertTestDtl(testDetail);
                }
            }
            Toast fancyToast = FancyToast.makeText(getActivity(), (String) getResources().getText(R.string.SaveOperationSuccess_msg), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false);
            fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            fancyToast.show();
            //Bastan Form Sabt
            G.fragmentNumStack.pop();
            G.startFragment(G.fragmentNumStack.pop(), true, null);
            isLocation=false;
        }



    }

    private void finishTestDialog(){
//        new AlertDialog.Builder(this.getContext())
//                .setTitle("خطا در ارتباط")
//                .setMessage(getResources().getText(R.string.MessageCancelTest).toString())
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .setPositiveButton(R.string.Ok, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        amaliyatViewModel.finishTest();
//                    }})
//                .setNegativeButton(
//                        getResources().getString(R.string.Retry),
//                        new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog,
//                                                int whichButton) {
//                                amaliyatViewModel.changeTestPauseStatus(false);
//                            }
//                        }).show();


        MyDialog myDialog=new MyDialog(this.getContext());

        myDialog.setTitle(getResources().getText(R.string.CancelTestProcessTitle).toString());
        myDialog.addBodyText(getResources().getText(R.string.CancelTest_msg).toString(),18);

        myDialog.addButton(getResources().getText(R.string.Retry).toString(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amaliyatViewModel.changeTestPauseStatus(false);
                myDialog.dismiss();
            }
        });

        myDialog.addButton(getResources().getText(R.string.Ok).toString(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amaliyatViewModel.finishTest(false);
                myDialog.dismiss();
            }
        });

        myDialog.show();

    }

    private void setUpRecyclerView(View view,List<TestResult> testResultList) {
        recyclerView = (RecyclerView) view.findViewById(R.id.rvKhata);

        adapter = new TestResultAdapter(getActivity(), testResultList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter.clearDataSet();
        adapter.addAll(testResultList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onPause() {
        super.onPause();
        amaliyatViewModel.finishTest(true);
    }

    @Override
    public void onResume() {
        super.onResume();
    }



}