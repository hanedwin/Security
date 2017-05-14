package com.example.administrator.security.common.http;


import android.util.Log;

import com.example.administrator.security.common.http.callback.ResponseCallBack;
import com.example.administrator.security.common.http.exception.FormatException;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.reactivestreams.Subscription;

import java.lang.reflect.Type;

import okhttp3.ResponseBody;

/**
 * <p>
 * 服务器返回格式统一处理
 * <p>
 * 只返回data中的数据
 */

public class NetSubscriber<T> extends BaseSubscriber<ResponseBody> {


    public static final String TAG = "[NetSubscriber]";

    private Type finalNeedType;
    private ResponseCallBack<T> callBack;

    public NetSubscriber(Type finalNeedType, ResponseCallBack<T> callBack) {
        this.finalNeedType = finalNeedType;
        this.callBack = callBack;
    }

    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
        super.onSubscribe(s);
        if (callBack != null) {
            callBack.onStart();
        }
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        // todo some common as show loadding  and check netWork is NetworkAvailable
//        if (callBack != null) {
//            callBack.onStart();
//        }
//    }

    @Override
    public void onComplete() {
        super.onComplete();
        if (callBack != null) {
            callBack.onCompleted();
        }
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        if (callBack != null) {
            callBack.onError(e);
        }
    }


    @Override
    public void onNext(ResponseBody responseBody) {

        try {
            byte[] bytes = responseBody.bytes();
            String jsStr = new String(bytes);
            Log.i(TAG, "====ResponseBody:====" + jsStr);
            if (callBack != null) {
                callBack.onSuccee((T) new Gson().fromJson(jsStr, finalNeedType));
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (callBack != null) {
                callBack.onError(e);
            }
        }
    }

}
