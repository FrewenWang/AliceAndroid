package com.frewen.android.demo.business.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.FragmentMainPageBinding
import com.frewen.android.demo.business.ui.main.fragment.discovery.MainDiscoveryFragment
import com.frewen.android.demo.business.ui.main.fragment.home.MainHomeFragment
import com.frewen.android.demo.business.ui.main.fragment.profile.MainMyProfileFragment
import com.frewen.android.demo.business.ui.main.fragment.recommend.MainRecommendFragment
import com.frewen.android.demo.mvvm.viewmodel.MainPageViewModel
import com.frewen.demo.library.ktx.ext.init
import com.frewen.demo.library.ktx.ext.initOnFragment
import com.frewen.demo.library.ktx.ext.interceptLongClick
import com.frewen.demo.library.ui.fragment.BaseDataBindingFragment
import kotlinx.android.synthetic.main.fragment_main_page.*

/**
 * 主页面的Fragment页面
 */
class MainFragment :
    BaseDataBindingFragment<MainPageViewModel, FragmentMainPageBinding>() {

    override fun getLayoutId() = R.layout.fragment_main_page

    val fragments: Array<Fragment> = arrayOf(
        MainHomeFragment.newInstance(),
        MainRecommendFragment.newInstance(),
        MainDiscoveryFragment.newInstance(),
        MainMyProfileFragment.newInstance()
    )

    override fun initView(view: View, savedInstanceState: Bundle?) {
        //初始化viewpager2，减少模板代码，使用扩展函数类初始化ViewPager2
        mainViewpager.initOnFragment(this, fragments)

        mainBottom.init {
            when (it) {
                R.id.navigation_home -> mainViewpager.setCurrentItem(0, false)
                R.id.navigation_recommend -> mainViewpager.setCurrentItem(1, false)
                R.id.navigation_discovery -> mainViewpager.setCurrentItem(2, false)
                R.id.navigation_profile -> mainViewpager.setCurrentItem(3, false)
            }
        }
        mainBottom.interceptLongClick(
            R.id.navigation_home,
            R.id.navigation_recommend,
            R.id.navigation_discovery,
            R.id.navigation_profile
        )
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun initObserver(savedInstanceState: Bundle?) {
    }
}