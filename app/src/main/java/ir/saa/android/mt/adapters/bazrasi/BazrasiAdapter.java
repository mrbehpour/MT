package ir.saa.android.mt.adapters.bazrasi;

import android.app.ProgressDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.location.Location;
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

import java.io.NotSerializableException;
import java.util.ArrayList;
import java.util.HashMap;
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
import ir.saa.android.mt.model.entities.InspectionWithAnswerGroup;
import ir.saa.android.mt.viewmodels.BazrasiViewModel;
import ir.saa.android.mt.viewmodels.LocationViewModel;

import static android.content.Context.WINDOW_SERVICE;

public class BazrasiAdapter extends RecyclerView.Adapter<BazrasiAdapter.MyViewHolder> {

    private List<RemarkItem> remarkItemListOrginal;
    private List<RemarkItem> mDataList;
    private LayoutInflater inflater;
    private Context context;
    private FragmentActivity activity;
    BazrasiViewModel bazrasiViewModel = null;
    LocationViewModel locationViewModel=null;
    ArrayList<Object> objects = new ArrayList<>();
    MyCheckList myCheckList;
    Location location;
    Boolean isLocation;
    ProgressDialog progressDialog;


    public BazrasiAdapter(Context context, List<RemarkItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        mDataList = new ArrayList<>();
        remarkItemListOrginal = new ArrayList<>();
        mDataList.addAll(data);
        remarkItemListOrginal.addAll(data);
        this.bazrasiViewModel = ViewModelProviders.of((AppCompatActivity) context).get(BazrasiViewModel.class);
        this.locationViewModel=ViewModelProviders.of((AppCompatActivity) context).get(LocationViewModel.class);
        isLocation=false;

    }
    private void connectToModuleDialog(){

        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage(context.getResources().getText(R.string.Wait_Location));
        progressDialog.setTitle(context.getResources().getText(R.string.ValidationLocation));
        progressDialog.setCancelable(true);
        progressDialog.show();

    }

    public void HideProgressDialog(){
        if(progressDialog!=null) progressDialog.dismiss();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.listitem_bazrasi, parent, false);

