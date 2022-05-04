package com.frewen.android.demo.logic.ui.recommend

import androidx.lifecycle.MutableLiveData
import com.frewen.android.demo.logic.model.ArticleModel
import com.frewen.android.demo.logic.model.ListDataStateWrapper
import com.frewen.android.demo.logic.model.RecommendTabRespData
import com.frewen.android.demo.network.NyxNetworkApi
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
        request({ NyxNetworkApi.instance.getRecommendTabData() }, recommendTabData)
    }

    fun getProjectData(isRefresh: Boolean, cid: Int, isNew: Boolean = false) {
        if (isRefresh) {
            pageNo = if (isNew) 0 else 1
        }
        request({
            NyxNetworkApi.instance.getProjectDataByType(pageNo, cid)
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