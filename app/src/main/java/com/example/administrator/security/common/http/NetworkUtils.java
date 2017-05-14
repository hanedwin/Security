package com.example.administrator.security.common.http;

import android.content.Context;
import android.util.Log;


import com.example.administrator.security.common.base.MyApplication;
import com.example.administrator.security.common.http.callback.ResponseCallBack;
import com.example.administrator.security.common.http.converter.JsonConverterFactory;
import com.example.administrator.security.common.http.cookie.ClearableCookieJar;
import com.example.administrator.security.common.http.cookie.PersistentCookieJar;
import com.example.administrator.security.common.http.cookie.cache.SetCookieCache;
import com.example.administrator.security.common.http.cookie.persistence.SharedPrefsCookiePersistor;
import com.example.administrator.security.login.http.HttpLoginService;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.FieldMap;



public class NetworkUtils {


    private static final String TAG = "[Network]";

    private Map<String, Flowable<ResponseBody>> downMaps = new HashMap<String, Flowable<ResponseBody>>() {
    };
    private Flowable<ResponseBody> downObservable;

    private static volatile OkHttpClient mOkHttpClient;
    private NetService mNetService;
    private static volatile NetworkUtils mInstance;

    private static Context mContext;
    private static volatile Object apiService;

    public static NetworkUtils getInstance(Context context) {
        if (mInstance == null) {
            synchronized (NetworkUtils.class) {
                if (mInstance == null) {
                    mInstance = new NetworkUtils(context);
                }
            }
        }
        return mInstance;
    }


    private NetworkUtils(Context context) {
        this.mContext = context;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetConfig.BASE_URL)
                .client(getOkHttpClient())
                .addConverterFactory(JsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mNetService = retrofit.create(NetService.class);
    }


    /**
     * 登录模块
     * <p>
     * 可以用来做模块开发
     * <p>
     * 不同的模块可以创建不同的Service
     *
     * @return
     */
    public static HttpLoginService getLogin() {
        return createApi(HttpLoginService.class);
    }


    /**
     * 根据传入的api创建retrofit
     */
    private static <T> T createApi(Class<T> clazz) {

        if (apiService == null) {
            synchronized (NetworkUtils.class) {
                if (apiService == null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(NetConfig.BASE_URL)
                            .client(getOkHttpClient())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                    apiService = retrofit.create(clazz);
                }
            }
        }
        return (T) apiService;
    }


