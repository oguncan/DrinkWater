package com.ocktr.drinkwater.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.ocktr.drinkwater.fragments.intro.AboutYouFragment;
import com.ocktr.drinkwater.fragments.intro.ScheduleFragment;
import com.ocktr.drinkwater.fragments.intro.WeightFragment;

public class IntroPagerAdapter extends FragmentStatePagerAdapter {

    private int numOfTabs;
    Fragment nowFragment;
    public IntroPagerAdapter(@NonNull FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new AboutYouFragment();
            case 1:
                return new WeightFragment();
            case 2:
                return new ScheduleFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

//    @Override
//    public int getItemPosition(@NonNull Object object) {
//
//        return POSITION_UNCHANGED;
//    }
}
