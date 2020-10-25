package com.frewen.android.demo.network

import com.frewen.android.demo.logic.model.data.CommunityRecommendModel
import com.frewen.network.api.BaseApiService
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * @filename: VideoApiService
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/8/1 11:17
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
interface NyxApiService {

    @GET
    fun getCommunityRecommend(@Url url: String): Call<CommunityRecommendModel>
}