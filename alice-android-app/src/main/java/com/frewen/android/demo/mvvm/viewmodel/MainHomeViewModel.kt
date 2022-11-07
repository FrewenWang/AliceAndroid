package com.frewen.android.demo.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.frewen.android.demo.ktx.ext.showEmpty
import com.frewen.android.demo.ktx.ext.showError
import com.frewen.android.demo.business.model.ArticleModel
import com.frewen.android.demo.business.model.BannerModel
import com.frewen.android.demo.business.model.ListDataStateWrapper
import com.frewen.android.demo.business.model.WXArticleContent
import com.frewen.android.demo.network.NyxNetworkApi
import com.frewen.demo.library.ktx.ext.request
import com.frewen.demo.library.mvvm.vm.BaseViewModel
import com.frewen.demo.library.network.ResultState
import com.kingja.loadsir.core.LoadService
import com.yanzhenjie.recyclerview.SwipeRecyclerView


/**
 * @filename: NewsViewModel
 * @introduction: 主页面的ViewModel
 * @author: Frewen.Wong
 * @time: 2020/4/14 19:20
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class MainHomeViewModel : BaseViewModel() {
    /**
     * 获取首页分页数据的页码字段
     */
    var pageNo = 0

    /**
     * 首页轮播图数据
     */
    var bannerData: MutableLiveData<ResultState<ArrayList<BannerModel>>> = MutableLiveData()

    var homeDataState: MutableLiveData<ListDataStateWrapper<ArticleModel>> = MutableLiveData()


    /**
     * 获取轮播图数据
     */
    fun getBannerData() {
        request({ NyxNetworkApi.instance.getBanner() }, bannerData)
    }

    /**
     * 获取首页文章列表数据
     * @param isRefresh 是否是刷新，即第一页
     */
    fun getHomeData(isRefresh: Boolean) {
        if (isRefresh) {
            pageNo = 0
        }
        request({ NyxNetworkApi.instance.getHomeData(pageNo) }, {
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
            homeDataState.value = listDataUiState
        }, {
            //请求失败
            val listDataUiState = ListDataStateWrapper(
                isSuccess = false,
                errMessage = it.errorMsg,
                isRefresh = isRefresh,
                listData = arrayListOf<ArticleModel>()
            )
            homeDataState.value = listDataUiState
        })
    }

    fun <T> loadListData(
        data: ListDataStateWrapper<T>,
        baseQuickAdapter: BaseQuickAdapter<T, *>,
        loadService: LoadService<*>,
        recyclerView: SwipeRecyclerView,
        swipeRefreshLayout: SwipeRefreshLayout
    ) {
        swipeRefreshLayout.isRefreshing = false
        recyclerView.loadMoreFinish(data.isEmpty, data.hasMore)
        if (data.isSuccess) {
            //成功
            when {
                //第一页并没有数据 显示空布局界面
                data.isFirstEmpty -> {
                    loadService.showEmpty()
                }
                //是第一页
                data.isRefresh -> {
                    baseQuickAdapter.setList(data.listData)
                    loadService.showSuccess()
                }
                //不是第一页
                else -> {
                    baseQuickAdapter.addData(data.listData)
                    loadService.showSuccess()
                }
            }
        } else {
            //失败
            if (data.isRefresh) {
                //如果是第一页，则显示错误界面，并提示错误信息
                loadService.showError(data.errMessage)
            } else {
                recyclerView.loadMoreError(0, data.errMessage)
            }
        }
    }
}