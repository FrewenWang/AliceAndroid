package com.frewen.android.demo.business.samples.navigation.annotation.recommend.content

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.frewen.android.demo.business.model.data.CommunityRecommendModel
import com.frewen.android.demo.business.repository.MainPageRepository
import com.frewen.aura.framework.mvvm.vm.AbsViewModel
import javax.inject.Inject


/**
 * @filename: EyepetizerRecommendFragment
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/10/24 10:31
 * @version 1.0.0
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class EyeRecommendViewModel @Inject constructor() : AbsViewModel() {

    companion object {
        private const val TAG = "EyeRecommendViewModel"
    }


    var nextPageUrl: String? = null
    var dataList = ArrayList<CommunityRecommendModel.Item>()

    private var requestParamLiveData = MutableLiveData<String>()

    /**
     * TODO 需要重点学习.LiveData配合Kotlin
     */
    val dataListLiveData = Transformations.switchMap(requestParamLiveData) { url ->
        liveData {
            val result = try {
                val recommend = MainPageRepository.getInstance().fetchCommunityRecommend(url)
                Log.d(TAG, "recommend() called == $recommend")
                Result.success(recommend)
            } catch (e: Exception) {
                Log.d(TAG, "recommend() called == $e")
                Result.failure<CommunityRecommendModel>(e)
            }
            emit(result)
        }
    }

    fun onRefresh() {
        requestParamLiveData.value = "http://baobab.kaiyanapp.com/api/v7/community/tab/rec"
    }

    fun onLoadMore() {
        requestParamLiveData.value = nextPageUrl ?: ""
    }

}