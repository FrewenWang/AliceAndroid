package com.frewen.android.demo.samples.tiktok.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import com.frewen.android.demo.R
import com.frewen.android.demo.samples.tiktok.fragments.MineViewModel
import com.frewen.aura.framework.fragment.BaseFragment

/**
 * @filename: HomeFragment
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/9/3 18:57
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class PersonalFragment : BaseFragment() {

    private lateinit var mineViewModel: PersonalViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mineViewModel = ViewModelProviders.of(this).get(PersonalViewModel::class.java)

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_tiktok_mine
    }


}