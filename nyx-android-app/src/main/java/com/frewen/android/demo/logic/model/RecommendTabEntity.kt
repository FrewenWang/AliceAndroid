package com.frewen.android.demo.logic.model

import com.flyco.tablayout.listener.CustomTabEntity

/**
 * @filename: RecommendTabEntity
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/10/24 16:01
 * @version 1.0.0
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class RecommendTabEntity(private var title: String, private var selectedIcon: Int = 0, private var unSelectedIcon: Int = 0) : CustomTabEntity {

    override fun getTabTitle(): String {
        return title
    }

    override fun getTabSelectedIcon(): Int {
        return selectedIcon
    }

    override fun getTabUnselectedIcon(): Int {
        return unSelectedIcon
    }
}