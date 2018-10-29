package ir.saa.android.mt.uicontrollers.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ir.saa.android.mt.R;
import ir.saa.android.mt.application.G;
import ir.saa.android.mt.enums.BundleKeysEnum;

public class MoshtarakFragment extends Fragment
{
    TabsPagerAdapter mAdapter;
    public Long ClientID = null;
    public MoshtarakFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        mAdapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_moshtarak, container, false);
        if(ClientID==null){
            Bundle bundle = this.getArguments();
            if (bundle != null) {
                ClientID = bundle.getLong(BundleKeysEnum.ClientID);
            }
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        // Initilization
        ViewPager viewPager = rootView.findViewById(R.id.pager);
        //viewPager.setOffscreenPageLimit(3);
        mAdapter = new TabsPagerAdapter(getActivity().getSupportFragmentManager(),ClientID);
        viewPager.setAdapter(mAdapter);

        Typeface tfByekan = ResourcesCompat.getFont(getActivity(), R.font.byekan);
        PagerTabStrip strip = viewPager.findViewById(R.id.pagerTabStrip);
        for (int i = 0; i < strip.getChildCount(); i++) {
            View nextChild = strip.getChildAt(i);
            if (nextChild instanceof TextView) {
                TextView textViewToConvert = (TextView) nextChild;
                textViewToConvert.setTypeface(tfByekan);
                //textViewToConvert.setTypeface(tfByekan,Typeface.BOLD);
                textViewToConvert.setTextSize(17);
            }
        }
    }

    public static class TabsPagerAdapter extends FragmentStatePagerAdapter {
        final int PAGE_COUNT = 2;
        private String tabtitles[] = new String[] {(String) G.context.getResources().getText(R.string.Tab_Amaliyat),
                (String) G.context.getResources().getText(R.string.Tab_Detail) };
        Long ClientID = null;
        public TabsPagerAdapter(FragmentManager fragmentManager,Long clientID) {
            super(fragmentManager);
            ClientID = clientID;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new MoshtarakOperationsTabFragment();
                case 1:
                    Fragment moshtarakDetailsTabFragment =  new MoshtarakDetailsTabFragment();
                    if(ClientID!=null) {
                        Bundle bundle = new Bundle();
                        bundle.putLong(BundleKeysEnum.ClientID, ClientID);
                        moshtarakDetailsTabFragment.setArguments(bundle);
                    }
                    return moshtarakDetailsTabFragment;
                default:
                    return new MoshtarakOperationsTabFragment();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabtitles[position];
        }
    }

}
