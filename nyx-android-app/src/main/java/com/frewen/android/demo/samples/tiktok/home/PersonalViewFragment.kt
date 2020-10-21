package com.frewen.android.demo.samples.tiktok.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
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
class PersonalViewFragment : BaseViewFragment() {

    private lateinit var mineViewModel: PersonalViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mineViewModel = ViewModelProviders.of(this).get(PersonalViewModel::class.java)

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_tiktok_mine
    }


}