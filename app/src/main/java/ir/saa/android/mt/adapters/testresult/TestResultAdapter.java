package ir.saa.android.mt.adapters.testresult;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.repositories.metertester.TestResult;
import ir.saa.android.mt.uicontrollers.activities.DaryaftMoshtarakinActivity;

public class TestResultAdapter extends RecyclerView.Adapter<TestResultAdapter.MyViewHolder> {


    private List<TestResult> testItemListOrginal ;
    private List<TestResult> mDataList ;
    private LayoutInflater inflater;
    private Context context;

    public TestResultAdapter(Context context, List<TestResult> data) {
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
        TestResultAdapter.MyViewHolder holder = new TestResultAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        TestResult current = mDataList.get(position);

        holder.listItemTestResultRoot.setOnClickListener(v->{
//            G.setActionbarTitleText("");
//            Bundle bundle = new Bundle();
//            bundle.putLong(BundleKeysEnum.ClientID,current.Id);
//            G.startFragment(FragmentsEnum.MoshtarakFragment,false,bundle);
        });


        try {
            holder.tvRoundNum.setText(String.valueOf(current.RoundNum));
            holder.tvErrPerc.setText(String.format("%s: %s", G.context.getResources().getText(R.string.ERR_TestResultAdapter), String.format("%.2f", current.ErrPerc)));
            holder.tvTestTime.setText(String.format("%s: %s %s  ", G.context.getResources().getText(R.string.DateTest_TestResultAdapter),
                    String.valueOf(Integer.parseInt(current.Time_Period1) * 100), G.context.getResources().getText(R.string.TypeDateTest_TestResultAdapter)));
            holder.tvPF_A.setText(String.format("%.2f", current.PF_A));
            holder.tvPF_B.setText(String.format("%.2f", current.PF_B));
            holder.tvPF_C.setText(String.format("%.2f", current.PF_C));
            holder.tvI_A.setText(String.valueOf(current.AIRMS_Period1));
            holder.tvI_B.setText(String.valueOf(current.BIRMS_Period1));
            holder.tvI_C.setText(String.valueOf(current.CIRMS_Period1));
            holder.tvV_A.setText(String.valueOf(current.AVRMS_Period1));
            holder.tvV_B.setText(String.valueOf(current.BVRMS_Period1));
            holder.tvV_C.setText(String.valueOf(current.CVRMS_Period1));
        }
        catch (Exception ex){
            //Toast.makeText(G.context,ex.getMessage(),Toast.LENGTH_SHORT).show();
            Toast fancyToast= FancyToast.makeText(G.context, (String) ex.getMessage(),FancyToast.LENGTH_SHORT,FancyToast.ERROR,false);
            fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            fancyToast.show();
        }

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void clearDataSet(){
        mDataList.clear();
    }

    public void addAll(List<TestResult> testResults){
        mDataList.clear();
        testItemListOrginal.clear();
        mDataList.addAll(testResults);
        testItemListOrginal.addAll(testResults);
    }

    public void filter(String query) {
        mDataList.clear();
        if(query.isEmpty()){
            mDataList.addAll(testItemListOrginal);
        } else{
            query = query.toLowerCase();
//            for(TestItem item: testItemListOrginal){
//                if(item.ErrPerc.contains(query) || item.PF.contains(query)){
//                    mDataList.add(item);
//                }
//            }
        }
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout listItemTestResultRoot;
        TextView tvErrPerc;
        TextView tvTestTime;
        TextView tvPF_A;
        TextView tvPF_B;
        TextView tvPF_C;
        TextView tvI_A;
        TextView tvI_B;
        TextView tvI_C;
        TextView tvV_A;
        TextView tvV_B;
        TextView tvV_C;


        TextView tvRoundNum;

        public MyViewHolder(View itemView) {
            super(itemView);
            listItemTestResultRoot = itemView.findViewById(R.id.listItemTestResultRoot);
            tvRoundNum = itemView.findViewById(R.id.tvNumber);
            tvErrPerc = itemView.findViewById(R.id.tvDarsadKhata);
            tvTestTime = itemView.findViewById(R.id.tvZamaneTest);
            tvPF_A = itemView.findViewById(R.id.tvZaribTavanA);
            tvPF_B = itemView.findViewById(R.id.tvZaribTavanB);
            tvPF_C = itemView.findViewById(R.id.tvZaribTavanC);
            tvI_A = itemView.findViewById(R.id.tvJaryaneFazeA);
            tvI_B = itemView.findViewById(R.id.tvJaryaneFazeB);
            tvI_C = itemView.findViewById(R.id.tvJaryaneFazeC);
            tvV_A = itemView.findViewById(R.id.tvVoltageFazeA);
            tvV_B = itemView.findViewById(R.id.tvVoltageFazeB);
            tvV_C = itemView.findViewById(R.id.tvVoltageFazeC);

        }
    }
}
