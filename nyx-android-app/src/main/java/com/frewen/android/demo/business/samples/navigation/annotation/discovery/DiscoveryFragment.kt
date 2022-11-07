package com.frewen.android.demo.business.samples.navigation.annotation.discovery

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.frewen.android.aura.annotations.FragmentDestination
import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.FragmentMainDiscoveryBinding
import com.frewen.demo.library.di.injector.Injectable
import com.frewen.demo.library.ui.fragment.BaseDataBindingFragment

/**
 * 首页的发现页面的Fragment
 */
@FragmentDestination(pageUrl = "main/tabs/discovery", asStarter = false)
class DiscoveryFragment :
    BaseDataBindingFragment<DiscoveryViewModel, FragmentMainDiscoveryBinding>(), Injectable {
    /**
     * 用来存放我们这个Tab下面的Fragment列表的容器List
     */
    private var fragments: Array<Fragment> = arrayOf()

    override fun getLayoutId(): Int {
        return R.layout.fragment_main_discovery
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    /**
     * 初始化数据
     */
    private fun initData() {

    }


    /**
     * 创建监听者
     */
    override fun initObserver(savedInstanceState: Bundle?) {

    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
    }

    override fun initData(savedInstanceState: Bundle?) {
    }


}

