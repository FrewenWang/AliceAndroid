package com.frewen.android.demo.logic.samples.navigation.annotation.recommend

import android.os.Bundle
import android.view.View
import com.frewen.android.aura.annotations.FragmentDestination
import com.frewen.demo.library.ui.fragment.BaseDataBindingLazyViewFragment
import com.frewen.demo.library.di.injector.Injectable
import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.FragmentMainRecommendBinding
import com.frewen.android.demo.logic.ui.main.fragment.recommend.MainRecommendViewModel

/**
 * @filename: RecommendFragment
 * @introduction: 主页的第二个sheet页面的Fragment布局
 * @author: Frewen.Wong
 * @time: 2020/7/19 23:36
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
@FragmentDestination(pageUrl = "main/tabs/recommend")
class RecommendFragment :
    BaseDataBindingLazyViewFragment<MainRecommendViewModel, FragmentMainRecommendBinding>(),
    Injectable {

    override fun getLayoutId(): Int {
        return R.layout.fragment_main_discovery
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {

    }

    private fun initLoadStateService() {


    }

    override fun lazyLoadData() {

    }

    override fun initData(savedInstanceState: Bundle?) {}

    override fun initObserver(savedInstanceState: Bundle?) {

    }

}