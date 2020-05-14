package com.frewen.github.library.network.interceptor

import com.frewen.github.library.network.core.AbsNetworkConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * @filename: RequestInterceptor
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/5/13 21:13
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class RequestInterceptor(private val networkConfig: AbsNetworkConfig) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        /* builder.cacheControl(CacheControl.FORCE_CACHE); */
        builder.addHeader("os", "android")
        // Kotlin中Int转Strong
        builder.addHeader("appVersion", networkConfig.getAppVersionCode().toString())
        return chain.proceed(builder.build())
    }

}