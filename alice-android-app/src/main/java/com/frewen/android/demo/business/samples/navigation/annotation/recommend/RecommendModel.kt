package com.frewen.android.demo.business.samples.navigation.annotation.recommend

import androidx.lifecycle.MutableLiveData
import com.frewen.android.demo.business.model.ArticleModel
import com.frewen.android.demo.business.model.ListDataStateWrapper
import com.frewen.android.demo.business.model.RecommendTabRespData
import com.frewen.android.demo.network.AliceNetworkApi
import com.frewen.demo.library.ktx.ext.request
import com.frewen.demo.library.mvvm.vm.BaseViewModel
import com.frewen.demo.library.network.ResultState

class RecommendModel : BaseViewModel() {

    var pageNo = 1

    //首页轮播图数据
    var recommendTabData: MutableLiveData<ResultState<ArrayList<RecommendTabRespData>>> =
        MutableLiveData()

    /**
     * 获取推荐页面的项目数据
     */
    var projectDataState: MutableLiveData<ListDataStateWrapper<ArticleModel>> = MutableLiveData()

    fun getRecommendData() {
        // request({ NyxNetworkApi.instance.getProjectTitle() }, recommendTabData)
    }

    fun getProjectData(isRefresh: Boolean, cid: Int, isNew: Boolean = false) {
        if (isRefresh) {
            pageNo = if (isNew) 0 else 1
        }
        request({
            AliceNetworkApi.instance.getProjectDataByType(pageNo, cid)
        }, {
            val listDataUiState =
                ListDataStateWrapper(
                    isSuccess = true,
                    isRefresh = isRefresh,
                    isEmpty = it.isEmpty,
                    hasMore = it.hasMoreData(),
                    isFirstEmpty = isRefresh && it.isEmpty,
                    listData = it.dataList
                )
            projectDataState.value = listDataUiState
        })

    }

}