package com.frewen.android.demo.business.samples.course.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.frewen.android.demo.R
import com.frewen.aura.framework.fragment.BaseButterKnifeFragment
import java.util.*

/**
 * @filename: HomeFragment
 * @introduction: 首页视频Feed流的设计
 * @author: Frewen.Wong
 * @time: 2020/9/3 18:57
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class MainFragment : BaseButterKnifeFragment() {

    private val fragments = ArrayList<Fragment>()
    private lateinit var mineViewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mineViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        initView()
    }

    /**
     * 初始化View
     */
    private fun initView() {
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_tiktok_home_video_feed
    }






}