    private static OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (NetworkUtils.class) {
                //设置网络缓存路径 缓存大小为10M
                Cache cache = new Cache(new File(MyApplication.getAppContext().getExternalCacheDir().toString(), "HttpCache"),
                        1024 * 1024 * 10);

                ClearableCookieJar cookieJar =
                        new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MyApplication.getAppContext()));

                if (mOkHttpClient == null) {
                    mOkHttpClient = new OkHttpClient.Builder().cache(cache)
                            .connectTimeout(5000, TimeUnit.SECONDS)
                            .readTimeout(5000, TimeUnit.SECONDS)
                            .writeTimeout(5000, TimeUnit.SECONDS)
                            .cookieJar(cookieJar)
                            .addInterceptor(new RequestInterceptor())
                            .build();
                }
            }
        }
        return mOkHttpClient;
    }

    /**
     * 执行post请求  带参数
     * 已过滤掉ResponseBody中的其他信息只留下返回数据中data节点
     * <p>
     * 已用gson解析
     * <p>
     * <p>
     * 返回的是 Subscription
     *
     * @param url
     * @param parameters
     * @param callBack
     * @param <T>
     * @return
     */
    public <T> Subscriber executePost(final String url, final Map<String, String> parameters, final ResponseCallBack<T> callBack) {

        final Type[] types = callBack.getClass().getGenericInterfaces();
        if (MethodHandler(types) == null || MethodHandler(types).size() == 0) {
            return null;
        }
        final Type finalNeedType = MethodHandler(types).get(0);

        Log.d(TAG, "=========Type:========" + types[0]);

        return mNetService.postRequestQuery(url, parameters)
                .compose(schedulersTransformer)
                .subscribeWith(new NetSubscriber(finalNeedType, callBack));
    }

    /**
     * 执行post请求  带参数
     * 已过滤掉ResponseBody中的其他信息只留下返回数据中data节点
     * <p>
     * 已用gson解析
     * <p>
     * 返回的是具体的实体类
     *
     * @param url
     * @param parameters
     * @param callBack
     * @param <T>
     * @return
     */
    public <T> T executePostRe(final String url, final Map<String, String> parameters, final ResponseCallBack<T> callBack) {


        final Type[] types = callBack.getClass().getGenericInterfaces();
        if (MethodHandler(types) == null || MethodHandler(types).size() == 0) {
            return null;
        }
        final Type finalNeedType = MethodHandler(types).get(0);

        Log.d(TAG, "=========Type:========" + types[0]);

        return (T) mNetService.postRequest(url, parameters)
                .compose(schedulersTransformer)
                .subscribeWith(new NetSubscriber(finalNeedType, callBack));
    }

    /**
     * 执行post请求 无参数
     * 已过滤掉ResponseBody中的其他信息只留下返回数据中data节点
     * <p>
     * 已用gson解析
     *
     * @param url
     * @param callBack
     * @param <T>
     * @return
     */
    public <T> Subscriber executePost(final String url, final ResponseCallBack<T> callBack) {

        final Type[] types = callBack.getClass().getGenericInterfaces();
        if (MethodHandler(types) == null || MethodHandler(types).size() == 0) {
            return null;
        }
        final Type finalNeedType = MethodHandler(types).get(0);

        Log.d(TAG, "=========Type:========" + types[0]);

        return mNetService.postRequest(url)
                .compose(schedulersTransformer)
                .subscribeWith(new NetSubscriber(finalNeedType, callBack));
    }


    /**
     * Retroift execute Post by Form
     *
     * @param url
     * @param fields
     * @param callBack
     * @param <T>
     * @return
     */
    public <T> Subscriber executeForm(final String url, final @FieldMap(encoded = true) Map<String, Object> fields, final ResponseCallBack<T> callBack) {
        final Type[] types = callBack.getClass().getGenericInterfaces();
        if (MethodHandler(types) == null || MethodHandler(types).size() == 0) {
            return null;
        }
        final Type finalNeedType = MethodHandler(types).get(0);
        Log.d(TAG, "=========Type:========" + types[0]);

        return mNetService.postForm(url, fields)
                .compose(schedulersTransformer)
                .subscribeWith(new NetSubscriber(finalNeedType, callBack));
    }


    /**
     * http execute Post by body
     * <p>
     * 执行需要请求体的post请求
     *
     * @return parsed data
     * you don't need to   parse ResponseBody
     */
    public <T> Subscriber executeBody(final String url, final Object body, final ResponseCallBack<T> callBack) {
        final Type[] types = callBack.getClass().getGenericInterfaces();
        if (MethodHandler(types) == null || MethodHandler(types).size() == 0) {
            return null;
        }
        final Type finalNeedType = MethodHandler(types).get(0);
        Log.d(TAG, "=========Type:========" + types[0]);

        return mNetService.executePostBody(url, body)
                .compose(schedulersTransformer)
                .subscribeWith(new NetSubscriber(finalNeedType, callBack));
    }


    /**
     * http execute Post by Json
     * <p>
     * 执行提交json数据的post请求
     *
     * @param url
     * @param jsonStr Json String
     * @return parsed data
     * you don't need to   parse ResponseBody
     */
    public <T> Subscriber executeJson(final String url, final String jsonStr, final ResponseCallBack<T> callBack) {
        final Type[] types = callBack.getClass().getGenericInterfaces();
        if (MethodHandler(types) == null || MethodHandler(types).size() == 0) {
            return null;
        }
        final Type finalNeedType = MethodHandler(types).get(0);
        Log.d(TAG, "=========Type:========" + types[0]);
        return mNetService.postJson(url, Utils.createJson(jsonStr))
                .compose(schedulersTransformer)
                .subscribeWith(new NetSubscriber(finalNeedType, callBack));
    }


    /**
     * upload Flie
     *
     * @param url
     * @param callBack callBack
     * @return
     */
    public <T> Subscriber uploadFlie(String url, File file, final ResponseCallBack<T> callBack) {

        final Type[] types = callBack.getClass().getGenericInterfaces();
        if (MethodHandler(types) == null || MethodHandler(types).size() == 0) {
            return null;
        }
        final Type finalNeedType = MethodHandler(types).get(0);

        Log.d(TAG, "=========Type:========" + types[0]);

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("application/otcet-stream"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        return mNetService.uploadFlie(url, filePart)
                .compose(schedulersTransformer)
                .subscribeWith(new NetSubscriber(finalNeedType, callBack));
    }


    /**
     * upload Flies
     *
     * @param url
     * @param subscriber subscriber
     * @param <T>        T
     * @return
     */
    public <T> T uploadFlies(String url, Map<String, RequestBody> files, Subscriber<ResponseBody> subscriber) {
        return (T) mNetService.uploadFiles(url, files)
                .compose(schedulersTransformer)
                .subscribeWith(subscriber);
    }


    /**
     * 下载 不指定文件名
     *
     * @param url
     * @param callBack
     */
    public void download(String url, DownLoadCallBack callBack) {
        download(url, null, callBack);
    }


    /**
     * @param url
     * @param name
     * @param callBack
     */
    public Subscriber download(String url, String name, DownLoadCallBack callBack) {
        return download(url, null, name, callBack);
    }


    /**
     * @param url
     * @param savePath
     * @param name
     * @param callBack
     */
    public Subscriber download(String url, String savePath, String name, DownLoadCallBack callBack) {

        if (downMaps.get(url) == null) {
            downObservable = mNetService.downloadFile(url);
            downMaps.put(url, downObservable);
        } else {
            downObservable = downMaps.get(url);
        }
        return executeDownload(savePath, name, callBack);
    }


    /**
     * executeDownload
     *
     * @param savePath
     * @param name
     * @param callBack
     */
    private Subscriber executeDownload(String savePath, String name, DownLoadCallBack callBack) {
        if (DownLoadManager.isDownLoading) {
            downObservable.unsubscribeOn(Schedulers.io());
            DownLoadManager.isDownLoading = false;
            DownLoadManager.isCancel = true;
            return null;
        }
        DownLoadManager.isDownLoading = true;
        return downObservable.compose(schedulersTransformerDown)
                .subscribeWith(new DownSubscriber<ResponseBody>(savePath, name, callBack, mContext));
    }


    /**
     * 指定线程
     */
    final FlowableTransformer schedulersTransformer = new FlowableTransformer() {
        @Override
        public Publisher apply(@NonNull Flowable upstream) {
            return (upstream).subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

    };


    final FlowableTransformer schedulersTransformerDown = new FlowableTransformer() {
        @Override
        public Publisher apply(@NonNull Flowable upstream) {
            return upstream.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io());
        }

    };

    /**
     * MethodHandler
     */
    private List<Type> MethodHandler(Type[] types) {
        Log.d(TAG, "========types size: =======" + types.length);
        List<Type> needtypes = new ArrayList<>();
        Type needParentType = null;

        for (Type paramType : types) {
            System.out.println("  " + paramType);
            // if Type is T
            if (paramType instanceof ParameterizedType) {
                Type[] parentypes = ((ParameterizedType) paramType).getActualTypeArguments();

                for (Type childtype : parentypes) {
                    Log.d(TAG, "===========childtype:=======" + childtype);
                    needtypes.add(childtype);
                    //needParentType = childtype;
                    if (childtype instanceof ParameterizedType) {
                        Type[] childtypes = ((ParameterizedType) childtype).getActualTypeArguments();
                        for (Type type : childtypes) {
                            needtypes.add(type);
                            //needChildType = type;
                            Log.d(TAG, "=========type:=======" + childtype);
                        }
                    }
                }
            }
        }
        return needtypes;
    }

}
