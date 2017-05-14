package com.example.administrator.security.main.activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.security.R;
import com.example.administrator.security.R2;
import com.example.administrator.security.common.base.BaseActivity;
import com.example.administrator.security.common.base.BaseFragmentAdapter;
import com.example.administrator.security.common.widget.CustomViewPager;
import com.example.administrator.security.main.fragment.FriendsFragment;
import com.example.administrator.security.main.fragment.MessageFragment;
import com.example.administrator.security.main.fragment.PersonalFragment;
import com.example.administrator.security.main.fragment.SOSFragment;
import com.example.administrator.security.main.fragment.SightFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R2.id.activity_main_sos_rb)
    RadioButton rbSOS;
    @BindView(R2.id.activity_main_friends_rb)
    RadioButton rbFriends;
    @BindView(R2.id.activity_main_sight_rb)
    RadioButton rbSight;
    @BindView(R2.id.activity_main_message_rb)
    RadioButton rbMessage;
    @BindView(R2.id.activity_main_personal_rb)
    RadioButton rbPersonal;
    @BindView(R2.id.activity_main_rg)
    RadioGroup rgMain;
    @BindView(R2.id.activity_main_cvp)
    CustomViewPager viewPager;


    private List<Fragment> viewList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        viewList = new ArrayList<>();
        viewList.add(new SOSFragment());
        viewList.add(new FriendsFragment());
        viewList.add(new SightFragment());
        viewList.add(new MessageFragment());
        viewList.add(new PersonalFragment());
        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getSupportFragmentManager(),viewList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(viewList.size());
        rgMain.check(R.id.activity_main_sos_rb);

        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.activity_main_sos_rb:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.activity_main_friends_rb:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.activity_main_sight_rb:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.activity_main_message_rb:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.activity_main_personal_rb:
                        viewPager.setCurrentItem(4);
                        break;
                }
            }
        });

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
