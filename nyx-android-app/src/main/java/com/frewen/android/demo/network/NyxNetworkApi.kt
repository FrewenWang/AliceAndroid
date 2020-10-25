package com.frewen.android.demo.network

import android.util.Log
import com.frewen.demo.library.network.core.NetworkApi
import com.frewen.demo.library.utils.TencentUtils.getAuthorization
import com.frewen.demo.library.utils.TencentUtils.timeStr
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @filename: TencentNetworkApi
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/5/13 21:30
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class NyxNetworkApi : NetworkApi() {

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
        return "http://baobab.kaiyanapp.com/"
    }

    override fun getPreUrl(): String {
        return "http://baobab.kaiyanapp.com/"
    }

    override fun getTestUrl(): String {
        return "http://baobab.kaiyanapp.com/"
    }

    override fun getDevUrl(): String {
        return "http://baobab.kaiyanapp.com/"
    }

    /**
     * 我们给Call增加一个扩展函数，来处理我们的协程逻辑
     */
    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    Log.d(Companion.TAG, "onFailure() called with: call = $call, t = $t")
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: retrofit2.Response<T>) {
                    Log.d(TAG, "onResponse() called with: call = $call, response = $response")
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }
            })
        }
    }

    /**
     * 这个是DoubleCheck的单例模式的Kotlin实现方法
     */
    companion object {
        private const val TAG = "NyxNetworkApi"

        @Volatile
        private var sInstance: NyxNetworkApi? = null
        val instance: NyxNetworkApi?
            get() {
                if (sInstance == null) {
                    synchronized(NyxNetworkApi::class.java) {
                        if (sInstance == null) {
                            sInstance = NyxNetworkApi()
                        }
                    }
                }
                return sInstance
            }

        fun <T> getService(service: Class<T>?): T {
            return instance!!.getRetrofit(service).create(service)
        }
    }

    /**
     * 请求推荐页面的推荐列表
     */
    suspend fun requestCommunityRecommend(url: String) = getService(NyxApiService::class.java).getCommunityRecommend(url).await()

}