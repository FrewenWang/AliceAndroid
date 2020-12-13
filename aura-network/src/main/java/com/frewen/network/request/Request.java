package com.frewen.network.request;

import android.content.Context;
import android.text.TextUtils;

import com.frewen.aura.toolkits.utils.AssertionsUtils;
import com.frewen.network.api.BaseApiService;
import com.frewen.network.cache.CacheStrategy;
import com.frewen.network.core.AuraRxHttp;
import com.frewen.network.interceptor.HeadersInterceptor;
import com.frewen.network.model.HttpHeaders;
import com.frewen.network.model.HttpParams;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;

import static com.frewen.network.core.AuraRxHttp.getRetrofitBuilder;

/**
 * @filename: Request
 * @author: Frewen.Wong
 * @time: 12/12/20 8:22 PM
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public abstract class Request<R extends Request> implements Cloneable {

    private Context mContext;

    protected String baseUrl;                                              //Http请求BaseUrl
    protected String pathUrl;                                              //Http请求路径url
    private HttpUrl httpUrl;                                               //Http请求的URL封装对象

    protected long readTimeOut;                                            //读超时
    protected long writeTimeOut;                                           //写超时
    protected long connectTimeout;                                         //链接超时
    protected int retryCount;                                              //重试次数默认3次

    protected HttpHeaders headers = new HttpHeaders();
    protected HttpParams params = new HttpParams();                        //添加的param
    /**
     * OkHttpClient
     */
    protected OkHttpClient okHttpClient;
    private Retrofit retrofit;
    /**
     * Retrol
     */
    protected BaseApiService mApiService;

    /**
     * 单独的请求参数的配置
     */
    protected List<Converter.Factory> converterFactories = new ArrayList<>();
    protected List<CallAdapter.Factory> adapterFactories = new ArrayList<>();

    protected final List<Interceptor> interceptors = new ArrayList<>();                 // 拦截器列表
    protected final List<Interceptor> networkInterceptors = new ArrayList<>();          // 网络拦截器列表


    private String cacheKey;
    private CacheStrategy cacheStrategy;
    protected boolean isSyncRequest;

    public Request(String url) {
        mContext = AuraRxHttp.getInstance().getContext();
        AuraRxHttp auraRxHttp = AuraRxHttp.getInstance();
        initRequestUrl(url);
        //超时重试次数
        retryCount = auraRxHttp.getRetryCount();

        //默认添加 Accept-Language
        String acceptLanguage = HttpHeaders.getAcceptLanguage();
        if (!TextUtils.isEmpty(acceptLanguage)) {
            addHeader(HttpHeaders.HEAD_KEY_ACCEPT_LANGUAGE, acceptLanguage);
        }

        //默认添加 User-Agent
        String userAgent = HttpHeaders.getUserAgent();
        if (!TextUtils.isEmpty(userAgent)) {
            addHeader(HttpHeaders.HEAD_KEY_USER_AGENT, userAgent);
        }
        //添加公共请求参数
        if (auraRxHttp.getCommonParams() != null) {
            params.put(auraRxHttp.getCommonParams());
        }
        // 添加公共请求参数头
        if (auraRxHttp.getCommonHeaders() != null) {
            headers.put(auraRxHttp.getCommonHeaders());
        }
    }

    private void initRequestUrl(String url) {
        if (TextUtils.isEmpty(url)) {
            throw new NullPointerException("request url should not be null");
        }
        AuraRxHttp auraRxHttp = AuraRxHttp.getInstance();
        // 再进行处理我们传入的pathUrl地址
        if (url.startsWith("http://") || url.startsWith("https://")) {
            this.httpUrl = HttpUrl.parse(url);
            this.baseUrl = httpUrl.url().getProtocol() + "://" + httpUrl.url().getHost() + "/";
            this.pathUrl = "";
        } else {
            // 获取RxHttp里面设置的BaseUrl
            this.baseUrl = auraRxHttp.getBaseUrl();
            this.httpUrl = HttpUrl.parse(baseUrl);
            this.pathUrl = url;
        }
    }

    /**
     * 添加头信息
     */
    public R addHeaders(HttpHeaders headers) {
        this.headers.put(headers);
        return (R) this;
    }

    /**
     * 添加头信息
     *
     * @param key   HeaderKey
     * @param value HeaderValue
     */
    public R addHeader(String key, String value) {
        headers.put(key, value);
        return (R) this;
    }

    /**
     * 添加头信息
     *
     * @param key   HeaderKey
     * @param value HeaderValue
     */
    public R addHeaders(String key, String value) {
        headers.put(key, value);
        return (R) this;
    }

    /**
     * 添加头信息
     */
    public R headers(HttpHeaders headers) {
        this.headers.put(headers);
        return (R) this;
    }

    /**
     * 添加参数键值对
     *
     * @param key
     * @param value
     */
    public R addParam(String key, Object value) {
        // 只能是基本类型 int string byte char
        // 我们通过反射去校验
        params.put(key, value);
        return (R) this;
    }

    /**
     * 设置是否是同步请求
     *
     * @param syncRequest
     */
    public R setSyncRequest(boolean syncRequest) {
        this.isSyncRequest = syncRequest;
        return (R) this;
    }

    /**
     * 设置缓存策略
     *
     * @param cacheStrategy
     */
    public R setCacheStrategy(CacheStrategy cacheStrategy) {
        this.cacheStrategy = cacheStrategy;
        return (R) this;
    }

    /**
     * 添加缓存读取的Key标记
     *
     * @param key
     */
    public R addCacheKey(String key) {
        this.cacheKey = key;
        return (R) this;
    }

    /**
     * 设置超时时间
     *
     * @param readTimeOut
     */
    public R readTimeOut(long readTimeOut) {
        this.readTimeOut = readTimeOut;
        return (R) this;
    }

    /**
     * 设置超时时间
     *
     * @param writeTimeOut
     */
    public R writeTimeOut(long writeTimeOut) {
        this.writeTimeOut = writeTimeOut;
        return (R) this;
    }

    /**
     * 设置超时时间
     *
     * @param connectTimeout
     */
    public R connectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
        return (R) this;
    }

    /**
     * ====================================设置参数==================================================
     */
    public R addParams(HttpParams params) {
        this.params.put(params);
        return (R) this;
    }

    public R addParams(Map<String, String> keyValues) {
        params.put(keyValues);
        return (R) this;
    }

    public R addParam(String key, String value) {
        params.put(key, value);
        return (R) this;
    }

    public R removeParam(String key) {
        params.remove(key);
        return (R) this;
    }

    public R removeAllParams() {
        params.clear();
        return (R) this;
    }

    /**
     * ========================================网络请求设置拦截器=====================================
     */
    public R addInterceptor(Interceptor interceptor) {
        interceptors.add(AssertionsUtils.assertNotNull(interceptor, "interceptor == null"));
        return (R) this;
    }

    public R addNetworkInterceptor(Interceptor interceptor) {
        networkInterceptors.add(AssertionsUtils.assertNotNull(interceptor, "interceptor == null"));
        return (R) this;
    }

    /**
     * =============================创建Request请求的路基=============================================
     */
    protected R build() {
        OkHttpClient.Builder okHttpClientBuilder = createOkHttpClientBuilder();
        final Retrofit.Builder retrofitBuilder = generateRetrofit();
        // 重新创建okHttpClient对象
        okHttpClient = okHttpClientBuilder.build();
        retrofitBuilder.client(okHttpClient);
        retrofit = retrofitBuilder.build();
        mApiService = retrofit.create(AuraRxHttp.getInstance().getApiService());
        return (R) this;
    }


    /**
     * 根据Request请求的参数
     * 构建生成的OkHttpClientBuilder
     */
    private OkHttpClient.Builder createOkHttpClientBuilder() {
        // 如果Request没有指定自定义的请求配置参数，
        // 我们就直接使用我们AuraRxHttp默认初始化OkHttpClient.Builder
        if (readTimeOut <= 0 && writeTimeOut <= 0 && connectTimeout <= 0 && headers.isEmpty()) {
            OkHttpClient.Builder builder = AuraRxHttp.getInstance().getOkHttpClientBuilder();
            return builder;
        } else {
            final OkHttpClient.Builder newClientBuilder =
                    AuraRxHttp.getInstance().getOkHttpClientBuilder().build().newBuilder();
            if (readTimeOut > 0)
                newClientBuilder.readTimeout(readTimeOut, TimeUnit.MILLISECONDS);
            if (writeTimeOut > 0)
                newClientBuilder.writeTimeout(writeTimeOut, TimeUnit.MILLISECONDS);
            if (connectTimeout > 0)
                newClientBuilder.connectTimeout(connectTimeout, TimeUnit.MILLISECONDS);
            //添加头  头添加放在最前面方便其他拦截器可能会用到
            newClientBuilder.addInterceptor(new HeadersInterceptor(headers));
            return newClientBuilder;
        }
    }


    private Retrofit.Builder generateRetrofit() {
        // 如果转换器工厂或者适配器工厂都是空的，那么我们就没有必要再去实例化自己的Retrofit.Builder
        if (converterFactories.isEmpty() && adapterFactories.isEmpty()) {
            Retrofit.Builder builder = getRetrofitBuilder();
            if (!TextUtils.isEmpty(baseUrl)) {
                builder.baseUrl(baseUrl);
            }
            return builder;
        } else {
            // w我们来实例化自己的Retrofit.Builder()
            final Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
            if (!TextUtils.isEmpty(baseUrl)) retrofitBuilder.baseUrl(baseUrl);

            // 如果转换器工厂不是控
            if (!converterFactories.isEmpty()) {
                // 添加所有的转换器工厂
                for (Converter.Factory converterFactory : converterFactories) {
                    retrofitBuilder.addConverterFactory(converterFactory);
                }
            } else {
                //如果转换器工厂是空的，那么我们就获取AuraRxHttp中的Retrofit的转换器工厂
                Retrofit.Builder newBuilder = getRetrofitBuilder();
                if (!TextUtils.isEmpty(baseUrl)) {
                    newBuilder.baseUrl(baseUrl);
                }
                List<Converter.Factory> listConverterFactory = newBuilder.build().converterFactories();
                for (Converter.Factory factory : listConverterFactory) {
                    retrofitBuilder.addConverterFactory(factory);
                }
            }
            if (!adapterFactories.isEmpty()) {
                for (CallAdapter.Factory adapterFactory : adapterFactories) {
                    retrofitBuilder.addCallAdapterFactory(adapterFactory);
                }
            } else {
                //获取全局的对象重新设置
                Retrofit.Builder newBuilder = getRetrofitBuilder();
                List<CallAdapter.Factory> listAdapterFactory =
                        newBuilder.baseUrl(baseUrl).build().callAdapterFactories();
                for (CallAdapter.Factory factory : listAdapterFactory) {
                    retrofitBuilder.addCallAdapterFactory(factory);
                }
            }
            return retrofitBuilder;
        }
    }


    /**
     * 创建Request。不同的Request对应各自实现他们的generateRequest方法
     */
    protected abstract Observable<ResponseBody> generateRequest();

    public boolean isSyncRequest() {
        return isSyncRequest;
    }
}
