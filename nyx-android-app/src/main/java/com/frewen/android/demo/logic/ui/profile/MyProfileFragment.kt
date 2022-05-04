package com.frewen.android.demo.logic.ui.profile

import android.os.Bundle
import android.view.View
import com.frewen.android.aura.annotations.FragmentDestination
import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.FragmentMainMyProfileBinding
import com.frewen.demo.library.di.injector.Injectable
import com.frewen.demo.library.ktx.ext.jumpByLogin
import com.frewen.demo.library.ktx.ext.nav
import com.frewen.demo.library.ui.fragment.BaseDataBindingFragment

/**
 * 个人介绍中心页面
 */
@FragmentDestination(pageUrl = "main/tabs/myProfile", asStarter = false)
class MyProfileFragment :
    BaseDataBindingFragment<MyProfileViewModel, FragmentMainMyProfileBinding>(), Injectable {

    override fun getLayoutId(): Int {
        return R.layout.fragment_main_my_profile
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun initData(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun initObserver(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }

    /**
     * 点击的代理类工具
     */
    inner class ProxyClick {
        /** 登录 */
        fun login() {
            nav().jumpByLogin {}
        }
    }

}
