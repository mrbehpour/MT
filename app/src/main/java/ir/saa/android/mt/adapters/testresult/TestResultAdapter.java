package ir.saa.android.mt.adapters.testresult;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.saa.android.mt.R;

public class TestResultAdapter extends RecyclerView.Adapter<TestResultAdapter.MyViewHolder> {


    private List<TestItem> testItemListOrginal ;
    private List<TestItem> mDataList ;
    private LayoutInflater inflater;
    private Context context;

    public TestResultAdapter(Context context, List<TestItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        mDataList = new ArrayList<>();
        testItemListOrginal = new ArrayList<>();
        mDataList.addAll(data);
        testItemListOrginal.addAll(data);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.listitem_test_result, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        TestItem current = mDataList.get(position);

        holder.listItemTestResultRoot.setOnClickListener(v->{
//            G.setActionbarTitleText("");
//            Bundle bundle = new Bundle();
//            bundle.putLong(BundleKeysEnum.ClientID,current.Id);
//            G.startFragment(FragmentsEnum.MoshtarakFragment,false,bundle);
        });

        //holder.imgBazdidMoshtarak.setImageResource(current.Pic);
        holder.tvErrPerc.setText(current.ErrPerc);
        holder.tvPF.setText(current.PF);

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void clearDataSet(){
        mDataList.clear();
    }

    public void addAll(List<TestItem> testItems){
        mDataList.clear();
        testItemListOrginal.clear();
        mDataList.addAll(testItems);
        testItemListOrginal.addAll(testItems);
    }

    public void filter(String query) {
        mDataList.clear();
        if(query.isEmpty()){
            mDataList.addAll(testItemListOrginal);
        } else{
            query = query.toLowerCase();
            for(TestItem item: testItemListOrginal){
                if(item.ErrPerc.contains(query) || item.PF.contains(query)){
                    mDataList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout listItemTestResultRoot;
        TextView tvErrPerc;
        TextView tvPF;

        public MyViewHolder(View itemView) {
            super(itemView);
            listItemTestResultRoot = itemView.findViewById(R.id.listItemTestResultRoot);
            tvErrPerc = itemView.findViewById(R.id.tvName);
            tvPF = itemView.findViewById(R.id.tvPF);

        }
    }
}
