package com.frewen.android.demo.samples.tiktok.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.frewen.android.demo.samples.tiktok.home.MainFragment
import com.frewen.android.demo.samples.tiktok.home.PersonalFragment
import com.frewen.demo.library.ui.fragment.BaseViewPager2Fragment

/**
 * @filename: HomeFragment
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/9/3 18:57
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class HomeFragment : BaseViewPager2Fragment() {
    override val createFragments: Array<Fragment>
        get() = arrayOf(MainFragment(), PersonalFragment())


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        
    }

}