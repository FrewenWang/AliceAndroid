package com.ainirobot.optimus.network;

import android.util.Log;

/**
 * @filename: OptimusNetClient
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2019/4/13 11:50
 * Copyright ©2018 Frewen.Wong. All Rights Reserved.
 */
public class OptimusNetClient {
    private static final String TAG = "OptimusNetClient";
    private long connectTimeout = 30000;
    private long readTimeout = 30000;
    private final Dispatcher mDispatcher;

    public OptimusNetClient() {
        this.mDispatcher = new Dispatcher();
    }


    public void setConnectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public void setReadTimeout(long readTimeout) {
        this.readTimeout = readTimeout;
    }

    public long getConnectTimeout() {
        return connectTimeout;
    }

    public long getReadTimeout() {
        return readTimeout;
    }

    public Dispatcher getDispatcher() {
        return mDispatcher;
    }

    /**
     * 设置最大网络请求数
     *
     * @param maxRequests
     */
    public void setMaxRequests(int maxRequests) {
        mDispatcher.setMaxRequests(maxRequests);
    }

    /**
     * 执行网络请求
     *
     * @param request
     */
    public void execute(Request request) {
        execute(request, null);
    }

    /**
     * 执行网络请求
     *
     * @param request
     * @param listener 请求结果监听回调
     */
    public void execute(Request request, RequestListener listener) {
        NetworkRunnable runnable = new NetworkRunnable(this, request, listener);
        mDispatcher.submit(runnable);
    }

    /**
     * 取消对象的
     * @param tag
     */
    public void cancel(Object tag) {
        Log.d(TAG, "cancel request tag:" + tag);
        mDispatcher.cancel(tag);
    }

    public void cancelAll() {
        mDispatcher.cancelAll();
    }
}
