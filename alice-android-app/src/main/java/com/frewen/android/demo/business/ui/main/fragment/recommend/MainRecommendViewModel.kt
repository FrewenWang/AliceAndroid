package com.frewen.android.demo.business.ui.main.fragment.recommend

import androidx.lifecycle.MutableLiveData
import com.frewen.android.demo.business.model.ArticleModel
import com.frewen.android.demo.business.model.ListDataStateWrapper
import com.frewen.android.demo.business.model.ProjectClassifyModel
import com.frewen.android.demo.network.WanAndroidApi
import com.frewen.demo.library.ktx.ext.request
import com.frewen.demo.library.mvvm.vm.BaseViewModel
import com.frewen.demo.library.network.ResultState

/**
 * @filename: NewsViewModel
 * @introduction: 推荐页面的ViewModel
 * @author: Frewen.Wong
 * @time: 2020/4/14 19:20
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class MainRecommendViewModel : BaseViewModel() {

    //页码
    var pageNo = 1

    var titleData: MutableLiveData<ResultState<ArrayList<ProjectClassifyModel>>> = MutableLiveData()

    var projectDataState: MutableLiveData<ListDataStateWrapper<ArticleModel>> = MutableLiveData()

    /**
     * 获取推荐页面的项目标题数据
     */
    fun getProjectTitleData() {
        request({ WanAndroidApi.instance.getProjectTitle() }, titleData)
    }

    /**
     * 获取推荐页面的项目分页数据
     */
    fun getProjectData(isRefresh: Boolean, cid: Int, isNew: Boolean = false) {
        if (isRefresh) {
            pageNo = if (isNew) 0 else 1
        }
        request({ WanAndroidApi.instance.getProjectDataByType(pageNo, cid, isRefresh) }, {
            pageNo++
            val listDataUiState = ListDataStateWrapper(
                isSuccess = true,
                isRefresh = isRefresh,
                isEmpty = it.isEmpty,
                hasMore = it.hasMoreData(),
                isFirstEmpty = isRefresh && it.isEmpty,
                listData = it.dataList
            )
            projectDataState.value = listDataUiState
        }, { //请求失败
            val listDataUiState = ListDataStateWrapper(
                isSuccess = false,
                errMessage = it.errorMsg,
                isRefresh = isRefresh,
                listData = arrayListOf<ArticleModel>()
            )
            projectDataState.value = listDataUiState
        })
    }
}