package com.frewen.android.demo.business.ui.main.fragment.discovery

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.LayoutFloatButtonRecylerViewBinding
import com.frewen.android.demo.ktx.ext.*
import com.frewen.android.demo.business.adapter.SystemAdapter
import com.frewen.android.demo.logic.model.SystemModel
import com.frewen.aura.toolkits.display.DisplayHelper
import com.frewen.aura.ui.recyclerview.SpaceItemDecoration
import com.frewen.demo.library.ktx.ext.init
import com.frewen.demo.library.ktx.ext.initFloatBtn
import com.frewen.demo.library.ktx.ext.nav
import com.frewen.demo.library.ktx.ext.navigateAction
import com.frewen.demo.library.ui.fragment.BaseDataBindingFragment
import com.kingja.loadsir.core.LoadService
import kotlinx.android.synthetic.main.layout_float_button_recyler_view.*
import kotlinx.android.synthetic.main.layout_include_recyclerview_common.*
import kotlinx.android.synthetic.main.layout_include_top_indicator_view_pager2.*
import kotlinx.android.synthetic.main.layout_include_top_toolbar_common.*

/**
 * 发现页面里面的广场的页面
 */
class DiscoverySystemFragment :
    BaseDataBindingFragment<MainDiscoveryViewModel, LayoutFloatButtonRecylerViewBinding>() {

    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    private val systemAdapter: SystemAdapter by lazy { SystemAdapter(arrayListOf()) }


    override fun getLayoutId() = R.layout.layout_float_button_recyler_view

    override fun initView(view: View, savedInstanceState: Bundle?) {
        //状态页配置
        loadsir = loadStateServiceInit(swipeRefreshLayout) {
            //点击重试时触发的操作
            loadsir.showLoading()
            viewModel.getSystemData()
        }
        //初始化recyclerView
        swipeRecyclerView.init(LinearLayoutManager(context), systemAdapter).let {
            it.addItemDecoration(SpaceItemDecoration(0, DisplayHelper.dip2px(8f)))
            it.initFloatBtn(floatBtn)
        }
        //初始化 SwipeRefreshLayout
        swipeRefreshLayout.init {
            //触发刷新监听时请求数据
            viewModel.getSystemData()
        }
        systemAdapter.run {
            setOnItemClickListener { _, view, position ->
                if (systemAdapter.data[position].children.isNotEmpty()) {
                    // nav().navigateAction(R.id.action_mainfragment_to_systemArrFragment,
                    //     Bundle().apply {
                    //         putParcelable("data", systemAdapter.data[position])
                    //     }
                    // )
                }
            }
            setChildClick { item: SystemModel, view, position ->
                // nav().navigateAction(R.id.action_mainfragment_to_systemArrFragment,
                //     Bundle().apply {
                //         putParcelable("data", item)
                //         putInt("index", position)
                //     }
                // )
            }
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        //设置界面 加载中
        loadsir.showLoading()
        viewModel.getSystemData()
    }

    override fun initObserver(savedInstanceState: Bundle?) {
        viewModel.systemDataState.observe(viewLifecycleOwner, Observer {
            swipeRefreshLayout.isRefreshing = false
            if (it.isSuccess) {
                loadsir.showSuccess()
                systemAdapter.setList(it.listData)
            } else {
                loadsir.showError(it.errMessage)
            }
        })
    }


}