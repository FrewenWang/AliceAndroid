package com.frewen.demo.library.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.frewen.aura.framework.fragment.BaseViewFragment
import com.frewen.demo.library.R
import com.frewen.demo.library.ktx.extention.initOnFragment
import kotlinx.android.synthetic.main.fragment_view_pager2.*
import kotlinx.android.synthetic.main.include_layout_main_page_title_bar.*

/**
 * @filename: BaseViewPager2Fragment
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/7/19 21:29
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
abstract class BaseViewPager2Fragment : BaseViewFragment() {

    companion object {
        private const val TAG = "BaseViewPager2ViewFragment"
    }

    private var pageChangeCallback: PageChangeCallBack? = null

    /**
     * ViewFragment中缓存的页面的个数
     */
    private var offscreenPageLimit = 1

    /**
     *
     */
    abstract val tabTitles: ArrayList<CustomTabEntity>

    /**
     *
     */
    abstract val contentFragments: Array<Fragment>

    override fun getLayoutId(): Int {
        return R.layout.fragment_view_pager2
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        initViewPager()
    }

    private fun initViewPager() {
        //初始化viewpager2
        viewPager2
                .initOnFragment(this, contentFragments)
                .offscreenPageLimit = offscreenPageLimit
        centerTabLayout?.setTabData(tabTitles)
        centerTabLayout?.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                viewPager2?.currentItem = position
            }

            override fun onTabReselect(position: Int) {

            }
        })
        pageChangeCallback = PageChangeCallBack()
        viewPager2?.registerOnPageChangeCallback(pageChangeCallback!!)
    }

    /**
     * 类可以嵌套在其他类中：
     * 标记为 inner 的嵌套类能够访问其外部类的成员。内部类会带有一个对外部类的对象的引用：
     * 匿名内部类
     * 使用对象表达式创建匿名内部类实例：
     * window.addMouseListener(object : MouseAdapter() {
     *      override fun mouseClicked(e: MouseEvent) { …… }
     *      override fun mouseEntered(e: MouseEvent) { …… }
     *       })
     */
    inner class PageChangeCallBack : OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
        }
    }

}