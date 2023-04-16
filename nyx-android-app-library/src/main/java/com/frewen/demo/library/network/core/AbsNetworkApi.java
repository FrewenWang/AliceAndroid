package com.frewen.demo.library.network.core;
import android.util.Log;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.frewen.aura.toolkits.utils.Preconditions;
import com.frewen.demo.library.network.env.AbsProgramEnv;
import com.frewen.demo.library.network.env.Env;
import com.frewen.demo.library.network.interceptor.CacheInterceptor;
import com.frewen.demo.library.network.interceptor.RequestInterceptor;
import com.frewen.demo.library.network.interceptor.ResponseInterceptor;
import com.frewen.demo.library.network.interceptor.HeadInterceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @filename: NetworkApi
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/5/13 20:36
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public abstract class AbsNetworkApi implements AbsProgramEnv {

    private static final String TAG = "NetworkApi";
    private static Map<String, Retrofit> retrofitHashMap = new HashMap<>();
    private static AbsNetworkConfig networkConfig;
    private String mBaseUrl;
    private OkHttpClient mOkHttpClient;
    private PersistentCookieJar mCookieJar = new PersistentCookieJar(new SetCookieCache(),
            new SharedPrefsCookiePersistor(networkConfig.getAppContext()));

    /**
     * NetworkApi 构造函数
     * 根据环境获取mBaseUrl
     */
    public AbsNetworkApi() {
        mBaseUrl = getHttpBaseUrl(networkConfig);
    }

    /**
     * 初始化参数
     *
     * @param config
     */
    public static void init(AbsNetworkConfig config) {
        AbsNetworkApi.networkConfig = config;
    }

    protected Retrofit getRetrofit(Class service) {
        Preconditions.notNull(mBaseUrl);
        if (retrofitHashMap.get(mBaseUrl + service.getName()) != null) {
            return retrofitHashMap.get(mBaseUrl + service.getName());
        }
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.baseUrl(mBaseUrl);
        retrofitBuilder.client(getOkHttpClient());
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
        retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        Retrofit retrofit = retrofitBuilder.build();
        retrofitHashMap.put(mBaseUrl + service.getName(), retrofit);
        Log.d(TAG, "getRetrofit() called with: service = [" + retrofit.baseUrl() + "]");
        return retrofit;
    }


    private String getHttpBaseUrl(AbsNetworkConfig config) {
        Preconditions.notNull(config);
        Env env = config.switchProgramEnv();
        switch (env) {
            case DEV:
                return getDevUrl();
            case TEST:
                return getTestUrl();
            case PRE:
                return getPreUrl();
            case PROD:
                return getProdUrl();
            default:
                break;
        }
        return getProdUrl();
    }

    private OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
            // 设置缓存，缓存大小是10MB。
            int cacheSize = 10 * 1024 * 1024;
            okHttpClientBuilder.cache(new Cache(networkConfig.getAppContext().getCacheDir(), cacheSize));
            //添加Cookies自动持久化
            okHttpClientBuilder.cookieJar(mCookieJar);
            // 设置请求拦截器。
            okHttpClientBuilder.addInterceptor(new RequestInterceptor(networkConfig));
            okHttpClientBuilder.addInterceptor(new ResponseInterceptor());

            //示例：添加公共heads 注意要设置在日志拦截器之前，不然Log中会不显示head信息
            okHttpClientBuilder.addInterceptor(new CacheInterceptor(networkConfig, 7));
            // 添加
            if (networkConfig != null && (networkConfig.isDebug())) {
                //OKHttp内置拦截器 https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);
            }
            if (getInterceptor() != null) {
                okHttpClientBuilder.addInterceptor(getInterceptor());
            }

            //超时时间 连接、读、写
            okHttpClientBuilder.connectTimeout(10, TimeUnit.SECONDS);
            okHttpClientBuilder.readTimeout(5, TimeUnit.SECONDS);
            okHttpClientBuilder.writeTimeout(5, TimeUnit.SECONDS);
            mOkHttpClient = okHttpClientBuilder.build();
        } return mOkHttpClient;
    }

    /**
     * 用户自定义拦截器
     */
    public abstract Interceptor getInterceptor();

    public String getBaseUrl() {
        Preconditions.notNull(mBaseUrl);
        return mBaseUrl;
    }

}
