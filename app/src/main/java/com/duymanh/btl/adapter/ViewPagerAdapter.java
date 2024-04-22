package com.duymanh.btl.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.duymanh.btl.fragment.FragmentHome;
import com.duymanh.btl.fragment.FragmentMess;
import com.duymanh.btl.fragment.FragmentSchedule;
import com.duymanh.btl.fragment.FragmentUser;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {


    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new FragmentHome();
            case 1: return new FragmentSchedule();
            case 2: return new FragmentMess();
            case 3: return new FragmentUser();
            default: return new FragmentHome();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
