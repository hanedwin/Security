package com.example.administrator.security.login.http;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/4/4 0004.
 */

public interface HttpLoginService {

    @POST("v3/account/login")
    Flowable<Map<String, String>> login(@QueryMap Map<String, String> option);
}
