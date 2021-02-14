package com.frewen.android.demo.network

import com.frewen.android.demo.logic.model.ArticleBean
import com.frewen.android.demo.logic.model.BannerModel
import com.frewen.network.response.AuraNetResponse
import retrofit2.http.GET


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
    suspend fun getTopArticleList(): AuraNetResponse<ArrayList<ArticleBean>>
    
}