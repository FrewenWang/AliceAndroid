package com.frewen.demo.library.widgets

import com.flyco.tablayout.listener.CustomTabEntity

/**
 * @filename: CustomTabEntityImpl
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/7/19 22:11
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class CustomTabEntityImpl(private var title: String, private var selectedIcon: Int = 0, private var unSelectedIcon: Int = 0) : CustomTabEntity {
    override fun getTabUnselectedIcon(): Int {
        return unSelectedIcon
    }

    override fun getTabSelectedIcon(): Int {
        return selectedIcon
    }

    override fun getTabTitle(): String {
        return title
    }

}