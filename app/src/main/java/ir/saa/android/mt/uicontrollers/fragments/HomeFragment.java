package ir.saa.android.mt.uicontrollers.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.FragmentsEnum;
import ir.saa.android.mt.uicontrollers.activities.DaryaftActivity;
import ir.saa.android.mt.uicontrollers.activities.DaryaftMoshtarakinActivity;
import ir.saa.android.mt.uicontrollers.activities.SendActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class HomeFragment extends Fragment
{
    LinearLayout layErsal;
    LinearLayout layGozareshat;
    LinearLayout layBazdid;
    LinearLayout laySettings;
    LinearLayout layMoshtarakin;
    LinearLayout layDaryaft;
    LinearLayout laySanjesh;
    AppCompatButton btnExit;

    public HomeFragment() {
        // Required empty public constructor
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
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        layBazdid=rootView.findViewById(R.id.layBazdid);
        layDaryaft=rootView.findViewById(R.id.layDaryaft);
        layErsal=rootView.findViewById(R.id.layErsal);
        layGozareshat=rootView.findViewById(R.id.layGozareshat);
        layMoshtarakin=rootView.findViewById(R.id.layMoshtarakin);
        laySettings=rootView.findViewById(R.id.laySettings);
        laySanjesh=rootView.findViewById(R.id.laySanjesh);
        btnExit=rootView.findViewById(R.id.btnExit);
        layBazdid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G.startFragment(FragmentsEnum.BazdidFragment,false,null);
            }
        });
        laySanjesh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G.startFragment(FragmentsEnum.SanjeshFragment,false,null);
            }
        });
        layDaryaft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(G.context,DaryaftActivity.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                G.context.startActivity(intent);
            }
        });
        layErsal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(G.context,SendActivity.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                G.context.startActivity(intent);
            }
        });

        layGozareshat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        laySettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                G.startFragment(FragmentsEnum.SettingFragment,false,null);
            }
        });

        layMoshtarakin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(G.context, DaryaftMoshtarakinActivity.class);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                G.context.startActivity(intent);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (G.fragmentNumStack.size() > 0) {
                    Integer targetFragmentNum = G.fragmentNumStack.pop();
                    G.startFragment(targetFragmentNum, true, null);
                }
                getActivity().finish();
                G.currentFragmentNum=null;
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                getActivity().finish();
                System.exit(0);
            }
        });
        return rootView;
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
