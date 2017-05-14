package com.example.administrator.security.login.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.security.R;
import com.example.administrator.security.R2;
import com.example.administrator.security.common.http.NetworkUtils;
import com.example.administrator.security.common.http.callback.ResponseCallBack;
import com.google.gson.GsonBuilder;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * login page
 */

public class LoginItemView extends LinearLayout{

    private Context mContext;


    public LoginItemView(Context context) {
        this(context,null);
    }

    public LoginItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        View v = LayoutInflater.from(context).inflate(R.layout.item_login,this,true);
        ButterKnife.bind(this,v);
    }


    @OnClick(R2.id.item_login_login_tv)
    public void login() {
        Map<String, String> param = new HashMap<>();
        param.put("enc_message", getEncryptString("yst0514yw", "123456"));
        //调用方式1
//        NetworkUtils.getLogin().login(param).compose(TransformUtils.schedulersTransformer())
//                .subscribeWith(new Subscriber<Map<String, String>>() {
//                    @Override
//                    public void onSubscribe(Subscription s) {
//                        s.request(Long.MAX_VALUE);
//                    }
//
//                    @Override
//                    public void onNext(Map<String, String> stringStringMap) {
//                        Log.i("result--->", stringStringMap.toString());
//                    }
//
//                    @Override
//                    public void onError(Throwable t) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

        //调用方式二
        NetworkUtils.getInstance(mContext).executePost("v3/account/login", param, new ResponseCallBack<Object>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onSuccee(Object response) {
                Log.i("result--->", response.toString());
            }
        });
    }


    private String getEncryptString(String name, String password) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("account", name);
        map.put("password", password);

        String string = "YS100@#eaglesoul";
        String str = new GsonBuilder().create().toJson(map);
        byte[] raw = string.getBytes();
        byte[] data;
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(raw, "AES"), new IvParameterSpec(raw));
            data = cipher.doFinal(str.getBytes());
            String enc_data = Base64.encodeToString(data, Base64.DEFAULT);
            return enc_data;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return "";
    }

}
