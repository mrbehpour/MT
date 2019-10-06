package ir.saa.android.mt.uicontrollers.fragments;

import android.app.ProgressDialog;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;
import java.util.TreeMap;

import ir.saa.android.mt.R;
import ir.saa.android.mt.adapters.polomp.PolompItem;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.components.MyCheckList;
import ir.saa.android.mt.components.MyCheckListItem;
import ir.saa.android.mt.components.MyCheckListMode;
import ir.saa.android.mt.components.MyDialog;
import ir.saa.android.mt.components.PersianCalendar;
import ir.saa.android.mt.enums.BundleKeysEnum;
import ir.saa.android.mt.enums.FragmentsEnum;
import ir.saa.android.mt.model.entities.InspectionAllInfo;
import ir.saa.android.mt.model.entities.InspectionInfo;
import ir.saa.android.mt.model.entities.PolompAllInfo;
import ir.saa.android.mt.model.entities.Remark;
import ir.saa.android.mt.model.entities.TariffAllInfo;
import ir.saa.android.mt.model.entities.TestAllInfo;
import ir.saa.android.mt.model.entities.TestInfo;
import ir.saa.android.mt.viewmodels.AmaliyatViewModel;
import ir.saa.android.mt.viewmodels.BazrasiViewModel;
import ir.saa.android.mt.viewmodels.LocationViewModel;
import ir.saa.android.mt.viewmodels.PolompViewModel;
import ir.saa.android.mt.viewmodels.ReadmeterViewModel;
import mehdi.sakout.fancybuttons.FancyButton;

public class MoshtarakOperationsTabFragment extends Fragment
{
    MyDialog myDialog;
    MyCheckList myCheckList;
    AmaliyatViewModel amaliyatViewModel;
    LocationViewModel locationViewModel=null;
    BazrasiViewModel bazrasiViewModel=null;
    PolompViewModel polompViewModel;
    ReadmeterViewModel readmeterViewModel;
    Location location;
    TextView tvMessage;
    ProgressDialog progressDialog;
    boolean waitForLocation=true;
    TestInfo testInfo;
    AlertDialog finalBasic_reg;

    Boolean isTest=false,isPolomp=false,isTariff=false,isBazarsi=false,isMoshahedate=false;

    public void HideProgressDialog(){
        if(progressDialog!=null) progressDialog.dismiss();
    }

    private void connectToModuleDialog(){

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage(getResources().getText(R.string.PleaseWait_msg));
        progressDialog.setTitle(getResources().getText(R.string.GetLocationInProgress_msg));
        progressDialog.setCancelable(true);
        progressDialog.show();
        waitForLocation=true;
    }
    private Long clientID = null;
    public MoshtarakOperationsTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationViewModel= ViewModelProviders.of(this).get(LocationViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_moshtarak_operation, container, false);

