package com.frewen.android.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Debug;
import android.os.Trace;

import com.androidnetworking.AndroidNetworking;
import com.frewen.android.demo.app.helper.KeepAliveHelper;
import com.frewen.android.demo.app.taskstarter.BuglyInitTask;
import com.frewen.android.demo.app.taskstarter.DexposedInitTask;
import com.frewen.android.demo.app.taskstarter.MmkvInitTask;
import com.frewen.android.demo.di.AppInjector;
import com.frewen.android.demo.error.ErrorActivity;
import com.frewen.android.demo.network.MyNetworkConfig;
import com.frewen.android.demo.network.NyxNetworkApi;
import com.frewen.android.demo.performance.AppBlockCanaryContext;
import com.frewen.android.demo.performance.LaunchTimeRecord;
import com.frewen.android.demo.logic.samples.hook.HookHelper;
import com.frewen.android.demo.logic.samples.network.Constant;
import com.frewen.android.demo.logic.ui.SplashActivity;
import com.frewen.aura.framework.app.BaseMVPApp;
import com.frewen.aura.framework.taskstarter.ModuleProvider;
import com.frewen.aura.framework.taskstarter.LaunchTaskDispatcher;
import com.frewen.aura.toolkits.concurrent.ThreadFactoryImpl;
import com.frewen.aura.toolkits.core.AuraToolKits;
import com.frewen.network.api.BaseApiService;
import com.frewen.network.core.AuraRxHttp;
import com.frewen.aura.toolkits.utils.ProcessInfoUtils;
import com.github.anrwatchdog.ANRError;
import com.github.anrwatchdog.ANRWatchDog;
import com.github.moduth.blockcanary.BlockCanary;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mmkv.MMKV;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import androidx.annotation.RequiresApi;
import androidx.core.os.TraceCompat;
import cat.ereza.customactivityoncrash.config.CaocConfig;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

import static com.frewen.android.demo.constant.AppKeyConstants.APP_ID_BUGLY;

/**
 * NyxAndroidApp
 *
 * Application的启动的异步优化：
 *
 * 1、Theme的切换
 * 2、异步优化：
 * 让子线程来分担主线程的任务，通过并行的任务来进行减少执行时间
 */
public class NyxApp extends BaseMVPApp implements HasActivityInjector, ModuleProvider {

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

