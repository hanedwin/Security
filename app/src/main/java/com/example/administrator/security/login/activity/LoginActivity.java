package com.example.administrator.security.login.activity;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.security.R;
import com.example.administrator.security.R2;
import com.example.administrator.security.common.base.BaseActivity;
import com.example.administrator.security.common.base.BasePagerAdapter;
import com.example.administrator.security.login.widget.LoginItemView;
import com.example.administrator.security.login.widget.RegisterItemView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity {

    @BindView(R2.id.activity_login_view_pager)
    ViewPager viewPager;

    @BindView(R2.id.activity_login_tab_strip)
    PagerTabStrip pagerTabStrip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        List<View> viewList = new ArrayList<>();
        LoginItemView loginItemView = new LoginItemView(this);
        loginItemView.setTag(getString(R.string.login));
        RegisterItemView registerItemView = new RegisterItemView(this);
        registerItemView.setTag(getString(R.string.register));
        viewList.add(loginItemView);
        viewList.add(registerItemView);
        TabAdapter adapter = new TabAdapter(viewList);
        viewPager.setAdapter(adapter);
        pagerTabStrip.setTabIndicatorColorResource(R.color.color_white_ddffffff);
        pagerTabStrip.setTextColor(Color.WHITE);
        pagerTabStrip.setFadingEdgeLength(1000);
        pagerTabStrip.setDrawFullUnderline(false);
    }

    private class TabAdapter<T> extends BasePagerAdapter{

        private List<T> list;

        public TabAdapter(List<T> list) {
            super(list);
            this.list = list;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            T v = list.get(position);
            return (CharSequence) ((LinearLayout) v).getTag();
        }
    }




}
