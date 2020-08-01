package com.frewen.android.demo.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.frewen.android.demo.di.AppInjector;
import com.frewen.android.demo.network.MyNetworkConfig;
import com.frewen.android.demo.network.VideoApiService;
import com.frewen.android.demo.samples.hook.HookHelper;
import com.frewen.android.demo.samples.network.Constant;
import com.frewen.aura.framework.app.BaseMVPApp;
import com.frewen.aura.toolkits.core.AuraToolKits;
import com.frewen.demo.library.network.core.NetworkApi;
import com.frewen.keepservice.KeepLiveService;
import com.frewen.network.core.AuraRxHttp;
import com.frewen.aura.toolkits.utils.ProcessInfoUtils;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/**
 * MyApp
 */
public class MyApp extends BaseMVPApp implements HasActivityInjector {

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

        AuraToolKits.init(null, "AndroidSamples");

        // 初始化Bugly
        initBugly();


        initAuraHttp();

        initNetworkApi();
        //Application级别注入
        AppInjector.INSTANCE.inject(this);
    }

    /**
     * https://bugly.qq.com/docs/user-guide/instruction-manual-android/?v=20200203205953
     */
    private void initBugly() {
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = ProcessInfoUtils.getProcessName(this);
        // 设置是否为上报进程
//        UserStrategy strategy = new UserStrategy(context);
//        strategy.setUploadProcess(processName == null || processName.equals(packageName));
//        // 初始化Bugly
//        CrashReport.initCrashReport(context, "注册时申请的APPID", BuildConfig.DEBUG, strategy);
        // 如果通过“AndroidManifest.xml”来配置APP信息，初始化方法如下
        // CrashReport.initCrashReport(context, strategy);
    }

    private void initNetworkApi() {
        NetworkApi.init(new MyNetworkConfig(this));
    }

    private void initAuraHttp() {
        AuraRxHttp.init(this);
        AuraRxHttp.getInstance()
                .setBaseUrl(Constant.BASE_URL)
                .setDebug("AuraRxHttp", true)
                .setReadTimeOut(5 * 1000)
                .setWriteTimeOut(10 * 1000)
                .setCacheMaxSize(100 * 1024 * 1024)//设置缓存大小为100M
                .setConnectTimeout(5 * 1000)
                .setApiService(VideoApiService.class)
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
