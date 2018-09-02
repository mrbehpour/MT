package ir.saa.android.mt.adapters.bazrasi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.saa.android.mt.R;

public class BazrasiAdapter extends RecyclerView.Adapter<BazrasiAdapter.MyViewHolder> {

    private List<RemarkItem> remarkItemListOrginal ;
    private List<RemarkItem> mDataList ;
    private LayoutInflater inflater;
    private Context context;

    public BazrasiAdapter(Context context, List<RemarkItem> data){
        this.context = context;
        inflater = LayoutInflater.from(context);
        mDataList = new ArrayList<>();
        remarkItemListOrginal = new ArrayList<>();
        mDataList.addAll(data);
        remarkItemListOrginal.addAll(data);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.listitem_bazrasi, parent, false);
        BazrasiAdapter.MyViewHolder holder = new BazrasiAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
            RemarkItem current=mDataList.get(position);
            holder.listitemRemarkRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            holder.tvId.setText(current.Id.toString());
            holder.tvSoal.setText(current.RemarkName);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
    public void addAll(List<RemarkItem> remmarkItems){
        mDataList.clear();
        remarkItemListOrginal.clear();
        mDataList.addAll(remmarkItems);
        remarkItemListOrginal.addAll(remmarkItems);
    }
    public void clearDataSet(){
        mDataList.clear();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        LinearLayout listitemRemarkRoot;
        TextView tvSoal;
        TextView tvId;


        public MyViewHolder(View itemView) {
            super(itemView);
            listitemRemarkRoot=itemView.findViewById(R.id.listItemBazrasiRoot);
            tvSoal=itemView.findViewById(R.id.tvSoal);
            tvId=itemView.findViewById(R.id.tvId);

        }
    }
}
