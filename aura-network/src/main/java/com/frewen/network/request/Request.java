package com.frewen.network.request;

import android.content.Context;
import android.text.TextUtils;

import com.frewen.network.api.BaseApiService;
import com.frewen.network.core.AuraRxHttp;
import com.frewen.network.interceptor.HeadersInterceptor;
import com.frewen.network.listener.ResponseCallback;
import com.frewen.network.model.HttpHeaders;
import com.frewen.network.model.HttpParams;


import java.util.ArrayList;
import java.util.List;

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
 * @filename: BaseRequest
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2019/4/15 0015 下午4:39
 *         Copyright ©2019 Frewen.Wong. All Rights Reserved.
 */
public abstract class Request<R extends Request> {

    private Context mContext;
    protected String baseUrl;                                              //BaseUrl
    protected String url;                                                  //请求url
    protected long readTimeOut;                                            //读超时
    protected long writeTimeOut;                                           //写超时
    protected long connectTimeout;                                         //链接超时
    protected int retryCount;                                              //重试次数默认3次

    private HttpUrl httpUrl;
    protected HttpHeaders headers = new HttpHeaders();
    protected HttpParams params = new HttpParams();                        //添加的param
    /**
     * OkHttpClient
     */
    protected OkHttpClient okHttpClient;
    private Retrofit retrofit;
    /**
     *
     */
    protected BaseApiService mApiService;

    /**
     * 单独的请求参数的配置
     */
    protected List<Converter.Factory> converterFactories = new ArrayList<>();
    protected List<CallAdapter.Factory> adapterFactories = new ArrayList<>();
    protected final List<Interceptor> interceptors = new ArrayList<>();
    private String cacheKey;

    public Request(String url) {
        this.url = url;
        mContext = AuraRxHttp.getInstance().getContext();

        AuraRxHttp auraRxHttp = AuraRxHttp.getInstance();

        this.baseUrl = auraRxHttp.getBaseUrl();

        if (!TextUtils.isEmpty(this.baseUrl)) {
            httpUrl = HttpUrl.parse(baseUrl);
        }

        if (null == this.baseUrl && !TextUtils.isEmpty(this.url)
                && (url.startsWith("http://") || url.startsWith("https://"))) {
            httpUrl = HttpUrl.parse(url);
            baseUrl = httpUrl.url().getProtocol() + "://" + httpUrl.url().getHost() + "/";
        }
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

    /**
     * 根据Request请求的参数
     * 构建生成的OkHttpClientBuilder
     */
    private OkHttpClient.Builder createOkHttpClientBuilder() {
        // 如果Request没有指定自定义的请求配置参数，
        // 我们就直接属于我们默认初始化OkHttpClient.Builder
        if (readTimeOut <= 0 && writeTimeOut <= 0 && connectTimeout <= 0 && headers.isEmpty()) {
            OkHttpClient.Builder builder = AuraRxHttp.getInstance().getOkHttpClientBuilder();
            return builder;
        } else {
            final OkHttpClient.Builder newClientBuilder = AuraRxHttp.getInstance().getOkHttpClientBuilder().build().newBuilder();
            //添加头  头添加放在最前面方便其他拦截器可能会用到
            newClientBuilder.addInterceptor(new HeadersInterceptor(headers));
            return newClientBuilder;
        }
    }

    private Retrofit.Builder generateRetrofit() {
        if (converterFactories.isEmpty() && adapterFactories.isEmpty()) {
            Retrofit.Builder builder = getRetrofitBuilder();
            if (!TextUtils.isEmpty(baseUrl)) {
                builder.baseUrl(baseUrl);
            }
            return builder;
        } else {

        }
        Retrofit.Builder builder = getRetrofitBuilder();
        return builder;
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
     * 添加缓存读取的Key标记
     *
     * @param key
     */
    public R addCacheKey(String key) {
        this.cacheKey = key;
        return (R) this;
    }

    protected abstract Observable<ResponseBody> generateRequest();

    protected R build() {
        OkHttpClient.Builder okHttpClientBuilder = createOkHttpClientBuilder();
        final Retrofit.Builder retrofitBuilder = generateRetrofit();
        okHttpClient = okHttpClientBuilder.build();
        retrofitBuilder.client(okHttpClient);
        retrofit = retrofitBuilder.build();
        mApiService = retrofit.create(BaseApiService.class);
        return (R) this;
    }

    // ==============Request的执行的两个请求=========================
    public <Data> void execute(ResponseCallback<Data> callbackListener) {

    }

    public <Data> void execute() {

    }

}
