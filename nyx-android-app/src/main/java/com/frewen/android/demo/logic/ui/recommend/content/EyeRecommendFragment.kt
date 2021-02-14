package com.frewen.android.demo.logic.ui.recommend.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.frewen.android.demo.R
import com.frewen.android.demo.logic.adapter.EyeCommendAdapter
import com.frewen.aura.framework.fragment.BaseViewFragment
import com.frewen.aura.toolkits.common.ResourcesUtils
import com.frewen.aura.toolkits.display.DisplayHelper
import com.frewen.demo.library.di.injector.Injectable
import com.scwang.smart.refresh.layout.constant.RefreshState
import kotlinx.android.synthetic.main.layout_container_refresh_recyclerview.*
import javax.inject.Inject

/**
 * @filename: EyeRecommendFragment
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/10/24 10:31
 * @version 1.0.0
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class EyeRecommendFragment : BaseViewFragment(), Injectable {
    
    /**
     * 是否已经加载过数据
     */
    private var mHasLoadedData = false
    
    /**
     * 列表左or右间距
     */
    val bothSideSpace = ResourcesUtils.getDimension(R.dimen.listSpaceSize)
    
    /**
     * 列表中间内间距，左or右。
     */
    val middleSpace = DisplayHelper.dip2px(3f)
    
    
    private lateinit var adapter: EyeCommendAdapter
    
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(EyeRecommendViewModel::class.java)
    }
    
    companion object {
        fun newInstance() = EyeRecommendFragment()
    }
    
    override fun getLayoutId() = R.layout.layout_container_refresh_recyclerview
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = context?.let {
            EyeCommendAdapter(it, viewModel.dataList)
            
        }!!
        /// 瀑布流
        val mainLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        
        mainLayoutManager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        recyclerView.layoutManager = mainLayoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(EyeCommendAdapter.ItemDecoration(this))
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = null
        refreshLayout.setOnRefreshListener { viewModel.onRefresh() }
        refreshLayout.setOnLoadMoreListener { viewModel.onLoadMore() }
        observeData()
    }
    
    override fun onResume() {
        super.onResume()
        //当Fragment在屏幕上可见并且没有加载过数据时调用
        if (!mHasLoadedData) {
            loadDataOnce()
            mHasLoadedData = true
        }
    }
    
    private fun loadDataOnce() {
        viewModel.onRefresh()
    }
    
    private fun observeData() {
    
    
    }
    
    
}