    // 根据CPU的核心数来进行设置核心线程的数量
    // 这个我们是模仿Android的源码中AsyncTask的实现。我们希望核心池中至少有 2 个线程，最多 4 个线程，
    // 但是如果CPU的核心数都不超过4个(3个、4个)。则宁愿比 CPU 数量少 1，以避免后台工作使 CPU 饱和
    // 但是注意。在最新的android-29的源码里面这种设计已经被更优的解决方案替代。我们有空可以学习一下
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));

    /**
     * 这句话是什么意思？？
     */
    private CountDownLatch mCountDownLatch = new CountDownLatch(1);

    @Override
    protected void attachBaseContext(Context base) {
        LaunchTimeRecord.INSTANCE.startRecord("Application");
        // 在这里调用Context的方法会崩溃
        super.attachBaseContext(base);
        // 在这里可以正常调用Context的方法
        HookHelper.hookActivityManager();
    }

    @Override
    public void onCreate() {
        // 使用TraceView生成TraceView的，文件默认存储8M的信息
        Debug.startMethodTracing("NyxAndroid Start");

        // 使用SysTrace进行分析
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Trace.beginSection("NyxAndroid Start");
        } else {
            TraceCompat.beginSection("NyxAndroid Start");
        }

        super.onCreate();

        //CPU的数量. 我们在启动的时候尽量使用CPU密集型的线程池，我们尽量榨取CPU执行的调度能力
        ExecutorService executorService = Executors.newFixedThreadPool(CORE_POOL_SIZE, new ThreadFactoryImpl("Application"));
        executorService.submit(() -> {
            initAuraHttp();
            mCountDownLatch.countDown();
        });


        initModuleTaskDispatcher();

        initPerformanceDetector();

        AuraToolKits.init(null, "AndroidSamples");

        initX5Browser();

        initNetworkApi();
        //Application级别注入
        AppInjector.INSTANCE.inject(this);

        /**
         * 将任务会在这个地方进行等待。需要判断mCountDownLatch是否需要满足等待。
         * 我们这个对象的在实例化的时候，我们可以判断徐亚满足几次
         * 任务的最后需要阻塞等待，一直等所有需要满足的任务执行完毕，再通过
         */
        try {
            mCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        initCustomActivityOnCrash();

        KeepAliveHelper.init(this);

        // 执行结束的时候录制TraceView的相关信息
        Debug.stopMethodTracing();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Trace.endSection();
        } else {
            TraceCompat.endSection();
        }
    }

    private void initModuleTaskDispatcher() {
        LaunchTaskDispatcher.getInstance().init(this);
        LaunchTaskDispatcher.getInstance()
                .addTask(new BuglyInitTask())
                .addTask(new DexposedInitTask())
                .addTask(new MmkvInitTask(this))
                .start();
    }

    /**
     * 初始化当应用崩溃的时候，所要跳转的Activity
     * 框架官网：https://github.com/Ereza/CustomActivityOnCrash
     */
    private void initCustomActivityOnCrash() {
        //防止项目崩溃，崩溃后打开错误界面
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //default: CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM
                .enabled(true)//是否启用CustomActivityOnCrash崩溃拦截机制 必须启用！不然集成这个库干啥？？？
                .showErrorDetails(false) //是否必须显示包含错误详细信息的按钮 default: true
                .showRestartButton(false) //是否必须显示“重新启动应用程序”按钮或“关闭应用程序”按钮default: true
                .logErrorOnRestart(false) //是否必须重新堆栈堆栈跟踪 default: true
                .trackActivities(true) //是否必须跟踪用户访问的活动及其生命周期调用 default: false
                .minTimeBetweenCrashesMs(2000) //应用程序崩溃之间必须经过的时间 default: 3000
                .restartActivity(SplashActivity.class) // 重启的activity
                .errorActivity(ErrorActivity.class) //发生错误跳转的activity
                .eventListener(null) //允许你指定事件侦听器，以便在库显示错误活动 default: null
                .apply();
    }

    /**
     * 初始化性能监控的相关逻辑检测器
     */
    private void initPerformanceDetector() {
        initBlockCanary();
        initAnrWatchDog();
        // StrictModeHelper.init();
        // 初始化Bugly
        // initBugly();
    }

    private void initAnrWatchDog() {
        int timeoutMillis = BuildConfig.DEBUG ? 5000 : 10000;
        ANRWatchDog anrWatchDog = new ANRWatchDog(timeoutMillis /*timeout*/).setIgnoreDebugger(true);
        anrWatchDog.setANRListener(new ANRWatchDog.ANRListener() {
            @Override
            public void onAppNotResponding(ANRError error) {
                //Log.d(TAG, "FMsg:onAppNotResponding() called with: error = [" + error + "]");
                // TODO 异常的上报和恢复
                //throw error;
            }
        });
        anrWatchDog.start();
    }

    private void initBlockCanary() {
        // 在主进程初始化调用
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
    }

    /**
     * 初始化X5内核的浏览器
     * 1、Gradle方式集成 您可以在使用SDK的模块的dependencies中添加引用进行集成：
     */
    private void initX5Browser() {

    }

    /**
     * https://bugly.qq.com/docs/user-guide/instruction-manual-android/?v=20200203205953
     * 为了保证运营数据的准确性，建议不要在异步线程初始化Bugly。
     */
    private void initBugly() {
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = ProcessInfoUtils.getProcessName(this);
        // 设置是否为上报进程的策略
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        // 初始化Bugly:
        CrashReport.initCrashReport(context, APP_ID_BUGLY, BuildConfig.DEBUG, strategy);
        // 如果通过“AndroidManifest.xml”来配置APP信息，初始化方法如下
        // CrashReport.initCrashReport(context, strategy);
    }

    private void initNetworkApi() {
        NyxNetworkApi.init(new MyNetworkConfig(this));

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
                .setApiService(BaseApiService.class)
                //默认网络不好自动重试3次
                .setRetryCount(3);
    }

    /**
     * 用来进行Activity的相关注入
     */
    @Override
    public AndroidInjector<Activity> activityInjector() {
        return this.dispatchingAndroidInjector;
    }
}
