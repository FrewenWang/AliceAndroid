package com.frewen.android.demo.business.ui.main.fragment.profile

import androidx.lifecycle.MutableLiveData
import com.frewen.android.demo.business.model.IntegralModel
import com.frewen.android.demo.network.AliceNetworkApi
import com.frewen.android.demo.utils.AppThemeUtil
import com.frewen.demo.library.ktx.ext.request
import com.frewen.demo.library.mvvm.databind.IntObservableField
import com.frewen.demo.library.mvvm.databind.StringObservableField
import com.frewen.demo.library.mvvm.vm.BaseViewModel
import com.frewen.demo.library.network.ResultState

/**
 * @filename: NewsViewModel
 * @introduction: MyProfileFragment的ViewModel
 * @author: Frewen.Wong
 * @time: 2020/4/14 19:20
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class MainMyProfileViewModel : BaseViewModel() {

    var name = StringObservableField("请先登录!!")

    var integral = IntObservableField(0)

    var rankInfo = StringObservableField("id：--排名：-")

    var integralData = MutableLiveData<ResultState<IntegralModel>>()

    var imageUrl = StringObservableField(AppThemeUtil.randomImage())

    fun getIntegralData() {
        request({ AliceNetworkApi.instance.getIntegralData() }, integralData)
    }
}