package ir.saa.android.mt.uicontrollers.fragments;

import android.app.Application;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.FragmentsEnum;
import ir.saa.android.mt.uicontrollers.pojos.TestContor.TestContorParams;
import ir.saa.android.mt.viewmodels.BaseInfoViewModel;
import ir.saa.android.mt.viewmodels.TestContorViewModel;
import ir.saa.android.mt.viewmodels.TestEnergyViewModel;

public class TestContorFragment extends Fragment
{
    BaseInfoViewModel baseInfoViewModel = null;
    TestContorViewModel testContorViewModel;

    TextView edtCTCoeff;
    TextView edtContorConst;
    TextView edtContorCoff;
    TextView edtRoundNum;

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
        testContorViewModel=ViewModelProviders.of(this).get(TestContorViewModel.class);

        edtCTCoeff = rootView.findViewById(R.id.edtZaribCT);
        edtContorConst = rootView.findViewById(R.id.edtSabeteKontor);
        edtContorCoff = rootView.findViewById(R.id.edtNesbatKontor);
        edtRoundNum = rootView.findViewById(R.id.edtTedadDor);
        com.github.angads25.toggle.LabeledSwitch switchTestType = rootView.findViewById(R.id.switchTestType);
        com.github.angads25.toggle.LabeledSwitch switchPhase = rootView.findViewById(R.id.switchPhase);
        com.github.angads25.toggle.LabeledSwitch switchTestNum = rootView.findViewById(R.id.switchTestNum);

        edtCTCoeff.setText("1");
        edtContorConst.setText("2000");
        edtContorCoff.setText("1");
        edtRoundNum.setText("1");

        Button btnTaeed=rootView.findViewById(R.id.btnTaeed);
        btnTaeed.setOnClickListener(view -> {
            if(validateParams()) {
                TestContorParams testContorParams = new TestContorParams(switchTestType.isOn(),
                        switchPhase.isOn(),
                        switchTestNum.isOn(),
                        Integer.parseInt(String.valueOf(edtCTCoeff.getText())),
                        Integer.parseInt(String.valueOf(edtContorConst.getText())),
                        Integer.parseInt(String.valueOf(edtContorCoff.getText())),
//                    Integer.parseInt(String.valueOf(edtRoundNum.getText()))
                        1);

                Bundle bundle = new Bundle();
                bundle.putSerializable("testContorParams", testContorParams);

                G.startFragment(FragmentsEnum.TestEnergyFragment, false, bundle);
            }
        });

        return rootView;
    }

    private boolean validateParams(){
        boolean res=true;

        if(edtCTCoeff.getText().toString().equals("")){
            Toast.makeText(G.context,"ضریب سی تی را لطفا وارد کنید",Toast.LENGTH_SHORT).show();
            res=false;
        }

        if(edtContorConst.getText().toString().equals("")){
            Toast.makeText(G.context,"ثابت کنتور را لطفا وارد کنید",Toast.LENGTH_SHORT).show();
            res=false;
        }

        if(edtContorCoff.getText().toString().equals("")){
            Toast.makeText(G.context,"ضریب کنتور را لطفا وارد کنید",Toast.LENGTH_SHORT).show();
            res=false;
        }

//        if(edtRoundNum.getText().toString().equals("")){
//            Toast.makeText(G.context,"تعداد دور را لطفا وارد کنید",Toast.LENGTH_SHORT).show();
//            res=false;
//        }

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
