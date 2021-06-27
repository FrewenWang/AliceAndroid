package com.frewen.android.demo.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.frewen.android.demo.logic.model.ArticleModel
import com.frewen.android.demo.logic.model.BannerModel
import com.frewen.android.demo.logic.model.WXArticleContent
import com.frewen.android.demo.network.NyxNetworkApi
import com.frewen.demo.library.ktx.ext.request
import com.frewen.demo.library.mvvm.vm.BaseViewModel
import com.frewen.demo.library.network.ResultState


/**
 * @filename: NewsViewModel
 * @introduction: 主页面的ViewModel
 * @author: Frewen.Wong
 * @time: 2020/4/14 19:20
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class MainHomeViewModel : BaseViewModel() {
    
    //页码 首页数据页码从0开始
    var pageNo = 0
    
    var homeDataState: MutableLiveData<WXArticleContent> = MutableLiveData()
    
    //首页轮播图数据
    var bannerData: MutableLiveData<ResultState<ArrayList<BannerModel>>> = MutableLiveData()
    var articleData: MutableLiveData<ResultState<ArrayList<ArticleModel>>> = MutableLiveData()
    
    
    /**
     * 获取轮播图数据
     */
    fun getBannerData() {
        request({ NyxNetworkApi.instance.getTopArticleList() }, articleData)
    }
}