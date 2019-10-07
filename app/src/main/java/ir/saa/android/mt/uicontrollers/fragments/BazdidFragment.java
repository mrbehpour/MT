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


import android.text.InputFilter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.gson.Gson;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import ir.saa.android.mt.R;
import ir.saa.android.mt.adapters.bazdid.BazdidAdapter;
import ir.saa.android.mt.adapters.bazdid.ClientItem;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.components.MyCheckList;
import ir.saa.android.mt.components.MyCheckListItem;
import ir.saa.android.mt.components.MyDialog;
import ir.saa.android.mt.components.PersianCalendar;
import ir.saa.android.mt.enums.SharePrefEnum;
import ir.saa.android.mt.model.entities.AddedClient;
import ir.saa.android.mt.model.entities.AddedClientInput;
import ir.saa.android.mt.model.entities.Client;
import ir.saa.android.mt.model.entities.ClientWithAction;
import ir.saa.android.mt.model.entities.MasterGroupDetail;
import ir.saa.android.mt.model.entities.Setting;
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
    Boolean isBlock=false;
    FloatingActionButton floatingBtnAdd;
    MutableLiveData<List<Client>> clientsLiveData;
    MyDialog myDialog;
    EditText etEshterak;
    EditText etFileId;
    EditText etRamz;
    EditText etRamzRayaneh;
    EditText etSerialContor;
    EditText etShenasai;
    AddedClientViewModel addedClientViewModel;
    MyCheckList myCheckList;
    ProgressDialog progressDialog;
    MyDialog myDialogForClient;
    private Handler handler = new Handler();
    HashMap<Integer,Integer> hashMapField=new HashMap<Integer,Integer>();
    HashMap<Integer,String> hashMapFarsiField=new HashMap<Integer, String>();
    String AddedClientForcedField1="0";
    String AddedClientForcedField2="0";
    String AddedClientForcedField3="0";
    String AddedClientForcedLength1="0";
    String AddedClientForcedLength2="0";
    String AddedClientForcedLength3="0";
    String AddedClientUniqueField1="0";
    String AddedClientUniqueField2="0";
    String AddedClientUniqueField3="0";
    String AddedClientUniqueLength1="0";
    String AddedClientUniqueLength2="0";
    String AddedClientUniqueLength3="0";
    String UniqeFieldOrder="";
    String UniqueField="";
    EditText editText;
    Spinner spnGroup;
    List<String> spinnerArrayGroup;
    ArrayAdapter<String> adapterGroup;
    HashMap<Integer,Integer> spinnerMapGroup = new HashMap<Integer, Integer>();
    long SubScript=-1;
    long FileId=-1;
    long ContorNum=-1;
    String CustId="-1";
    long ClientPass=-1;
    long Param5=-1;

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
        spinnerArrayGroup=new ArrayList<>();


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
        bazdidViewModel.getSettingByKey("AddedClientForcedField1").
                observe((LifecycleOwner) getContext(), new Observer<Setting>() {
                    @Override
                    public void onChanged(@Nullable Setting setting) {
                        if(setting!=null){
                            AddedClientForcedField1 = setting.SettingValue;
                        }
                    }
                });
        bazdidViewModel.getSettingByKey("AddedClientForcedField2").
                observe((LifecycleOwner) getContext(), new Observer<Setting>() {
                    @Override
                    public void onChanged(@Nullable Setting setting) {
                        if(setting!=null) {
                            AddedClientForcedField2 = setting.SettingValue;
                        }
                    }
                });
        bazdidViewModel.getSettingByKey("AddedClientForcedField3").
                observe((LifecycleOwner) getContext(), new Observer<Setting>() {
                    @Override
                    public void onChanged(@Nullable Setting setting) {
                        if(setting!=null) {
                            AddedClientForcedField3 = setting.SettingValue;
                        }
                    }
                });
        bazdidViewModel.getSettingByKey("AddedClientForcedLength1").
                observe((LifecycleOwner) getContext(), new Observer<Setting>() {
                    @Override
                    public void onChanged(@Nullable Setting setting) {
                        if(setting!=null) {
                            AddedClientForcedLength1 = setting.SettingValue;
                        }
                    }
                });
        bazdidViewModel.getSettingByKey("AddedClientForcedLength2").
                observe((LifecycleOwner) getContext(), new Observer<Setting>() {
                    @Override
                    public void onChanged(@Nullable Setting setting) {
                        if(setting!=null) {
                            AddedClientForcedLength2 = setting.SettingValue;
                        }
                    }
                });
        bazdidViewModel.getSettingByKey("AddedClientForcedLength3").
                observe((LifecycleOwner) getContext(), new Observer<Setting>() {
                    @Override
                    public void onChanged(@Nullable Setting setting) {
                        if(setting!=null) {
                            AddedClientForcedLength3 = setting.SettingValue;
                        }
                    }
                });
        bazdidViewModel.getSettingByKey("AddedClientUniqueField1").
                observe((LifecycleOwner) getContext(), new Observer<Setting>() {
                    @Override
                    public void onChanged(@Nullable Setting setting) {
                        if(setting!=null) {
                            AddedClientUniqueField1 = setting.SettingValue;
                        }
                    }
                });
        bazdidViewModel.getSettingByKey("AddedClientUniqueField2").
                observe((LifecycleOwner) getContext(), new Observer<Setting>() {
                    @Override
                    public void onChanged(@Nullable Setting setting) {
                        if(setting!=null) {
                            AddedClientUniqueField2 = setting.SettingValue;
                        }
                    }
                });
        bazdidViewModel.getSettingByKey("AddedClientUniqueField3").
                observe((LifecycleOwner) getContext(), new Observer<Setting>() {
                    @Override
                    public void onChanged(@Nullable Setting setting) {
                        if(setting!=null) {
                            AddedClientUniqueField3 = setting.SettingValue;
                        }
                    }
                });
        bazdidViewModel.getSettingByKey("AddedClientUniqueLength1").
                observe((LifecycleOwner) getContext(), new Observer<Setting>() {
                    @Override
                    public void onChanged(@Nullable Setting setting) {
                        if(setting!=null) {
                            AddedClientUniqueLength1 = setting.SettingValue;
                        }
                    }
                });
        bazdidViewModel.getSettingByKey("AddedClientUniqueLength2").
                observe((LifecycleOwner) getContext(), new Observer<Setting>() {
                    @Override
                    public void onChanged(@Nullable Setting setting) {
                        if(setting!=null) {
                            AddedClientUniqueLength2 = setting.SettingValue;
                        }
                    }
                });
        bazdidViewModel.getSettingByKey("AddedClientUniqueLength3").
                observe((LifecycleOwner) getContext(), new Observer<Setting>() {
                    @Override
                    public void onChanged(@Nullable Setting setting) {
                        if(setting!=null) {
                            AddedClientUniqueLength3 = setting.SettingValue;
                        }
                    }
                });
        bazdidViewModel.getSettingByKey("UniqeFieldOrder").
                observe((LifecycleOwner) getContext(), new Observer<Setting>() {
                    @Override
                    public void onChanged(@Nullable Setting setting) {
                        if(setting!=null) {
                            UniqeFieldOrder = setting.SettingValue;
                        }
                    }
                });
        bazdidViewModel.getSettingByKey("UniqueField").
                observe((LifecycleOwner) getContext(), new Observer<Setting>() {
                    @Override
                    public void onChanged(@Nullable Setting setting) {
                        if(setting!=null) {
                            UniqueField = setting.SettingValue;
                        }
                    }
                });

        //floatingBtnAdd.setVisibility(View.INVISIBLE);
        floatingBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myDialogForClient=new MyDialog(getContext());
                myDialog=new MyDialog(getContext());
                myDialog.setTitle((String) getResources().getText(R.string.AddClientTitle));
                myDialog.addContentXmlScorllView(R.layout.body_addclient);
                etEshterak = (EditText) myDialog.getDialog().findViewById(R.id.etEshterak);
                etFileId = (EditText) myDialog.getDialog().findViewById(R.id.etFileId);
                etRamz = (EditText) myDialog.getDialog().findViewById(R.id.etRamz);
                etSerialContor = (EditText) myDialog.getDialog().findViewById(R.id.etSerialContor);
                etShenasai = (EditText) myDialog.getDialog().findViewById(R.id.etShenasai);
                etRamzRayaneh=(EditText)myDialog.getDialog().findViewById(R.id.etRamzRayaneh);
                spnGroup=(Spinner)myDialog.getDialog().findViewById(R.id.spnGroup);

                bazdidViewModel.getMasterGroupDetail().observe(getActivity(), new Observer<List<MasterGroupDetail>>() {
                    @Override
                    public void onChanged(@Nullable List<MasterGroupDetail> masterGroupDetails) {
                        spinnerArrayGroup.clear();
                        spinnerMapGroup.clear();
                        for (int i=0 ; i<masterGroupDetails.size();i++) {
                            spinnerArrayGroup.add(masterGroupDetails.get(i).ChoiceValue);
                            spinnerMapGroup.put(i,masterGroupDetails.get(i).ChoiceID);
                        }

                        adapterGroup=new ArrayAdapter<>(getActivity(), R.layout.al_polomp_save_spinner_item,spinnerArrayGroup);
                        adapterGroup.setDropDownViewResource(R.layout.al_polomp_save_spinner_dropdown);
                        spnGroup.setAdapter(adapterGroup);
                    }
                });

                hashMapField.put(1, R.id.etEshterak);
                hashMapField.put(2, R.id.etFileId);
                hashMapField.put(3, R.id.etSerialContor);
                hashMapField.put(4, R.id.etShenasai);
                hashMapField.put(5, R.id.etRamz);
                hashMapField.put(6, R.id.etRamzRayaneh);

                hashMapFarsiField.put(1, (String) getText(R.string.Eshterak));
                hashMapFarsiField.put(2, (String) getText(R.string.Parvande));
                hashMapFarsiField.put(3, (String) getText(R.string.SerialNumber));
                hashMapFarsiField.put(4, (String) getText(R.string.Shenas));
                hashMapFarsiField.put(5, (String) getText(R.string.Ramz));
                hashMapFarsiField.put(6, (String) getText(R.string.RamzRayaneh));
                if(AddedClientForcedField1.equals("0")==false){
                    InputFilter[] filters = new InputFilter[1];
                    if(AddedClientForcedLength1.equals("0")==false) {
                        filters[0] = new InputFilter.LengthFilter(Integer.valueOf(AddedClientForcedLength1));
                        ((EditText)myDialog.getDialog().findViewById( hashMapField.get(Integer.valueOf(AddedClientForcedField1)))).setFilters(filters);
                    }

                }
                if(AddedClientForcedField2.equals("0")==false){
                    InputFilter[] filters = new InputFilter[1];
                    if(AddedClientForcedLength2.equals("0")==false) {
                        filters[0] = new InputFilter.LengthFilter(Integer.valueOf(AddedClientForcedLength2));
                        ((EditText)myDialog.getDialog().findViewById( hashMapField.get(Integer.valueOf(AddedClientForcedField2)))).setFilters(filters);
                    }

                }
                if(AddedClientForcedField3.equals("0")==false){
                    InputFilter[] filters = new InputFilter[1];
                    if(AddedClientForcedLength3.equals("0")==false) {
                        filters[0] = new InputFilter.LengthFilter(Integer.valueOf(AddedClientForcedLength3));
                        ((EditText)myDialog.getDialog().findViewById( hashMapField.get(Integer.valueOf(AddedClientForcedField3)))).setFilters(filters);
                    }

                }
                if(AddedClientUniqueField1.equals("0")==false){
                    InputFilter[] filters = new InputFilter[1];
                    if(AddedClientUniqueLength1.equals("0")==false) {
                        filters[0] = new InputFilter.LengthFilter(Integer.valueOf(AddedClientUniqueLength1));
                        ((EditText)myDialog.getDialog().findViewById( hashMapField.get(Integer.valueOf(AddedClientUniqueField1)))).setFilters(filters);

                    }
                }
                if(AddedClientUniqueField2.equals("0")==false){
                    InputFilter[] filters = new InputFilter[1];
                    if(AddedClientUniqueLength2.equals("0")==false) {
                        filters[0] = new InputFilter.LengthFilter(Integer.valueOf(AddedClientUniqueLength2));
                        ((EditText)myDialog.getDialog().findViewById( hashMapField.get(Integer.valueOf(AddedClientUniqueField2)))).setFilters(filters);

                    }
                }
                if(AddedClientUniqueField3.equals("0")==false){
                    InputFilter[] filters = new InputFilter[1];
                    if(AddedClientUniqueLength3.equals("0")==false) {
                        filters[0] = new InputFilter.LengthFilter(Integer.valueOf(AddedClientUniqueLength3));
                        ((EditText)myDialog.getDialog().findViewById( hashMapField.get(Integer.valueOf(AddedClientUniqueField3)))).setFilters(filters);

                    }
                }
                myDialog.addButton((String) getResources().getText(R.string.Cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                myDialog.dismiss();
                            }
                        }).addButton((String) getResources().getText(R.string.Ok), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(AddedClientUniqueField1.equals("0")==false){
                                    editText=(EditText)myDialog.getDialog().findViewById( hashMapField.get(Integer.valueOf(AddedClientUniqueField1)));
                                    if(editText.getText().toString().equals("")){
                                        Toast fancyToast = FancyToast.makeText(G.context, String.format((String) getText(R.string.FillForceField_msg)
                                                ,hashMapFarsiField.get(Integer.valueOf(AddedClientUniqueField1)))
                                                , FancyToast.LENGTH_SHORT, FancyToast.INFO, false);
                                        fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                        fancyToast.show();
                                        return;
                                    }else{
                                        switch(AddedClientUniqueField1) {
                                            case "1":
                                                SubScript=etEshterak.getText().toString().equals("") ? -1 :
                                                        Long.parseLong(etEshterak.getText().toString());
                                                break;
                                            case "2":
                                                FileId=etFileId.getText().toString().equals("") ? -1 :
                                                        Long.parseLong(etFileId.getText().toString());
                                                break;
                                            case "3":
                                                ContorNum=etSerialContor.getText().toString().equals("") ? -1 :
                                                        Long.parseLong(etSerialContor.getText().toString());
                                                break;
                                            case "4":
                                                CustId=etShenasai.getText().toString().equals("") ? "-1" :
                                                        etShenasai.getText().toString();
                                                break;
                                            case "5":
                                                ClientPass=etRamz.getText().toString().equals("") ? -1 :
                                                        Long.parseLong(etRamz.getText().toString());
                                                break;
                                            case "6":
                                                Param5=etRamzRayaneh.getText().toString().equals("") ? -1 :
                                                        Long.parseLong(etRamzRayaneh.getText().toString());
                                                break;
                                        }
                                    }
                                }
                                if(AddedClientUniqueField2.equals("0")==false){
                                    if(((EditText)myDialog.getDialog().findViewById( hashMapField.get(Integer.valueOf(AddedClientUniqueField2))))
                                            .getText().toString().equals("")
                                    ){
                                        Toast fancyToast = FancyToast.makeText(G.context, String.format((String) getText(R.string.FillForceField_msg)
                                                ,hashMapFarsiField.get(Integer.valueOf(AddedClientUniqueField2)))
                                                , FancyToast.LENGTH_SHORT, FancyToast.INFO, false);
                                        fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                        fancyToast.show();
                                        return;
                                    }else{
                                        switch(AddedClientUniqueField2) {
                                            case "1":
                                                SubScript=etEshterak.getText().toString().equals("") ? -1 :
                                                        Long.parseLong(etEshterak.getText().toString());
                                                break;
                                            case "2":
                                                FileId=etFileId.getText().toString().equals("") ? -1 :
                                                        Long.parseLong(etFileId.getText().toString());
                                                break;
                                            case "3":
                                                ContorNum=etSerialContor.getText().toString().equals("") ? -1 :
                                                        Long.parseLong(etSerialContor.getText().toString());
                                                break;
                                            case "4":
                                                CustId=etShenasai.getText().toString().equals("") ? "-1" :
                                                        etShenasai.getText().toString();
                                                break;
                                            case "5":
                                                ClientPass=etRamz.getText().toString().equals("") ? -1 :
                                                        Long.parseLong(etRamz.getText().toString());
                                                break;
                                            case "6":
                                                Param5=etRamzRayaneh.getText().toString().equals("") ? -1 :
                                                        Long.parseLong(etRamzRayaneh.getText().toString());
                                                break;
                                        }
                                    }
                                }
                                if(AddedClientUniqueField3.equals("0")==false){
                                    if(((EditText)myDialog.getDialog().findViewById( hashMapField.get(Integer.valueOf(AddedClientUniqueField3))))
                                            .getText().toString().equals("")
                                    ){
                                        Toast fancyToast = FancyToast.makeText(G.context, String.format((String) getText(R.string.FillForceField_msg)
                                                ,hashMapFarsiField.get(Integer.valueOf(AddedClientUniqueField3)))
                                                , FancyToast.LENGTH_SHORT, FancyToast.INFO, false);
                                        fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                        fancyToast.show();
                                        return;
                                    }else {
                                        switch(AddedClientUniqueField3) {
                                            case "1":
                                                SubScript=etEshterak.getText().toString().equals("") ? -1 :
                                                        Long.parseLong(etEshterak.getText().toString());
                                                break;
                                            case "2":
                                                FileId=etFileId.getText().toString().equals("") ? -1 :
                                                        Long.parseLong(etFileId.getText().toString());
                                                break;
                                            case "3":
                                                ContorNum=etSerialContor.getText().toString().equals("") ? -1 :
                                                        Long.parseLong(etSerialContor.getText().toString());
                                                break;
                                            case "4":
                                                CustId=etShenasai.getText().toString().equals("") ? "-1" :
                                                        etShenasai.getText().toString();
                                                break;
                                            case "5":
                                                ClientPass=etRamz.getText().toString().equals("") ? -1 :
                                                        Long.parseLong(etRamz.getText().toString());
                                                break;
                                            case "6":
                                                Param5=etRamzRayaneh.getText().toString().equals("") ? -1 :
                                                        Long.parseLong(etRamzRayaneh.getText().toString());
                                                break;
                                        }
                                    }
                                }
                                if(AddedClientForcedField1.equals("0")==false){
                                    if(((EditText)myDialog.getDialog().findViewById( hashMapField.get(Integer.valueOf(AddedClientForcedField1))))
                                            .getText().toString().equals("")
                                    ){
                                        Toast fancyToast = FancyToast.makeText(G.context, String.format((String) getText(R.string.FillForceField_msg)
                                                ,hashMapFarsiField.get(Integer.valueOf(AddedClientForcedField1)))
                                                , FancyToast.LENGTH_SHORT, FancyToast.INFO, false);
                                        fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                        fancyToast.show();
                                        return;
                                    }
                                }
                                if(AddedClientForcedField2.equals("0")==false){
                                    if(((EditText)myDialog.getDialog().findViewById( hashMapField.get(Integer.valueOf(AddedClientForcedField2))))
                                            .getText().toString().equals("")
                                    ){
                                        Toast fancyToast = FancyToast.makeText(G.context, String.format((String) getText(R.string.FillForceField_msg)
                                                ,hashMapFarsiField.get(Integer.valueOf(AddedClientForcedField2)))
                                                , FancyToast.LENGTH_SHORT, FancyToast.INFO, false);
                                        fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                        fancyToast.show();
                                        return;
                                    }
                                }
                                if(AddedClientForcedField3.equals("0")==false){
                                    if(((EditText)myDialog.getDialog().findViewById( hashMapField.get(Integer.valueOf(AddedClientForcedField3))))
                                            .getText().toString().equals("")
                                    ){
                                        Toast fancyToast = FancyToast.makeText(G.context, String.format((String) getText(R.string.FillForceField_msg)
                                                ,hashMapFarsiField.get(Integer.valueOf(AddedClientForcedField3)))
                                                , FancyToast.LENGTH_SHORT, FancyToast.INFO, false);
                                        fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                        fancyToast.show();
                                        return;
                                    }
                                }
                                AddedClientInput addedClientInput = new AddedClientInput();

                                if (G.isNetWorkConnection()) {
                                    addedClientInput.clientPass = etRamz.getText().toString().equals("") ? null : Long.parseLong(etRamz.getText().toString());
                                    addedClientInput.custId = etShenasai.getText().toString();
                                    addedClientInput.fileId = etFileId.getText().toString().equals("") ? null :
                                            Long.valueOf(etFileId.getText().toString());
                                    addedClientInput.subScript = etEshterak.getText().toString().equals("") ? null :
                                            Long.parseLong(etEshterak.getText().toString());
                                    addedClientInput.meterNumActive = etSerialContor.getText().toString().equals("") ? null :
                                            Long.parseLong(etSerialContor.getText().toString());
                                    long clientId=0;
                                    //مشخص کردن فیلد یونیک
                                    switch(UniqueField) {
                                        case "1":
                                            clientId=etEshterak.getText().toString().equals("") ? null :
                                                    Long.parseLong(etEshterak.getText().toString());
                                            break;
                                        case "2":
                                           clientId=etFileId.getText().toString().equals("") ? 0 :
                                                    Long.parseLong(etFileId.getText().toString());
                                            break;
                                        case "3":
                                            clientId=etSerialContor.getText().toString().equals("") ? 0 :
                                                    Long.parseLong(etSerialContor.getText().toString());
                                            break;
                                        case "4":
                                            clientId=etShenasai.getText().toString().equals("") ? 0 :
                                                    Long.parseLong(etShenasai.getText().toString());
                                            break;
                                        case "5":
                                            clientId=etRamz.getText().toString().equals("") ? 0 :
                                                    Long.parseLong(etRamz.getText().toString());
                                            break;
                                        case "6":
                                            clientId=etRamzRayaneh.getText().toString().equals("") ? 0 :
                                                    Long.parseLong(etRamzRayaneh.getText().toString());
                                            break;
                                    }
                                    Client client=new Client();
                                    //چک کردن مشترک بر اساس فیلد یونیک اصلی و یونیک های تعیین شده از سوی سایت
                                    if(clientId ==0){
                                        client=bazdidViewModel.getClientByUniq(SubScript,FileId
                                                ,ContorNum,CustId, ClientPass,Param5 );
                                    }else {
                                        client = bazdidViewModel.getClientByClientId(clientId);
                                    }
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
                                                myDialog.dismiss();
                                                //Client client1withoutClientid=new Client();
                                                AddedClient addedClient=new AddedClient();
                                                addedClient.AddDate=Integer.valueOf(PersianCalendar.getCurrentSimpleShamsiDate());
                                                addedClient.AddTime=Integer.valueOf(PersianCalendar.getCurrentSimpleTime());

                                                Client client1withoutClientid=new Client();
                                                client1withoutClientid.ClientPass = etRamz.getText().toString().equals("") ? null : Long.parseLong(etRamz.getText().toString());
                                                client1withoutClientid.MasterGroupDtlID=spinnerMapGroup.get(
                                                        Integer.valueOf(spnGroup.getSelectedItemPosition()));
                                                client1withoutClientid.CustId = etShenasai.getText().toString();
                                                client1withoutClientid.FileID = etFileId.getText().toString().equals("") ? null :
                                                        Long.valueOf(etFileId.getText().toString());
                                                client1withoutClientid.SubScript = etEshterak.getText().toString().equals("") ? null :
                                                        Long.parseLong(etEshterak.getText().toString());
                                                client1withoutClientid.MeterNumActive = etSerialContor.getText().toString().equals("") ? null :
                                                        Long.parseLong(etSerialContor.getText().toString());
                                                client1withoutClientid.Name= (String) getResources().getText(R.string.AddClientTitle);

                                                addedClient.FileID=client1withoutClientid.FileID;
                                                addedClient.ClientPass=client1withoutClientid.ClientPass;
                                                addedClient.CustID=client1withoutClientid.CustId;
                                                addedClient.Subscript=client1withoutClientid.SubScript;
                                                addedClient.MeterNumActive=client1withoutClientid.MeterNumActive;
                                                addedClient.FollowUpCode=G.clientInfo.FollowUpCode;

                                                client1withoutClientid.RegionID=Integer.valueOf(G.getPref(SharePrefEnum.RegionId));

                                                long clientId = 0;
                                                switch(UniqueField) {
                                                    case "1":
                                                        clientId=etEshterak.getText().toString().equals("") ? null :
                                                                Long.parseLong(etEshterak.getText().toString());
                                                        break;
                                                    case "2":
                                                        clientId=etFileId.getText().toString().equals("") ? null :
                                                                Long.parseLong(etFileId.getText().toString());
                                                        break;
                                                    case "3":
                                                        clientId=etSerialContor.getText().toString().equals("") ? null :
                                                                Long.parseLong(etSerialContor.getText().toString());
                                                        break;
                                                    case "4":
                                                        clientId=etShenasai.getText().toString().equals("") ? null :
                                                                Long.parseLong(etShenasai.getText().toString());
                                                        break;
                                                    case "5":
                                                        clientId=etRamz.getText().toString().equals("") ? null :
                                                                Long.parseLong(etRamz.getText().toString());
                                                        break;
                                                    case "6":
                                                        clientId=etRamzRayaneh.getText().toString().equals("") ? null :
                                                                Long.parseLong(etRamzRayaneh.getText().toString());
                                                        break;
                                                }
                                                client1withoutClientid.ClientID=clientId;
                                                addedClient.ClientID=clientId;

                                                Client client1=bazdidViewModel.getClientByClientId(client1withoutClientid.ClientID);
                                                bazdidViewModel.insertAddedClient(addedClient);
                                                if(client1==null) {
                                                    bazdidViewModel.insertClient(client1withoutClientid);
                                                    setUpRecyclerView(rootView,0);
                                                    Toast fancyToast = FancyToast.makeText(G.context, (String) getResources().getText(R.string.SaveOperationSuccess_msg), FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false);
                                                    fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                                    fancyToast.show();
                                                    myDialog.dismiss();
                                                   // adapter.notifyDataSetChanged();
                                                }
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
                                    client.MasterGroupDtlID=spinnerMapGroup.get(
                                            Integer.valueOf(spnGroup.getSelectedItemPosition()));
                                    client.ClientPass = etRamz.getText().toString().equals("") ? null : Long.parseLong(etRamz.getText().toString());
                                    client.CustId = etShenasai.getText().toString();
                                    client.FileID = etFileId.getText().toString().equals("") ? null :
                                            Long.valueOf(etFileId.getText().toString());
                                    client.SubScript = etEshterak.getText().toString().equals("") ? null :
                                            Long.parseLong(etEshterak.getText().toString());
                                    client.MeterNumActive = etSerialContor.getText().toString().equals("") ? null :
                                            Long.parseLong(etSerialContor.getText().toString());
                                   client.Name= (String) getResources().getText(R.string.AddClientTitle);

                                   client.RegionID=Integer.valueOf(G.getPref(SharePrefEnum.RegionId));
                                    long clientId = 0;
                                    switch(UniqueField) {
                                        case "1":
                                            clientId=etEshterak.getText().toString().equals("") ? null :
                                                    Long.parseLong(etEshterak.getText().toString());
                                            break;
                                        case "2":
                                            clientId=etFileId.getText().toString().equals("") ? null :
                                                    Long.parseLong(etFileId.getText().toString());
                                            break;
                                        case "3":
                                            clientId=etSerialContor.getText().toString().equals("") ? null :
                                                    Long.parseLong(etSerialContor.getText().toString());
                                            break;
                                        case "4":
                                            clientId=etShenasai.getText().toString().equals("") ? null :
                                                    Long.parseLong(etShenasai.getText().toString());
                                            break;
                                        case "5":
                                            clientId=etRamz.getText().toString().equals("") ? null :
                                                    Long.parseLong(etRamz.getText().toString());
                                            break;
                                        case "6":
                                            clientId=etRamzRayaneh.getText().toString().equals("") ? null :
                                                    Long.parseLong(etRamzRayaneh.getText().toString());
                                            break;
                                    }
                                    client.ClientID=clientId;
                                   Client client1=bazdidViewModel.getClientByClientId(client.ClientID);

                                   if(client1==null) {
                                       bazdidViewModel.insertClient(client);
                                       setUpRecyclerView(rootView,0);
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
        recyclerView.setScrollBarSize(10);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

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
                                       isBlock=client.isBlock!=0?true:false;
                                       RowId+=1;
                                       clientItems.add(new ClientItem(client.ClientID,client.Name,client.Address,UniqField ,String.valueOf(client.SubScript),
                                               R.drawable.account,client.SendId,client.MasterGroupDtlID,isTest,isPolomp,isBazrasi,isTariff,isBlock,
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
