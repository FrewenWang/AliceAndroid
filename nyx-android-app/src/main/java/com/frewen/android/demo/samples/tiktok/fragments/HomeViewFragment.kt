package com.frewen.android.demo.samples.tiktok.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.frewen.android.demo.samples.tiktok.home.MainFragment
import com.frewen.android.demo.samples.tiktok.home.PersonalViewFragment
import com.frewen.demo.library.ui.fragment.BaseViewPager2ViewFragment

/**
 * @filename: HomeFragment
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/9/3 18:57
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class HomeViewFragment : BaseViewPager2ViewFragment() {

    companion object {
        private const val TAG = "HomeFragment"
    }

    override val createFragments: Array<Fragment>
        get() = arrayOf(MainFragment(), PersonalViewFragment())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(Companion.TAG, "onViewCreated() called with: view = $view, savedInstanceState = $savedInstanceState")

    }

}