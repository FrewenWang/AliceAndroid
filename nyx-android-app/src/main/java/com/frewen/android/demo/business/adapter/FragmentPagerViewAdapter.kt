package com.frewen.android.demo.business.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * @filename: FragmentPagerViewAdapter
 * @introduction: Fragment的ViewPager的适配器
 * @author: Frewen.Wong
 * @time: 2020/4/3 17:35
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
open class FragmentPagerViewAdapter(
        private val titleList: Array<String>,
        private val fragmentList: List<Fragment>,
        fragmentManager: FragmentManager,
        behavior: Int) : FragmentPagerAdapter(fragmentManager, behavior) {


    /**
     * 获取ViewPager中的每个页面的Fragment
     */
    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    /**
     * 获取ViewPager页面的个数
     */
    override fun getCount(): Int {
        return fragmentList.size
    }

    /**
     * 获取页面的标题
     */
    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

}