package ir.saa.android.mt.adapters.polomp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.BundleKeysEnum;
import ir.saa.android.mt.enums.FragmentsEnum;
import ir.saa.android.mt.model.entities.PolompAllInfo;
import ir.saa.android.mt.uicontrollers.pojos.Polomp.PolompParams;
import ir.saa.android.mt.viewmodels.PolompViewModel;


public class PolompAdapter extends RecyclerView.Adapter<PolompAdapter.MyViewHolder> {

    private List<PolompItem> polompItemListOrginal ;
    private List<PolompItem> mDataList ;
    private LayoutInflater inflater;
    private Context context;
    PolompViewModel polompViewModel=null;


    public PolompAdapter(Context context, List<PolompItem> data){
        this.context = context;
        inflater = LayoutInflater.from(context);
        mDataList = new ArrayList<>();
        polompItemListOrginal = new ArrayList<>();
        mDataList.addAll(data);
        polompItemListOrginal.addAll(data);
        this.polompViewModel= ViewModelProviders.of((AppCompatActivity)context).get(PolompViewModel.class);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.listitem_polomp, parent, false);
        PolompAdapter.MyViewHolder holder = new PolompAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PolompItem current = mDataList.get(position);
        PolompParams polompParams=new PolompParams(current.Id,current.ClientId);


        holder.listitemPolompRoot.setBackgroundColor(Color.parseColor("#FDFDFD"));

        PolompAllInfo polompAllInfo= polompViewModel.getPolompData(polompParams);
        if(polompAllInfo!=null){
            holder.listitemPolompRoot.setBackgroundColor(Color.parseColor("#FFC9CCF1"));
        }


        holder.listitemPolompRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(current.ClientId!=null){
                    PolompParams polompParams=new PolompParams(current.Id, current.ClientId);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable(BundleKeysEnum.ClassPolompParams,polompParams);
                    G.startFragment(FragmentsEnum.PolompFragmentSave,false,bundle);
                }
            }
        });

        holder.tvId.setText(current.Id.toString());
        holder.tvName.setText(current.Name);
        holder.tvClientid.setText(String.valueOf(current.ClientId));
    }

    public void clearDataSet(){
        mDataList.clear();
    }
    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void addAll(List<PolompItem> polompItems){
        mDataList.clear();
        polompItemListOrginal.clear();
        mDataList.addAll(polompItems);
        polompItemListOrginal.addAll(polompItems);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        LinearLayout listitemPolompRoot;
        TextView tvName;
        TextView tvId;
        TextView tvClientid;
        public MyViewHolder(View itemView) {
            super(itemView);
            listitemPolompRoot=itemView.findViewById(R.id.listItempolompRoot);
            tvName=itemView.findViewById(R.id.tvName);
            tvId=itemView.findViewById(R.id.tvId);
            tvClientid=itemView.findViewById(R.id.tvClientId);
        }
    }
}
