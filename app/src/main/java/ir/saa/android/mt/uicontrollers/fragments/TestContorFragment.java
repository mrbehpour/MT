package ir.saa.android.mt.uicontrollers.fragments;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.BundleKeysEnum;
import ir.saa.android.mt.enums.FragmentsEnum;
import ir.saa.android.mt.enums.SharePrefEnum;
import ir.saa.android.mt.repositories.bluetooth.Bluetooth;
import ir.saa.android.mt.uicontrollers.pojos.TestContor.DecimalDigitsInputFilter;
import ir.saa.android.mt.uicontrollers.pojos.TestContor.TestContorParams;
import ir.saa.android.mt.viewmodels.TestContorViewModel;

public class TestContorFragment extends Fragment
{
    TestContorViewModel testContorViewModel;

    TextView edtCTCoeff;
    TextView edtContorConst;
    TextView edtSensorRatio;
    TextView edtRoundNum;

    AlertDialog ad;
    Bundle bundle;

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

        edtCTCoeff = rootView.findViewById(R.id.edtZaribCT);
        edtContorConst = rootView.findViewById(R.id.edtSabeteKontor);
        edtSensorRatio = rootView.findViewById(R.id.edtNesbatKontor);
        edtRoundNum = rootView.findViewById(R.id.edtTedadDor);

        //for Do ragham ashar
        //edtContorConst.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,2)});

        com.github.angads25.toggle.LabeledSwitch switchTestType = rootView.findViewById(R.id.switchTestType);
        com.github.angads25.toggle.LabeledSwitch switchPhase = rootView.findViewById(R.id.switchPhase);
        com.github.angads25.toggle.LabeledSwitch switchTestNum = rootView.findViewById(R.id.switchTestNum);
        com.github.angads25.toggle.LabeledSwitch switchPaulserType = rootView.findViewById(R.id.switchPaulserType);

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

        return rootView;
    }

    private void setDefaultValue(){
        TestContorParams testContorParams;
        if(G.bundleHashMap.containsKey(BundleKeysEnum.TestContorParams)) {
            Bundle b = G.bundleHashMap.get(BundleKeysEnum.TestContorParams);
            testContorParams = (TestContorParams)b.getSerializable(BundleKeysEnum.TestContorParams);

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

    private void connectToModuleDialog(){
        String BluetoothDeviceName = G.getPref(SharePrefEnum.ModuleBluetoothName);
        ad = new AlertDialog.Builder(this.getContext()).create();
        ad.setCancelable(true);
        ad.setTitle(String.format("%s %s", getResources().getText(R.string.ValidationConnect),BluetoothDeviceName));
        ad.setMessage(getResources().getText(R.string.Wait_Connect));
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

        if(edtCTCoeff.getText().toString().equals("")){
            Toast.makeText(G.context,getResources().getText(R.string.ZaribContor_Message),Toast.LENGTH_SHORT).show();
            res=false;
        }

        if(edtContorConst.getText().toString().equals("")){
            Toast.makeText(G.context,getResources().getText(R.string.SabetContor_Message),Toast.LENGTH_SHORT).show();
            res=false;
        }

        if(edtSensorRatio.getText().toString().equals("")){
            Toast.makeText(G.context,getResources().getText(R.string.NesbatSensor_Message),Toast.LENGTH_SHORT).show();
            res=false;
        }
        if(edtCTCoeff.getText().toString().equals("0")){
            Toast.makeText(G.context,getResources().getText(R.string.ZaribContor_Message_Zero),Toast.LENGTH_SHORT).show();
            res=false;
        }

        if(edtContorConst.getText().toString().equals("0")){
            Toast.makeText(G.context,getResources().getText(R.string.SabetContor_Message_Zero),Toast.LENGTH_SHORT).show();
            res=false;
        }

        if(edtSensorRatio.getText().toString().equals("0")){
            Toast.makeText(G.context,getResources().getText(R.string.NesbatSensor_Message_Zero),Toast.LENGTH_SHORT).show();
            res=false;
        }

        if(edtRoundNum.getText().toString().equals("")){
            Toast.makeText(G.context,getResources().getText(R.string.TedadDore_Message),Toast.LENGTH_SHORT).show();
            res=false;
        }
        if(edtRoundNum.getText().toString().equals("0")){
            Toast.makeText(G.context,getResources().getText(R.string.TedadDore_Message_Zero),Toast.LENGTH_SHORT).show();
            res=false;
        }

        return  res;
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
