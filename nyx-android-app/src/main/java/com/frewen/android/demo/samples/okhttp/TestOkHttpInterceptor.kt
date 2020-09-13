package com.frewen.android.demo.samples.okhttp

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.lang.String

/**
 * @filename: TestOkHttpInterceptor
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/9/12 22:00
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class TestOkHttpInterceptor : Interceptor {

    companion object {
        const val TAG = "TestOkHttpInterceptor"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d(TAG, "=======TestOkHttpInterceptor拦截器开始===========" + Thread.currentThread().name)

        // 获取请求对象
        // 获取请求对象
        val request: Request = chain.request()

        val t1 = System.nanoTime()
        Log.d(TAG, String.format("发送请求 request %s on %s%n%s",
                request.url, chain.connection(), request.headers))

        // 发起HTTP请求，并获取响应对象
        val response = chain.proceed(request)

        val t2 = System.nanoTime()
        Log.d(TAG, kotlin.String.format("接收到响应结果 response for %s in %.1fms%n%s",
                response.request.url, (t2 - t1) / 1e6, response.headers))

        return response;
    }
}