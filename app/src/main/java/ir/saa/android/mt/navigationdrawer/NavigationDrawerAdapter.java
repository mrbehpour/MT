package ir.saa.android.mt.navigationdrawer;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.FragmentsEnum;
import ir.saa.android.mt.uicontrollers.activities.DaryaftActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {

    private List<NavigationDrawerItem> mDataList ;
    private LayoutInflater inflater;
    private Context context;

    public NavigationDrawerAdapter(Context context, List<NavigationDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.mDataList = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_list_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        NavigationDrawerItem current = mDataList.get(position);

        holder.imgIcon.setImageResource(current.getImageId());
        holder.title.setText(current.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, holder.title.getText().toString(), Toast.LENGTH_SHORT).show();
                if(holder.title.getText().toString().compareTo(G.context.getResources().getString(R.string.menuItem1))==0){
                    G.startFragment(FragmentsEnum.BazdidFragment,false);
                }else if(holder.title.getText().toString().compareTo(G.context.getResources().getString(R.string.menuItem2))==0){

                }else if(holder.title.getText().toString().compareTo(G.context.getResources().getString(R.string.menuItem3))==0){

                }else if(holder.title.getText().toString().compareTo(G.context.getResources().getString(R.string.menuItem4))==0){
                    G.startFragment(FragmentsEnum.SettingFragment,false);
                }else if(holder.title.getText().toString().compareTo(G.context.getResources().getString(R.string.menuItem5))==0){
                    Intent intent=new Intent(G.context,DaryaftActivity.class);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    G.context.startActivity(intent);
//                    Activity activity=new DaryaftActivity();
//                    activity.startActivity(intent);

                }else if(holder.title.getText().toString().compareTo(G.context.getResources().getString(R.string.menuItem6))==0){

                }else if(holder.title.getText().toString().compareTo(G.context.getResources().getString(R.string.menuItem7))==0){

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView imgIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            imgIcon = (ImageView) itemView.findViewById(R.id.imgIcon);
        }
    }
}
