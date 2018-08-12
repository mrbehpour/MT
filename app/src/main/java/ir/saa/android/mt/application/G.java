package ir.saa.android.mt.application;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.TextView;

import java.util.Stack;

import ir.saa.android.mt.R;
import ir.saa.android.mt.components.TextViewFont;
import ir.saa.android.mt.enums.FragmentsEnum;
import ir.saa.android.mt.uicontrollers.fragments.BazdidFragment;
import ir.saa.android.mt.uicontrollers.fragments.HomeFragment;
import ir.saa.android.mt.uicontrollers.fragments.MoshtarakFragment;
import ir.saa.android.mt.uicontrollers.fragments.SettingFragment;

public class G extends Application {

    public static FragmentManager fragmentManager;
    public static Context context;
    public static ActionBar actionBar;
    public static DrawerLayout mDrawerLayout;
    public static View fragmentDrawer;

    public static Integer currentFragmentNum = null;
    public static Stack<Integer> fragmentNumStack;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        fragmentNumStack = new Stack<>();
    }

    public static void startFragment(int targetFragmentNum, boolean backward, Bundle bundle) {
        //for singleton issue
        if(currentFragmentNum != null && currentFragmentNum == targetFragmentNum) {
            //close navigation drawer if is open
            if (mDrawerLayout.isDrawerOpen(G.fragmentDrawer))
                mDrawerLayout.closeDrawer(G.fragmentDrawer);
            return;
        }
        if(!backward){
            //for first time
            if (currentFragmentNum != null)
                fragmentNumStack.add(currentFragmentNum);
        }
        //check target and go to it
        Fragment targetFragment=null;
        switch (targetFragmentNum) {
            case FragmentsEnum.HomeFragment:
                targetFragment=new HomeFragment();
                setActionbarTitleText("");
                break;
            case FragmentsEnum.SettingFragment:
                targetFragment=new SettingFragment();
                setActionbarTitleText("تنظیمات");
                break;
            case FragmentsEnum.BazdidFragment:
                targetFragment=new BazdidFragment();
                setActionbarTitleText("بازدید");
                break;
            case FragmentsEnum.MoshtarakFragment:
                targetFragment=new MoshtarakFragment();
                break;
        }
        if(targetFragment!=null){
            if(bundle!=null){
                targetFragment.setArguments(bundle);
            }
            G.fragmentManager.beginTransaction().replace(R.id.frame_container, targetFragment).commit();
        }
        //update current fragment number
        currentFragmentNum = targetFragmentNum;
        //close navigation drawer if is open
        if (mDrawerLayout.isDrawerOpen(G.fragmentDrawer))
            mDrawerLayout.closeDrawer(G.fragmentDrawer);
        //for showing back button on actionbar
        if(fragmentNumStack.size()>0)
            actionBar.setDisplayHomeAsUpEnabled(true);
        else
            actionBar.setDisplayHomeAsUpEnabled(false);
    }

    public static void setActionbarTitleText(String strTitle)
    {
        TextView tvActionbarTitle = G.actionBar.getCustomView().findViewById(R.id.tvActionbarTitle);
        tvActionbarTitle.setText(strTitle);
    }

}
