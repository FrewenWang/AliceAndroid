package com.frewen.android.demo.logic.main.fragment

import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.FragmentMainPageBinding
import com.frewen.android.demo.mvvm.viewmodel.MainPageViewModel
import com.frewen.demo.library.ui.fragment.BaseDataBindingFragment

/**
 * 主页面的Fragment页面
 */
class MainPageFragment : BaseDataBindingFragment<MainPageViewModel, FragmentMainPageBinding>() {
    
    override fun getLayoutId() = R.layout.fragment_main_page
    
    
}