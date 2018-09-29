package ir.saa.android.mt.uicontrollers.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ir.saa.android.mt.R;
import ir.saa.android.mt.enums.BundleKeysEnum;

public class DisplayTestFragment extends Fragment {

    TabsPagerAdapter tabsPagerAdapter;

    public DisplayTestFragment(){

    }

    @Override
    public void onResume() {
        tabsPagerAdapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_test_tab, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        // Initilization
        ViewPager viewPager = rootView.findViewById(R.id.pagerTest);
        //viewPager.setOffscreenPageLimit(3);
        tabsPagerAdapter = new DisplayTestFragment.TabsPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(tabsPagerAdapter);

        Typeface tfByekan = ResourcesCompat.getFont(getActivity(), R.font.byekan);
        PagerTabStrip strip = viewPager.findViewById(R.id.pagerTabStripTest);
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
        private String tabtitles[] = new String[] { "تست", "نمایش تست" };

        public TabsPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);

        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new TestContorFragment();
                case 1:
                    Fragment testResultDisplay =  new TestResultDisplay();
//                    if(ClientID!=null) {
//                        Bundle bundle = new Bundle();
//                        bundle.putLong(BundleKeysEnum.ClientID, ClientID);
//                        moshtarakDetailsTabFragment.setArguments(bundle);
//                    }
                    return testResultDisplay;
                default:
                    return new TestFragment();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabtitles[position];
        }
    }
}



