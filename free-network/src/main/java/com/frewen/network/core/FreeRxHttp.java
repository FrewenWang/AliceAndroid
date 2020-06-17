package com.frewen.network.core;

import android.app.Application;
import android.text.TextUtils;

import com.frewen.aura.toolkits.core.AuraToolKits;
import com.frewen.network.interceptor.HttpLoggingInterceptor;
import com.frewen.network.logger.Logger;
import com.frewen.network.model.HttpHeaders;
import com.frewen.network.model.HttpParams;
import com.frewen.network.request.GetRequest;
import com.frewen.network.utils.CommonUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * @filename: FreeRxHttp
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2019/4/14 23:03
 *         Copyright ©2018 Frewen.Wong. All Rights Reserved.
 */
public final class FreeRxHttp {

    /**
     * 默认超时时间：
     */
    public static final int DEFAULT_MILLISECONDS = 5 * 1000;
    private static Application mContext;
    private static volatile FreeRxHttp mInstance;
    /**
     * 设置重试次数
     */
    private static final int DEFAULT_RETRY_COUNT = 3;
    /**
     * OKHttp对象构建
     */
    private final OkHttpClient.Builder okHttpClientBuilder;
    private final Retrofit.Builder retrofitBuilder;
    /**
     * 全局BaseUrl
     */
    private String mBaseUrl;
    private int mRetryCount = DEFAULT_RETRY_COUNT;
    /**
     * 全局公共请求参数
     */
    private HttpParams mCommonParams;
    /**
     * 全局公共请求头
     */
    private HttpHeaders mCommonHeaders;

    private FreeRxHttp() {
        okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.connectTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        okHttpClientBuilder.readTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        okHttpClientBuilder.writeTimeout(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);

        retrofitBuilder = new Retrofit.Builder();
        //增加RxJava2CallAdapterFactory
        retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());

    }

    /**
     * getContext方法
     */
    public Application getContext() {
        CommonUtils.checkNotNull(mContext);
        return mContext;
    }

    /**
     * 必须在全局Application先调用，获取context上下文，否则缓存无法使用
     */
    public static void init(Application app) {
        mContext = app;
        AuraToolKits.init(app, "FreeRxHttp");
    }

    /**
     * 获取单例对象的方法
     */
    public static FreeRxHttp getInstance() {
        CommonUtils.checkNotNull(mContext, "you should init FreeRxHttp First");
        if (mInstance == null) {
            synchronized (FreeRxHttp.class) {
                if (mInstance == null) {
                    mInstance = new FreeRxHttp();
                }
            }
        }
        return mInstance;
    }

    /**
     * 调试模式,默认打开所有的异常调试
     */
    public FreeRxHttp setDebug(String tag) {
        return setDebug(tag, true);
    }

    /**
     * 设置Debug开关
     *
     * @param tag
     * @param debug
     */
    public FreeRxHttp setDebug(String tag, boolean debug) {
        String tempTag = TextUtils.isEmpty(tag) ? "FreeRxHttp" : tag;
        if (debug) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(tempTag, debug);
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClientBuilder.addInterceptor(loggingInterceptor);
        }
        Logger.getConfig()
                // 设计全局的TAG
                .setGlobalTag(tag)
                // 设置 log 总开关，包括输出到控制台和文件，默认开
                .setLogSwitch(debug)
                // 设置是否输出到控制台开关，默认开
                .setConsoleSwitch(debug)
                // 设置 log 全局标签，默认为空
                .setGlobalTag(null)
                // 当全局标签不为空时，我们输出的 log 全部为该 tag，
                // 为空时，如果传入的 tag 为空那就显示类名，否则显示 tag
                // 设置 log 头信息开关，默认为开
                .setLogHeadSwitch(true)
                // 打印 log 时是否存到文件的开关，默认关
                .setLog2FileSwitch(false)
                // 当自定义路径为空时，写入应用的/cache/log/目录中
                .setDir("")
                // 当文件前缀为空时，默认为"util"，即写入文件为"util-yyyy-MM-dd.txt"
                .setFilePrefix("")
                // 输出日志是否带边框开关，默认开
                .setBorderSwitch(true)
                // 一条日志仅输出一条，默认开，为美化 AS 3.1 的 Logcat
                .setSingleTagSwitch(true)
                // log 的控制台过滤器，和 logcat 过滤器同理，默认 Verbose
                .setConsoleFilter(Logger.V)
                // log 文件过滤器，和 logcat 过滤器同理，默认 Verbose
                .setFileFilter(Logger.V)
                // log 栈深度，默认为 1
                .setStackDeep(1)
                // 设置栈偏移，比如二次封装的话就需要设置，默认为 0
                .setStackOffset(0)
                // 设置日志可保留天数，默认为 -1 表示无限时长
                .setSaveDays(3);
        return this;
    }

    /**
     * 全局读取超时时间
     */
    public FreeRxHttp setReadTimeOut(long readTimeOut) {
        okHttpClientBuilder.readTimeout(readTimeOut, TimeUnit.MILLISECONDS);
        return this;
    }

    /**
     * 全局写入超时时间
     */
    public FreeRxHttp setWriteTimeOut(long writeTimeout) {
        okHttpClientBuilder.writeTimeout(writeTimeout, TimeUnit.MILLISECONDS);
        return this;
    }

    /**
     * 全局连接超时时间
     */
    public FreeRxHttp setConnectTimeout(long connectTimeout) {
        okHttpClientBuilder.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS);
        return this;
    }

    /**
     * 超时重试次数
     */
    public FreeRxHttp setRetryCount(int retryCount) {
        if (retryCount < 0) {
            throw new IllegalArgumentException("retryCount must > 0");
        }
        mRetryCount = retryCount;
        return this;
    }

    /**
     * 超时重试次数
     */
    public int getRetryCount() {
        return getInstance().mRetryCount;
    }

    /**
     * setBaseUrl
     *
     * @param baseUrl
     */
    public FreeRxHttp setBaseUrl(String baseUrl) {
        mBaseUrl = CommonUtils.checkNotNull(baseUrl, "baseUrl == null");
        return this;
    }

    /**
     * getBaseUrl
     */
    public String getBaseUrl() {
        return mBaseUrl;
    }

    /**
     * 添加全局公共请求参数
     */
    public FreeRxHttp addCommonParams(HttpParams commonParams) {
        if (mCommonParams == null) {
            mCommonParams = new HttpParams();
        }
        mCommonParams.put(commonParams);
        return this;
    }

    /**
     * 获取全局公共请求参数
     */
    public HttpParams getCommonParams() {
        return mCommonParams;
    }

    /**
     * 获取全局公共请求头
     */
    public HttpHeaders getCommonHeaders() {
        return mCommonHeaders;
    }

    /**
     * 添加全局公共请求参数
     */
    public FreeRxHttp addCommonHeaders(HttpHeaders commonHeaders) {
        if (mCommonHeaders == null) {
            mCommonHeaders = new HttpHeaders();
        }
        mCommonHeaders.put(commonHeaders);
        return this;
    }

    /**
     * get请求
     */
    public static GetRequest get(String url) {
        return new GetRequest(url);
    }
}