        if(clientID==null){
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                clientID = bundle.getLong(BundleKeysEnum.ClientID);
            }else{
                clientID=G.clientInfo.ClientId;

            }
        }
        polompViewModel=ViewModelProviders.of(this).get(PolompViewModel.class);
        readmeterViewModel=ViewModelProviders.of(this).get(ReadmeterViewModel.class);
        bazrasiViewModel=ViewModelProviders.of(this).get(BazrasiViewModel.class);
        amaliyatViewModel=ViewModelProviders.of(this).get(AmaliyatViewModel.class);

        List<PolompAllInfo> polompAllInfos= polompViewModel.getPolompAllInfoWithClientId(G.clientInfo.ClientId);
        if(polompAllInfos.size()!=0){
            isPolomp=true;
        }

        List<TariffAllInfo> tariffAllInfos= readmeterViewModel.getTariffAllInfo(G.clientInfo.ClientId, G.clientInfo.SendId);
        if(tariffAllInfos.size()!=0){
            isTariff=true;
        }
         List<InspectionAllInfo> inspectionAllInfos=  bazrasiViewModel.getInspectionAllInfos(G.clientInfo.ClientId);
        if(inspectionAllInfos.size()!=0){
            isBazarsi=true;
        }
        List<TestAllInfo> testAllInfos= amaliyatViewModel.getTestAllInfoWithClientIdWithoutBlockId(G.clientInfo.ClientId);
        if(testAllInfos.size()!=0){
            isTest=true;
        }


        //Button btnTest=rootView.findViewById(R.id.btnTest);
        FancyButton btnTest=rootView.findViewById(R.id.btnTest);
        FancyButton btnPolomp=rootView.findViewById(R.id.btnPolomp);
        FancyButton btnBazrasi=rootView.findViewById(R.id.btnBazrasi);
        FancyButton btnReadmeter=rootView.findViewById(R.id.btnReadmeter);
        FancyButton btnMoshahedat=rootView.findViewById(R.id.btnMoshahedat);
        FancyButton btnManehTest=rootView.findViewById(R.id.btnManehTest);

        btnManehTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog=new MyDialog(getActivity()).setTitle(btnManehTest.getText().toString());
                myCheckList=new MyCheckList(getActivity()
                        ,new MyCheckListItem("مانع 1",1) ,new MyCheckListItem("مانع 2", 2))
                        .setSelectionMode(MyCheckListMode.SingleSelection).setCheckListOrientation(LinearLayout.VERTICAL);
                List<TestInfo> testInfos=amaliyatViewModel.getTestInfoWithBlockId(G.clientInfo.ClientId,
                        G.clientInfo.SendId);
               List<Remark> remarks= bazrasiViewModel.getManehTestAndBazrasi("Block");

                        myCheckList.removeAllCkeckItems();
                        for(Remark remark:remarks){
                            MyCheckListItem myCheckListItem=new MyCheckListItem(remark.RemarkName, remark.RemarkID);
                            myCheckList.addCheckItem(myCheckListItem);
                        }




                myDialog.addContentView(myCheckList);

                if(testInfos.size()!=0){
                    myCheckList.setSelectionByValue(testInfos.get(0).BlockID);

                }

                myDialog.addButton((String) getText(R.string.Cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog.dismiss();
                    }
                }).addButton((String) getText(R.string.Save), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog basic_reg = null;
                        if(myCheckList.getSelectedItemsValues().size()!=0){
                            if(isBazarsi || isPolomp || isTariff || isTest){

                                TextView txtDialogTitle;
                                TextView tvMessage;
                                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                                View v = getLayoutInflater().inflate(R.layout.custom_alretdialog, null);
                                builder.setView(v);
                                builder.setCancelable(false);
                                builder.create();
                                basic_reg=builder.show();
                                txtDialogTitle=(TextView)v.findViewById(R.id.txtDialogTitle);
                                tvMessage=(TextView)v.findViewById(R.id.tvMessage);
                                txtDialogTitle.setText(getText(R.string.msg));
                                tvMessage.setText(getText(R.string.msg_Save_Maneh));
                                Button btnCancel=(Button)v.findViewById(R.id.btnCancel);
                                Button btnRegister=(Button)v.findViewById(R.id.btnRegister);
                                finalBasic_reg = basic_reg;
                                btnRegister.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        for (PolompAllInfo polompAllInfo:polompAllInfos){
                                            polompViewModel.deleteAllPolomp(polompAllInfo.PolompInfoID,
                                                    polompAllInfo.PolompDtlID);
                                        }
                                        for (TestAllInfo testAllInfo:testAllInfos){
                                            amaliyatViewModel.deleteAllTestInfo(testAllInfo.TestInfoID,
                                                    testAllInfo.TestDtlID);
                                        }
                                        for (InspectionAllInfo inspectionAllInfo:inspectionAllInfos){
                                            bazrasiViewModel.deleteInspectionAllInfo(inspectionAllInfo.InspectionInfoID
                                            , inspectionAllInfo.InspectionDtlID);
                                        }
                                        for (TariffAllInfo tariffAllInfo:tariffAllInfos){
                                            readmeterViewModel.deleteAllTarif(tariffAllInfo.TariffInfoID,
                                                    tariffAllInfo.TariffDtlID);

                                        }
                                        if(location==null) {
                                            if (locationViewModel.isGpsEnable() == false) {
                                                location = null;
                                                locationViewModel.trunOnGps(getActivity());
                                                return;
                                            } else {
                                                connectToModuleDialog();
                                                locationViewModel.getLocation(getActivity());
                                                return;
                                            }
                                        }
                                        if(location!=null) {
                                            testInfo = new TestInfo();
                                            testInfo.AgentID = Integer.valueOf(G.getPref("UserID"));
                                            testInfo.TestDate = Integer.valueOf(PersianCalendar.getCurrentSimpleShamsiDate());
                                            testInfo.TestTime = Integer.valueOf(PersianCalendar.getCurrentSimpleTime());
                                            testInfo.SendID = G.clientInfo.SendId;
                                            testInfo.FollowUpCode = G.clientInfo.FollowUpCode;
                                            testInfo.ClientID = G.clientInfo.ClientId;
                                            testInfo.Lat = String.valueOf(location.getLatitude());
                                            testInfo.Long = String.valueOf(location.getLongitude());
                                            testInfo.BlockID = Integer.parseInt(myCheckList.getSelectedItemsValues().get(0).toString());
                                            Long testInfoId = amaliyatViewModel.insertTestInfo(testInfo);

                                            if (testInfoId != null) {
                                                Toast fancyToast = FancyToast.makeText(getContext(), (String) getResources().getText(R.string.SaveOperationSuccess_msg), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false);
                                                fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                                fancyToast.show();
                                                finalBasic_reg.dismiss();
                                            }
                                        }

                                    }
                                });
                                btnCancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        finalBasic_reg.dismiss();
                                    }
                                });
                            }else {

                                if(location==null) {
                                    if (locationViewModel.isGpsEnable() == false) {
                                        location = null;
                                        locationViewModel.trunOnGps(getActivity());
                                        return;
                                    } else {
                                        connectToModuleDialog();
                                        locationViewModel.getLocation(getActivity());
                                        return;
                                    }
                                }
                                if(location!=null) {
                                    testInfo = new TestInfo();
                                    testInfo.AgentID = Integer.valueOf(G.getPref("UserID"));
                                    testInfo.TestDate = Integer.valueOf(PersianCalendar.getCurrentSimpleShamsiDate());
                                    testInfo.TestTime = Integer.valueOf(PersianCalendar.getCurrentSimpleTime());
                                    testInfo.SendID = G.clientInfo.SendId;
                                    testInfo.FollowUpCode = G.clientInfo.FollowUpCode;
                                    testInfo.ClientID = G.clientInfo.ClientId;
                                    testInfo.Lat = String.valueOf(location.getLatitude());
                                    testInfo.Long = String.valueOf(location.getLongitude());
                                    testInfo.BlockID = Integer.parseInt(myCheckList.getSelectedItemsValues().get(0).toString());
                                    Long testInfoId = amaliyatViewModel.insertTestInfo(testInfo);

                                    if (testInfoId != null) {
                                        Toast fancyToast = FancyToast.makeText(getContext(), (String) getResources().getText(R.string.SaveOperationSuccess_msg), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false);
                                        fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                        fancyToast.show();

                                    }
                                }
                            }
                        }
                        myDialog.dismiss();
                    }
                }).show();
            }
        });

        btnTest.setOnClickListener(view -> {
            locationViewModel.trunOnGps(getContext());

            final LocationManager manager = (LocationManager) getContext().getSystemService( Context.LOCATION_SERVICE );

            if ( manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                G.startFragment(FragmentsEnum.DisplayTestFragment,false,null);

                return;
            }

        });

        btnPolomp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle=new Bundle();
                if(clientID!=null) {
                    bundle.putLong(BundleKeysEnum.ClientID, clientID);
                }else{
                    bundle.putLong(BundleKeysEnum.ClientID, G.clientInfo.ClientId);
                }

                locationViewModel.trunOnGps(getContext());

                final LocationManager manager = (LocationManager) getContext().getSystemService( Context.LOCATION_SERVICE );

                if ( manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                    G.startFragment(FragmentsEnum.PolompFragment, false, bundle);
                    return;
                }


            }
        });

        btnBazrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationViewModel.trunOnGps(getContext());

                final LocationManager manager = (LocationManager) getContext().getSystemService( Context.LOCATION_SERVICE );

                if ( manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
                    G.startFragment(FragmentsEnum.BazrasiFragment,false,null);

                    return;
                }

            }
        });

        btnReadmeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G.startFragment(FragmentsEnum.ReadmeterFragment,false,null);
                return;
            }
        });

        btnMoshahedat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G.startFragment(FragmentsEnum.MoshahedatFragment,false,null);
            }
        });
        locationViewModel.locationMutableLiveData.observe((LifecycleOwner) getContext(), new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location locationObserve) {
                if(locationObserve!=null){
                    location =locationObserve;

                    HideProgressDialog();

                        if(waitForLocation){

                            waitForLocation=false;
                        }

                }
            }
        });

        G.setActionbarTitleText(G.clientInfo.ClientName);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }

}
