package com.frewen.android.demo.ui.discovery.content

import android.os.Bundle
import android.view.View
import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.FragmentMainMyProfileBinding
import com.frewen.android.demo.ui.discovery.DiscoveryViewModel
import com.frewen.demo.library.ui.fragment.BaseDataBindingFragment

/**
 * @filename: DailyQuestionFragment
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/9/27 19:02
 * @version: 1.0.0
 *      每日一答的Fragment的实现逻辑。
 *      java.lang.IllegalArgumentException: No injector factory bound for
 *      Class<com.frewen.android.demo.ui.discovery.content.DailyQuestionFragment>
 *
 *     需要添加： 见 {@link }
 *     @ContributesAndroidInjector
 *       abstract fun contributeDailyQuestionFragment(): DailyQuestionFragment
 *
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class DailyQuestionFragment : BaseDataBindingFragment<FragmentMainMyProfileBinding, DiscoveryViewModel>() {

    override fun getViewModelClass(): Class<DiscoveryViewModel> = DiscoveryViewModel::class.java

    override fun getLayoutId(): Int {
        return R.layout.fragment_daily_question
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(savedInstanceState)
    }

    /**
     * 初始化View的相关逻辑
     */
    private fun initView(savedInstanceState: Bundle?) {

    }

}