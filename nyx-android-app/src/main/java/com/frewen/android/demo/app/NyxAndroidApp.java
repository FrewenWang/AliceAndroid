package com.frewen.android.demo.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.frewen.android.demo.di.AppInjector;
import com.frewen.android.demo.network.MyNetworkConfig;
import com.frewen.android.demo.network.VideoApiService;
import com.frewen.nyx.hilt.demo.hilt.RoomServiceProvider;
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
import dagger.hilt.android.HiltAndroidApp;

/**
 * NyxAndroidApp
 * <p>
 * 所有使用 Hilt 的应用都必须包含一个带有 @HiltAndroidApp 注释的 Application 类。
 *
 * @HiltAndroidApp 会触发Hilt的代码生成操作，生成的代码包括应用的一个基类，该基类充当应用级依赖项容器。
 * 生成的这一 Hilt 组件会附加到 Application 对象的生命周期，并为其提供依赖项。
 * 此外，它也是应用的父组件，这意味着，其他组件可以访问它提供的依赖项。
 */
@HiltAndroidApp
public class NyxAndroidApp extends BaseMVPApp implements HasActivityInjector {

    private static final String TAG = "T:NyxAndroidApp";
    /**
     * 分发Activity的注入
     * <p>
     * 在Activity调用AndroidInjection.inject(this)时
     * 从Application获取一个DispatchingAndroidInjector<Activity>，并将activity传递给inject(activity)
     * DispatchingAndroidInjector通过AndroidInjector.Factory创建AndroidInjector
     */
    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    private String processName;
    /**
     * Room的本地数据库的提供器
     */
    public RoomServiceProvider roomServiceProvider;


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


        initX5Browser();

        initAuraHttp();

        initNetworkApi();
        //Application级别注入
        AppInjector.INSTANCE.inject(this);


        initRoomDataProvider();
    }

    private void initRoomDataProvider() {
        roomServiceProvider = new RoomServiceProvider(this);
    }

    /**
     * 初始化X5内核的浏览器
     * 1、Gradle方式集成 您可以在使用SDK的模块的dependencies中添加引用进行集成：
     */
    private void initX5Browser() {

    }

    /**
     * https://bugly.qq.com/docs/user-guide/instruction-manual-android/?v=20200203205953
     */
    private void initBugly() {
        Context context = getApplicationContext();
        // 获取当前包名
//        String packageName = context.getPackageName();
        // 获取当前进程名
//        String processName = ProcessInfoUtils.getProcessName(this);
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

        // 初始化AndroidNetworking网络请求框架
        // https://github.com/amitshekhariitbhu/Fast-Android-Networking
        AndroidNetworking.initialize(getApplicationContext());
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
