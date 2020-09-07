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
import com.frewen.aura.framework.fragment.BaseButterKnifeFragment
import com.frewen.aura.framework.fragment.BaseFragment

/**
 * @filename: HomeFragment
 * @introduction: 首页视频Feed流的设计
 * @author: Frewen.Wong
 * @time: 2020/9/3 18:57
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class MainFragment : BaseButterKnifeFragment() {

    private lateinit var mineViewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mineViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_tiktok_home_video_feed
    }


}