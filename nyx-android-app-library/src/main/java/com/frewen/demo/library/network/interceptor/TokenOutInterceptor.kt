package com.frewen.demo.library.network.interceptor

import com.frewen.network.response.AuraNetResponse
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

/**
 * TODO 暂时没有启用，按照业务进行处理
 * 设置token过期的拦截器
 */
class TokenOutInterceptor : Interceptor {

    val gson: Gson by lazy { Gson() }

    @kotlin.jvm.Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        return if (response.body != null && response.body!!.contentType() != null) {
            val mediaType = response.body!!.contentType()
            val string = response.body!!.string()
            val responseBody = ResponseBody.create(mediaType, string)
            val apiResponse = gson.fromJson(string, AuraNetResponse::class.java)
            //判断逻辑 模拟一下
            // if (apiResponse.errorCode == 99999) {
            //     //如果是普通的activity话 可以直接跳转，如果是navigation中的fragment，可以发送通知跳转
            //     appContext.startActivity(Intent(appContext, TestActivity::class.java).apply {
            //         flags = Intent.FLAG_ACTIVITY_NEW_TASK
            //     })
            // }
            response.newBuilder().body(responseBody).build()
        } else {
            response
        }
    }
}