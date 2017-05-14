package com.example.administrator.security.common.http;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;




public interface NetService {

    @POST()
    Flowable<ResponseBody> postRequestQuery(
            @Url() String url,
            @QueryMap Map<String, String> params);

    @POST()
    @FormUrlEncoded
    Flowable<ResponseBody> postRequest(
            @Url() String url,
            @FieldMap Map<String, String> params);

    @POST()
    Flowable<ResponseBody> postRequest(
            @Url() String url
    );


    @FormUrlEncoded
    @POST()
    Flowable<ResponseBody> postForm(
            @Url() String url,
            @FieldMap Map<String, Object> params);


    @POST("{url}")
    Flowable<ResponseBody> executePostBody(
            @Path("url") String url,
            @Body Object object);


    @POST()
    Flowable<ResponseBody> postJson(
            @Url() String url,
            @Body RequestBody jsonBody);


    @Multipart
    @POST
    Flowable<ResponseBody> uploadFlie(
            @Url String fileUrl,
            @Part MultipartBody.Part filePart);


    @Multipart
    @POST()
    Flowable<ResponseBody> uploadFiles(
            @Url() String url,
            @PartMap() Map<String, RequestBody> maps);


    @Streaming
    @GET
    Flowable<ResponseBody> downloadFile(@Url String fileUrl);
}
