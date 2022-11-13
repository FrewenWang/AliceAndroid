package com.frewen.android.demo.business.ui.main.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.frewen.android.demo.R
import com.frewen.android.demo.business.adapter.ArticleAdapter
import com.frewen.android.demo.business.adapter.holder.HomeBannerViewHolder
import com.frewen.android.demo.databinding.FragmentMainHomeBinding
import com.frewen.android.demo.ktx.ext.loadStateServiceInit
import com.frewen.android.demo.ktx.ext.showLoading
import com.frewen.android.demo.business.adapter.HomeBannerAdapter
import com.frewen.android.demo.business.model.BannerModel
import com.frewen.android.demo.mvvm.viewmodel.MainHomeViewModel
import com.frewen.aura.toolkits.utils.ToastUtil
import com.frewen.demo.library.ktx.ext.init
import com.frewen.demo.library.network.ResultState
import com.frewen.demo.library.recyclerview.DefineLoadMoreView
import com.frewen.demo.library.ui.fragment.BaseDataBindingFragment
import com.frewen.network.response.exception.AuraNetException
import com.kingja.loadsir.core.LoadService
import com.zhpan.bannerview.BannerViewPager
import kotlinx.android.synthetic.main.layout_include_recyclerview_common.*
import kotlinx.android.synthetic.main.layout_include_top_toolbar_common.toolbar

/**
 * @filename: MainHomeFragment
 * @author: Frewen.Wong
 * @time: 2/6/21 5:21 PM
 * @version: 1.0.0
 * @introduction:  Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
class MainHomeFragment : BaseDataBindingFragment<MainHomeViewModel, FragmentMainHomeBinding>() {

    companion object {
        /**
         * 如果想要让Java代码也能调用这个伴生对象的方法
         * 需要加上@JvmStatic
         */
        @JvmStatic
        fun newInstance() = MainHomeFragment()
    }

    /**
     * 文章显示的适配器
     */
    private val articleAdapter: ArticleAdapter by lazy {
        ArticleAdapter(arrayListOf(), showTag = true)
    }

    //界面状态管理者
    private lateinit var loadsir: LoadService<Any>

    //recyclerview的底部加载view 因为在首页要动态改变他的颜色，所以加了他这个字段
    private lateinit var footView: DefineLoadMoreView

    override fun getLayoutId() = R.layout.fragment_main_home

    override fun initView(view: View, savedInstanceState: Bundle?) {
        //状态页配置
        loadsir = loadStateServiceInit(swipeRefreshLayout) {
            //点击重试时触发的操作
            loadsir.showLoading()
            viewModel.getBannerData()
            viewModel.getHomeData(true)
        }

        initToolBar()
        initRecyclerView()
    }

    /**
     * 初始化Banner数据
     */
    override fun initData(savedInstanceState: Bundle?) {
        viewModel.getBannerData()
        viewModel.getHomeData(true)
    }

    override fun initObserver(savedInstanceState: Bundle?) {
        viewModel.run {
            bannerData.observe(viewLifecycleOwner, Observer { resultState ->
                Log.e(TAG, "initObserver() called with: resultState = $resultState")
                parseState(resultState, { data ->
                    //请求轮播图数据成功，添加轮播图到HeadView ，如果等于0说明没有添加过头部，添加一个
                    if (swipeRecyclerView.headerCount == 0) {
                        val headView = LayoutInflater.from(context)
                            .inflate(R.layout.layout_include_bannner_view_common, null).apply {
                                findViewById<BannerViewPager<BannerModel, HomeBannerViewHolder>>(R.id.banner_view).apply {
                                    adapter = HomeBannerAdapter()
                                    setLifecycleRegistry(lifecycle)
                                    create(data)
                                }
                            }
                        swipeRecyclerView.addHeaderView(headView)
                        swipeRecyclerView.scrollToPosition(0)
                    }
                })
            })

            homeDataState.observe(viewLifecycleOwner, Observer {
                //设值 新写了个拓展函数，搞死了这个恶心的重复代码
                loadListData(it, articleAdapter, loadsir, swipeRecyclerView, swipeRefreshLayout)
            })
        }
    }

    /**
     * 我们解析返回的回调的结果的State的结果
     */
    private fun <T> parseState(
        resultState: ResultState<T>, onSuccess: (T) -> Unit,
        onError: ((AuraNetException) -> Unit)? = null, onLoading: (() -> Unit)? = null
    ) {
        when (resultState) {
            is ResultState.Loading -> {
                ToastUtil.showLong("正在请求数据中")
                onLoading?.invoke()
            }
            is ResultState.Success -> {
                onSuccess(resultState.data)
            }
            is ResultState.Error -> {
                onError?.run { this(resultState.error) }
            }
            else -> {
                ToastUtil.showLong("正在请求数据中")
            }
        }
    }


    private fun initRecyclerView() {
        swipeRecyclerView.init(LinearLayoutManager(context), articleAdapter)
    }

    private fun initToolBar() {
        //初始化 toolbar
        toolbar.run {
            title = getString(R.string.title_home)
            inflateMenu(R.menu.main_toolbar_menu)
            setOnMenuItemClickListener {
                ToastUtil.showShort("点击搜索")
                true
            }
        }
    }

}