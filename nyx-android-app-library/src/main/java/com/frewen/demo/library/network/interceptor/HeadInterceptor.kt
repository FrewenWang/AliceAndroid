package com.frewen.demo.library.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * 自定义头部参数拦截器，传入heads
 */
class HeadInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("token", "token123456").build()
        builder.addHeader("device", "Android").build()
        // 设置是否登录的标识变量。暂时没有实现
        // builder.addHeader("isLogin", AbsNetworkConfig::isDebug.toString())
        return chain.proceed(builder.build())
    }

}