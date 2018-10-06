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
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.components.MyCheckList;
import ir.saa.android.mt.components.MyCheckListItem;
import ir.saa.android.mt.components.MyCheckListMode;
import ir.saa.android.mt.components.MyDialog;
import ir.saa.android.mt.components.Tarikh;
import ir.saa.android.mt.model.entities.AnswerGroupDtl;
import ir.saa.android.mt.model.entities.InspectionAllInfo;
import ir.saa.android.mt.model.entities.InspectionDtl;
import ir.saa.android.mt.model.entities.InspectionInfo;
import ir.saa.android.mt.viewmodels.BazrasiViewModel;

import static android.content.Context.WINDOW_SERVICE;

public class BazrasiAdapter extends RecyclerView.Adapter<BazrasiAdapter.MyViewHolder> {

    private List<RemarkItem> remarkItemListOrginal ;
    private List<RemarkItem> mDataList ;
    private LayoutInflater inflater;
    private Context context;
    private FragmentActivity activity;
    BazrasiViewModel bazrasiViewModel=null;
    ArrayList<Object> objects=new ArrayList<>();
    MyCheckList myCheckList;


    public BazrasiAdapter(Context context, List<RemarkItem> data){
        this.context = context;
        inflater = LayoutInflater.from(context);
        mDataList = new ArrayList<>();
        remarkItemListOrginal = new ArrayList<>();
        mDataList.addAll(data);
        remarkItemListOrginal.addAll(data);
        this.bazrasiViewModel= ViewModelProviders.of((AppCompatActivity)context).get(BazrasiViewModel.class);
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
            holder.listitemRemarkRoot.setCardBackgroundColor(Color.parseColor("#FDFDFD"));
            if(current.remarkValue!="-1"){
                holder.listitemRemarkRoot.setCardBackgroundColor(Color.parseColor("#FFC9CCF1"));
            }
            holder.listitemRemarkRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     final  MyDialog dialog=new MyDialog(context) ;
                    InspectionAllInfo inspectionAllInfo=bazrasiViewModel.getInspectionAllInfo(G.clientInfo.ClientId,current.Id);
                    if(inspectionAllInfo!=null){
                        current.remarkValue=inspectionAllInfo.RemarkValue;
                    }else {
                        current.remarkValue="-1";
                    }
                    if(current.answerGroupId!=null){
                        bazrasiViewModel.getAnswerGroupDtls(current.answerGroupId).observe((AppCompatActivity) context, new Observer<List<AnswerGroupDtl>>() {
                            @Override
                            public void onChanged(@Nullable List<AnswerGroupDtl> answerGroupDtls) {


                                dialog.clearAllPanel();

                                 myCheckList=
                                        new MyCheckList(G.context
                                                ,new MyCheckListItem(answerGroupDtls.get(0).AnswerGroupDtlName,answerGroupDtls.get(0).AnswerGroupDtlID),
                                                new MyCheckListItem(answerGroupDtls.get(1).AnswerGroupDtlName,answerGroupDtls.get(1).AnswerGroupDtlID));
                                for (Integer i=2;i<answerGroupDtls.size();i++) {
                                    myCheckList.addCheckItem(new MyCheckListItem(answerGroupDtls.get(i).AnswerGroupDtlName,
                                            answerGroupDtls.get(i).AnswerGroupDtlID));

                                }
                                dialog.clearButtonPanel();

                                myCheckList.setCheckListOrientation(LinearLayout.VERTICAL)
                                        .setSelectionMode(MyCheckListMode.SingleSelection)
                                        .setCheckItemsHeight(80);

                                myCheckList.setSelectionByValue(Integer.valueOf(current.remarkValue));


                                dialog.addButton("ذخیره", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {


                                        objects=myCheckList.getSelectedItemsValues();
                                    if(objects.size()==0){
                                        saveBazrasi(current, null);
                                        holder.listitemRemarkRoot.setCardBackgroundColor(Color.parseColor("#FDFDFD"));
                                    }else {

                                        saveBazrasi(current, objects.get(0));
                                        holder.listitemRemarkRoot.setCardBackgroundColor(Color.parseColor("#FFC9CCF1"));
                                    }
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

    private void saveBazrasi(RemarkItem currentItem,Object objectValue){
        InspectionAllInfo inspectionAllInfo=bazrasiViewModel.getInspectionAllInfo(G.clientInfo.ClientId,currentItem.Id);
        if(inspectionAllInfo==null){
            if(objectValue!=null) {
                InspectionInfo inspectionInfo = new InspectionInfo();
                inspectionInfo.AgentID = Integer.valueOf(G.getPref("UserID"));
                inspectionInfo.ClientID = G.clientInfo.ClientId;
                inspectionInfo.SendID=G.clientInfo.SendId;
                inspectionInfo.InspectionDate = Integer.valueOf(Tarikh.getCurrentShamsidatetimeWithoutSlash().substring(0, 8));
                inspectionInfo.InspectionTime = Integer.valueOf(Tarikh.getTimeWithoutColon());
                inspectionInfo.RemarkID = currentItem.Id;
                Long inspectionInfoId = bazrasiViewModel.insertInspectionInfo(inspectionInfo);
                InspectionDtl inspectionDtl = new InspectionDtl();
                inspectionDtl.RemarkID = currentItem.Id;
                inspectionDtl.InspectionInfoID = Integer.valueOf(inspectionInfoId.toString());
                inspectionDtl.RemarkValue = String.valueOf(objectValue);
                inspectionDtl.ReadTypeID = 1;
                Long inspectionDtlId = bazrasiViewModel.insertInspectionDtl(inspectionDtl);
                if (inspectionDtlId != null) {
                    Toast.makeText((AppCompatActivity) context, "اطلاعات با موفقیت ثبت شد", Toast.LENGTH_SHORT).show();
                }
            }
        }else{

            InspectionInfo inspectionInfo=new InspectionInfo();
            inspectionInfo.AgentID=inspectionAllInfo.AgentID;
            inspectionInfo.ClientID=inspectionAllInfo.ClientID;
            inspectionInfo.InspectionDate=inspectionAllInfo.InspectionDate;
            inspectionInfo.InspectionTime=inspectionAllInfo.InspectionTime;
            inspectionInfo.RemarkID=inspectionAllInfo.RemarkID;
            inspectionInfo.InspectionInfoID=inspectionAllInfo.InspectionInfoID;
            int inspectionInfoId =bazrasiViewModel.updateInspectionInfo(inspectionInfo);
            InspectionDtl inspectionDtl=new InspectionDtl();
            inspectionDtl.RemarkID=inspectionAllInfo.RemarkID;
            inspectionDtl.InspectionInfoID=inspectionAllInfo.InspectionInfoID;
            inspectionDtl.RemarkValue=String.valueOf(objectValue);
            inspectionDtl.ReadTypeID=1;
            inspectionDtl.InspectionDtlID=inspectionAllInfo.InspectionDtlID;

            int inspectionDtlId =bazrasiViewModel.updateInspectionDtl(inspectionDtl);
            if(objectValue==null){
                bazrasiViewModel.deleteAll(inspectionInfo,inspectionDtl);
            }
            if(inspectionDtlId!=0){
                Toast.makeText((AppCompatActivity)context,"اطلاعات با موفقیت ثبت شد",Toast.LENGTH_SHORT).show();
            }

        }




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
