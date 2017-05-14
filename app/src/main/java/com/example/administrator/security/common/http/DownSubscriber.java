package com.example.administrator.security.common.http;

import android.content.Context;
import android.util.Log;

import org.reactivestreams.Subscription;



public  class DownSubscriber <ResponseBody extends okhttp3.ResponseBody> extends BaseSubscriber<ResponseBody> {
    public static final String TAG = "[DownLoad]";
    private DownLoadCallBack callBack;
    private String path;
    private String name;
    private Context mContext;

    public DownSubscriber(String path, String name, DownLoadCallBack callBack, Context mContext) {
        this.path = path;
        this.name = name;
        this.callBack = callBack;
        this.mContext = mContext;
    }



//    @Override
//    public void onStart() {
//        super.onStart();
//        if (callBack != null) {
//            callBack.onStart();
//        }
//    }
//
//    @Override
//    public void onCompleted() {
//        if (callBack != null) {
//            callBack.onCompleted();
//        }
//    }

    @Override
    public void onError(Throwable e) {
        Log.d( TAG, "======DownSubscriber:onError========" + e.getMessage());
        callBack.onError(e);
    }

    @Override
    public void onComplete() {
        if (callBack != null) {
            callBack.onCompleted();
        }
    }


    @Override
    public void onSubscribe(Subscription s) {
        if (callBack != null) {
            callBack.onStart();
        }
    }

    @Override
    public void onNext(ResponseBody responseBody) {

        Log.d(TAG, "=======DownSubscriber:>>>> onNext===========");

        DownLoadManager.getInstance(callBack,mContext).writeResponseBodyToDisk(path, name, responseBody);

    }
}

