package com.example.udaysaikumar.clgattendance.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class BottomPagerAdapter extends FragmentPagerAdapter{
    private final List<Fragment> fragmentList=new ArrayList<>();

    public BottomPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
    public  void addFrag(Fragment fragment){
fragmentList.add(fragment);
    }
}
