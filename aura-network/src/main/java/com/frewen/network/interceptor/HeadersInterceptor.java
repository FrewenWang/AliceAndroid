package com.frewen.network.interceptor;

import com.frewen.network.model.HttpHeaders;

import java.io.IOException;
import java.util.Map;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @filename: HeadersInterceptor
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/7/28 00:05
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 * @see com.frewen.network.request.Request 中给HttpClientBuilder对象添加Header实例
 */
public class HeadersInterceptor implements Interceptor {

    private HttpHeaders mHttpHeaders;

    public HeadersInterceptor(HttpHeaders httpHeaders) {
        mHttpHeaders = httpHeaders;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        if (null != mHttpHeaders || mHttpHeaders.getHeadersMap().isEmpty()) {
            return chain.proceed(builder.build());
        }

        /// 这个其实是在请求之前做的一些操作
        try {
            for (Map.Entry<String, String> entry : mHttpHeaders.getHeadersMap().entrySet()) {
                //去除重复的header
                //builder.removeHeader(entry.getKey());
                //builder.addHeader(entry.getKey(), entry.getValue()).build();
                builder.header(entry.getKey(), entry.getValue()).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return chain.proceed(builder.build());
    }
}