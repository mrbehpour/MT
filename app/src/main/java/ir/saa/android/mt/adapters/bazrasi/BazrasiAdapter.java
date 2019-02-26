package ir.saa.android.mt.adapters.bazrasi;

import android.app.Application;
import android.app.ProgressDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
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
import ir.saa.android.mt.components.MyCheckList;
import ir.saa.android.mt.components.MyCheckListItem;
import ir.saa.android.mt.components.MyCheckListMode;
import ir.saa.android.mt.components.MyDialog;
import ir.saa.android.mt.components.Tarikh;
import ir.saa.android.mt.model.entities.AnswerGroupDtl;
import ir.saa.android.mt.model.entities.InspectionDtl;
import ir.saa.android.mt.model.entities.InspectionInfo;
import ir.saa.android.mt.model.entities.InspectionWithAnswerGroup;
import ir.saa.android.mt.services.GPSTracker;
import ir.saa.android.mt.services.ILocationCallBack;
import ir.saa.android.mt.viewmodels.BazrasiViewModel;
import ir.saa.android.mt.viewmodels.LocationViewModel;

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
        progressDialog.setMessage(context.getResources().getText(R.string.PleaseWait_msg));
        progressDialog.setTitle(context.getResources().getText(R.string.GetLocationInProgress_msg));
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
//        locationViewModel.locationMutableLiveData.observe((LifecycleOwner) context, new Observer<Location>() {
//            @Override
//            public void onChanged(@Nullable Location locationOberver) {
//                if(locationOberver!=null){
//                    location=locationOberver;
//                    HideProgressDialog();
//                }
//            }
//        });

        GPSTracker gpsTracker=GPSTracker.getInstance(context);
        gpsTracker.setiLocationCallBack(new ILocationCallBack() {
            @Override
            public void HasLocation(Location locationCallBack) {
                if(locationCallBack!=null){
                    location=locationCallBack;
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

                location=gpsTracker.getLocation();
                //location=locationViewModel.getLocation(context);
                if(locationViewModel.isGpsEnable()){
                    if(location==null){
                        connectToModuleDialog();
                    }
                }else{
                    location=null;
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


                                dialog.addButton(G.context.getResources().getString(R.string.Save), new View.OnClickListener() {
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
                                                bazrasiViewModel.saveBazrasi(current, null,location);
                                                holder.listitemRemarkRoot.setCardBackgroundColor(Color.parseColor("#FDFDFD"));

                                            } else {

                                                boolean state=bazrasiViewModel.saveBazrasi(current, objects.get(0),location);

                                                holder.listitemRemarkRoot.setCardBackgroundColor(Color.parseColor("#FFC9CCF1"));
                                                holder.tvResult.setText(current.AnswerCaption);

                                                if(state){
                                                    //Toast.makeText((AppCompatActivity)context,G.context.getResources().getText(R.string.MessageSuccess),Toast.LENGTH_SHORT).show();
                                                    Toast fancyToast= FancyToast.makeText((AppCompatActivity)context, (String) G.context.getResources().getText(R.string.SaveOperationSuccess_msg),FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false);
                                                    fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                                    fancyToast.show();

                                                }

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
