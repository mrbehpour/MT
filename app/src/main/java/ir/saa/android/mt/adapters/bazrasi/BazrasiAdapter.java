package ir.saa.android.mt.adapters.bazrasi;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
import ir.saa.android.mt.components.MyCheckList;
import ir.saa.android.mt.components.MyCheckListItem;
import ir.saa.android.mt.components.MyCheckListMode;
import ir.saa.android.mt.components.MyDialog;
import ir.saa.android.mt.model.entities.AnswerGroupDtl;
import ir.saa.android.mt.viewmodels.BazrasiViewModel;

public class BazrasiAdapter extends RecyclerView.Adapter<BazrasiAdapter.MyViewHolder> {

    private List<RemarkItem> remarkItemListOrginal ;
    private List<RemarkItem> mDataList ;
    private LayoutInflater inflater;
    private Context context;
    private FragmentActivity activity;

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
            BazrasiViewModel bazrasiViewModel= ViewModelProviders.of((AppCompatActivity)context).get(BazrasiViewModel.class);
            holder.listitemRemarkRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     final  MyDialog dialog=new MyDialog(context) ;
                    if(current.answerGroupId!=null){
                        bazrasiViewModel.getAnswerGroupDtls(current.answerGroupId).observe((AppCompatActivity) context, new Observer<List<AnswerGroupDtl>>() {
                            @Override
                            public void onChanged(@Nullable List<AnswerGroupDtl> answerGroupDtls) {


                                dialog.clearAllPanel();
                                MyCheckList myCheckList=
                                        new MyCheckList(G.context
                                                ,new MyCheckListItem(answerGroupDtls.get(0).AnswerGroupDtlName,answerGroupDtls.get(0).AnswerGroupDtlID),
                                                new MyCheckListItem(answerGroupDtls.get(1).AnswerGroupDtlName,answerGroupDtls.get(1).AnswerGroupDtlID));
                                for (Integer i=2;i<answerGroupDtls.size();i++) {
                                    myCheckList.addCheckItem(new MyCheckListItem(answerGroupDtls.get(i).AnswerGroupDtlName,
                                            answerGroupDtls.get(i).AnswerGroupDtlID));
                                }
                                myCheckList.setCheckListOrientation(LinearLayout.VERTICAL)
                                        .setSelectionMode(MyCheckListMode.SingleSelection)
                                        .setCheckItemsHeight(40);
                                dialog.addButton("انصراف", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                    }
                                });
                                dialog.addButton("ذخیره", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        holder.listitemRemarkRoot.setCardBackgroundColor(Color.parseColor("#FFC9CCF1"));
                                        dialog.dismiss();


                                    }
                                });

                                dialog.setTitle(current.RemarkName)

                                        .addContentView(myCheckList)
                                        .setLeftImageTitle(G.context.getResources().getDrawable(R.drawable.account),new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                            }
                                        })

                                        .show();
                            }
                        });
                    }
                    else{

                    }


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
        CardView listitemRemarkRoot;
        TextView tvSoal;
        TextView tvId;


        public MyViewHolder(View itemView) {
            super(itemView);
            listitemRemarkRoot=itemView.findViewById(R.id.cvSoal);
            tvSoal=itemView.findViewById(R.id.tvSoal);
            tvId=itemView.findViewById(R.id.tvId);

        }
    }
}
