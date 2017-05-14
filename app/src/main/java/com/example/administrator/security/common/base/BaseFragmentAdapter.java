package com.example.administrator.security.common.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


public class BaseFragmentAdapter extends FragmentPagerAdapter {
    List<Fragment> mList;

    public BaseFragmentAdapter(FragmentManager fm, List<Fragment> mList) {
        super(fm);
        this.mList = mList;
    }



    @Override
    public int getCount() {
        if(mList== null)
        {
            return 0;
        }
        return mList.size();
    }

    @Override
    public Fragment getItem(int arg0) {
        return mList.get(arg0);
    }




}
