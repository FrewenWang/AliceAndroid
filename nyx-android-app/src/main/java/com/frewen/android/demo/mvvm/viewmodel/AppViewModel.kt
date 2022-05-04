package com.frewen.android.demo.mvvm.viewmodel

import com.frewen.android.demo.NyxApp
import com.frewen.android.demo.logic.model.UserInfo
import com.frewen.android.demo.utils.AppThemeUtil
import com.frewen.demo.library.mvvm.livedata.EventLiveData
import com.frewen.demo.library.mvvm.vm.BaseViewModel
import com.frewen.android.demo.utils.MmkvUtil
import com.kunminx.architecture.ui.callback.UnPeekLiveData

/**
 * 应用程序级别的AppViewModel
 */
class AppViewModel : BaseViewModel() {

    // App的账户信息
    var userInfo: UnPeekLiveData<UserInfo> =
        UnPeekLiveData.Builder<UserInfo>().setAllowNullValue(true).create()

    // App主题颜色 中大型项目不推荐以这种方式改变主题颜色，
    // 比较繁琐耦合，且容易有遗漏某些控件没有设置主题色
    var appColor = EventLiveData<Int>()

    //App 列表动画
    var appAnimation = EventLiveData<Int>()

    init {
        //默认值保存的账户信息，没有登陆过则为null
        userInfo.value = MmkvUtil.getUser()
        //默认值颜色
        appColor.value = AppThemeUtil.getThemeColor(NyxApp.getInstance())
        //初始化列表动画
        appAnimation.value = AppThemeUtil.getListAnimMode()
    }

}