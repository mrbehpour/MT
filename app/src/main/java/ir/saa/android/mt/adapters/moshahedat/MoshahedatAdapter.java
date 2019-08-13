package ir.saa.android.mt.adapters.moshahedat;

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
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.shashank.sony.fancytoastlib.FancyToast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.adapters.bazrasi.BazrasiAdapter;
import ir.saa.android.mt.adapters.bazrasi.RemarkItem;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.components.MyCheckList;
import ir.saa.android.mt.components.MyCheckListItem;
import ir.saa.android.mt.components.MyCheckListMode;
import ir.saa.android.mt.components.MyDialog;
import ir.saa.android.mt.model.entities.AnswerGroupDtl;
import ir.saa.android.mt.model.entities.InspectionWithAnswerGroup;
import ir.saa.android.mt.services.GPSTracker;
import ir.saa.android.mt.services.ILocationCallBack;
import ir.saa.android.mt.viewmodels.BazrasiViewModel;
import ir.saa.android.mt.viewmodels.LocationViewModel;

public class MoshahedatAdapter extends RecyclerView.Adapter<MoshahedatAdapter.MyViewHolder> {
    private List<MoshahedatItem> remarkItemListOrginal;
    private List<MoshahedatItem> mDataList;
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
    boolean state=false;
    EditText edtAnwer;
    ArrayList<Object> getObjects;

