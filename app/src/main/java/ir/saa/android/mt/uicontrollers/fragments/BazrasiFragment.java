package ir.saa.android.mt.uicontrollers.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import ir.saa.android.mt.R;
import ir.saa.android.mt.adapters.bazrasi.BazrasiAdapter;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.components.MyCheckList;
import ir.saa.android.mt.components.MyCheckListItem;
import ir.saa.android.mt.components.MyCheckListMode;
import ir.saa.android.mt.components.MyDialog;
import ir.saa.android.mt.viewmodels.BazrasiViewModel;

public class BazrasiFragment extends Fragment {

    BazrasiViewModel bazrasiViewModel;
    BazrasiAdapter adapter;

    public BazrasiFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bazrasiViewModel = ViewModelProviders.of(getActivity()).get(BazrasiViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bazrasi, container, false);

        AppCompatButton btnsave=rootView.findViewById(R.id.btnSave);

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialog dialog;
                dialog = new MyDialog(getActivity());
                dialog.clearAllPanel();
                MyCheckList myCheckList=new MyCheckList(G.context,new MyCheckListItem("Iman",0),new MyCheckListItem("Ehsan",1));
                myCheckList.addCheckItem(new MyCheckListItem("Hassan",2));
                myCheckList.addCheckItem(new MyCheckListItem("Hamid",3));
                myCheckList.addCheckItem(new MyCheckListItem("Hadi",4));
                myCheckList.addCheckItem(new MyCheckListItem("Hessam",5));
                myCheckList.setCheckListOrientation(LinearLayout.VERTICAL)
                        .setSelectionMode(MyCheckListMode.SingleSelection)
                        .setCheckItemsHeight(30);
                dialog.setTitle("سلام")
                        .setRightTitle(String.format("%d/%d",1,4))
                        .addContentView(myCheckList)
                        .setLeftImageTitle(G.context.getResources().getDrawable(R.drawable.account),new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).show();
            }
        });
        setUpRecyclerView(rootView);
        return rootView;
    }

    private void setUpRecyclerView(View view) {

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvSoal);

        adapter = new BazrasiAdapter(getActivity(), bazrasiViewModel.getRemarks().getValue()==null?new ArrayList<>(): bazrasiViewModel.getRemarks().getValue());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        bazrasiViewModel.getRemarks().observe(this, remarkItems -> {
            adapter.clearDataSet();
            adapter.addAll(remarkItems);
            adapter.notifyDataSetChanged();
        });
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
