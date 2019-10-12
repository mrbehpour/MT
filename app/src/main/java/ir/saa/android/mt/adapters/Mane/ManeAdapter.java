package ir.saa.android.mt.adapters.Mane;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ir.saa.android.mt.R;


public class ManeAdapter extends RecyclerView.Adapter<ManeAdapter.MyViewHolder> {
    int CheckedPosion=-1;
    private List<ManeItem> maneItemListOrginal;
    private List<ManeItem> mDataList;
    private LayoutInflater inflater;
    private Context context;
    HashMap<Integer,Boolean> hashMap;
    HashMap<Integer,Boolean> integerBooleanHashMap;
    RadioGroup radioGroup;
    public ManeAdapter(Context context, List<ManeItem> data){
        this.context = context;
        inflater = LayoutInflater.from(context);
        mDataList = new ArrayList<>();
        maneItemListOrginal = new ArrayList<>();
        mDataList.addAll(data);
        maneItemListOrginal.addAll(data);
        radioGroup=new RadioGroup(context);


    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.listitem_maneh, parent, false);

        ManeAdapter.MyViewHolder myViewHolder=new MyViewHolder(view);
        if(mDataList.size()!=0) {
            hashMap = new HashMap<Integer, Boolean>();
            for (int i = 0; i < mDataList.size(); i++) {
                hashMap.put(i, false);
            }
        }
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ManeItem current=mDataList.get(position);


        holder.chkManeh.setText(current.ManeName);
        integerBooleanHashMap=new HashMap<>();




//        holder.chkManeh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(b){
//                    integerBooleanHashMap=new HashMap<>();
//                   for(int i=0;i<hashMap.size();i++){
//                     if(i!=position){
//                         integerBooleanHashMap.put(i,false);
//                     }else{
//                         integerBooleanHashMap.put(i,true);
//                     }
//                   }
//                }
//            }
//        });

    }
    public void addAll(List<ManeItem> remmarkItems) {
        mDataList.clear();
        maneItemListOrginal.clear();
        mDataList.addAll(remmarkItems);
        maneItemListOrginal.addAll(remmarkItems);
    }

    public void clearDataSet() {
        mDataList.clear();
    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        CardView listitemRemarkRoot;

        //TextView tvSoal;
        //TextView tvId;
        //TextView tvResult;
        RadioButton chkManeh;
        public MyViewHolder(View itemView) {
            super(itemView);
            listitemRemarkRoot = itemView.findViewById(R.id.cvSoal);
            chkManeh=itemView.findViewById(R.id.chkManeh);

            //tvSoal = itemView.findViewById(R.id.tvSoal);
            //tvId = itemView.findViewById(R.id.tvId);
            //tvResult = itemView.findViewById(R.id.tvResult);
        }
    }
}
