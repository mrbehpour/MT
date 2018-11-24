package ir.saa.android.mt.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Stack;

import ir.saa.android.mt.R;
import ir.saa.android.mt.components.ClientInfo;
import ir.saa.android.mt.components.TextViewFont;
import ir.saa.android.mt.enums.FragmentsEnum;
import ir.saa.android.mt.enums.SharePrefEnum;
import ir.saa.android.mt.uicontrollers.fragments.AmaliyatFragment;
import ir.saa.android.mt.uicontrollers.fragments.BazdidFragment;
import ir.saa.android.mt.uicontrollers.fragments.BazrasiFragment;
import ir.saa.android.mt.uicontrollers.fragments.DisplayTestFragment;
import ir.saa.android.mt.uicontrollers.fragments.HomeFragment;
import ir.saa.android.mt.uicontrollers.fragments.ModuleFragment;
import ir.saa.android.mt.uicontrollers.fragments.MoshtarakFragment;
import ir.saa.android.mt.uicontrollers.fragments.PolompFragment;
import ir.saa.android.mt.uicontrollers.fragments.PolompFragmentSave;
import ir.saa.android.mt.uicontrollers.fragments.SettingFragment;
import ir.saa.android.mt.uicontrollers.fragments.TestContorFragment;
import ir.saa.android.mt.uicontrollers.fragments.TestEnergyFragment;
import ir.saa.android.mt.uicontrollers.fragments.TestFragment;

public class G extends Application {

    public static FragmentManager fragmentManager;
    public static Context context;
    public static ActionBar actionBar;
    public static DrawerLayout mDrawerLayout;
    public static View fragmentDrawer;
    public static ClientInfo clientInfo;

    public static Integer currentFragmentNum = null;
    public static Stack<Integer> fragmentNumStack;

    private static SharedPreferences pref;
    private static SharedPreferences.Editor prefEditor;
    public static HashMap<String,Bundle> bundleHashMap=new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        fragmentNumStack = new Stack<>();
        pref = getSharedPreferences("MTPrefs", Context.MODE_PRIVATE);
        prefEditor = pref.edit();
        setPref(SharePrefEnum.AddressServer,"http://192.168.3.176:8650");
        setPref(SharePrefEnum.FontSize,"1.3");
    }

    public static void removePref(String prefName){
        prefEditor.remove(prefName);
        prefEditor.commit();
    }
    public static void setPref(String prefName,String prefValue){
        prefEditor.putString(prefName, prefValue);
        prefEditor.commit();
    }
    public static String getPref(String prefName,String defultValue){
        return pref.getString(prefName,defultValue);
    }
    public static String getPref(String prefName){
        return pref.getString(prefName,null);
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
                setActionbarTitleText((String) context.getResources().getText(R.string.Setting_G));
                break;
            case FragmentsEnum.BazdidFragment:
                targetFragment=new BazdidFragment();
                setActionbarTitleText((String) context.getResources().getText(R.string.Bazdid_G));
                break;
            case FragmentsEnum.MoshtarakFragment:
                targetFragment=new MoshtarakFragment();
                //setActionbarTitleText("مشترکین");
                break;
            case FragmentsEnum.ModuleFragment:
                targetFragment=new ModuleFragment();
                setActionbarTitleText((String) context.getResources().getText(R.string.Module_G));
                break;
            case FragmentsEnum.TestContorFragment:
                targetFragment=new TestContorFragment();
                setActionbarTitleText((String) context.getResources().getText(R.string.Test_G));
                break;
            case FragmentsEnum.DisplayTestFragment:
                targetFragment=new DisplayTestFragment();
                setActionbarTitleText((String) context.getResources().getText(R.string.TestContor_G));
                break;
            case FragmentsEnum.TestEnergyFragment:
                targetFragment=new TestEnergyFragment();
                setActionbarTitleText((String) context.getResources().getText(R.string.TestEnrege_G));
                break;
            case FragmentsEnum.AmaliyatFragment:
                targetFragment=new AmaliyatFragment();
                setActionbarTitleText((String) context.getResources().getText(R.string.ResultTest_G));
                break;
            case FragmentsEnum.PolompFragment:
                targetFragment=new PolompFragment();
                setActionbarTitleText((String) context.getResources().getText(R.string.Polomp_G));
                break;
            case FragmentsEnum.PolompFragmentSave:
                targetFragment=new PolompFragmentSave();
                //setActionbarTitleText("ذخیره نتایج پلمپ");
                break;
            case FragmentsEnum.BazrasiFragment:
                targetFragment=new BazrasiFragment();
                setActionbarTitleText((String) context.getResources().getText(R.string.Bazrasi_G));
                break;
        }

        if(targetFragment!=null){
            if(bundle!=null){
                targetFragment.setArguments(bundle);
            }
            G.fragmentManager.beginTransaction().replace(R.id.frame_container, targetFragment).disallowAddToBackStack().commit();
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

    public static void setEnableActionBar(Boolean state){
        actionBar.setDisplayHomeAsUpEnabled(state);
    }

}
