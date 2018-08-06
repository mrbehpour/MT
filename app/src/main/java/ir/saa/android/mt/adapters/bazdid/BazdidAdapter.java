package ir.saa.android.mt.adapters.bazdid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ir.saa.android.mt.R;

public class BazdidAdapter  extends RecyclerView.Adapter<BazdidAdapter.MyViewHolder> {

    private List<ClientItem> mDataList ;
    private LayoutInflater inflater;
    private Context context;

    public BazdidAdapter(Context context, List<ClientItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mDataList = data;
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

        holder.imgBazdidMoshtarak.setImageResource(current.Pic);
        holder.tvName.setText(current.Name);
        holder.tvUniqueTitle.setText(current.UniqueFieldTitle);
        holder.tvUniqueValue.setText(current.UniqueFieldValue);
        holder.tvAddress.setText(current.Address);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvUniqueTitle;
        TextView tvUniqueValue;
        TextView tvAddress;
        ImageView imgBazdidMoshtarak;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvUniqueTitle = (TextView) itemView.findViewById(R.id.tvUniqueTitle);
            tvUniqueValue = (TextView) itemView.findViewById(R.id.tvUniqueValue);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            imgBazdidMoshtarak = (ImageView) itemView.findViewById(R.id.imgBazdidMoshtarak);
        }
    }
}
