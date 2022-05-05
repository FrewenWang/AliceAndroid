package com.frewen.android.demo.logic.samples.navigation.annotation.home

import android.os.Bundle
import android.view.View
import com.frewen.android.aura.annotations.FragmentDestination
import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.FragmentMainHomeBinding
import com.frewen.android.demo.logic.adapter.ArticleAdapter
import com.frewen.demo.library.di.injector.Injectable
import com.frewen.demo.library.ui.fragment.BaseDataBindingLazyViewFragment


@FragmentDestination(pageUrl = "main/tabs/home", asStarter = true)
class HomeFragment : BaseDataBindingLazyViewFragment<HomeViewModel, FragmentMainHomeBinding>(),
    Injectable {

    private val articleAdapter: ArticleAdapter by lazy {
        ArticleAdapter(
            arrayListOf(),
            true
        )
    }

    override fun getLayoutId() = R.layout.fragment_main_home

    override fun initView(view: View, savedInstanceState: Bundle?) {


    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initObserver(savedInstanceState: Bundle?) {

    }


    override fun lazyLoadData() {

    }
}
