package ir.saa.android.mt.adapters.bazdid;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.components.TextViewFont;
import ir.saa.android.mt.enums.BundleKeysEnum;
import ir.saa.android.mt.enums.FragmentsEnum;
import ir.saa.android.mt.uicontrollers.pojos.FontManager.FontManager;
import ir.saa.android.mt.viewmodels.BazdidViewModel;

public class BazdidAdapter  extends RecyclerView.Adapter<BazdidAdapter.MyViewHolder> {

    private List<ClientItem> clientItemListOrginal ;
    private List<ClientItem> mDataList ;
    private LayoutInflater inflater;
    private Context context;
    BazdidViewModel bazdidViewModel;

    public BazdidAdapter(Context context, List<ClientItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        mDataList = new ArrayList<>();
        clientItemListOrginal = new ArrayList<>();
        mDataList.addAll(data);
        clientItemListOrginal.addAll(data);
        this.bazdidViewModel= ViewModelProviders.of((AppCompatActivity)context).get(BazdidViewModel.class);
    }

    @Override
    public BazdidAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.listitem_bazdid, parent, false);
        BazdidAdapter.MyViewHolder holder = new BazdidAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final BazdidAdapter.MyViewHolder holder, int position) {
        ClientItem current = mDataList.get(position);

        holder.listItemBazdidRoot.setOnClickListener(v->{
            G.setActionbarTitleText(current.Name);
            Bundle bundle = new Bundle();
            bundle.putLong(BundleKeysEnum.ClientID,current.Id);
            G.clientInfo.ClientId=current.Id;
            G.clientInfo.SendId=current.SendId;
            G.clientInfo.GroupId=current.GroupId;
            G.clientInfo.FollowUpCode=current.FollowUpCode;
            G.clientInfo.ClientName=current.Name;
            G.setActionbarTitleText(current.Name);
            G.startFragment(FragmentsEnum.MoshtarakFragment,false,bundle);
        });
        //holder.imgBazdidMoshtarak.setImageResource(current.Pic);
        holder.tvName.setText(current.Name);
        holder.tvUniqueTitle.setText(current.UniqueFieldTitle);
        holder.tvUniqueValue.setText(current.UniqueFieldValue);
        holder.tvAddress.setText(current.Address);


        if(current.isPolommp){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.IconPolomp.setImageTintList(G.context.getResources().getColorStateList(R.color.icon_on));
                }
            }
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.IconPolomp.setImageTintList(G.context.getResources().getColorStateList(R.color.icon_off));
            }
        }

        if(current.isTest){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.IconTest.setImageTintList(ContextCompat.getColorStateList(G.context.getApplicationContext(), R.color.icon_on));
            }
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.IconTest.setImageTintList(ContextCompat.getColorStateList(G.context.getApplicationContext(), R.color.icon_off));
            }
                }
        if(current.isBazrasi){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.IconBazrasi.setImageTintList(ContextCompat.getColorStateList(G.context.getApplicationContext(), R.color.icon_on));
            }
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.IconBazrasi.setImageTintList(ContextCompat.getColorStateList(G.context.getApplicationContext(), R.color.icon_off));
            }
        }


    }



    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void clearDataSet(){
        mDataList.clear();
    }

    public void addAll(List<ClientItem> clientItems){
        mDataList.clear();
        clientItemListOrginal.clear();
        mDataList.addAll(clientItems);
        clientItemListOrginal.addAll(clientItems);
    }

    public void filter(String query) {
        mDataList.clear();
        if(query.isEmpty()){
            mDataList.addAll(clientItemListOrginal);
        } else{
            query = query.toLowerCase();
            for(ClientItem item: clientItemListOrginal){
                if(item.Name.toLowerCase().contains(query) || item.Address.toLowerCase().contains(query) || item.UniqueFieldValue.toLowerCase().contains(query)){
                    mDataList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout listItemBazdidRoot;
        TextView tvName;
        TextView tvUniqueTitle;
        TextView tvUniqueValue;
        TextView tvAddress;
        ImageView IconTest;
        ImageView IconBazrasi;
        ImageView IconPolomp;
        ImageView imgBazdidMoshtarak;

        public MyViewHolder(View itemView) {
            super(itemView);
            listItemBazdidRoot = itemView.findViewById(R.id.listItemBazdidRoot);
            tvName = itemView.findViewById(R.id.tvName);
            tvUniqueTitle = itemView.findViewById(R.id.tvUniqueTitle);
            tvUniqueValue = itemView.findViewById(R.id.tvUniqueValue);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            imgBazdidMoshtarak = itemView.findViewById(R.id.imgBazdidMoshtarak);
            IconTest=itemView.findViewById(R.id.iconTest);
            IconBazrasi=itemView.findViewById(R.id.iconBazrasi);
            IconPolomp=itemView.findViewById(R.id.iconPolomp);

//            Typeface iconFont = FontManager.getTypeface(context, FontManager.FONTAWESOME);
//            FontManager.markAsIconContainer(tvfIconTest,iconFont);
//            FontManager.markAsIconContainer(tvfIconBazrasi,iconFont);
//            FontManager.markAsIconContainer(tvfIconPolomp,iconFont);
        }
    }
}
