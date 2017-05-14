package com.example.administrator.security.common.http;


import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;


public  class BaseSubscriber<T> implements Subscriber<T> {

    @Override
    public void onSubscribe(Subscription s){

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onError(Throwable e) {
    }

    @Override
    public void onNext(T t) {

    }
}
