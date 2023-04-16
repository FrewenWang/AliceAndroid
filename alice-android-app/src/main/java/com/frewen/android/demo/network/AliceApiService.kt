package com.frewen.android.demo.network

import com.frewen.android.demo.business.model.*
import com.frewen.android.demo.business.model.wrapper.ApiPagerResponseWrapper
import com.frewen.network.response.BasePagerRespData
import com.frewen.network.response.AuraNetResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * @filename: VideoApiService
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/8/1 11:17
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
interface AliceApiService {
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
    suspend fun getArticleList(@Path("page") pageNo: Int): AuraNetResponse<BasePagerRespData<ArrayList<ArticleModel>>>

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

    /**
     * 推荐页面的项目分类的标题
     */
    @GET("project/tree/json")
    suspend fun getProjectTitle(): AuraNetResponse<ArrayList<ProjectClassifyModel>>

    /**
     * 根据分类id获取项目数据
     */
    @GET("project/list/{page}/json")
    suspend fun getProjectDataByType(
        @Path("page") pageNo: Int, @Query("cid") cid: Int
    ): AuraNetResponse<BasePagerRespData<ArrayList<ArticleModel>>>

    /**
     * 获取最新项目数据
     */
    @GET("article/listproject/{page}/json")
    suspend fun getProjectNewData(@Path("page") pageNo: Int)
            : AuraNetResponse<BasePagerRespData<ArrayList<ArticleModel>>>

    /**
     * 获取广场列表的分页数据
     */
    @GET("user_article/list/{page}/json")
    suspend fun getSquareData(@Path("page") page: Int)
            : AuraNetResponse<BasePagerRespData<ArrayList<ArticleModel>>>


    /**
     * 获取当前账户的个人积分
     */
    @GET("lg/coin/userinfo/json")
    suspend fun getIntegralData(): AuraNetResponse<IntegralModel>

    /**
     * 获取导航数据
     */
    @GET("navi/json")
    suspend fun getNavigationData(): AuraNetResponse<ArrayList<NavigationModel>>

    /**
     * 每日一问列表数据
     */
    @GET("wenda/list/{page}/json")
    suspend fun getAskData(@Path("page") page: Int)
            : AuraNetResponse<BasePagerRespData<ArrayList<ArticleModel>>>

    /**
     * 获取体系数据
     */
    @GET("tree/json")
    suspend fun getSystemData(): AuraNetResponse<ArrayList<SystemModel>>


}