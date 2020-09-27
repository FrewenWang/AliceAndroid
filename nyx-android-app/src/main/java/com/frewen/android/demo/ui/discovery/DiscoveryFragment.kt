package com.frewen.android.demo.ui.discovery

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.frewen.android.aura.annotations.FragmentDestination
import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.FragmentMainDiscoveryBinding
import com.frewen.android.demo.extention.bindViewPager2
import com.frewen.android.demo.extention.initOnFragment
import com.frewen.android.demo.ui.discovery.content.DailyQuestionFragment
import com.frewen.android.demo.ui.profile.MyProfileViewModel
import com.frewen.demo.library.di.injector.Injectable
import com.frewen.demo.library.ui.fragment.BaseDataBindingFragment
import kotlinx.android.synthetic.main.fragment_main_discovery.*

/**
 *   所有使用实现自Injectable的类。需要有注册inject的
 *   Process: com.frewen.android.demo.debug, PID: 2570
 *   java.lang.IllegalArgumentException: No injector was found for com.frewen.android.demo.ui.discovery.DiscoveryFragment
 */
@FragmentDestination(pageUrl = "main/tabs/discovery", asStarter = false)
class DiscoveryFragment : BaseDataBindingFragment<FragmentMainDiscoveryBinding, MyProfileViewModel>(), Injectable {

    var titleData = arrayListOf("广场", "每日一问", "体系", "导航")

    /**
     * 用来存放我们这个Tab下面的Fragment列表的容器List
     */
    private var fragments: ArrayList<Fragment> = arrayListOf()

    init {
        fragments.add(DailyQuestionFragment())
        fragments.add(DailyQuestionFragment())
        fragments.add(DailyQuestionFragment())
        fragments.add(DailyQuestionFragment())
    }


    override fun getViewModelClass(): Class<MyProfileViewModel> {
        return MyProfileViewModel::class.java
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_main_discovery
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initObserver()
    }

    /**
     * 初始化数据
     */
    private fun initData() {
        //初始化viewpager2
        viewPager2.initOnFragment(this, fragments).offscreenPageLimit = fragments.size
        //初始化 magic_indicator
        magicIndicator.bindViewPager2(viewPager2, mStringList = titleData) {
            if (it != 0) {
                include_viewpager_toolbar.menu.clear()
            } else {

            }
        }
    }


    /**
     * 创建监听者
     */
    private fun initObserver() {

    }


}

