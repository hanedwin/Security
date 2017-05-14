package com.example.administrator.security.common.utlis;

import android.os.Looper;


public class ThreadUtil {
    /**
     * 检查是否在主线程内
     *
     * @return
     */
    public static boolean checkMain() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }
}
