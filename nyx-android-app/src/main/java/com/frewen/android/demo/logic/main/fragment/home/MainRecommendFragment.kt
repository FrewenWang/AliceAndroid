package com.frewen.android.demo.logic.main.fragment.home

import android.os.Bundle
import android.view.View
import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.FragmentMainHomeBinding
import com.frewen.android.demo.databinding.FragmentMainRecommendBinding
import com.frewen.android.demo.logic.ui.recommend.content.EyeRecommendFragment
import com.frewen.android.demo.mvvm.viewmodel.MainHomeViewModel
import com.frewen.aura.framework.fragment.BaseFragment
import com.frewen.demo.library.ui.fragment.BaseDataBindingFragment

/**
 * @filename: MainHomeFragment
 * @author: Frewen.Wong
 * @time: 2/6/21 5:21 PM
 * @version: 1.0.0
 * @introduction:  Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
class MainRecommendFragment() : BaseDataBindingFragment<MainHomeViewModel, FragmentMainRecommendBinding>() {
    
    
    companion object {
        /**
         * 如果想要让Java代码也能调用这个伴生对象的方法
         * 需要加上@JvmStatic
         */
        @JvmStatic
        fun newInstance() = MainRecommendFragment()
    }
    
    override fun initView(view: View, savedInstanceState: Bundle?) {
    
    }
    
    override fun getLayoutId() = R.layout.fragment_main_recommend
    override fun initData(savedInstanceState: Bundle?) {
    
    }
    
    override fun initObserver(savedInstanceState: Bundle?) {
    }
    
}