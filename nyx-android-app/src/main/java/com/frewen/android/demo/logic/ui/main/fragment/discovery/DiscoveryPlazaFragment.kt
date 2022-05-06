package com.frewen.android.demo.logic.ui.main.fragment.discovery

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.LayoutFloatButtonRecylerViewBinding
import com.frewen.android.demo.ktx.ext.loadStateServiceInit
import com.frewen.android.demo.ktx.ext.showEmpty
import com.frewen.android.demo.ktx.ext.showError
import com.frewen.android.demo.ktx.ext.showLoading
import com.frewen.android.demo.logic.adapter.ArticleAdapter
import com.frewen.android.demo.logic.model.ListDataStateWrapper
import com.frewen.aura.toolkits.display.DisplayHelper
import com.frewen.aura.ui.recyclerview.SpaceItemDecoration
import com.frewen.demo.library.ktx.ext.init
import com.frewen.demo.library.ktx.ext.initFloatBtn
import com.frewen.demo.library.ktx.ext.initFooter
import com.frewen.demo.library.recyclerview.DefineLoadMoreView
import com.frewen.demo.library.ui.fragment.BaseDataBindingFragment
import com.kingja.loadsir.core.LoadService
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import kotlinx.android.synthetic.main.layout_float_button_recyler_view.*
import kotlinx.android.synthetic.main.layout_include_recyclerview_common.*

/**
 * 发现页面里面的广场的页面
 */
class DiscoveryPlazaFragment :
    BaseDataBindingFragment<MainDiscoveryViewModel, LayoutFloatButtonRecylerViewBinding>() {

    /**
     * 界面的状态管理类
     */
    private lateinit var loadService: LoadService<Any>

    /**
     * 文章显示的适配器
     */
    private val articleAdapter: ArticleAdapter by lazy {
        ArticleAdapter(arrayListOf(), showTag = true)
    }

    /**
     * recyclerview的底部加载view
     * 因为要在首页动态改变他的颜色，所以加了他这个字段
     */
    private lateinit var footView: DefineLoadMoreView


    override fun getLayoutId() = R.layout.layout_float_button_recyler_view

    override fun initView(view: View, savedInstanceState: Bundle?) {
        initLoadService()
        initRecyclerView()
    }

    private fun initLoadService() {
        //状态页配置
        loadService = loadStateServiceInit(swipeRefreshLayout) {
            //点击重试时触发的操作
            loadService.showLoading()
            viewModel.getPlazaData(true)
        }
    }

    private fun initRecyclerView() {
        //初始化recyclerView
        swipeRecyclerView.init(LinearLayoutManager(context), articleAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0, DisplayHelper.dip2px(8f)))
            footView = it.initFooter(requireContext()) {
                // 开始获取广场分页数据
                viewModel.getPlazaData(false)
            }
            //初始化FloatingActionButton
            it.initFloatBtn(floatBtn)
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        //设置界面 加载中
        loadService.showLoading()
        viewModel.getPlazaData(true)
    }

    override fun initObserver(savedInstanceState: Bundle?) {
        viewModel.plazaDataState.observe(viewLifecycleOwner, Observer {
            //设值 新写了个拓展函数，搞死了这个恶心的重复代码
            loadListData(it, articleAdapter, loadService, swipeRecyclerView, swipeRefreshLayout)
        })
    }

    /**
     * 加载列表数据
     */
    fun <T> loadListData(
        data: ListDataStateWrapper<T>,
        baseQuickAdapter: BaseQuickAdapter<T, *>,
        loadService: LoadService<*>,
        recyclerView: SwipeRecyclerView,
        swipeRefreshLayout: SwipeRefreshLayout
    ) {
        swipeRefreshLayout.isRefreshing = false
        recyclerView.loadMoreFinish(data.isEmpty, data.hasMore)
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
                //如果是第一页，则显示错误界面，并提示错误信息
                loadService.showError(data.errMessage)
            } else {
                recyclerView.loadMoreError(0, data.errMessage)
            }
        }
    }


}