        BazrasiAdapter.MyViewHolder holder = new BazrasiAdapter.MyViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RemarkItem current = mDataList.get(position);
        locationViewModel.locationMutableLiveData.observe((LifecycleOwner) context, new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location locationOberver) {
                if(locationOberver!=null){
                    location=locationOberver;
                    HideProgressDialog();
                }
            }
        });

        holder.listitemRemarkRoot.setCardBackgroundColor(Color.parseColor("#FDFDFD"));
        if (current.remarkValue != "-1") {
            holder.listitemRemarkRoot.setCardBackgroundColor(Color.parseColor("#FFC9CCF1"));

        }
        holder.listitemRemarkRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                location=locationViewModel.getLocation(context);
                if(locationViewModel.isGpsEnable()){
                    if(location==null){
                        connectToModuleDialog();
                    }
                }

                if (location != null) {
                    final MyDialog dialog = new MyDialog(context);
                    InspectionWithAnswerGroup inspectionAllInfo = bazrasiViewModel.getInspectionAllInfo(G.clientInfo.ClientId, current.Id, current.answerGroupId);
                    if (inspectionAllInfo != null) {
                        current.remarkValue = inspectionAllInfo.RemarkValue;
                    } else {
                        current.remarkValue = "-1";
                    }
                    if (current.answerGroupId != null) {
                        bazrasiViewModel.getAnswerGroupDtls(current.answerGroupId).observe((AppCompatActivity) context, new Observer<List<AnswerGroupDtl>>() {
                            @Override
                            public void onChanged(@Nullable List<AnswerGroupDtl> answerGroupDtls) {


                                dialog.clearAllPanel();

                                myCheckList =
                                        new MyCheckList(G.context
                                                , new MyCheckListItem(answerGroupDtls.get(0).AnswerGroupDtlName, answerGroupDtls.get(0).AnswerGroupDtlID),
                                                new MyCheckListItem(answerGroupDtls.get(1).AnswerGroupDtlName, answerGroupDtls.get(1).AnswerGroupDtlID));
                                for (Integer i = 2; i < answerGroupDtls.size(); i++) {
                                    myCheckList.addCheckItem(new MyCheckListItem(answerGroupDtls.get(i).AnswerGroupDtlName,
                                            answerGroupDtls.get(i).AnswerGroupDtlID));

                                }
                                dialog.clearButtonPanel();

                                myCheckList.setCheckListOrientation(LinearLayout.VERTICAL)
                                        .setSelectionMode(MyCheckListMode.SingleSelection)
                                        .setCheckItemsHeight(80);

                                myCheckList.setSelectionByValue(Integer.valueOf(current.remarkValue));


                                dialog.addButton(G.context.getResources().getString(R.string.BazrasiAdapter_BtnSave), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {



                                            objects = myCheckList.getSelectedItemsValues();
                                            if (myCheckList.getSelectedCheckListItems().size() != 0) {
                                                current.AnswerCaption = myCheckList.getSelectedCheckListItems().get(0).Text;
                                            } else {
                                                current.AnswerCaption = "";
                                                holder.tvResult.setText(current.AnswerCaption);
                                            }


                                            if (objects.size() == 0) {
                                                saveBazrasi(current, null);
                                                holder.listitemRemarkRoot.setCardBackgroundColor(Color.parseColor("#FDFDFD"));

                                            } else {

                                                saveBazrasi(current, objects.get(0));
                                                holder.listitemRemarkRoot.setCardBackgroundColor(Color.parseColor("#FFC9CCF1"));
                                                holder.tvResult.setText(current.AnswerCaption);

                                            }
                                            dialog.dismiss();



                                    }
                                });

                                dialog.setTitle(current.RemarkName)

                                        .addContentView(myCheckList)
                                        .setLeftImageTitle(G.context.getResources().getDrawable(R.drawable.account), new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                            }
                                        })

                                        .show();

                            }
                        });
                    }
                }else{
                    locationViewModel.trunOnGps((AppCompatActivity) context);



                }


                }

        });

        holder.tvId.setText(current.Id.toString());
        holder.tvSoal.setText(current.RemarkName);
        holder.tvResult.setText(current.AnswerCaption);

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void addAll(List<RemarkItem> remmarkItems) {
        mDataList.clear();
        remarkItemListOrginal.clear();
        mDataList.addAll(remmarkItems);
        remarkItemListOrginal.addAll(remmarkItems);
    }

    public void clearDataSet() {
        mDataList.clear();
    }

    private void saveBazrasi(RemarkItem currentItem, Object objectValue) {


            InspectionWithAnswerGroup inspectionAllInfo = bazrasiViewModel.getInspectionAllInfo(G.clientInfo.ClientId, currentItem.Id, currentItem.answerGroupId);
        if (inspectionAllInfo == null) {
            if (objectValue != null) {
                InspectionInfo inspectionInfo = new InspectionInfo();
                inspectionInfo.AgentID = Integer.valueOf(G.getPref("UserID"));
                inspectionInfo.ClientID = G.clientInfo.ClientId;
                inspectionInfo.SendID = G.clientInfo.SendId;

                inspectionInfo.FollowUpCode = G.clientInfo.FollowUpCode;


                inspectionInfo.InspectionDate = Integer.valueOf(Tarikh.getCurrentShamsidatetimeWithoutSlash().substring(0, 8));
                inspectionInfo.InspectionTime = Integer.valueOf(Tarikh.getTimeWithoutColon());
                //inspectionInfo.RemarkID = currentItem.Id;
                Long inspectionInfoId = bazrasiViewModel.insertInspectionInfo(inspectionInfo);
                InspectionDtl inspectionDtl = new InspectionDtl();
                inspectionDtl.RemarkID = currentItem.Id;
                inspectionDtl.InspectionInfoID = Integer.valueOf(inspectionInfoId.toString());
                inspectionDtl.RemarkValue = String.valueOf(objectValue);
                inspectionDtl.ReadTypeID = 1;
                inspectionDtl.Lat=String.valueOf(location.getLatitude());
                inspectionDtl.Long=String.valueOf(location.getLongitude());
                inspectionDtl.AgentID = Integer.valueOf(G.getPref("UserID"));
                Long inspectionDtlId = bazrasiViewModel.insertInspectionDtl(inspectionDtl);
                if (inspectionDtlId != null) {
                    Toast.makeText((AppCompatActivity) context, G.context.getResources().getText(R.string.MessageSuccess), Toast.LENGTH_SHORT).show();
                }
            }
        } else {

            InspectionInfo inspectionInfo = new InspectionInfo();
            inspectionInfo.AgentID = inspectionAllInfo.AgentID;
            inspectionInfo.ClientID = inspectionAllInfo.ClientID;
            inspectionInfo.InspectionDate = inspectionAllInfo.InspectionDate;
            inspectionInfo.InspectionTime = inspectionAllInfo.InspectionTime;
            //inspectionInfo.RemarkID=inspectionAllInfo.RemarkID;
            inspectionInfo.InspectionInfoID = inspectionAllInfo.InspectionInfoID;
            int inspectionInfoId = bazrasiViewModel.updateInspectionInfo(inspectionInfo);
            InspectionDtl inspectionDtl = new InspectionDtl();
            inspectionDtl.RemarkID = inspectionAllInfo.RemarkID;
            inspectionDtl.InspectionInfoID = inspectionAllInfo.InspectionInfoID;
            inspectionDtl.RemarkValue = String.valueOf(objectValue);
            inspectionDtl.ReadTypeID = 1;
            inspectionDtl.Lat=String.valueOf(location.getLatitude());
            inspectionDtl.Long=String.valueOf(location.getLongitude());
            inspectionDtl.InspectionDtlID = inspectionAllInfo.InspectionDtlID;
            inspectionDtl.AgentID = Integer.valueOf(G.getPref("UserID"));
            int inspectionDtlId = bazrasiViewModel.updateInspectionDtl(inspectionDtl);
            if (objectValue == null) {
                bazrasiViewModel.deleteAll(inspectionInfo, inspectionDtl);
            }
            if (inspectionDtlId != 0) {
                Toast.makeText((AppCompatActivity) context, G.context.getResources().getText(R.string.MessageSuccess), Toast.LENGTH_SHORT).show();
            }

        }




    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CardView listitemRemarkRoot;
        TextView tvSoal;
        TextView tvId;
        TextView tvResult;


        public MyViewHolder(View itemView) {
            super(itemView);
            listitemRemarkRoot = itemView.findViewById(R.id.cvSoal);
            tvSoal = itemView.findViewById(R.id.tvSoal);
            tvId = itemView.findViewById(R.id.tvId);
            tvResult = itemView.findViewById(R.id.tvResult);

        }
    }
}
