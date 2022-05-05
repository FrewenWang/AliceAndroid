package com.frewen.android.demo.logic.ui.main.fragment.discovery

import android.os.Bundle
import android.view.View
import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.LayoutFloatButtonRecylerViewBinding
import com.frewen.demo.library.ui.fragment.BaseDataBindingFragment

/**
 * 发现页面里面的广场的页面
 */
class DiscoveryNavigationFragment :
    BaseDataBindingFragment<MainDiscoveryViewModel, LayoutFloatButtonRecylerViewBinding>() {

    override fun getLayoutId() = R.layout.layout_float_button_recyler_view

    override fun initView(view: View, savedInstanceState: Bundle?) {

    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun initObserver(savedInstanceState: Bundle?) {
    }


}