package com.frewen.demo.library.ktx.ext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

/**
 * @filename: ViewPager2Ext
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/9/27 17:38
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
@Deprecated("方法已经过时，请使用下面的initOnFragment的方法")
fun ViewPager2.initOnFragment(
        fragment: Fragment,
        fragments: Array<Fragment>,
        offscreenPageLimit: Int = 1,
        isUserInputEnabled: Boolean = false
): ViewPager2 {
    this.offscreenPageLimit = offscreenPageLimit
    //是否可滑动
    this.isUserInputEnabled = isUserInputEnabled
    adapter = object : FragmentStateAdapter(fragment) {
        override fun getItemCount() = fragments.size
        override fun createFragment(position: Int) = fragments[position]
    }
    return this
}

fun ViewPager2.initOnFragment(
        fragment: Fragment,
        fragments: ArrayList<Fragment>,
        offscreenPageLimit: Int = 1,
        isUserInputEnabled: Boolean = false
): ViewPager2 {
    this.offscreenPageLimit = offscreenPageLimit
    //是否可滑动
    this.isUserInputEnabled = isUserInputEnabled
    adapter = object : FragmentStateAdapter(fragment) {
        override fun getItemCount() = fragments.size
        override fun createFragment(position: Int) = fragments[position]
    }
    return this
}


fun ViewPager2.initOnActivity(
        activity: FragmentActivity,
        fragments: ArrayList<Fragment>,
        isUserInputEnabled: Boolean = true
): ViewPager2 {
    //是否可滑动
    this.isUserInputEnabled = isUserInputEnabled
    adapter = object : FragmentStateAdapter(activity) {
        override fun getItemCount() = fragments.size
        override fun createFragment(position: Int) = fragments[position]
    }
    return this
}