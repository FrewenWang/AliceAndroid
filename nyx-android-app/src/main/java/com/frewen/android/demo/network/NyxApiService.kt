package com.frewen.android.demo.network

import com.frewen.android.demo.logic.model.ArticleModel
import com.frewen.android.demo.logic.model.BannerModel
import com.frewen.android.demo.logic.model.WXArticleContent
import com.frewen.android.demo.logic.model.WXArticleTitle
import com.frewen.android.demo.logic.model.wrapper.ApiPagerResponseWrapper
import com.frewen.network.response.BasePagerResponseData
import com.frewen.network.response.AuraNetResponse
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * @filename: VideoApiService
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/8/1 11:17
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
interface NyxApiService {
    
    
    /**
     * 获取banner数据
     */
    @GET("banner/json")
    suspend fun getBanner(): AuraNetResponse<ArrayList<BannerModel>>
    
    /**
     * 获取置顶文章集合数据
     */
    @GET("article/top/json")
    suspend fun getTopArticleList(): AuraNetResponse<ArrayList<ArticleModel>>
    
    /**
     * 获取首页的文章数据
     */
    @GET("article/list/{page}/json")
    suspend fun getArticleList(@Path("page") pageNo: Int): AuraNetResponse<BasePagerResponseData<ArrayList<ArticleModel>>>
    
    
    /**
     * 公众号分类
     */
    @GET("wxarticle/chapters/json")
    suspend fun getWXArticleTitle(): AuraNetResponse<ArrayList<WXArticleTitle>>
    
    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun getWXContentData(
        @Path("page") pageNo: Int,
        @Path("id") id: Int
    ): AuraNetResponse<ApiPagerResponseWrapper<ArrayList<WXArticleContent>>>
    
    
}