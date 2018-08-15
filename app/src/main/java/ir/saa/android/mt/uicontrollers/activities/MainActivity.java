package ir.saa.android.mt.uicontrollers.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.components.TextViewFont;
import ir.saa.android.mt.enums.FragmentsEnum;
import ir.saa.android.mt.navigationdrawer.NavigationDrawerFragment;
//import ir.saa.android.mt.navigationdrawer.NavigationDrawerFragment;


public class MainActivity extends AppCompatActivity{

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Actionbar For This Activity
        setUpActionBar();
        //Initialize NavigationDrawer For This Activity
        setUpDrawer();
        //Initialize FragmentManager For This Activity
        G.fragmentManager = getSupportFragmentManager();
        //Initialize Actionbar For This Activity
        G.actionBar = getSupportActionBar();

        G.startFragment(FragmentsEnum.HomeFragment,false,null);


    }

    private void setUpActionBar() {
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.LEFT);
        //Initialize Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        LinearLayout actionBarLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.actionbar_custom_layout, null);
        getSupportActionBar().setCustomView(actionBarLayout,params);

        G.actionBar = getSupportActionBar();
    }

    private void setUpDrawer() {
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.nav_drwr_fragment);
        G.mDrawerLayout = findViewById(R.id.drawer_layout);
        drawerFragment.setUpDrawer(R.id.nav_drwr_fragment, G.mDrawerLayout, toolbar);
        G.fragmentDrawer = findViewById(R.id.nav_drwr_fragment);
        TextViewFont   tvfName= G.fragmentDrawer.findViewById(R.id.tvfName);
        TextViewFont   tvfRegion= G.fragmentDrawer.findViewById(R.id.tvfRegion);
        if(G.getPref("UserName")!=null){
            tvfName.setText(G.getPref("UserName"));
        }
        tvfRegion.setText("");
        if(G.getPref("RegionName")!=null){
            String RegionName=getText(R.string.CaptionRegion)+" "+G.getPref("RegionName");
            tvfRegion.setText(RegionName);
        }
        else{
            String RegionName=R.string.CaptionRegion+" ";
            tvfRegion.setText(RegionName);
        }


        // set llMenu's width to 90% of screen's width
        int width = (getResources().getDisplayMetrics().widthPixels * 90)/100;
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) G.fragmentDrawer.getLayoutParams();
        params.width = width;
        G.fragmentDrawer.setLayoutParams(params);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;

        }
        if (item != null && item.getItemId() == R.id.menuDrawer) {
            if (NavigationDrawerFragment.mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                NavigationDrawerFragment.mDrawerLayout.closeDrawer(Gravity.RIGHT);
            }
            else {
                NavigationDrawerFragment.mDrawerLayout.openDrawer(Gravity.RIGHT);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    Boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if(G.fragmentNumStack.size()>0){
            G.startFragment(G.fragmentNumStack.pop(),true,null);
        }else{
            if (doubleBackToExitPressedOnce) {
                MainActivity.this.finish();
                System.exit(0);
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(G.context,"برای خروج دوبار کلیک کنید", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(() -> doubleBackToExitPressedOnce=false, 1500);
        }
    }
}