    public MoshahedatAdapter(Context context, List<MoshahedatItem> data){
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
        View view = inflater.inflate(R.layout.listitem_moshahedat, parent, false);

        MoshahedatAdapter.MyViewHolder holder = new MoshahedatAdapter.MyViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MoshahedatItem current = mDataList.get(position);
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

        if (current.remarkValue == "-1") {
            holder.listitemRemarkRoot.setCardBackgroundColor(Color.parseColor("#FDFDFD"));

        }else{

            holder.listitemRemarkRoot.setCardBackgroundColor(Color.parseColor("#FFC9CCF1"));

        }

        holder.listitemRemarkRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                objects=new ArrayList<>();
                gpsTracker.getLocation();
                //location=locationViewModel.getLocation(context);
                if (locationViewModel.isGpsEnable()) {
                    if (location == null) {
                        connectToModuleDialog();
                    }
                } else {
                    location = null;
                }
                if (location != null) {
                    final MyDialog myDialog = new MyDialog(context);
                    myDialog.setTitle(current.RemarkName);
                    List<AnswerGroupDtl> answerGroupDtls =bazrasiViewModel.getAnswerGroupDtls(current.answerGroupId);
                            myCheckList=new MyCheckList(context
                                    ,new MyCheckListItem("",""),
                                    new MyCheckListItem("",""));
                        myCheckList.removeAllCkeckItems();

                        for(AnswerGroupDtl answerGroupDtl:answerGroupDtls){
                            myCheckList.addCheckItem(new MyCheckListItem(answerGroupDtl.AnswerGroupDtlName,answerGroupDtl.AnswerGroupDtlID));
                        }

                    myDialog.addButton(G.context.getResources().getString(R.string.Cancel), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            myDialog.dismiss();
                        }
                    });
                    myDialog.addButton(G.context.getResources().getString(R.string.Save), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            myDialog.dismiss();
                            switch (current.PropertyTypeID) {
                                case 1:
                                    break;
                                case 2:
                                    edtAnwer= (EditText) myDialog.getDialog().findViewById(R.id.edtAnwer);
                                    state=bazrasiViewModel.saveBazrasiRemarkShenavar(current
                                            ,edtAnwer.getText().toString().equals("")?null:edtAnwer.getText().toString(),location);
                                    if(state){
                                        holder.tvResult.setText(edtAnwer.getText().toString());
                                        holder.listitemRemarkRoot.setCardBackgroundColor(Color.parseColor("#FFC9CCF1"));
                                        current.remarkValue=edtAnwer.getText().toString();
                                    }else{
                                        holder.listitemRemarkRoot.setCardBackgroundColor(Color.parseColor("#FDFDFD"));
                                        holder.tvResult.setText("");
                                        current.remarkValue="-1";
                                    }
                                    break;
                                case 3:
                                     edtAnwer = (EditText) myDialog.getDialog().findViewById(R.id.edtAnwer);
                                    state=bazrasiViewModel.saveBazrasiRemarkShenavar(current
                                            ,edtAnwer.getText().toString().equals("")?null:edtAnwer.getText().toString(),location);
                                    if(state){
                                        holder.tvResult.setText(edtAnwer.getText().toString());
                                        holder.listitemRemarkRoot.setCardBackgroundColor(Color.parseColor("#FFC9CCF1"));
                                        current.remarkValue=edtAnwer.getText().toString();
                                    }else{
                                        holder.listitemRemarkRoot.setCardBackgroundColor(Color.parseColor("#FDFDFD"));
                                        holder.tvResult.setText("");
                                        current.remarkValue="-1";
                                    }
                                    break;
                                case 4:
                                    edtAnwer = (EditText) myDialog.getDialog().findViewById(R.id.edtAnwer);
                                    state=bazrasiViewModel.saveBazrasiRemarkShenavar(current
                                            ,edtAnwer.getText().toString().equals("")?null:edtAnwer.getText().toString(),location);
                                    if(state){
                                        holder.tvResult.setText(edtAnwer.getText().toString());
                                        holder.listitemRemarkRoot.setCardBackgroundColor(Color.parseColor("#FFC9CCF1"));
                                        current.remarkValue=edtAnwer.getText().toString();
                                    }else{
                                        holder.listitemRemarkRoot.setCardBackgroundColor(Color.parseColor("#FDFDFD"));
                                        holder.tvResult.setText("");
                                        current.remarkValue="-1";
                                    }
                                    break;
                                case 5:
                                    edtAnwer = (EditText) myDialog.getDialog().findViewById(R.id.edtAnwer);
                                    state=bazrasiViewModel.saveBazrasiRemarkShenavar(current
                                            ,edtAnwer.getText().toString().equals("")?null:edtAnwer.getText().toString(),location);
                                    if(state){
                                        holder.tvResult.setText(edtAnwer.getText().toString());
                                        holder.listitemRemarkRoot.setCardBackgroundColor(Color.parseColor("#FFC9CCF1"));
                                        current.remarkValue=edtAnwer.getText().toString();
                                    }else{
                                        holder.listitemRemarkRoot.setCardBackgroundColor(Color.parseColor("#FDFDFD"));
                                        holder.tvResult.setText("");
                                        current.remarkValue="-1";
                                    }
                                    break;
                                case 6:
                                    objects=myCheckList.getSelectedItemsValues();
                                    Object objValue=null;
                                        if(objects.size()!=0){
                                            objValue=objects.get(0);
                                        }
                                        if(objValue==null){
                                            current.remarkValue="-1";
                                        }
                                        state=bazrasiViewModel.saveBazrasiRemarkShenavar(current,objValue,location);
                                        if(state){
                                            holder.tvResult.setText(myCheckList.getSelectedItemsText().get(0));
                                            holder.listitemRemarkRoot.setCardBackgroundColor(Color.parseColor("#FFC9CCF1"));
                                            current.remarkValue=objValue.toString();
                                            current.AnswerCaption=holder.tvResult.getText().toString();
                                        }else{
                                            holder.listitemRemarkRoot.setCardBackgroundColor(Color.parseColor("#FDFDFD"));
                                            holder.tvResult.setText("");
                                        }

                                    break;
                                case 7:
                                    objects=myCheckList.getSelectedItemsValues();
//                                    if(objects.size()!=0){
                                        String ValForSave="";
                                        String CaptionForDisplay="";
                                        for(Object o:objects){
                                            ValForSave+=o.toString()+",";
                                        }
                                        if(objects.size()==0){
                                            current.remarkValue="-1";
                                            ValForSave=",";
                                        }
                                        if(ValForSave!="") {
                                            state = bazrasiViewModel.saveBazrasiRemarkShenavar(current, ValForSave.substring(0, ValForSave.length() - 1).equals("")?null:ValForSave.substring(0, ValForSave.length() - 1)
                                                    , location);
                                        }
                                        if(state){
                                            for(String s:myCheckList.getSelectedItemsText()){
                                                CaptionForDisplay+=" - "+s;
                                            }
                                            holder.tvResult.setText(CaptionForDisplay.substring(3));
                                            holder.listitemRemarkRoot.setCardBackgroundColor(Color.parseColor("#FFC9CCF1"));
                                            current.remarkValue=ValForSave.substring(0,ValForSave.length()-1);
                                        }else{
                                            holder.listitemRemarkRoot.setCardBackgroundColor(Color.parseColor("#FDFDFD"));
                                            holder.tvResult.setText("");
                                        }
                                    //}
                                    break;
                                case 8:
                                    edtAnwer = (EditText) myDialog.getDialog().findViewById(R.id.edtAnwer);
                                    state=bazrasiViewModel.saveBazrasiRemarkShenavar(current
                                            ,edtAnwer.getText().toString().equals("")?null:edtAnwer.getText().toString(),location);
                                    if(state){
                                        holder.tvResult.setText(edtAnwer.getText().toString());
                                        holder.listitemRemarkRoot.setCardBackgroundColor(Color.parseColor("#FFC9CCF1"));
                                        current.remarkValue=edtAnwer.getText().toString();
                                    }else{
                                        holder.listitemRemarkRoot.setCardBackgroundColor(Color.parseColor("#FDFDFD"));
                                        holder.tvResult.setText("");
                                        current.remarkValue="-1";
                                    }
                                    break;
                            }
                            if(state){
                                //Toast.makeText((AppCompatActivity)context,G.context.getResources().getText(R.string.MessageSuccess),Toast.LENGTH_SHORT).show();
                                Toast fancyToast= FancyToast.makeText((AppCompatActivity)context, (String) G.context.getResources().getText(R.string.SaveOperationSuccess_msg),FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false);
                                fancyToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                                fancyToast.show();

                            }
                        }
                    });
                    switch (current.PropertyTypeID){
                        case 1:
                            break;
                        case 2:

                            myDialog.addContentXml(R.layout.layout_dialog);
                            EditText edtAnwer = (EditText) myDialog.getDialog().findViewById(R.id.edtAnwer);
                            edtAnwer.setInputType(InputType.TYPE_CLASS_NUMBER);
                            if(current.remarkValue!="-1"){
                                edtAnwer.setText(current.remarkValue);
                            } else {
                                edtAnwer.setText("");
                            }
                            myDialog.show();
                            break;
                        case 3:
                            myDialog.addContentXml(R.layout.layout_dialog);
                            edtAnwer = (EditText) myDialog.getDialog().findViewById(R.id.edtAnwer);
                            edtAnwer.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                            if(current.remarkValue!="-1"){
                                edtAnwer.setText(current.remarkValue);
                            }
                            else {
                                edtAnwer.setText("");
                            }
                            myDialog.show();
                            break;
                        case 4:
                            myDialog.addContentXml(R.layout.layout_dialog);
                            edtAnwer = (EditText) myDialog.getDialog().findViewById(R.id.edtAnwer);
                            edtAnwer.setInputType(InputType.TYPE_CLASS_TEXT);
                            if(current.remarkValue!="-1"){
                                edtAnwer.setText(current.remarkValue);
                            }
                            else {
                                edtAnwer.setText("");
                            }
                            myDialog.show();
                            break;
                        case 5:
                            myDialog.addContentXml(R.layout.layout_dialog);
                            edtAnwer = (EditText) myDialog.getDialog().findViewById(R.id.edtAnwer);
                            edtAnwer.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
                            if(current.remarkValue!="-1"){
                                edtAnwer.setText(current.remarkValue);
                            }
                            else {
                                edtAnwer.setText("");
                            }
                            myDialog.show();
                            break;
                        case 6:


                            myCheckList.setCheckListOrientation(LinearLayout.VERTICAL)
                                    .setSelectionMode(MyCheckListMode.SingleSelection)
                                    .setCheckItemsHeight(80);
                            myDialog.addContentView(myCheckList);
                            if(current.remarkValue!="-1"){
                                myCheckList.setSelectionByValue(Integer.valueOf(current.remarkValue));
                            }
                            myDialog.show();
                            break;
                        case 7:

                            myCheckList.setCheckListOrientation(LinearLayout.VERTICAL)
                                    .setSelectionMode(MyCheckListMode.MultiSelection)
                                    .setCheckItemsHeight(80);
                            myDialog.addContentView(myCheckList);
                            if(current.remarkValue!="-1"){
                                for (String s:current.remarkValue.split(",")){
                                    myCheckList.setSelectionByValue(Integer.valueOf(s));
                                }
                            }

                            myDialog.show();
                            break;
                        case 8:

                            myDialog.addContentXml(R.layout.layout_dialog);
                            edtAnwer = (EditText) myDialog.getDialog().findViewById(R.id.edtAnwer);
                            edtAnwer.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME);
                            if(current.remarkValue!="-1"){
                                edtAnwer.setText(current.remarkValue);
                            }else {
                                edtAnwer.setText("");
                            }
                            myDialog.show();
                            break;
                    }

                } else {
                    locationViewModel.trunOnGps((AppCompatActivity) context);


                }

            }


        });

        holder.tvId.setText(current.Id.toString());
        holder.tvSoal.setText(current.RemarkName);
        String RemarkDisplayValue=holder.tvResult.getText().toString();
        switch (current.PropertyTypeID){
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 8:
                RemarkDisplayValue=current.remarkValue;
                break;
            case 6:
                if(current.remarkValue!="-1") {

                    if (current.AnswerCaption != "") {
                        RemarkDisplayValue = current.AnswerCaption;
                    }
                }
                break;
            case 7:
                ArrayList<Integer>  integerArrayList=new ArrayList<>();
                if(current.remarkValue!="-1") {
                    for(String s:current.remarkValue.split(",")){
                        integerArrayList.add(Integer.valueOf(s));
                    }
                    List<AnswerGroupDtl> answerGroupDtls = bazrasiViewModel.getAnswerGroupDtlByAnswerGroupIds(integerArrayList);

                    for(AnswerGroupDtl answerGroupDtl:answerGroupDtls){
                        RemarkDisplayValue+=" - "+answerGroupDtl.AnswerGroupDtlName;
                    }
                    RemarkDisplayValue=RemarkDisplayValue.substring(3);
                }
                break;

        }
        holder.tvResult.setText(RemarkDisplayValue.equals("-1")?"":RemarkDisplayValue);

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }
    public void addAll(List<MoshahedatItem> remmarkItems) {
        mDataList.clear();
        remarkItemListOrginal.clear();
        mDataList.addAll(remmarkItems);
        remarkItemListOrginal.addAll(remmarkItems);
    }

    public void clearDataSet() {
        mDataList.clear();
    }

    class MyViewHolder extends RecyclerView.ViewHolder  {
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
