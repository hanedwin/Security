package com.example.administrator.security.common.base;

import android.app.Application;
import android.content.Context;


import com.example.administrator.security.common.bdlocation.LocationService;

import java.io.File;


public class MyApplication extends Application {

    private static Context mContext;

    public LocationService locationService;

    /**
     * 获取系统Context
     * @return 返回值
     */
    public static Context getAppContext(){

        return mContext;


    }




//    public static ACache getACache() {
//        return getACache(null);
//    }
//
//    public static ACache getACache(String cacheName) {
//        File cacheDir = getAppContext().getCacheDir();
//        if (!cacheDir.exists()) {
//            cacheDir.mkdirs();
//        }
//        if (cacheName == null) {
//            return ACache.get(getAppContext());
//        } else {
//            return ACache.get(getAppContext(), cacheName);
//        }
//    }


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        locationService = new LocationService(getApplicationContext());
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        // 注册crashHandler
//        crashHandler.init(getApplicationContext());
//        //创建非多媒体文件夹
//        FileUtils.createNoMedia();
		// 发送以前没发送的报告(可选)
//		crashHandler.sendPreviousReportsToServer();
    }
}
