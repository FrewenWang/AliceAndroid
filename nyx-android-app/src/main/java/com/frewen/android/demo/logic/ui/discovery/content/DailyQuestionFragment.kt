package com.frewen.android.demo.logic.ui.discovery.content

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.FragmentMainMyProfileBinding
import com.frewen.android.demo.logic.ui.discovery.adapter.DailyQuestionAdapter
import com.frewen.aura.toolkits.display.DisplayHelper
import com.frewen.demo.library.ktx.ext.init
import com.frewen.demo.library.recyclerview.decoration.DividerItemDecoration
import com.frewen.demo.library.ui.fragment.BaseDataBindingFragment
import kotlinx.android.synthetic.main.layout_include_recyclerview_common.*

/**
 * @filename: DailyQuestionFragment
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/9/27 19:02
 * @version: 1.0.0
 *      每日一答的Fragment的实现逻辑。
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class DailyQuestionFragment : BaseDataBindingFragment<DailyQuestionViewModel, FragmentMainMyProfileBinding>() {
    
    private val dailyQuestionAdapter: DailyQuestionAdapter by lazy {
        DailyQuestionAdapter(
                arrayListOf(), showTag = true
        )
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        initView(savedInstanceState)
    }
    
    override fun getLayoutId(): Int {
        return R.layout.layout_float_button_recyler_view
    }
    
    
    /**
     * 初始化View的相关逻辑
     */
    private fun initView(savedInstanceState: Bundle?) {
        // 调用recyclerView的初始化扩展函数
        recyclerView.init(LinearLayoutManager(context), dailyQuestionAdapter)?.let {
            it.addItemDecoration(DividerItemDecoration(0, DisplayHelper.dip2px(8f)))
        }
        
        viewModel.requestDailyQuestionData();
    }
    
    override fun initView(view: View, savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }
    
    override fun initData(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }
    
    override fun initObserver(savedInstanceState: Bundle?) {
        TODO("Not yet implemented")
    }
    
}