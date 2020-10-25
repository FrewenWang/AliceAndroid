package com.frewen.demo.library.network.core;

import com.frewen.aura.toolkits.utils.AssertionsUtils;
import com.frewen.demo.library.network.env.AbsProgramEnv;
import com.frewen.demo.library.network.env.Env;
import com.frewen.demo.library.network.interceptor.RequestInterceptor;
import com.frewen.demo.library.network.interceptor.ResponseInterceptor;

import java.util.HashMap;
import java.util.Map;

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
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public abstract class NetworkApi implements AbsProgramEnv {

    private static Map<String, Retrofit> retrofitHashMap = new HashMap<>();
    private static AbsNetworkConfig networkConfig;
    private String mBaseUrl;
    private OkHttpClient mOkHttpClient;

    /**
     * NetworkApi 构造函数
     * 根据环境获取mBaseUrl
     */
    public NetworkApi() {
        mBaseUrl = getHttpBaseUrl(networkConfig);
    }

    /**
     * 初始化参数
     *
     * @param config
     */
    public static void init(AbsNetworkConfig config) {
        NetworkApi.networkConfig = config;
    }

    protected Retrofit getRetrofit(Class service) {
        AssertionsUtils.assertNotNull(mBaseUrl);
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
        return retrofit;
    }


    private String getHttpBaseUrl(AbsNetworkConfig config) {
        AssertionsUtils.assertNotNull(config);
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
            if (getInterceptor() != null) {
                okHttpClientBuilder.addInterceptor(getInterceptor());
            }
            // 缓存大小是10MB
            int cacheSize = 10 * 1024 * 1024;
            okHttpClientBuilder.cache(new Cache(networkConfig.getAppContext().getCacheDir(), cacheSize));
            okHttpClientBuilder.addInterceptor(new RequestInterceptor(networkConfig));
            okHttpClientBuilder.addInterceptor(new ResponseInterceptor());
            if (networkConfig != null && (networkConfig.isDebug())) {
                //OKHttp内置拦截器 https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);
            }
            mOkHttpClient = okHttpClientBuilder.build();
        }
        return mOkHttpClient;
    }

    /**
     * 用户自定义拦截器
     */
    public abstract Interceptor getInterceptor();

    public String getBaseUrl() {
        AssertionsUtils.assertNotNull(mBaseUrl);
        return mBaseUrl;
    }

}
