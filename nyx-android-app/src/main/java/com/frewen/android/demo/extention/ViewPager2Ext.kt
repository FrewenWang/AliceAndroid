package com.frewen.android.demo.extention

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

/**
 * @filename: ViewPager2Ext
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/9/27 17:38
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
fun ViewPager2.initOnFragment(
        fragment: Fragment,
        fragments: ArrayList<Fragment>,
        isUserInputEnabled: Boolean = true
): ViewPager2 {
    //是否可滑动
    this.isUserInputEnabled = isUserInputEnabled
    adapter = object : FragmentStateAdapter(fragment) {
        override fun getItemCount() = fragments.size
        override fun createFragment(position: Int) = fragments[position]
    }
    return this
}