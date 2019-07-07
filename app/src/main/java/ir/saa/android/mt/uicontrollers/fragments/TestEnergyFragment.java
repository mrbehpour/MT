package ir.saa.android.mt.uicontrollers.fragments;

import android.annotation.TargetApi;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.BundleKeysEnum;
import ir.saa.android.mt.repositories.metertester.ElectericalParams;
import ir.saa.android.mt.repositories.metertester.EnergiesState;
import ir.saa.android.mt.uicontrollers.pojos.TestContor.TestContorParams;
import ir.saa.android.mt.viewmodels.TestEnergyViewModel;

public class TestEnergyFragment extends Fragment
{
    TestEnergyViewModel testEnergyViewModel = null;
    TestContorParams testContorParams;

    TextView tvVR,tvVS,tvVT;
    TextView tvIR,tvIS,tvIT;
    TextView tvPFR,tvPFS,tvPFT;

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

        LinearLayout llLeft = rootView.findViewById(R.id.llLeft);
        LinearLayout llCenter = rootView.findViewById(R.id.llCenter);
        LinearLayout llRight = rootView.findViewById(R.id.llRight);
        TextView tvLeft = rootView.findViewById(R.id.tvLeft);
        TextView tvCenter = rootView.findViewById(R.id.tvCenter);
        TextView tvRight = rootView.findViewById(R.id.tvRight);
        Button btnStartTest = rootView.findViewById(R.id.btnStartTest);

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

    private void showSanjeshResult(List<ElectericalParams> sanjeshResult) {

        tvVR.setText(sanjeshResult.get(0).AVRMS);
        tvIR.setText(sanjeshResult.get(0).AIRMS);
        tvPFR.setText(sanjeshResult.get(0).ANGLE0);

        tvVS.setText(sanjeshResult.get(1).AVRMS);
        tvIS.setText(sanjeshResult.get(1).AIRMS);
        tvPFS.setText(sanjeshResult.get(1).ANGLE0);

        tvVT.setText(sanjeshResult.get(2).AVRMS);
        tvIT.setText(sanjeshResult.get(2).AIRMS);
        tvPFT.setText(sanjeshResult.get(2).ANGLE0);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



}
