package com.example.administrator.security.common.http;



public class BaseResponse<T> {
    private int showType;
    private int resultCode;
    private String msg;
    private T data;

    public int getShowType() {
        return showType;
    }

    public  T getData() {
        return data;
    }

    public int getResultCode() {
        return resultCode;
    }

    public String getMsg() {
        return msg;
    }
}
