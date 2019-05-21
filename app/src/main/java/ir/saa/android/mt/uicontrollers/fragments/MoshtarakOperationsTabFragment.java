package ir.saa.android.mt.uicontrollers.fragments;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.HashMap;
import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.BundleKeysEnum;
import ir.saa.android.mt.enums.FragmentsEnum;
import ir.saa.android.mt.model.entities.Menu;
import ir.saa.android.mt.viewmodels.LocationViewModel;
import ir.saa.android.mt.viewmodels.MenuViewModel;

public class MoshtarakOperationsTabFragment extends Fragment
{
    MenuViewModel menuViewModel;
    LocationViewModel locationViewModel=null;
    HashMap<String,Integer> stringHashMapMenu;
    HashMap<Integer,Integer> integerHashMapMenu;
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
        menuViewModel=ViewModelProviders.of(getActivity()).get(MenuViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_moshtarak_operation, container, false);
        stringHashMapMenu=new HashMap<String, Integer>();
        integerHashMapMenu=new HashMap<Integer, Integer>();
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

        stringHashMapMenu.put("Test",R.id.btnTest);
        stringHashMapMenu.put("Polomp",R.id.btnPolomp);
        stringHashMapMenu.put("Bazresi",R.id.btnBazrasi);
        stringHashMapMenu.put("Tariff",R.id.btnReadmeter);

        menuViewModel.getMenus().observe(getActivity(), new Observer<List<Menu>>() {
            @Override
            public void onChanged(@Nullable List<Menu> menus) {
                AppCompatButton buttonIndex;
                for (Menu menuItem:menus) {
                   integerHashMapMenu.put(menuItem.OrderId,stringHashMapMenu.get(menuItem.KeyName));

                }
                for (Integer i=0;i<integerHashMapMenu.size();i++){
                    buttonIndex=rootView.findViewById(integerHashMapMenu.get(i+1));
                    RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    p.addRule(RelativeLayout.CENTER_VERTICAL);
                    p.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    if(i==0) {
                        p.addRule(RelativeLayout.BELOW, R.id.lyTop);
                        buttonIndex.setLayoutParams(p);

                    }else{
                        p.addRule(RelativeLayout.BELOW,integerHashMapMenu.get(i));
                        buttonIndex.setLayoutParams(p);
                    }
                    buttonIndex.setWidth(350);
                    if(menus.get(i).CanView==false){
                        buttonIndex.setVisibility(View.GONE);
                    }
                }
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

        G.setActionbarTitleText(G.clientInfo.ClientName);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
    }

}
