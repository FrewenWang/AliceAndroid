package com.frewen.android.demo.network

import com.frewen.demo.library.network.core.NetworkApi
import com.frewen.demo.library.utils.TencentUtils.getAuthorization
import com.frewen.demo.library.utils.TencentUtils.timeStr
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * @filename: TencentNetworkApi
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/5/13 21:30
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class TencentNetworkApi : NetworkApi() {
    override fun getInterceptor(): Interceptor {
        /**
         * 匿名内部类的实现
         */
        return object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val timeStr = timeStr
                val builder = chain.request().newBuilder()
                builder.addHeader("Source", "source")
                builder.addHeader("Authorization", getAuthorization(timeStr))
                builder.addHeader("Date", timeStr)
                return chain.proceed(builder.build())
            }
        }
    }

    override fun getProdUrl(): String {
        return "http://service-o5ikp40z-1255468759.ap-shanghai.apigateway.myqcloud.com/"
    }

    override fun getPreUrl(): String {
        return "http://service-o5ikp40z-1255468759.ap-shanghai.apigateway.myqcloud.com/"
    }

    override fun getTestUrl(): String {
        return "http://service-o5ikp40z-1255468759.ap-shanghai.apigateway.myqcloud.com/"
    }

    override fun getDevUrl(): String {
        return "http://service-o5ikp40z-1255468759.ap-shanghai.apigateway.myqcloud.com/"
    }

    /**
     * 这个是DoubleCheck的单例模式的Kotlin实现方法
     */
    companion object {
        @Volatile
        private var sInstance: TencentNetworkApi? = null
        val instance: TencentNetworkApi?
            get() {
                if (sInstance == null) {
                    synchronized(TencentNetworkApi::class.java) {
                        if (sInstance == null) {
                            sInstance = TencentNetworkApi()
                        }
                    }
                }
                return sInstance
            }

        fun <T> getService(service: Class<T>?): T {
            return instance!!.getRetrofit(service).create(service)
        }
    }
}