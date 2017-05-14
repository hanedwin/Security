package com.example.administrator.security;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.administrator.security.common.base.BaseActivity;
import com.example.administrator.security.common.preference.SharePreferenceHelper;
import com.example.administrator.security.common.preference.SharedPreferenceUtils;
import com.example.administrator.security.common.utlis.StringUtils;
import com.example.administrator.security.login.activity.LoginActivity;
import com.example.administrator.security.main.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {

    private final static int SWITCH_MAIN_ACTIVITY = 1000;
    private final static int SWITCH_LOGIN_ACTIVITY = 1001;

    @BindView(R2.id.activity_splash_iv)
    ImageView ivSplash;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SWITCH_MAIN_ACTIVITY:
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    break;
                case SWITCH_LOGIN_ACTIVITY:
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        ivSplash.setImageResource(R.mipmap.ic_launcher);


        init();

    }

    /**
     * 开机动画初始化
     */
    private void init() {
        boolean mFirst = SharedPreferenceUtils.getIsFirst();
        if (!mFirst) {
            mHandler.sendEmptyMessageDelayed(SWITCH_LOGIN_ACTIVITY, 500);
            SharedPreferenceUtils.saveIsFirst(true);
        } else{
            String token = SharedPreferenceUtils.getToken();
            if(StringUtils.isEmpty(token)){
                mHandler.sendEmptyMessageDelayed(SWITCH_LOGIN_ACTIVITY, 500);
            }else{
                mHandler.sendEmptyMessageDelayed(SWITCH_MAIN_ACTIVITY, 500);
            }
        }
    }
}
