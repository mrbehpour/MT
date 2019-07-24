package ir.saa.android.mt.adapters.answerGroup;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.model.entities.AnswerGroup;
import ir.saa.android.mt.model.entities.AnswerGroupDtl;
import ir.saa.android.mt.viewmodels.BazrasiViewModel;


public class AnswerGroupAdapter extends RecyclerView.Adapter<AnswerGroupAdapter.MyViewHolder> {

    private List<AnswerGroupItem> clientItemListOrginal;
    private List<AnswerGroupItem> mDataList;
    private LayoutInflater inflater;
    private Context context;
    BazrasiViewModel bazrasiViewModel = null;

    public AnswerGroupAdapter(Context context, List<AnswerGroupItem> data){
        this.context = context;
        inflater = LayoutInflater.from(context);
        mDataList = new ArrayList<>();
        clientItemListOrginal = new ArrayList<>();
        mDataList.addAll(data);
        clientItemListOrginal.addAll(data);
        this.bazrasiViewModel = ViewModelProviders.of((AppCompatActivity) context).get(BazrasiViewModel.class);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.listitem_answergroup,parent,false);
        AnswerGroupAdapter.MyViewHolder myViewHolder=new MyViewHolder(view);
        return  myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
            AnswerGroupItem current=mDataList.get(position);

            holder.textView.setText(current.AnswerGroupName);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ColorPickerDialogBuilder
                            .with(context)
                            .setTitle((String) context.getText(R.string.ResultRemark))
                            .initialColor(Color.parseColor("#FFC9CCF1"))
                            .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                            .density(12)
                            .setOnColorSelectedListener(new OnColorSelectedListener() {
                                @Override
                                public void onColorSelected(int selectedColor) {

                                }
                            }).setPositiveButton(context.getText(R.string.Ok), new ColorPickerClickListener() {
                        @Override
                        public void onClick(DialogInterface d, int lastSelectedColor, Integer[] allColors) {
                            holder.imageView.setBackgroundColor(lastSelectedColor);
                            current.AnswerGroupColor=lastSelectedColor;
                            AnswerGroupDtl answerGroupDtl=new AnswerGroupDtl();
                            answerGroupDtl.AnswerGroupDtlName=current.AnswerGroupName;
                            answerGroupDtl.AnswerGroupDtlColor=current.AnswerGroupColor;
                            answerGroupDtl.AnswerGroupDtlID=current.AnswerGroupId;
                            answerGroupDtl.AnswerGroupID=11;
                            bazrasiViewModel.updateAnswerGroupDtl(answerGroupDtl);

                        }
                    }).build().show();
                }
            });
            if(current.AnswerGroupColor!=0) {
                holder.imageView.setBackgroundColor(current.AnswerGroupColor);
            }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void clearDataSet() {
        mDataList.clear();
    }

    public void addAll(List<AnswerGroupItem> clientItems) {
        mDataList.clear();
        clientItemListOrginal.clear();
        mDataList.addAll(clientItems);
        clientItemListOrginal.addAll(clientItems);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);

            imageView=(ImageView)itemView.findViewById(R.id.ivColor);
            textView=(TextView)itemView.findViewById(R.id.tvCaption);
        }
    }
}
