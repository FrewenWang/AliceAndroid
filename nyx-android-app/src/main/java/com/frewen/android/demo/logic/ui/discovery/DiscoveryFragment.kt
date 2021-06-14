package com.frewen.android.demo.logic.ui.discovery

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.frewen.android.aura.annotations.FragmentDestination
import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.FragmentMainDiscoveryBinding
import com.frewen.android.demo.ktx.ext.bindViewPager2
import com.frewen.demo.library.ktx.ext.initOnFragment
import com.frewen.android.demo.logic.ui.profile.MyProfileViewModel
import com.frewen.demo.library.di.injector.Injectable
import com.frewen.demo.library.ui.fragment.BaseDataBindingFragment
import kotlinx.android.synthetic.main.layout_include_top_indicator_view_pager2.*
import kotlinx.android.synthetic.main.layout_include_top_toolbar_common.*

/**
 * 首页的发现页面的Fragment
 */
@FragmentDestination(pageUrl = "main/tabs/discovery", asStarter = false)
class DiscoveryFragment : BaseDataBindingFragment<MyProfileViewModel, FragmentMainDiscoveryBinding>(), Injectable {
    
    var titleData = arrayListOf("广场", "每日一问", "体系", "导航")
    
    /**
     * 用来存放我们这个Tab下面的Fragment列表的容器List
     */
    private var fragments: Array<Fragment> = arrayOf()
    
    override fun getLayoutId(): Int {
        return R.layout.fragment_main_discovery
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initObserver(savedInstanceState)
    }
    
    /**
     * 初始化数据
     */
    private fun initData() {
        //初始化viewpager2
        view_pager2.initOnFragment(this, fragments).offscreenPageLimit = 1
        //初始化 magic_indicator
        magic_indicator.bindViewPager2(view_pager2, mStringList = titleData) {
            if (it != 0) {
                toolbar.menu.clear()
            } else {
            
            }
        }
    }
    
    
    /**
     * 创建监听者
     */
    override fun initObserver(savedInstanceState: Bundle?) {
    
    }
    
    override fun initView(view: View, savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }
    
    override fun initData(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }
    
    
}

