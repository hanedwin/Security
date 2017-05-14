package com.example.administrator.security.common.base;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;



public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //系统状态栏字体变深色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }


        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        RunningActivityManager.getInstance().addActivity(this);

//        //获取标题栏颜色
//        StatusBarCompat.setStatusBarColor(this, 0xFFA9A9A9,true);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //避免activity里面添加fragment时，在待机过程中出现异常
//        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
//        RunningActivityManager.getInstance().removeActivity(this);
        super.onDestroy();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



}
