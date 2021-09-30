package com.frewen.aura.perfguard.core.engine.cpu;

import android.util.Log;

import com.frewen.aura.perfguard.core.engine.IPerfGuardEngine;
import com.frewen.aura.perfguard.core.utils.RxJavaUtils;
import com.frewen.aura.toolkits.utils.AssertUtils;


import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;

/**
 * @filename: CPUEngine
 * @author: Frewen.Wong
 * @time: 2021/8/14 14:19
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
public class CPUEngine implements IPerfGuardEngine<CpuConfig> {
    private static final String TAG = "CPUEngine";
    private CpuConfig mConfig;
    private boolean mInitialized = false;
    private CompositeDisposable mCompositeDisposable;

    @Override
    public synchronized boolean init(CpuConfig cpuConfig) {
        if (initialized()) {
            Log.d(TAG, "CPUEngine has initialized !!! Ignored. ");
            return true;
        }
        this.mConfig = cpuConfig;
        /**
         * Disposable的定义：Represents a disposable resource.
         * 表示一个可释放的、用完即可丢弃的资源。它是一种资源。
         */
        mCompositeDisposable = new CompositeDisposable();
        startWork();
        this.mInitialized = true;
        return true;
    }

    @Override
    public void startWork() {
        mCompositeDisposable.add(Observable.interval(this.mConfig.intervalMillis(), TimeUnit.MILLISECONDS)
                .subscribeOn(RxJavaUtils.computationScheduler())
                .subscribeOn(RxJavaUtils.computationScheduler())
                .map(new Function<Long, CpuInfo>() {
                    @Override
                    public CpuInfo apply(Long aLong) throws Throwable {
                        AssertUtils.onWorkThread("CpuEngine apply");
                        return CpuUsageHelper.getCpuInfo(CPUEngine.this.mConfig.pId);
                    }
                }).filter(new Predicate<CpuInfo>() {
                    @Override
                    public boolean test(CpuInfo cpuInfo) throws Throwable {
                        return true;
                    }
                }).subscribe(new Consumer<CpuInfo>() {
                    @Override
                    public void accept(CpuInfo cpuInfo) throws Throwable {
                        Log.d(TAG, "accept  cpuInfo: " + cpuInfo);
                        AssertUtils.onWorkThread("CpuEngine accept");
                    }
                }));
    }

    @Override
    public void stopWork() {
        mCompositeDisposable.dispose();
    }

    @Override
    public void destroy() {
        if (null != mCompositeDisposable && !mCompositeDisposable.isDisposed()) {
            stopWork();
        }
        mCompositeDisposable = null;
        mInitialized = false;
    }

    @Override
    public boolean initialized() {
        return mInitialized;
    }

    @Nullable
    @Override
    public CpuConfig config() {
        return this.mConfig;
    }
}
