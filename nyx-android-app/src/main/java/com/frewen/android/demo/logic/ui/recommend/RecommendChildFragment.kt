package com.frewen.android.demo.logic.ui.recommend

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.frewen.android.demo.R
import com.frewen.demo.library.ui.fragment.BaseDataBindingLazyViewFragment
import com.frewen.android.demo.databinding.LayoutFloatButtonRecylerViewBinding
import com.frewen.android.demo.ktx.ext.*
import com.frewen.android.demo.logic.adapter.ArticleAdapter
import com.frewen.android.demo.logic.model.ArticleModel
import com.frewen.android.demo.logic.model.ListDataStateWrapper
import com.frewen.demo.library.recyclerview.DefineLoadMoreView
import com.frewen.aura.toolkits.display.DisplayHelper
import com.frewen.aura.ui.recyclerview.SpaceItemDecoration
import com.frewen.demo.library.ktx.ext.init
import com.frewen.demo.library.ktx.ext.initFloatBtn
import com.frewen.demo.library.ktx.ext.initFooter
import com.kingja.loadsir.core.LoadService
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import kotlinx.android.synthetic.main.layout_common_recyclerview.*
import kotlinx.android.synthetic.main.layout_include_coordinator_recycler_view.*
import kotlinx.android.synthetic.main.layout_include_recyclerview_common.*

/**
 * @filename: RecommendChildFragment
 * @introduction: Recommend页面的子页面
 * @author: Frewen.Wong
 * @time: 2020/7/19 23:36
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class RecommendChildFragment :
    BaseDataBindingLazyViewFragment<RecommendModel, LayoutFloatButtonRecylerViewBinding>() {
    private lateinit var loadStateService: LoadService<Any>
    
    // 声明私有属性属性变量，可以通过一下两种方法，Kotlin可以自己进行类型推倒
    // private var isNew: Boolean = false
    private var isNew = false
    
    // 该项目对应的id
    private var cid = 0
    
    //kotlin可以请请求的ViewModel
    private val recommendModel: RecommendModel by viewModels()
    
    /**
     *
     */
    private val articleAdapter: ArticleAdapter by lazy { ArticleAdapter(arrayListOf()) }
    
    //recyclerview的底部加载view 因为在首页要动态改变他的颜色，所以加了他这个字段
    private lateinit var footView: DefineLoadMoreView
    
    /**
     * 伴生对象，用于创建RecommendChildFragment的子页面对象
     */
    companion object {
        fun newInstance(cid: Int, isNew: Boolean): RecommendChildFragment {
            val args = Bundle()
            args.putInt("cid", cid)
            args.putBoolean("isNew", isNew)
            val fragment = RecommendChildFragment()
            fragment.arguments = args
            return fragment
        }
    }
    
    override fun getLayoutId() = R.layout.layout_float_button_recyler_view
    
    override fun initView(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            isNew = it.getBoolean("isNew")
            cid = it.getInt("cid")
        }
        
        //状态页配置
        loadStateService = loadStateServiceInit(swipeRefreshLayout) {
            //点击重试时触发的操作
            loadStateService.showLoading()
            recommendModel.getProjectData(true, cid, isNew)
        }
        
        initRecyclerView()
    }
    
    /**
     * 初始化RecyclerView
     */
    private fun initRecyclerView() {
        swipeRecyclerView.init(LinearLayoutManager(context), articleAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0, DisplayHelper.dip2px(8f)))
            footView = it.initFooter(requireContext(), SwipeRecyclerView.LoadMoreListener {
                //触发加载更多时请求数据
                recommendModel.getProjectData(false, cid, isNew)
            })
            //初始化FloatingActionButton
            it.initFloatBtn(floatBtn)
        }
        
        //初始化 SwipeRefreshLayout
        swipeRefreshLayout.init {
            //触发刷新监听时请求数据
            recommendModel.getProjectData(true, cid, isNew)
        }
    }
    
    override fun initData(savedInstanceState: Bundle?) {
        articleAdapter.run {
            
            setOnItemClickListener { adapter, view, position ->
            
            }
            addChildClickViewIds(R.id.item_home_author, R.id.item_project_author)
            setOnItemChildClickListener { _, view, position ->
                when (view.id) {
                    R.id.item_home_author, R.id.item_project_author -> {
                    
                    }
                }
            }
        }
    }
    
    override fun initObserver(savedInstanceState: Bundle?) {
        recommendModel.projectDataState.observe(viewLifecycleOwner, Observer {
            //设值 新写了个拓展函数，搞死了这个恶心的重复代码
            loadListData(it, articleAdapter, loadStateService, swipeRecyclerView, swipeRefresh)
        })
    }
    
    override fun lazyLoadData() {
        loadStateService.showLoading()
        recommendModel.getProjectData(true, cid, isNew)
    }
    
    
    private fun loadListData(
        data: ListDataStateWrapper<ArticleModel>?,
        baseQuickAdapter: ArticleAdapter,
        loadService: LoadService<Any>,
        recyclerView: SwipeRecyclerView?,
        swipeRefreshLayout: SwipeRefreshLayout?
    ) {
        swipeRefreshLayout?.isRefreshing = false
        data?.let {
            recyclerView?.loadMoreFinish(it.isEmpty, data.hasMore)
            if (data.isSuccess) {
                //成功
                when {
                    //第一页并没有数据 显示空布局界面
                    data.isFirstEmpty -> {
                        loadService.showEmpty()
                    }
                    //是第一页
                    data.isRefresh -> {
                        baseQuickAdapter.setList(data.listData)
                        loadService.showSuccess()
                    }
                    //不是第一页
                    else -> {
                        baseQuickAdapter.addData(data.listData)
                        loadService.showSuccess()
                    }
                }
            } else {
                //失败
                if (data.isRefresh) {
                    // 如果是第一页，则显示错误界面，并提示错误信息
                    loadService.showError(data.errMessage)
                } else {
                    recyclerView?.loadMoreError(0, data.errMessage)
                }
            }
        }
    }
    
}