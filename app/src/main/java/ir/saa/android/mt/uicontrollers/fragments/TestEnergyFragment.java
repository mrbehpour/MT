package ir.saa.android.mt.uicontrollers.fragments;

import android.animation.ArgbEvaluator;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.BundleKeysEnum;
import ir.saa.android.mt.enums.FragmentsEnum;
import ir.saa.android.mt.repositories.metertester.ElectericalParams;
import ir.saa.android.mt.repositories.metertester.EnergiesState;
import ir.saa.android.mt.uicontrollers.pojos.TestContor.TestContorParams;
import ir.saa.android.mt.viewmodels.TestEnergyViewModel;

public class TestEnergyFragment extends Fragment
{
    TestEnergyViewModel testEnergyViewModel = null;
    TestContorParams testContorParams;

    LinearLayout lyClampType;
    RadioButton radioClamp1,radioClamp2;
    TextView tvVR,tvVS,tvVT;
    TextView tvIR,tvIS,tvIT;
    TextView tvPFR,tvPFS,tvPFT;
    TextView tvMinV,tvMaxV,tvMinI,tvMaxI,tvMinPF,tvMaxPF;


    public TestEnergyFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_test_energy, container, false);
        testEnergyViewModel = ViewModelProviders.of(this).get(TestEnergyViewModel.class);
        initObjects(rootView);
        Bundle args = getArguments();
        if (args != null) {
            testContorParams = (TestContorParams)args.getSerializable(BundleKeysEnum.TestContorParams);
        }

        lyClampType = rootView.findViewById(R.id.lyClampType);
        radioClamp1 = rootView.findViewById(R.id.radioBtnClamp1);
        radioClamp2 = rootView.findViewById(R.id.radioBtnClamp2);
        lyClampType.setVisibility(View.GONE);

        LinearLayout llLeft = rootView.findViewById(R.id.llLeft);
        LinearLayout llCenter = rootView.findViewById(R.id.llCenter);
        LinearLayout llRight = rootView.findViewById(R.id.llRight);
        TextView tvLeft = rootView.findViewById(R.id.tvLeft);
        TextView tvCenter = rootView.findViewById(R.id.tvCenter);
        TextView tvRight = rootView.findViewById(R.id.tvRight);
        Button btnStartTest = rootView.findViewById(R.id.btnStartTest);
        Button btnSetClamp = rootView.findViewById(R.id.btnSetClamp);


        tvMinV = rootView.findViewById(R.id.tvMin_V);
        tvMinV.setText("130");
        tvMaxV = rootView.findViewById(R.id.tvMax_V);
        tvMaxV.setText("290");
        tvMinI = rootView.findViewById(R.id.tvMin_I);
        tvMinI.setText("0.1");
        tvMaxI = rootView.findViewById(R.id.tvMax_I);
        tvMaxI.setText("1100");
        tvMinPF = rootView.findViewById(R.id.tvMin_PF);
        tvMinPF.setText("0.05");
        tvMaxPF = rootView.findViewById(R.id.tvMax_PF);


        testEnergyViewModel.energiesStateMutableLiveData.observe(this, new Observer<EnergiesState>() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onChanged(@Nullable EnergiesState energiesState) {
                        if((energiesState.energy1AState && energiesState.energy2AState && energiesState.energy3AState) ||
                                (!energiesState.energy1AState && !energiesState.energy2AState && !energiesState.energy3AState)   ){
                            llLeft.setBackground(G.context.getDrawable(R.drawable.shape_circle_green));
                            llCenter.setBackground(G.context.getDrawable(R.drawable.shape_circle_green));
                            llRight.setBackground(G.context.getDrawable(R.drawable.shape_circle_green));
                        }
                        else{
                            llLeft.setBackground(G.context.getDrawable(R.drawable.shape_circle_red));
                            llCenter.setBackground(G.context.getDrawable(R.drawable.shape_circle_red));
                            llRight.setBackground(G.context.getDrawable(R.drawable.shape_circle_red));
                        }

                        tvLeft.setText(energiesState.energy1AState?"+":"-");
                        tvCenter.setText(energiesState.energy2AState?"+":"-");
                        tvRight.setText(energiesState.energy3AState?"+":"-");
                    }
                }
        );

        testEnergyViewModel.sanjeshResultMutableLiveData.observe(this, new Observer<List<ElectericalParams>>() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onChanged(@Nullable List<ElectericalParams> sanjeshResult) {
                        showSanjeshResult(sanjeshResult);
                    }
                }
        );

        testEnergyViewModel.checkClampMutableLiveData.observe(this, new Observer<int[]>() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onChanged(@Nullable int[] clampCheckResult) {
//                        if(clampCheckResult[0]==2) {
//                            lyClampType.setVisibility(View.VISIBLE);
//                            btnStartTest.setEnabled(false);
//                            selectClampTypeDialog(clampCheckResult);
//                        }else{
//                            lyClampType.setVisibility(View.GONE);
//                            btnStartTest.setEnabled(true);
//                            testEnergyViewModel.timerCheckStart(2000);
//                        }
                    }
                }
        );

        //testEnergyViewModel.CheckClampType();

        btnStartTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(testContorParams==null) {
                    Bundle b = G.bundleHashMap.get(BundleKeysEnum.TestContorParams);
                    testContorParams = (TestContorParams)b.getSerializable(BundleKeysEnum.TestContorParams);
                }

                testEnergyViewModel.setTestContorParams(testContorParams);
                testEnergyViewModel.confirmEnergies();
            }
        });

        btnSetClamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnStartTest.setEnabled(true);
                testEnergyViewModel.timerCheckStart(2000);
            }
        });

        return rootView;
    }

    private void initObjects(View rootView){

        tvVR = rootView.findViewById(R.id.tvR_V);
        tvVS = rootView.findViewById(R.id.tvS_V);
        tvVT = rootView.findViewById(R.id.tvT_V);

        tvIR = rootView.findViewById(R.id.tvR_I);
        tvIS = rootView.findViewById(R.id.tvS_I);
        tvIT = rootView.findViewById(R.id.tvT_I);

        tvPFR = rootView.findViewById(R.id.tvR_PF);
        tvPFS = rootView.findViewById(R.id.tvS_PF);
        tvPFT = rootView.findViewById(R.id.tvT_PF);
    }

    private void selectClampTypeDialog(int[] clampType){

        String[] clampTypes =  new String[5];
        clampTypes[0] = (String) getResources().getText(R.string.ClampType1);
        clampTypes[1] = (String) getResources().getText(R.string.ClampType2);
        clampTypes[2] = (String) getResources().getText(R.string.ClampType3);
        clampTypes[3] = (String) getResources().getText(R.string.ClampType4);
        clampTypes[4] = (String) getResources().getText(R.string.ClampType5);

        radioClamp1.setText(clampTypes[clampType[1]]);
        radioClamp2.setText(clampTypes[clampType[2]]);
    }

    private void setRanges(){
        tvMinV.setText("80");
        tvMaxV.setText("280");
        tvMinI.setText("0.1");
        tvMaxI.setText("1100");
        tvMinPF.setText("0.05");
        tvMaxPF.setText("1");
    }

    private void showSanjeshResult(List<ElectericalParams> sanjeshResult) {

        tvVR.setText(sanjeshResult.get(2).AVRMS);
        tvIR.setText(sanjeshResult.get(2).AIRMS);
        tvPFR.setText(sanjeshResult.get(2).ANGLE0);

        tvVS.setText(sanjeshResult.get(1).AVRMS);
        tvIS.setText(sanjeshResult.get(1).AIRMS);
        tvPFS.setText(sanjeshResult.get(1).ANGLE0);

        tvVT.setText(sanjeshResult.get(0).AVRMS);
        tvIT.setText(sanjeshResult.get(0).AIRMS);
        tvPFT.setText(sanjeshResult.get(0).ANGLE0);

        checkVoltRange();
        checkAmpRange();
        checkPFRange();
    }

    public void checkVoltRange(){
        try{
            if (Float.valueOf(tvVR.getText().toString()) < Float.valueOf(tvMinV.getText().toString())) { setAnimation(tvVR,1);  }
            else if (Float.valueOf(tvVR.getText().toString()) > Float.valueOf(tvMaxV.getText().toString())) { setAnimation(tvVR,2);  }
            else { setAnimation(tvVR,0);  }

            if (Float.valueOf(tvVS.getText().toString()) < Float.valueOf(tvMinV.getText().toString())) { setAnimation(tvVS,1);  }
            else if (Float.valueOf(tvVS.getText().toString()) > Float.valueOf(tvMaxV.getText().toString())) { setAnimation(tvVS,2);  }
            else { setAnimation(tvVS,0);  }

            if (Float.valueOf(tvVT.getText().toString()) < Float.valueOf(tvMinV.getText().toString())) { setAnimation(tvVT,1);  }
            else if (Float.valueOf(tvVT.getText().toString()) > Float.valueOf(tvMaxV.getText().toString())) { setAnimation(tvVT,2);  }
            else { setAnimation(tvVT,0);  }
        }
        catch (Exception ex){
            String err = ex.getMessage();
        }
    }

    public void checkAmpRange(){
        try{
            if (Float.valueOf(tvIR.getText().toString()) < Float.valueOf(tvMinI.getText().toString())) { setAnimation(tvIR,1);  }
            else if (Float.valueOf(tvIR.getText().toString()) > Float.valueOf(tvMaxI.getText().toString())) { setAnimation(tvIR,2);  }
            else { setAnimation(tvIR,0);  }

            if (Float.valueOf(tvIS.getText().toString()) < Float.valueOf(tvMinI.getText().toString())) { setAnimation(tvIS,1);  }
            else if (Float.valueOf(tvIS.getText().toString()) > Float.valueOf(tvMaxI.getText().toString())) { setAnimation(tvIS,2);  }
            else { setAnimation(tvIS,0);  }

            if (Float.valueOf(tvIT.getText().toString()) < Float.valueOf(tvMinI.getText().toString())) { setAnimation(tvIT,1);  }
            else if (Float.valueOf(tvIT.getText().toString()) > Float.valueOf(tvMaxI.getText().toString())) { setAnimation(tvIT,2);  }
            else { setAnimation(tvIT,0);  }
        }
        catch (Exception ex){
            String err = ex.getMessage();
        }
    }

    public void checkPFRange(){
        try{
            if (Math.abs(Float.valueOf(tvPFR.getText().toString())) < Float.valueOf(tvMinPF.getText().toString())) { setAnimation(tvPFR,1);  }
            else { setAnimation(tvPFR,0);  }
//            if (Float.valueOf(tvPFR.getText().toString()) > Float.valueOf(tvMaxPF.getText().toString())) { setAnimation(tvPFR,2);  }
            if (Math.abs(Float.valueOf(tvPFS.getText().toString())) < Float.valueOf(tvMinPF.getText().toString())) { setAnimation(tvPFS,1);  }
            else { setAnimation(tvPFS,0);  }
//            if (Float.valueOf(tvPFS.getText().toString()) > Float.valueOf(tvMaxPF.getText().toString())) { setAnimation(tvPFS,2);  }
            if (Math.abs(Float.valueOf(tvPFT.getText().toString())) < Float.valueOf(tvMinPF.getText().toString())) { setAnimation(tvPFT,1);  }
            else { setAnimation(tvPFT,0);  }
//            if (Float.valueOf(tvPFT.getText().toString()) > Float.valueOf(tvMaxPF.getText().toString())) { setAnimation(tvPFT,2);  }
        }
        catch (Exception ex){
            String err = ex.getMessage();
        }
    }


    private void setAnimation(TextView selText,int status){
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500); //You can manage the blinking time with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);


        switch (status){
            case 0:
                //selText.setTextColor(ContextCompat.getColor(G.context, R.color.red_400));
                selText.setBackgroundColor(Color.WHITE);
                selText.clearAnimation();
                break;
            case 1:
                //selText.setTextColor(ContextCompat.getColor(G.context, R.color.red_400));
                selText.setBackgroundColor(Color.BLUE);
                selText.startAnimation(anim);
                break;
            case 2:
                selText.setBackgroundColor(Color.RED);
                selText.startAnimation(anim);
                break;
            case 3:
                selText.setBackgroundColor(Color.YELLOW);
                selText.startAnimation(anim);
                break;
        }


    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



}
