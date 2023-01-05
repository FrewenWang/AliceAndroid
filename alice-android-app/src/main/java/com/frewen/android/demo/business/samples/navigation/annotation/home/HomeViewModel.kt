package com.frewen.android.demo.business.samples.navigation.annotation.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.frewen.android.demo.business.model.ArticleModel
import com.frewen.android.demo.business.model.BannerModel
import com.frewen.android.demo.business.model.ListDataStateWrapper
import com.frewen.android.demo.business.model.Post
import com.frewen.android.demo.network.WanAndroidApi
import com.frewen.demo.library.ktx.ext.request
import com.frewen.demo.library.mvvm.vm.BaseViewModel
import com.frewen.demo.library.network.ResultState
import javax.inject.Inject

/**
 * @filename: MsgViewModel
 * @introduction:
 *
 *
 * @author: Frewen.Wong
 * @time: 2020/9/5 15:01
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class HomeViewModel @Inject constructor() : BaseViewModel() {

    /**
     * Kotlin中，我们使用TAG 一般使用伴生对象
     */
    companion object {
        private const val TAG = "HomeViewModel"
    }

    private var pageNo: Int = 0

    /**
     * 由于LiveData和MutableLiveData都是一个概念的东西(只是作用范围不同)所以就不重复解释了,直接理解LiveData就可以明白MutableLiveData
     * 直接理解LiveData的字面意思是前台数据,其实这其实是很准确的表达.下面我们来说说LiveData的几个特征:
     * 1.首先LiveData其实与数据实体类(POJO类)是一样的东西,它负责暂存数据.
     * 2.其次LiveData其实也是一个观察者模式的数据实体类,它可以跟它注册的观察者回调数据是否已经更新.
     * 3.LiveData还能知晓它绑定的Activity或者Fragment的生命周期,它只会给前台活动的activity回调(这个很厉害).
     * 这样你可以放心的在它的回调方法里直接将数据添加到View,而不用担心会不会报错.(你也可以不用费心费力判断Fragment是否还存活)
     * LiveData与MutableLiveData区别
     *
     * LiveData与MutableLiveData的其实在概念上是一模一样的.唯一几个的区别如下:
     * 1.MutableLiveData的父类是LiveData
     * 2.LiveData在实体类里可以通知指定某个字段的数据更新.
     * 3.MutableLiveData则是完全是整个实体类或者数据类型变化后才通知.不会细节到某个字段
     */
    private val cacheLiveData: MutableLiveData<PagedList<Post>> = MutableLiveData()

    //首页轮播图数据
    var bannerData: MutableLiveData<ResultState<ArrayList<BannerModel>>> = MutableLiveData()

    //首页文章列表数据
    var homeDataState: MutableLiveData<ListDataStateWrapper<ArticleModel>> = MutableLiveData()

    /**
     * 获取首页的轮播图数据
     */
    fun getBannerData() {
        Log.d(TAG, "getBannerData() called")
        request({ WanAndroidApi.instance.getBanner() }, bannerData)
    }

    /**
     * 获取首页文章列表数据
     * @param isRefresh 是否是刷新，即第一页
     */
    fun getHomeData(isRefresh: Boolean) {
        Log.d(TAG, "getHomeData() called with: isRefresh = $isRefresh")
        if (isRefresh) {
            pageNo = 0
        }
        request({ WanAndroidApi.instance.getHomeData(pageNo) }, {
            Log.d(TAG, "getHomeData Response = $it")
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
            homeDataState.value = listDataUiState
        }, {
            Log.d(TAG, "getHomeData Exception = $it")
        })

    }

}