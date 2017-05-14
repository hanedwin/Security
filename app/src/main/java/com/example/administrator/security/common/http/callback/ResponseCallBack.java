package com.example.administrator.security.common.http.callback;



public interface ResponseCallBack<T> {
    void onStart();

    void onCompleted();

    void onError(Throwable e);

    void onSuccee(T response);
}
