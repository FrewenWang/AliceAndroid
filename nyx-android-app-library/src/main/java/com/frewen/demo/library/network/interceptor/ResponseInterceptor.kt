package com.frewen.demo.library.network.interceptor

import com.frewen.aura.logger.core.ALogger
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
class ResponseInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBeginTime = System.currentTimeMillis()
        val response = chain.proceed(chain.request())
        ALogger.d(TAG, "request cost time=" + (System.currentTimeMillis() - requestBeginTime))
        return response
    }

    companion object {
        private const val TAG = "ResponseInterceptor"
    }
}