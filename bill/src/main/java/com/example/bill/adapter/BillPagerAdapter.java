package com.example.bill.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.bill.fragment.BillFragment;

/**
 * @author ZZ
 */
public class BillPagerAdapter extends FragmentPagerAdapter {
    private int mYear;


    public BillPagerAdapter(FragmentManager fm, int year) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mYear = year;
    }


    public int getCount() {
        return 12;
    }

    public Fragment getItem(int position) {
        return BillFragment.newInstance(mYear*100 + (position + 1));
    }

    public CharSequence getPageTitle(int position) {
        return (position + 1)+"";
    }

}
