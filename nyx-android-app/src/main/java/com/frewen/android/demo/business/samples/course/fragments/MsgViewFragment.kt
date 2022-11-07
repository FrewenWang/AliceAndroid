package com.frewen.android.demo.business.samples.course.fragments

import com.frewen.android.demo.R
import com.frewen.aura.framework.fragment.BaseViewFragment

/**
 * @filename: HomeFragment
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/9/3 18:57
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class MsgViewFragment : BaseViewFragment() {

    private lateinit var msgViewModel: MsgViewModel

    override fun getLayoutId(): Int {
        return R.layout.fragment_tiktok_msg
    }


}