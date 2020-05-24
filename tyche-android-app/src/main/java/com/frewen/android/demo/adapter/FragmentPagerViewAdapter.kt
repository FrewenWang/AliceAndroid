package com.frewen.android.demo.adapter

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
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
open class FragmentPagerViewAdapter(private val fragmentList: List<Fragment>, fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val mChannels: ObservableList<Object> = ObservableArrayList()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

}