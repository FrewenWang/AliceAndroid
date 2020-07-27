package com.frewen.demo.library.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.flyco.tablayout.CommonTabLayout
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.frewen.aura.framework.fragment.BaseFragment
import com.frewen.demo.library.R

/**
 * @filename: BaseViewPager2Fragment
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/7/19 21:29
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
abstract class BaseViewPager2Fragment : BaseFragment() {

    protected var viewPager: ViewPager2? = null

    protected var tabLayout: CommonTabLayout? = null

    /**
     * ViewFragment中缓存的页面的个数
     */
    protected var offscreenPageLimit = 1

    abstract val createTitles: ArrayList<CustomTabEntity>

    abstract val createFragments: Array<Fragment>

    protected val adapter: ViewPager2Adapter by lazy { ViewPager2Adapter(activity!!).apply { addFragments(createFragments) } }


    override fun createView(inflater: LayoutInflater?, container: ViewGroup?, attachToRoot: Boolean): View? {

        return inflater?.inflate(R.layout.fragment_view_pager2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        initViewPager()
    }

    private fun initViewPager() {
//        viewPager = rootView?.findViewById(R.id.viewPager)
//        tabLayout = rootView?.findViewById(R.id.tabLayout)
        viewPager?.offscreenPageLimit = offscreenPageLimit


        viewPager?.adapter = adapter
        tabLayout?.setTabData(createTitles)
        tabLayout?.setOnTabSelectListener(object : OnTabSelectListener {

            override fun onTabSelect(position: Int) {
                viewPager?.currentItem = position
            }

            override fun onTabReselect(position: Int) {

            }
        })

    }

    /**
     * BaseViewPager2Fragment的内部类
     * ViewPager2Adapter   inner 修饰符进行修饰
     */
    inner class ViewPager2Adapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
        /**
         * 可变的Fragment数组List
         */
        private val fragments = mutableListOf<Fragment>()

        fun addFragments(fragment: Array<Fragment>) {
            fragments.addAll(fragment)
        }

        override fun getItemCount() = fragments.size

        override fun createFragment(position: Int) = fragments[position]
    }


}