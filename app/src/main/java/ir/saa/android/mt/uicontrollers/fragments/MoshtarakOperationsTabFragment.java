package ir.saa.android.mt.uicontrollers.fragments;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.BundleKeysEnum;
import ir.saa.android.mt.enums.FragmentsEnum;
import ir.saa.android.mt.viewmodels.LocationViewModel;

public class MoshtarakOperationsTabFragment extends Fragment
{
    LocationViewModel locationViewModel=null;
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
        Button btnTest=rootView.findViewById(R.id.btnTest);
        Button btnPolomp=rootView.findViewById(R.id.btnPolomp);
        Button btnBazrasi=rootView.findViewById(R.id.btnBazrasi);
        Button btnReadmeter=rootView.findViewById(R.id.btnReadmeter);

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

        G.setActionbarTitleText(G.clientInfo.ClientName);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }

}
