package ir.saa.android.mt.adapters.bazdid;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import ir.saa.android.mt.model.entities.Bazdid;
import ir.saa.android.mt.uicontrollers.pojos.FontManager.FontManager;
import ir.saa.android.mt.viewmodels.BazdidViewModel;
import ir.saa.android.mt.viewmodels.LocationViewModel;

public class BazdidAdapter extends RecyclerView.Adapter<BazdidAdapter.MyViewHolder> {

    private List<ClientItem> clientItemListOrginal;
    private List<ClientItem> mDataList;
    private LayoutInflater inflater;
    private Context context;
    BazdidViewModel bazdidViewModel;
    LocationViewModel locationViewModel;

    public BazdidAdapter(Context context, List<ClientItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        mDataList = new ArrayList<>();
        clientItemListOrginal = new ArrayList<>();
        mDataList.addAll(data);
        clientItemListOrginal.addAll(data);
        this.bazdidViewModel = ViewModelProviders.of((AppCompatActivity) context).get(BazdidViewModel.class);
        this.locationViewModel=ViewModelProviders.of((AppCompatActivity) context).get(LocationViewModel.class);
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

        holder.listItemBazdidRoot.setOnClickListener(v -> {
            G.setActionbarTitleText(current.Name);
            Bundle bundle = new Bundle();
            bundle.putLong(BundleKeysEnum.ClientID, current.Id);
            G.clientInfo.ClientId = current.Id;
            G.clientInfo.SendId = current.SendId;
            G.clientInfo.GroupId = current.GroupId;
            G.clientInfo.FollowUpCode = current.FollowUpCode;
            G.clientInfo.ClientName = current.Name;
            G.clientInfo.Postion = position;
            G.clientInfo.forcibleMasterGroup=current.forcibleMasterGroup;
            G.setActionbarTitleText(current.Name);
;
            G.startFragment(FragmentsEnum.MoshtarakFragment, false, bundle);




        });
        //holder.imgBazdidMoshtarak.setImageResource(current.Pic);
        holder.tvName.setText(current.Name);
        holder.tvUniqueTitle.setText(current.UniqueFieldTitle);
        holder.tvUniqueValue.setText(current.UniqueFieldValue);
        holder.tvAddress.setText(current.Address);
        holder.tvRowId.setText(current.RowId.toString());
        int CountAmliyat=0;
        if (current.isPolommp) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.IconPolomp.setImageTintList(ContextCompat.getColorStateList(holder.contextHolder, R.color.icon_on));
            }

            CountAmliyat+=1;
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.IconPolomp.setImageTintList(ContextCompat.getColorStateList(holder.contextHolder, R.color.icon_off));
            }
        }

        if (current.isTest) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.IconTest.setImageTintList(ContextCompat.getColorStateList(context, R.color.icon_on));
            }
            CountAmliyat+=1;
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.IconTest.setImageTintList(ContextCompat.getColorStateList(context, R.color.icon_off));
            }
        }
        if (current.isBazrasi) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.IconBazrasi.setImageTintList(ContextCompat.getColorStateList(context, R.color.icon_on));
            }
            CountAmliyat++;
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.IconBazrasi.setImageTintList(ContextCompat.getColorStateList(context, R.color.icon_off));
            }
        }

        if (current.isTariff) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.IconGherat.setImageTintList(ContextCompat.getColorStateList(context, R.color.icon_on));
            }
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.IconGherat.setImageTintList(ContextCompat.getColorStateList(context, R.color.icon_off));
            }
        }
        if (current.isBazrasi) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.iconMoshahedat.setImageTintList(ContextCompat.getColorStateList(context, R.color.icon_on));
            }
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.iconMoshahedat.setImageTintList(ContextCompat.getColorStateList(context, R.color.icon_off));
            }
        }
        if(CountAmliyat==2){
            Bazdid bazdidAsli=new Bazdid();
            bazdidAsli.ClientId=current.Id;
            bazdidAsli.isBazdid=true;
            if(bazdidViewModel.getBazdid(current.Id)==null){

                    bazdidViewModel.insertBazdid(bazdidAsli);

            }else{
                bazdidViewModel.updateBazdid(bazdidAsli);
            }
        }


    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void clearDataSet() {
        mDataList.clear();
    }

    public void addAll(List<ClientItem> clientItems) {
        mDataList.clear();
        clientItemListOrginal.clear();
        mDataList.addAll(clientItems);
        clientItemListOrginal.addAll(clientItems);
    }

    public void filter(String query) {
        mDataList.clear();
        if (query.isEmpty()) {
            mDataList.addAll(clientItemListOrginal);
        } else {
            query = query.toLowerCase();
            for (ClientItem item : clientItemListOrginal) {
                if (item.Name.toLowerCase().contains(query) || item.Address.toLowerCase().contains(query)
                        || item.UniqueFieldValue.toLowerCase().contains(query) || item.RowId.toString().contains(query) ) {
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
        TextView tvRowId;
        ImageView IconTest;
        ImageView IconBazrasi;
        ImageView IconPolomp;
        ImageView IconGherat;
        ImageView imgBazdidMoshtarak;
        ImageView iconMoshahedat;
        Context contextHolder;

        public MyViewHolder(View itemView) {
            super(itemView);
            listItemBazdidRoot = itemView.findViewById(R.id.listItemBazdidRoot);
            tvName = itemView.findViewById(R.id.tvName);
            tvUniqueTitle = itemView.findViewById(R.id.tvUniqueTitle);
            tvUniqueValue = itemView.findViewById(R.id.tvUniqueValue);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            imgBazdidMoshtarak = itemView.findViewById(R.id.imgBazdidMoshtarak);
            IconTest = itemView.findViewById(R.id.iconTest);
            IconBazrasi = itemView.findViewById(R.id.iconBazrasi);
            IconPolomp = itemView.findViewById(R.id.iconPolomp);
            IconGherat=itemView.findViewById(R.id.iconGherat);
            iconMoshahedat=itemView.findViewById(R.id.iconMoshahedat);
            tvRowId=itemView.findViewById(R.id.tvRow);
            contextHolder = itemView.getContext().getApplicationContext();

//            Typeface iconFont = FontManager.getTypeface(context, FontManager.FONTAWESOME);
//            FontManager.markAsIconContainer(tvfIconTest,iconFont);
//            FontManager.markAsIconContainer(tvfIconBazrasi,iconFont);
//            FontManager.markAsIconContainer(tvfIconPolomp,iconFont);
        }
    }
}
