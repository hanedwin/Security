package com.example.administrator.security.common.http;

import com.google.gson.JsonParseException;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;



public class ResponseFunc<T> implements Consumer<ResponseBody> {

//    @SuppressWarnings("unchecked")
//    @Override
//    public Observable<T> call(final ResponseBody response) {
//        if (response == null) {
//            throw new JsonParseException("服务器返回数据异常");
//        }
//
//        return Observable.create(new Observable.OnSubscribe<T>() {
//            @Override
//            public void call(Subscriber<? super T> subscriber) {
//
//                try {
//                    JSONObject jsonObject = new JSONObject(response.string());
//
//
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//
//                subscriber.onNext((T)response);
//
//                subscriber.onCompleted();
//
//            }
//        });
//
//    }

    @Override
    public void accept(@NonNull ResponseBody responseBody) throws Exception {
        if (responseBody == null) {
            throw new JsonParseException("服务器返回数据异常");
        }


    }
}
