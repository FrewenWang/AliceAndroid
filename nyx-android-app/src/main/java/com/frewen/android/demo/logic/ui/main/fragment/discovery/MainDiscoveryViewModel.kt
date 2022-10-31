package com.frewen.android.demo.logic.ui.main.fragment.discovery

import androidx.lifecycle.MutableLiveData
import com.frewen.android.demo.logic.model.*
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
     * 发现页面的广场数据的状态数据
     */
    var plazaDataState: MutableLiveData<ListDataStateWrapper<ArticleModel>> = MutableLiveData()

    /**
     * 实例化可变实时数据的微信公众号标题的数据
     * 我们使用懒加载的形式来实例化wxArticleTitleData对象
     */
    val wxArticleTitleData: MutableLiveData<ResultState<ArrayList<WXArticleTitle>>> by lazy {
        MutableLiveData<ResultState<ArrayList<WXArticleTitle>>>()
    }

    var wxArticleContentModel: MutableLiveData<ListDataStateWrapper<WXArticleContent>> =
        MutableLiveData()

    //每日一问数据
    var askDataState: MutableLiveData<ListDataStateWrapper<ArticleModel>> = MutableLiveData()

    var systemDataState: MutableLiveData<ListDataStateWrapper<SystemModel>> = MutableLiveData()

    var navigationDataState: MutableLiveData<ListDataStateWrapper<NavigationModel>> =
        MutableLiveData()


    /**
     * 请求发现页面的Title的数据
     */
    fun requestDiscoveryTitleData() {
        request({ NyxNetworkApi.instance.getWXArticleTitle() }, wxArticleTitleData)
    }

    /**
     * 获取广场分页数据
     */
    fun getPlazaData(isRefresh: Boolean = false) {
        if (isRefresh) {
            pageNo = 0
        }
        request({ NyxNetworkApi.instance.getSquareData(pageNo) }, {
            //请求成功
            pageNo++
            val listDataUiState = ListDataStateWrapper(
                isSuccess = true,
                isRefresh = isRefresh,
                isEmpty = it.isEmpty,
                hasMore = it.hasMoreData(),
                isFirstEmpty = isRefresh && it.isEmpty,
                listData = it.dataList
            )
            plazaDataState.value = listDataUiState
        }, {
            //请求失败
            val listDataUiState = ListDataStateWrapper(
                isSuccess = false,
                errMessage = it.errorMsg,
                isRefresh = isRefresh,
                listData = arrayListOf<ArticleModel>()
            )
            plazaDataState.value = listDataUiState
        })
    }

    /**
     * 获取每日一问数据
     */
    fun getAskData(isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 1 //每日一问的页码从1开始
        }
        request({ NyxNetworkApi.instance.getAskData(pageNo) }, {
            //请求成功
            pageNo++
            val listDataUiState = ListDataStateWrapper(
                isSuccess = true,
                isRefresh = isRefresh,
                isEmpty = it.isEmpty(),
                hasMore = it.hasMoreData(),
                isFirstEmpty = isRefresh && it.isEmpty(),
                listData = it.dataList
            )
            askDataState.value = listDataUiState
        }, {
            //请求失败
            val listDataUiState = ListDataStateWrapper(
                isSuccess = false,
                errMessage = it.errorMsg,
                isRefresh = isRefresh,
                listData = arrayListOf<ArticleModel>()
            )
            askDataState.value = listDataUiState
        })
    }


    fun getSystemData() {
        request({ NyxNetworkApi.instance.getSystemData() }, {
            //请求成功
            val dataUiState = ListDataStateWrapper(
                isSuccess = true,
                listData = it
            )
            systemDataState.value = dataUiState
        }, {
            //请求失败
            val dataUiState =
                ListDataStateWrapper(
                    isSuccess = false,
                    errMessage = it.errorMsg,
                    listData = arrayListOf<SystemModel>()
                )
            systemDataState.value = dataUiState
        })
    }


    fun getNavigationData() {
        request({ NyxNetworkApi.instance.getNavigationData() }, {
            //请求成功
            val dataUiState = ListDataStateWrapper(
                isSuccess = true,
                listData = it
            )
            navigationDataState.value = dataUiState
        }, {
            //请求失败
            val dataUiState = ListDataStateWrapper(
                isSuccess = false,
                errMessage = it.errorMsg,
                listData = arrayListOf<NavigationModel>()
            )
            navigationDataState.value = dataUiState
        })
    }

    /**
     *  请求发现的页面的的微信公众号的分页数据
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
                listData = it.datas
            )
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