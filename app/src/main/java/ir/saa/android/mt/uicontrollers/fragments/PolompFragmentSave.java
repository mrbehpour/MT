package ir.saa.android.mt.uicontrollers.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.components.Tarikh;
import ir.saa.android.mt.enums.BundleKeysEnum;
import ir.saa.android.mt.enums.FragmentsEnum;
import ir.saa.android.mt.model.entities.PolompAllInfo;
import ir.saa.android.mt.model.entities.PolompColor;
import ir.saa.android.mt.model.entities.PolompDtl;
import ir.saa.android.mt.model.entities.PolompInfo;
import ir.saa.android.mt.model.entities.PolompType;
import ir.saa.android.mt.uicontrollers.pojos.Polomp.PolompParams;
import ir.saa.android.mt.uicontrollers.pojos.Polomp.PolompParams;
import ir.saa.android.mt.viewmodels.PolompViewModel;

public class PolompFragmentSave extends Fragment {

    PolompViewModel polompViewModel;
    PolompParams polompParams;

    Spinner spnModelPolompJadid;
    Spinner spnRangPolompJadid;
    Spinner spnModelPolompGhadim;
    Spinner spnRangPolompGhadim;

    Boolean chkNadaradJadid;
    Boolean chkNadradGhadim;
    Boolean chkNewNakhana;
    Boolean chkOldNakhana;

    EditText etPolompJadid;
    EditText etPolompGhadim;
    AppCompatButton btnSave;

    CheckBox cbGhadimNadard;
    CheckBox cbJadidNadard;
    CheckBox cbNewNakhana;
    CheckBox cbOldNakhana;

    TextView tvModelPolompGhadim;
    TextView tvRangPolompGhadim;
    TextInputLayout tilGhadim;

    TextView tvModelPolompJadid;
    TextView tvRangPolompJadid;
    TextInputLayout tilNew;


    List<String> spinnerArrayModelJadid;
    List<String> spinnerArrayModelGhadim;
    List<String> spinnerArrayColorJadid;
    List<String> spinnerArrayColorGhadim;

    ArrayAdapter<String> adapterModelGhadim;
    ArrayAdapter<String> adapterModelJadid;
    ArrayAdapter<String> adapterColorGhadim;
    ArrayAdapter<String> adapterColorJadid;

    HashMap<Integer,Integer> spinnerMapModelJadid = new HashMap<Integer, Integer>();
    HashMap<Integer,Integer> spinnerMapModelGhadim = new HashMap<Integer, Integer>();
    HashMap<Integer,Integer> spinnerMapColorJadid = new HashMap<Integer, Integer>();
    HashMap<Integer,Integer> spinnerMapColorGhadim = new HashMap<Integer, Integer>();

    HashMap<Integer,Integer> spinnerMapColor = new HashMap<Integer, Integer>();
    HashMap<Integer,Integer> spinnerMapModel = new HashMap<Integer, Integer>();

