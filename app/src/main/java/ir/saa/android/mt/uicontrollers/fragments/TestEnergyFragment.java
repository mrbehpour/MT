package ir.saa.android.mt.uicontrollers.fragments;

import android.annotation.TargetApi;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.repositories.metertester.EnergiesState;
import ir.saa.android.mt.uicontrollers.pojos.TestContor.TestContorParams;
import ir.saa.android.mt.viewmodels.TestEnergyViewModel;

public class TestEnergyFragment extends Fragment
{
    TestEnergyViewModel testEnergyViewModel = null;
    TestContorParams testContorParams;

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



        Bundle args = getArguments();
        if (args != null) {
            //Restore the fragment's state here
            testContorParams = (TestContorParams)args.getSerializable("testContorParams");
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

        btnStartTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testEnergyViewModel.setTestContorParams(testContorParams);
                testEnergyViewModel.confirmEnergies();
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("testContorParams", testContorParams);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(savedInstanceState!=null) {
            // Retrieve the user email value from bundle.
            testContorParams = (TestContorParams)savedInstanceState.getSerializable("testContorParams");
        }
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
