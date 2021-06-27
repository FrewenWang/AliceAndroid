package com.frewen.android.demo.logic.main.fragment.discovery

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.FragmentMainDiscoveryPageBinding
import com.frewen.android.demo.logic.adapter.HomeArticleAdapter
import com.frewen.android.demo.logic.model.ArticleModel
import com.frewen.android.demo.logic.model.ListDataStateWrapper
import com.frewen.android.demo.logic.model.WXArticleContent
import com.frewen.android.demo.mvvm.viewmodel.MainDiscoveryViewModel
import com.frewen.aura.toolkits.display.DisplayHelper
import com.frewen.aura.ui.recyclerview.SpaceItemDecoration
import com.frewen.demo.library.ktx.ext.init
import com.frewen.demo.library.ui.fragment.BaseDataBindingFragment
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import kotlinx.android.synthetic.main.layout_include_recyclerview_common.*

class MainDiscoveryPageFragment :
    BaseDataBindingFragment<MainDiscoveryViewModel, FragmentMainDiscoveryPageBinding>() {
    
    /**
     *
     */
    private val articleAdapter: HomeArticleAdapter by lazy { HomeArticleAdapter(arrayListOf()) }
    
    /**
     * recyclerview的底部加载view 因为在首页要动态改变他的颜色，所以加了他这个字段
     */
    // private lateinit var footView: DefineLoadMoreView
    
    private var cid: Int = 0
    
    companion object {
        fun newInstance(cid: Int): MainDiscoveryPageFragment {
            val args = Bundle()
            args.putInt("cid", cid)
            val fragment = MainDiscoveryPageFragment()
            fragment.arguments = args
            return fragment
        }
    }
    
    override fun getLayoutId() = R.layout.fragment_main_discovery_page
    
    override fun initView(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "initView() called with: view = $view, savedInstanceState = $savedInstanceState")
        arguments?.let {
            cid = it.getInt("cid")
        }
        viewModel.getWXContentData(cid, true)
        //初始化recyclerView
        recyclerView.init(LinearLayoutManager(context), articleAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0, DisplayHelper.dip2px(8f)))
        }
    }
    
    override fun initData(savedInstanceState: Bundle?) {
        Log.d(TAG, "initData() called with: savedInstanceState = $savedInstanceState")
    }
    
    override fun initObserver(savedInstanceState: Bundle?) {
        //
        viewModel.wxArticleContentModel.observe(viewLifecycleOwner, Observer {
            //设值 新写了个拓展函数，搞死了这个恶心的重复代码
            // loadListData(it, articleAdapter, recyclerView, swipeRefreshLayout)
        })
    }
    
    /**
     * 加载的列表数据
     */
    private fun loadListData(
        data: ListDataStateWrapper<ArticleModel>,
        articleAdapter: HomeArticleAdapter,
        recyclerView: SwipeRecyclerView,
        swipeRefreshLayout: SwipeRefreshLayout
    ) {
        swipeRefreshLayout.isRefreshing = false
        recyclerView.loadMoreFinish(data.isEmpty, data.hasMore)
        if (data.isSuccess) {
            when {
                //第一页并没有数据 显示空布局界面
                data.isFirstEmpty -> {
                
                }
                //是第一页
                data.isRefresh -> {
                    articleAdapter.setList(data.listData)
                    
                }
            }
        }
    }
    
}