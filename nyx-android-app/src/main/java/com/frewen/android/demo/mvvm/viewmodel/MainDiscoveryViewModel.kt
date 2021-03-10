package com.frewen.android.demo.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.frewen.android.demo.logic.model.WXArticleModel
import com.frewen.android.demo.network.NyxNetworkApi
import com.frewen.demo.library.mvvm.vm.BaseViewModel
import com.frewen.demo.library.network.ResultState

/**
 * 主页面的发现页面的ViewModel
 */
class MainDiscoveryViewModel : BaseViewModel() {
    /**
     * 实例化可变实时数据的微信公众号标题的数据
     */
    var wxArticleTitleData: MutableLiveData<ResultState<ArrayList<WXArticleModel>>> = MutableLiveData()
    
    /**
     * 请求发现页面的Title的数据
     */
    fun requestDiscoveryTitleData() {
        request({ NyxNetworkApi.instance.getWXArticleTitle() }, wxArticleTitleData)
    }
    
}