package com.ainirobot.optimus.network;

import android.util.Log;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @filename: Dispatcher
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2019/4/13 11:52
 * Copyright ©2018 Frewen.Wong. All Rights Reserved.
 */
public class Dispatcher {
    private static final String TAG = "Dispatcher";
    private int mMaxRequests = 64;
    private final ExecutorService mExecutor;
    private final Deque<NetworkRunnable> mRunningRunnables = new ArrayDeque<>();
    private final Deque<NetworkRunnable> mWaitingRunnables = new ArrayDeque<>();

    public Dispatcher() {
        mExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
    }

    /**
     * 设置最大网络请求数
     *
     * @param maxRequests
     */
    public void setMaxRequests(int maxRequests) {
        if (maxRequests < 1) {
            throw new IllegalArgumentException("maxRequests < 1 : " + maxRequests);
        }
        this.mMaxRequests = maxRequests;
        performRunnable();
    }

    /**
     * 获取最大网络请求数
     *
     * @return
     */
    public int getMaxRequests() {
        return mMaxRequests;
    }

    /**
     * 取消所有网络请求
     */
    public synchronized void cancelAll() {
        for (NetworkRunnable runnable : mRunningRunnables) {
            runnable.cancel();
        }

        mWaitingRunnables.clear();
    }

    /**
     * 根据TAG来取消网络请求
     *
     * @param tag
     */
    public synchronized void cancel(Object tag) {
        // 遍历所有运行的网络请求， 取消网络请求
        for (NetworkRunnable runnable : mRunningRunnables) {
            Log.d(TAG, "request cancel runable.getTag():" + runnable.getTag() + ",tag:" + tag);
            if (runnable.getTag() == tag) {
                runnable.cancel();
                return;
            }
        }
        // 遍历所有等待的网络请求
        Iterator<NetworkRunnable> iterator = mWaitingRunnables.iterator();
        while (iterator.hasNext()) {
            NetworkRunnable runnable = iterator.next();
            if (runnable.getTag() == tag) {
                iterator.remove();
            }
        }
    }

    /**
     * 网络请求处理
     */
    private void performRunnable() {
        if (mRunningRunnables.size() >= mMaxRequests
                || mWaitingRunnables.isEmpty()) {
            return;
        }
        // 遍历所有的等待请求列表
        Iterator<NetworkRunnable> iterator = mWaitingRunnables.iterator();
        while (iterator.hasNext()) {
            NetworkRunnable runnable = iterator.next();
            // 加入到请求中的列表中
            mRunningRunnables.add(runnable);
            mExecutor.execute(runnable);
            iterator.remove();
            if (mRunningRunnables.size() > mMaxRequests) {
                return;
            }
        }
    }

    /**
     * 提交网络请求：如果当前运行的网络请求小于最大请求数，则添加到请求
     * 如果，超过最大请求数，则添加到等待队列中
     *
     * @param runnable
     */
    public synchronized void submit(NetworkRunnable runnable) {
        if (mRunningRunnables.size() < mMaxRequests) {
            mRunningRunnables.add(runnable);
            mExecutor.execute(runnable);
        } else {
            mWaitingRunnables.add(runnable);
        }
    }

    /**
     * 从运行的网络请求的列表中去掉这个请求
     *
     * @param runnable
     * @return
     */
    public synchronized boolean finished(NetworkRunnable runnable) {
        if (mRunningRunnables.remove(runnable)) {
            performRunnable();
            return true;
        }
        return false;
    }
}
