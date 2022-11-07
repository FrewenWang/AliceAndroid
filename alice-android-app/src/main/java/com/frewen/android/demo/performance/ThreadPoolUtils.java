package com.frewen.android.demo.performance;

import com.frewen.aura.toolkits.concurrent.ThreadFactoryImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @filename: ThreadPoolUtils
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/9/14 13:28
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class ThreadPoolUtils {

    /**
     * 我们创建一个newFixedThreadPool的线程。他的核心线程数是5个。
     * 但是其实他的最大线程也是5个,也就是说这个线程没有非核心线程
     * 而且他使用的是LinkedBlockingQueue
     * 这样就导致他的特性其实就是当这个5个核心线程在运行的时候，我们就任务存档在队列中
     * 一直到空出核心线程的时候才会
     *
     *
     */
    private static ExecutorService mExecutorService = Executors.newFixedThreadPool(5, new ThreadFactoryImpl("我的线程池"));

    public static ExecutorService getExecutorService() {
        return mExecutorService;
    }
}
