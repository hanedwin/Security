package com.example.administrator.security.common.http;


public abstract class DownLoadCallBack  {
    public void onStart(){}

    public void onCancel(){}

    public void onCompleted(){}


    /**
     * @param e
     */
    abstract public void onError(Throwable e);

    public void onProgress(long downSize,long fileSize){}

    /**
     * @param path
     * @param fileName
     * @param fileSize
     */
    abstract public void onSuccess(String path, String fileName, long fileSize);
}
