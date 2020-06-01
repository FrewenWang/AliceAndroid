package com.frewen.android.demo.network;

import android.app.Application;

import com.frewen.android.demo.BuildConfig;
import com.frewen.github.library.network.env.Env;
import com.frewen.github.library.network.core.AbsNetworkConfig;

import org.jetbrains.annotations.NotNull;

/**
 * @filename: MyNetworkConfig
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/5/13 21:31
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class MyNetworkConfig implements AbsNetworkConfig {

    private final Application mApplication;

    /**
     * MyNetworkConfig构造函数
     * @param application
     */
    public MyNetworkConfig(Application application) {
        this.mApplication = application;
    }

    @NotNull
    @Override
    public String getAppVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    @Override
    public int getAppVersionCode() {
        return BuildConfig.VERSION_CODE;
    }

    @Override
    public boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    @NotNull
    @Override
    public Application getAppContext() {
        return mApplication;
    }

    @NotNull
    @Override
    public Env switchProgramEnv() {
        return Env.PROD;
    }

}
