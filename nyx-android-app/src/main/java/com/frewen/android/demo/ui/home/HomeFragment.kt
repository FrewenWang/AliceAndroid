package com.frewen.android.demo.ui.home

import com.frewen.android.aura.annotations.FragmentDestination
import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.FragmentMainHomeBinding
import com.frewen.android.demo.samples.tiktok.fragments.HomeViewModel
import com.frewen.demo.library.di.injector.Injectable
import com.frewen.demo.library.ui.fragment.BaseDataBindingFragment


@FragmentDestination(pageUrl = "main/tabs/home", asStarter = true)
class HomeFragment() : BaseDataBindingFragment<FragmentMainHomeBinding, HomeViewModel>(), Injectable {
    override fun getLayoutId(): Int {
        return R.layout.fragment_main_home
    }

    override fun getViewModelClass(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }


}
