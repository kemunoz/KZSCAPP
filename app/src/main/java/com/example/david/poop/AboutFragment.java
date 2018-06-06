package com.example.david.poop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

/**
 * Created by David on 4/20/2018.
 */

public class AboutFragment extends Fragment {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);
        Log.d("LOL1", "wassup yo1");

//        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
//        Toolbar.setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());
        Log.d("LOL2", "wassup yo2");
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) rootView.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        Log.d("LOL3", "wassup yo3");
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            Log.d("LOL4", "wassup yo4");
        }

        @Override
        public Fragment getItem(int position) {
            Log.d("GETITEM", "GETITEM!!!!");
            switch (position){
                case 0:
                    Log.d("ZERO", "INSIDE TAB1");
                    Tab1Purpose tab1 = new Tab1Purpose();
                    return tab1;
                case 1:
                    Tab2MissionStatement tab2 =new Tab2MissionStatement();
                    return tab2;
                case 2:
                    Tab3Contact tab3 =new Tab3Contact();
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position){
            switch (position){
                case 0:
                    return "Purpose";
                case 1:
                    return "Mission Statement";
                case 2:
                    return "Contact";
            }
            return null;
        }
    }

}
