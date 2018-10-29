package ir.saa.android.mt.adapters.testdisplay;

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
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.repositories.metertester.TestResult;

public class TestDisplayAdapter extends RecyclerView.Adapter<TestDisplayAdapter.MyViewHolder> {

    private List<TestResultItemDisplay> testItemListOrginal ;
    private List<TestResultItemDisplay> mDataList ;
    private LayoutInflater inflater;
    private Context context;

    public TestDisplayAdapter(Context context, List<TestResultItemDisplay> data){
        this.context = context;
        inflater = LayoutInflater.from(context);
        mDataList = new ArrayList<>();
        testItemListOrginal = new ArrayList<>();
        mDataList.addAll(data);
        testItemListOrginal.addAll(data);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.listitem_test_display,parent,false);
        TestDisplayAdapter.MyViewHolder myViewHolder=new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TestResultItemDisplay current=mDataList.get(position);
        holder.tvResultTitle.setText(current.FisrtTest==true? G.context.getResources().getText(R.string.TypeTest1_TestDisplayAdapter)
                :G.context.getResources().getText(R.string.TypeTest2_TestDisplayAdapter));
        holder.tvResultError.setText(String.valueOf(current.ErrPerc));
        holder.tvTestType.setText(current.Active==true?G.context.getResources().getText(R.string.ModelActive_TestDisplayAdapter)
                :G.context.getResources().getText(R.string.ModelReActive_TestDisplayAdapter));
        holder.tvRowV_R.setText(current.AVRMS_Period1);
        holder.tvRowV_S.setText(current.BVRMS_Period1);
        holder.tvRowV_T.setText(current.CVRMS_Period1);
        holder.tvRowI_R.setText(current.AIRMS_Period1);
        holder.tvRowI_S.setText(current.BIRMS_Period1);
        holder.tvRowI_T.setText(current.CIRMS_Period1);

        holder.tvRowP_R.setText(String.valueOf(current.Pow_A));
        holder.tvRowP_S.setText(String.valueOf(current.Pow_B));
        holder.tvRowP_T.setText(String.valueOf(current.Pow_C));

        holder.tvRowQ_R.setText(String.valueOf(current.Q_A));
        holder.tvRowQ_S.setText(String.valueOf(current.Q_B));
        holder.tvRowQ_T.setText(String.valueOf(current.Q_C));

        holder.tvRowS_R.setText(String.valueOf(current.S_A));
        holder.tvRowS_S.setText(String.valueOf(current.S_B));
        holder.tvRowS_T.setText(String.valueOf(current.S_C));

        holder.tvRowPF_R.setText(String.valueOf(current.PF_A));
        holder.tvRowPF_S.setText(String.valueOf(current.PF_B));
        holder.tvRowPF_T.setText(String.valueOf(current.PF_C));




    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void clearDataSet(){
        mDataList.clear();
    }

    public void addAll(List<TestResultItemDisplay> testResults){
        mDataList.clear();
        testItemListOrginal.clear();
        mDataList.addAll(testResults);
        testItemListOrginal.addAll(testResults);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        LinearLayout listItemTestDisplayRoot;
        TextView tvResultTitle;
        TextView tvResultError;
        TextView tvTestType;
        TextView tvRowV_R;
        TextView tvRowV_S;
        TextView tvRowI_T;
        TextView tvRowI_R;
        TextView tvRowI_S;
        TextView tvRowV_T;
        TextView tvRowP_R;
        TextView tvRowP_S;
        TextView tvRowP_T;
        TextView tvRowQ_R;
        TextView tvRowQ_S;
        TextView tvRowQ_T;
        TextView tvRowS_R;
        TextView tvRowS_S;
        TextView tvRowS_T;
        TextView tvRowPF_R;
        TextView tvRowPF_S;
        TextView tvRowPF_T;

        public MyViewHolder(View itemView) {
            super(itemView);
            listItemTestDisplayRoot=itemView.findViewById(R.id.listItemTestDisplayRoot);
            tvResultTitle=itemView.findViewById(R.id.tvNatijeTitle);
            tvResultError=itemView.findViewById(R.id.tvNatijeKhata);
            tvTestType=itemView.findViewById(R.id.tvTestType);
            tvRowV_R=itemView.findViewById(R.id.tvRowV_R);
            tvRowV_S=itemView.findViewById(R.id.tvRowV_S);
            tvRowI_T=itemView.findViewById(R.id.tvRowI_T);
            tvRowI_R=itemView.findViewById(R.id.tvRowI_R);
            tvRowI_S=itemView.findViewById(R.id.tvRowI_S);
            tvRowV_T=itemView.findViewById(R.id.tvRowV_T);
            tvRowP_R=itemView.findViewById(R.id.tvRowP_R);
            tvRowP_S=itemView.findViewById(R.id.tvRowP_S);
            tvRowP_T=itemView.findViewById(R.id.tvRowP_T);
            tvRowQ_R=itemView.findViewById(R.id.tvRowQ_R);
            tvRowQ_S=itemView.findViewById(R.id.tvRowQ_S);
            tvRowQ_T=itemView.findViewById(R.id.tvRowQ_T);
            tvRowS_R=itemView.findViewById(R.id.tvRowS_R);
            tvRowS_S=itemView.findViewById(R.id.tvRowS_S);
            tvRowS_T=itemView.findViewById(R.id.tvRowS_T);
            tvRowPF_R=itemView.findViewById(R.id.tvRowPF_R);
            tvRowPF_S=itemView.findViewById(R.id.tvRowPF_S);
            tvRowPF_T=itemView.findViewById(R.id.tvRowPF_T);

        }
    }
}
