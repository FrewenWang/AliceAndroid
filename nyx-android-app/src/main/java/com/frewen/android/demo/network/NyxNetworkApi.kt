package com.frewen.android.demo.network

import android.util.Log
import com.frewen.android.demo.logic.model.*
import com.frewen.android.demo.logic.model.wrapper.ApiPagerResponseWrapper
import com.frewen.demo.library.network.core.NetworkApi
import com.frewen.demo.library.utils.TencentUtils.getAuthorization
import com.frewen.demo.library.utils.TencentUtils.timeStr
import com.frewen.network.response.BasePagerRespData
import com.frewen.network.response.AuraNetResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
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
        return "https://wanandroid.com/"
    }

    override fun getPreUrl(): String {
        return "https://wanandroid.com/"
    }

    override fun getTestUrl(): String {
        return "https://wanandroid.com/"
    }

    override fun getDevUrl(): String {
        return "https://wanandroid.com/"
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
        val instance: NyxNetworkApi
            get() {
                if (sInstance == null) {
                    synchronized(NyxNetworkApi::class.java) {
                        if (sInstance == null) {
                            sInstance = NyxNetworkApi()
                        }
                    }
                }
                return sInstance!!
            }

        fun <T> getService(service: Class<T>): T {
            return instance.getRetrofit(service).create(service)
        }
    }

    /**
     * 获取首页的Banner数据
     */
    suspend fun getBanner(): AuraNetResponse<ArrayList<BannerModel>> {
        return getService(NyxApiService::class.java).getBanner()
    }

    suspend fun getHomeData(pageNo: Int): AuraNetResponse<BasePagerRespData<ArrayList<ArticleModel>>> {
        return withContext(Dispatchers.IO) {
            val listData = async { getService(NyxApiService::class.java).getArticleList(pageNo) }
            if (pageNo == 0) {
                val topData = async { getService(NyxApiService::class.java).getTopArticleList() }
                listData.await().data.dataList.addAll(0, topData.await().data)
                listData.await()
            } else {
                listData.await()
            }
        }
    }


    suspend fun getTopArticleList(): AuraNetResponse<ArrayList<ArticleModel>> {
        return getService(NyxApiService::class.java).getTopArticleList()
    }

    /**
     * 推荐页面的项目分类的标题
     */
    suspend fun getProjectTitle(): AuraNetResponse<ArrayList<ProjectClassifyModel>> {
        return getService(NyxApiService::class.java).getProjectTitle()
    }

    /**
     * 根据分类id获取项目数据
     */
    suspend fun getProjectDataByType(
        pageNo: Int,
        cid: Int,
        isNew: Boolean = false
    ): AuraNetResponse<BasePagerRespData<ArrayList<ArticleModel>>> {
        return if (isNew) {
            getService(NyxApiService::class.java).getProjectNewData(pageNo)
        } else {
            getService(NyxApiService::class.java).getProjectDataByType(pageNo, cid)
        }
    }


    suspend fun getWXArticleTitle(): AuraNetResponse<ArrayList<WXArticleTitle>> {
        return getService(NyxApiService::class.java).getWXArticleTitle()
    }


    /**
     * 获取最新项目分分页数据
     */
    suspend fun getProjectNewData(pageNo: Int): AuraNetResponse<BasePagerRespData<ArrayList<ArticleModel>>> {
        return getService(NyxApiService::class.java).getProjectNewData(pageNo)
    }

    /**
     * 获取广场列表的分页数据
     */
    suspend fun getSquareData(pageNo: Int): AuraNetResponse<BasePagerRespData<ArrayList<ArticleModel>>> {
        return getService(NyxApiService::class.java).getSquareData(pageNo)
    }

    /**
     * 获取微信公众号的数据
     */
    suspend fun getWXContentData(
        pageNo: Int,
        cid: Int
    ): AuraNetResponse<ApiPagerResponseWrapper<ArrayList<WXArticleContent>>> {
        return getService(NyxApiService::class.java).getWXContentData(pageNo, cid)
    }

    fun requestCommunityRecommend(url: String): Any {
        return Any()
    }

    /**
     * 获取当前账户的个人积分
     */
    suspend fun getIntegralData(): AuraNetResponse<IntegralModel> {
        return getService(NyxApiService::class.java).getIntegralData()
    }

    suspend fun getSystemData(): AuraNetResponse<ArrayList<SystemModel>> {
        return getService(NyxApiService::class.java).getSystemData()
    }


    suspend fun getNavigationData(): AuraNetResponse<ArrayList<NavigationModel>> {
        return getService(NyxApiService::class.java).getNavigationData()
    }

    suspend fun getAskData(pageNo: Int): AuraNetResponse<BasePagerRespData<ArrayList<ArticleModel>>> {
        return getService(NyxApiService::class.java).getAskData(pageNo)
    }


}