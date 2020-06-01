package com.frewen.android.demo.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.frewen.android.demo.di.AppInjector;
import com.frewen.android.demo.network.MyNetworkConfig;
import com.frewen.android.demo.samples.hook.HookHelper;
import com.frewen.android.demo.samples.network.Constant;
import com.frewen.aura.framework.app.BaseApp;
import com.frewen.github.library.network.core.NetworkApi;
import com.frewen.keepservice.KeepLiveService;
import com.frewen.network.core.FreeRxHttp;
import com.frewen.aura.toolkits.core.FreeToolKits;
import com.frewen.aura.toolkits.utils.ProcessInfoUtils;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * MyApp
 */
public class MyApp extends BaseApp implements HasActivityInjector {

    private static final String TAG = "T:MyApp";
    /**
     * 分发Activity的注入
     *
     * 在Activity调用AndroidInjection.inject(this)时
     * 从Application获取一个DispatchingAndroidInjector<Activity>，并将activity传递给inject(activity)
     * DispatchingAndroidInjector通过AndroidInjector.Factory创建AndroidInjector
     */
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    private String processName;


    @Override
    protected void attachBaseContext(Context base) {
        // 在这里调用Context的方法会崩溃
        super.attachBaseContext(base);
        // 在这里可以正常调用Context的方法
        HookHelper.hookActivityManager();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        FreeToolKits.init(this, "AndroidSamples");

        initFreeHttp();

        initNetworkApi();
        //Application级别注入
        AppInjector.INSTANCE.inject(this);
    }

    private void initNetworkApi() {
        NetworkApi.init(new MyNetworkConfig(this));
    }

    private void initFreeHttp() {
        FreeRxHttp.init(this);
        FreeRxHttp.getInstance()
                .setBaseUrl(Constant.BASE_URL)
                .setDebug("FreeRxHttp", true)
                .setReadTimeOut(5 * 1000)
                .setWriteTimeOut(10 * 1000)
                .setConnectTimeout(5 * 1000)
                //默认网络不好自动重试3次
                .setRetryCount(3);
    }

    private void attach() {
        processName = ProcessInfoUtils.getProcessName(this);
        if (processName != null) {
            boolean defaultProcess = processName.equals(getPackageName());
            if (defaultProcess) {
                startService(new Intent(this, KeepLiveService.class));
            } else if (processName.contains(":live")) {
                Log.i(TAG, "FMsg:attach() called : ");
            }
        }
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return this.dispatchingAndroidInjector;
    }
}