    PolompInfo polompInfo;
    PolompDtl polompDtl;
    public PolompFragmentSave(){

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        polompViewModel= ViewModelProviders.of(getActivity()).get(PolompViewModel.class);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_polomp_save, container, false);
        chkNadaradJadid=false;
        chkNadradGhadim=false;
        chkNewNakhana=false;
        chkOldNakhana=false;
        if(polompParams==null){
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                polompParams = (PolompParams) bundle.getSerializable(BundleKeysEnum.ClassPolompParams);
            }
        }

        spnModelPolompGhadim=rootView.findViewById(R.id.spnModelPolompGhadim);
        spnModelPolompJadid=rootView.findViewById(R.id.spnModelPolompJadid);
        spnRangPolompGhadim=rootView.findViewById(R.id.spnRangPolompGhadim);
        spnRangPolompJadid=rootView.findViewById(R.id.spnRangPolompJadid);



        cbNewNakhana=rootView.findViewById(R.id.cbJadidNakhana);
        cbNewNakhana.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    tilNew.setHintEnabled(false);
                    etPolompJadid.setEnabled(false);
                    chkNewNakhana=true;
                    cbJadidNadard.setEnabled(false);

                }else{
                    tilNew.setHintEnabled(true);
                    chkNewNakhana=false;
                    etPolompJadid.setEnabled(true);
                    cbJadidNadard.setEnabled(true);

                }
            }
        });

        cbOldNakhana=rootView.findViewById(R.id.cbGhadimNakhana);
        cbOldNakhana.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    tilGhadim.setHintEnabled(false);
                    etPolompGhadim.setEnabled(false);
                    chkOldNakhana=true;
                    cbGhadimNadard.setEnabled(false);
                }else{
                    tilGhadim.setHintEnabled(true);
                    chkOldNakhana=false;
                    etPolompGhadim.setEnabled(true);
                    cbGhadimNadard.setEnabled(true);

                }

            }
        });
        tvModelPolompGhadim=rootView.findViewById(R.id.tvModelPolompGhadim);
        tvRangPolompGhadim=rootView.findViewById(R.id.tvRangPolompGhadim);
        tilGhadim=rootView.findViewById(R.id.tilGhadim);
        cbGhadimNadard=rootView.findViewById(R.id.cbGhadimNadarad);
        cbGhadimNadard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    tvRangPolompGhadim.setTextColor(getResources().getColor(R.color.grey_500));
                    tvModelPolompGhadim.setTextColor(getResources().getColor(R.color.grey_500)  );

                    tilGhadim.setHintEnabled(false);
                   chkNadradGhadim=true;
                    spnModelPolompGhadim.setEnabled(false);
                    spnRangPolompGhadim.setEnabled(false);
                    etPolompGhadim.setEnabled(false);
                    cbOldNakhana.setEnabled(false);
                    spnModelPolompGhadim.setSelection(0);
                    spnRangPolompGhadim.setSelection(0);
                    etPolompGhadim.setText("");
                }else{
                    tvRangPolompGhadim.setTextColor(getResources().getColor(R.color.ms_black));
                    tvModelPolompGhadim.setTextColor(getResources().getColor(R.color.ms_black)  );
                    cbOldNakhana.setEnabled(true);
                    chkNadradGhadim=false;
                    spnModelPolompGhadim.setEnabled(true);
                    spnRangPolompGhadim.setEnabled(true);
                    etPolompGhadim.setEnabled(true);
                    tilGhadim.setHintEnabled(true);
                    etPolompGhadim.setTextColor(getResources().getColor(R.color.ms_black)  );

                }

            }
        });

        tvModelPolompJadid=rootView.findViewById(R.id.tvModelPolompJadid);
        tvRangPolompJadid=rootView.findViewById(R.id.tvRangPolompJadid);
        tilNew=rootView.findViewById(R.id.tilNew);

        cbJadidNadard=rootView.findViewById(R.id.cbJadidNadarad);
        cbJadidNadard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                   chkNadaradJadid=true;
                    tvRangPolompJadid.setTextColor(getResources().getColor(R.color.grey_500));
                    tvModelPolompJadid.setTextColor(getResources().getColor(R.color.grey_500)  );

                    tilNew.setHintEnabled(false);
                    spnModelPolompJadid.setEnabled(false);
                    spnRangPolompJadid.setEnabled(false);
                    etPolompJadid.setEnabled(false);
                    cbNewNakhana.setEnabled(false);
                    spnModelPolompJadid.setSelection(0);
                    spnRangPolompJadid.setSelection(0);
                    etPolompJadid.setText("");
                }else{
                    tvRangPolompJadid.setTextColor(getResources().getColor(R.color.ms_black));
                    tvModelPolompJadid.setTextColor(getResources().getColor(R.color.ms_black)  );

                    tilNew.setHintEnabled(true);
                    cbNewNakhana.setEnabled(true);
                    chkNadaradJadid=false;
                    spnModelPolompJadid.setEnabled(true);
                    spnRangPolompJadid.setEnabled(true);
                    etPolompJadid.setEnabled(true);

                }

            }
        });

        spinnerArrayColorGhadim=new ArrayList<>();
        spinnerArrayColorJadid=new ArrayList<>();
        spinnerArrayModelGhadim=new ArrayList<>();
        spinnerArrayModelJadid=new ArrayList<>();

        adapterColorGhadim=new ArrayAdapter<>(getActivity(), R.layout.al_polomp_save_spinner_item,spinnerArrayColorGhadim);
        adapterColorGhadim.setDropDownViewResource(R.layout.al_polomp_save_spinner_dropdown);
        spnRangPolompGhadim.setAdapter(adapterColorGhadim);

        adapterColorJadid=new ArrayAdapter<>(getActivity(), R.layout.al_polomp_save_spinner_item,spinnerArrayColorJadid);
        adapterColorJadid.setDropDownViewResource(R.layout.al_polomp_save_spinner_dropdown);
        spnRangPolompJadid.setAdapter(adapterColorJadid);

        adapterModelGhadim=new ArrayAdapter<>(getActivity(), R.layout.al_polomp_save_spinner_item,spinnerArrayModelGhadim);
        adapterModelGhadim.setDropDownViewResource(R.layout.al_polomp_save_spinner_dropdown);
        spnModelPolompGhadim.setAdapter(adapterModelGhadim);

        adapterModelJadid=new ArrayAdapter<>(getActivity(), R.layout.al_polomp_save_spinner_item,spinnerArrayModelJadid);
        adapterModelJadid.setDropDownViewResource(R.layout.al_polomp_save_spinner_dropdown);
        spnModelPolompJadid.setAdapter(adapterModelJadid);

        adapterInit();

        polompViewModel.getPolompColor().observe(getActivity(), polompColors -> {
            spinnerArrayColorGhadim.clear();
            spinnerMapColorGhadim.clear();
            spinnerArrayColorJadid.clear();
            spinnerMapColorJadid.clear();

            spinnerArrayColorGhadim.add("");
            spinnerMapColorGhadim.put(0,0);
            spinnerArrayColorJadid.add("");
            spinnerMapColorJadid.put(0,0);
            spinnerMapColor.put(0,0);

            for(int i=0;i<polompColors.size();i++){
                spinnerArrayColorGhadim.add(polompColors.get(i).FldName);
                spinnerMapColorGhadim.put(i+1,polompColors.get(i).FldID);
                spinnerArrayColorJadid.add(polompColors.get(i).FldName);
                spinnerMapColorJadid.put(i+1,polompColors.get(i).FldID);
                spinnerMapColor.put(polompColors.get(i).FldID,i+1);
            }
            adapterColorJadid.notifyDataSetChanged();
            adapterColorGhadim.notifyDataSetChanged();
        });
        polompViewModel.getPolompType().observe(getActivity(),polompTypes -> {
            spinnerArrayModelGhadim.clear();
            spinnerMapModelGhadim.clear();
            spinnerArrayModelJadid.clear();
            spinnerMapModelJadid.clear();

            spinnerArrayModelGhadim.add("");
            spinnerMapModelGhadim.put(0,0);
            spinnerArrayModelJadid.add("");
            spinnerMapModelJadid.put(0,0);
            spinnerMapModel.put(0,0);

            for(int i=0;i<polompTypes.size();i++){
                spinnerArrayModelGhadim.add(polompTypes.get(i).PolompTypeName);
                spinnerMapModelGhadim.put(i+1,polompTypes.get(i).PolompTypeID);
                spinnerArrayModelJadid.add(polompTypes.get(i).PolompTypeName);
                spinnerMapModelJadid.put(i+1,polompTypes.get(i).PolompTypeID);
                spinnerMapModel.put(polompTypes.get(i).PolompTypeID,i+1);
            }
            adapterModelJadid.notifyDataSetChanged();
            adapterModelGhadim.notifyDataSetChanged();

        });
        etPolompJadid=rootView.findViewById(R.id.edtJadidShomarePolomp);
        etPolompGhadim=rootView.findViewById(R.id.edtGhadimShomarePolomp);
        if(polompParams!=null){
            polompViewModel.getPolompDataLiveData(polompParams).observe(getActivity(),polompAllInfo -> {
                 if(polompAllInfo!=null) {
                    if(polompAllInfo.PreviousPolomp!=null ){
                        etPolompGhadim.setText(polompAllInfo.PreviousPolomp);
                        }
                    if(polompAllInfo.CurrentPolomp!=null ){
                        etPolompJadid.setText(polompAllInfo.CurrentPolomp);
                    }
                    if(polompAllInfo.CurrentColorID!=null)
                        spnRangPolompJadid.setSelection(spinnerMapColor.get(polompAllInfo.CurrentColorID));
                    if(polompAllInfo.PreviousColorID!=null)
                        spnRangPolompGhadim.setSelection(spinnerMapColor.get(polompAllInfo.PreviousColorID));
                    if(polompAllInfo.PolompTypeID!=null)
                        spnModelPolompJadid.setSelection(spinnerMapModel.get(polompAllInfo.PolompTypeID));
                    if(polompAllInfo.PreviousPolompTypeID!=null)
                        spnModelPolompGhadim.setSelection(spinnerMapModel.get(polompAllInfo.PreviousPolompTypeID));
                    if(polompAllInfo.StatePolomp==0){
                        cbOldNakhana.setChecked(false);
                        cbGhadimNadard.setChecked(false);
                    }

                    if(polompAllInfo.StatePolomp==1){
                        cbGhadimNadard.setChecked(true);
                    }
                     if(polompAllInfo.StatePolomp==2){
                         cbOldNakhana.setChecked(true);
                     }


                }
            });
        }

        btnSave=rootView.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PolompSave();
            }
        });


        return rootView;
    }
    private void adapterInit() {
        spinnerArrayColorJadid.clear();
        spinnerArrayColorGhadim.clear();
        spinnerArrayModelJadid.clear();
        spinnerArrayModelGhadim.clear();

        spinnerArrayColorJadid.add("");
        spinnerArrayColorGhadim.add("");
        spinnerArrayModelJadid.add("");
        spinnerArrayModelGhadim.add("");

        if(polompViewModel.getPolompColor().getValue()!=null) {
            for (PolompColor polompColor : polompViewModel.getPolompColor().getValue()) {
                spinnerArrayColorJadid.add(polompColor.FldName);
                spinnerArrayColorGhadim.add(polompColor.FldName);
            }
        }
        if(polompViewModel.getPolompType().getValue()!=null) {
            for (PolompType polompType : polompViewModel.getPolompType().getValue()) {
                spinnerArrayModelJadid.add(polompType.PolompTypeName);
                spinnerArrayModelGhadim.add(polompType.PolompTypeName);
            }
        }
    }

    private void PolompSave(){


        if(spnRangPolompJadid.getSelectedItemPosition()==0 && etPolompJadid.getText().toString().equals("") &&
                spnRangPolompJadid.getSelectedItemPosition()==0 && spnRangPolompGhadim.getSelectedItemPosition()==0 &&
                etPolompGhadim.getText().toString().equals("") && spnModelPolompGhadim.getSelectedItemPosition()==0 &&
                 !chkOldNakhana && !chkNewNakhana && !chkNadradGhadim && !chkNadaradJadid){
            return;

        }

          PolompAllInfo polompAllInfo=polompViewModel.getPolompData(polompParams);
        if(polompAllInfo==null){

            polompInfo=new PolompInfo();
            polompInfo.AgentID= Integer.valueOf (G.getPref("UserID"));
            polompInfo.ChangeDate = Integer.valueOf (Tarikh.getCurrentShamsidatetimeWithoutSlash().substring(0,8));
            polompInfo.ChangeTime = Integer.valueOf (Tarikh.getTimeWithoutColon());
            polompInfo.ClientID=polompParams.ClientId;
            polompInfo.SendID= G.clientInfo.SendId;

            polompInfo.FollowUpCode=G.clientInfo.FollowUpCode==null?0:G.clientInfo.FollowUpCode;

            polompDtl=new PolompDtl();

            polompDtl.StatePolomp=0;
            if(chkNadradGhadim==true){
                polompDtl.StatePolomp=1;
            }
            if(chkOldNakhana==true){
                polompDtl.StatePolomp=2;
            }
            polompDtl.ReadTypeID=1;
            polompDtl.CurrentColorID=spinnerMapColorJadid.get(spnRangPolompJadid.getSelectedItemPosition())==0?null:spinnerMapColorJadid.get(spnRangPolompJadid.getSelectedItemPosition());
            polompDtl.CurrentPolomp=etPolompJadid.getText().toString();
            polompDtl.PolompID=polompParams.PolompId;
            polompDtl.PolompTypeID=spinnerMapModelJadid.get(spnModelPolompJadid.getSelectedItemPosition())==0?null:spinnerMapModelJadid.get(spnModelPolompJadid.getSelectedItemPosition());
            polompDtl.PreviousColorID=spinnerMapColorGhadim.get(spnRangPolompGhadim.getSelectedItemPosition())==0?null:spinnerMapColorGhadim.get(spnRangPolompGhadim.getSelectedItemPosition());
            polompDtl.PreviousPolomp=etPolompGhadim.getText().toString();
            polompDtl.PreviousPolompTypeID=spinnerMapModelGhadim.get(spnModelPolompGhadim.getSelectedItemPosition())==0?null:spinnerMapModelGhadim.get(spnModelPolompGhadim.getSelectedItemPosition());
            polompDtl.AgentID=Integer.valueOf (G.getPref("UserID"));
            Long  polompInfoId= polompViewModel.insertPolompInfo(polompInfo);
            polompDtl.PolompInfoID=polompInfoId;
            Long polompDtlId=polompViewModel.insertPolompDtl(polompDtl);
            if(polompDtlId!=null){
                Toast.makeText(getActivity(),getResources().getText(R.string.MessageSuccess),Toast.LENGTH_SHORT).show();
            }
        }else{
            polompDtl=new PolompDtl();
            polompDtl.StatePolomp=0;

            if(chkNadradGhadim==true){
                polompDtl.StatePolomp=1;
            }
            if(chkOldNakhana==true){

                polompDtl.StatePolomp=2;
            }

            polompDtl.ReadTypeID=1;
            polompDtl.PolompDtlID=polompAllInfo.PolompDtlID;
            polompDtl.PolompInfoID=Long.valueOf( polompAllInfo.PolompInfoID);
            polompDtl.CurrentColorID=spinnerMapColorJadid.get(spnRangPolompJadid.getSelectedItemPosition())==0?null:spinnerMapColorJadid.get(spnRangPolompJadid.getSelectedItemPosition());
            polompDtl.CurrentPolomp=etPolompJadid.getText().toString();
            polompDtl.PolompID=polompParams.PolompId;
            polompDtl.PolompTypeID=spinnerMapModelJadid.get(spnModelPolompJadid.getSelectedItemPosition())==0?null:spinnerMapModelJadid.get(spnModelPolompJadid.getSelectedItemPosition());
            polompDtl.PreviousColorID=spinnerMapColorGhadim.get(spnRangPolompGhadim.getSelectedItemPosition())==0?null:spinnerMapColorGhadim.get(spnRangPolompGhadim.getSelectedItemPosition());
            polompDtl.PreviousPolomp=etPolompGhadim.getText().toString();
            polompDtl.PreviousPolompTypeID=spinnerMapModelGhadim.get(spnModelPolompGhadim.getSelectedItemPosition())==0?null:spinnerMapModelGhadim.get(spnModelPolompGhadim.getSelectedItemPosition());
            polompDtl.AgentID=Integer.valueOf (G.getPref("UserID"));
            polompViewModel.updatePolompDtl(polompDtl);
            Toast.makeText(getActivity(),getResources().getText(R.string.MessageSuccess),Toast.LENGTH_SHORT).show();


        }
            //Bastan Form Sabt polomp
            G.startFragment(G.fragmentNumStack.pop(), true, null);



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
