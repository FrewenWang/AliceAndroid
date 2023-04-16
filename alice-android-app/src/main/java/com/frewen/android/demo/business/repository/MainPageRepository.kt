package com.frewen.android.demo.business.repository

import com.frewen.android.demo.network.AliceNetworkApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @filename: MainPageRepository
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/10/24 18:39
 * @version 1.0.0
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class MainPageRepository private constructor() {

    companion object {
        @Volatile
        private var repository: MainPageRepository? = null

        fun getInstance(): MainPageRepository {
            if (repository == null) {
                synchronized(MainPageRepository::class.java) {
                    if (repository == null) {
                        repository = MainPageRepository()
                    }
                }
            }
            return repository!!
        }
    }

    /**
     * 进行刷新推荐页面的推荐列表的请求接口
     */
    suspend fun fetchCommunityRecommend(url: String) = requestCommunityRecommend(url)


    private suspend fun requestCommunityRecommend(url: String) = withContext(Dispatchers.IO) {
        val response = AliceNetworkApi.instance?.requestCommunityRecommend(url)
        response
    }


}