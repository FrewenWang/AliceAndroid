package com.frewen.android.demo.logic.ui.main.fragment.discovery

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.FragmentMainDiscoveryBinding
import com.frewen.android.demo.ktx.ext.bindViewPager2
import com.frewen.aura.toolkits.utils.ToastUtils
import com.frewen.demo.library.ktx.ext.initOnFragment
import com.frewen.demo.library.ui.fragment.BaseDataBindingFragment
import kotlinx.android.synthetic.main.layout_include_top_indicator_view_pager2.*
import kotlinx.android.synthetic.main.layout_include_top_toolbar_common.toolbar

/**
 * 首页布局页面的发现页面的容器布局
 */
class MainDiscoveryFragment :
    BaseDataBindingFragment<MainDiscoveryViewModel, FragmentMainDiscoveryBinding>() {


    companion object {
        /**
         * 如果想要让Java代码也能调用这个伴生对象的方法
         * 需要加上@JvmStatic
         */
        @JvmStatic
        fun newInstance() = MainDiscoveryFragment()
    }

    /** 首页的发现页面的顶部标题栏 */
    var titleData = arrayListOf("广场", "每日一问", "体系", "导航")

    private var fragments: ArrayList<Fragment> = arrayListOf()

    init {
        fragments.add(DiscoveryPlazaFragment())
        fragments.add(DiscoveryQuestionFragment())
        fragments.add(DiscoverySystemFragment())
        fragments.add(DiscoveryNavigationFragment())
    }

    /** 顶部带有导航栏的ViewPager页面 */
    override fun getLayoutId() = R.layout.fragment_main_discovery

    override fun initView(view: View, savedInstanceState: Bundle?) {
        initToolBar()

    }

    private fun initToolBar() {
        toolbar.run {
            inflateMenu(R.menu.menu_add)
            setOnMenuItemClickListener {
                ToastUtils.showShort("点击了添加按钮")
                true
            }
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        //初始化viewpager2
        view_pager2.initOnFragment(this, fragments).offscreenPageLimit = fragments.size
        magic_indicator.bindViewPager2(view_pager2, mStringList = titleData) {
            if (it != 0) {
                toolbar.menu.clear()
            } else {
                toolbar.menu.hasVisibleItems().let { flag ->
                    if (!flag) {
                        toolbar.inflateMenu(R.menu.menu_add)
                    }
                }
            }
        }
    }

    override fun initObserver(savedInstanceState: Bundle?) {

    }


}