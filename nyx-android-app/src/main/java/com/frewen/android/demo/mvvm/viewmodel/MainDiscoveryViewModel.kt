package com.frewen.android.demo.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.frewen.android.demo.logic.model.ListDataStateWrapper
import com.frewen.android.demo.logic.model.WXArticleContent
import com.frewen.android.demo.logic.model.WXArticleTitle
import com.frewen.android.demo.network.NyxNetworkApi
import com.frewen.demo.library.ktx.ext.request
import com.frewen.demo.library.mvvm.vm.BaseViewModel
import com.frewen.demo.library.network.ResultState

/**
 * 主页面的发现页面的ViewModel
 */
class MainDiscoveryViewModel : BaseViewModel() {
    var pageNo = 1
    
    /**
     * 实例化可变实时数据的微信公众号标题的数据
     */
    var wxArticleTitleData: MutableLiveData<ResultState<ArrayList<WXArticleTitle>>> = MutableLiveData()
    
    var wxArticleContentModel: MutableLiveData<ListDataStateWrapper<WXArticleContent>> = MutableLiveData()
    
    /**
     * 请求发现页面的Title的数据
     */
    fun requestDiscoveryTitleData() {
        request({ NyxNetworkApi.instance.getWXArticleTitle() }, wxArticleTitleData)
    }
    
    /**
     *  请求发现的页面的的微信公众号的数据
     */
    fun getWXContentData(cid: Int, isRefresh: Boolean = true) {
        if (isRefresh) {
            pageNo = 1
        }
        request({ NyxNetworkApi.instance.getWXContentData(pageNo, cid) }, { //请求成功
            pageNo++
            val listDataUiState = ListDataStateWrapper(
                    isSuccess = true,
                    isRefresh = isRefresh,
                    isEmpty = it.isEmpty(),
                    hasMore = it.hasMore(),
                    isFirstEmpty = isRefresh && it.isEmpty(),
                    listData = it.datas)
            wxArticleContentModel.value = listDataUiState
        }, { //请求失败
            val listDataUiState = ListDataStateWrapper(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    isRefresh = isRefresh,
                    listData = arrayListOf<WXArticleContent>()
            )
            wxArticleContentModel.value = listDataUiState
        })
    }
    
}