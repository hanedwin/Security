package com.example.administrator.security.common.base;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;



public class BasePagerAdapter<T> extends PagerAdapter{

    private List<T> list;

    public BasePagerAdapter(List<T> list){
        this.list = list;
    }

    @Override
    public int getCount() {
        return  list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public  Object instantiateItem(ViewGroup container, int position) {
        T t = list.get(position);
        container.addView((View)t);
        return t;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView((View)list.get(position));
    }
}
