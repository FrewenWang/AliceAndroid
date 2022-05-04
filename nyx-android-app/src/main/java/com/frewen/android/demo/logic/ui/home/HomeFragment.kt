package com.frewen.android.demo.logic.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.frewen.android.aura.annotations.FragmentDestination
import com.frewen.android.demo.R
import com.frewen.android.demo.adapter.holder.HomeBannerViewHolder
import com.frewen.android.demo.databinding.FragmentMainHomeBinding
import com.frewen.android.demo.ktx.ext.*
import com.frewen.android.demo.logic.adapter.DiscoveryArticleAdapter
import com.frewen.android.demo.logic.adapter.HomeBannerAdapter
import com.frewen.android.demo.logic.model.ArticleModel
import com.frewen.android.demo.logic.model.BannerModel
import com.frewen.android.demo.logic.model.ListDataStateWrapper
import com.frewen.aura.logger.core.AuraLogger
import com.frewen.aura.toolkits.display.DisplayHelper
import com.frewen.aura.ui.recyclerview.SpaceItemDecoration
import com.frewen.demo.library.di.injector.Injectable
import com.frewen.demo.library.ktx.ext.*
import com.kingja.loadsir.core.LoadService
import kotlinx.android.synthetic.main.layout_include_recyclerview_common.*
import com.frewen.demo.library.ui.fragment.BaseDataBindingLazyViewFragment
import com.yanzhenjie.recyclerview.SwipeRecyclerView
import com.zhpan.bannerview.BannerViewPager
import kotlinx.android.synthetic.main.layout_include_top_toolbar_common.*


@FragmentDestination(pageUrl = "main/tabs/home", asStarter = true)
class HomeFragment : BaseDataBindingLazyViewFragment<HomeViewModel, FragmentMainHomeBinding>(),
    Injectable {
    /**
     * 顾名思义，这是指一个延迟初始化的变量。
     * 在kotlin里面，如果在类型声明之后没有使用符号?，则表示该变量不会为null。
     * 但是这个时候会要求我们初始化一个值。
     * 有些时候，我们在声明变量的时候，并不能初始化这个变量。
     * 一个声明成lateinit的变量，如果在整个代码里面都没有进行任何的初始化， 那么能否编译通过？
     * 如果你加上了lateinit关键字，kotlin的编译器不会做这种检查。 如果你将变量声明为lateinit，它就认为你肯定会初始化，
     * 至于你是怎么初始化它的，它就不管了
     * 1. lateinit 延迟加载
     * 2. lateinit 只能修饰, 非kotlin基本类型
     * 3. 如果你的代码真的显示初始化了lateinit变量，而又抛出了UninitializedPropertyAccessException异常， 因为你恰好将变量初始化为null了
     * 因为Kotlin会使用null来对每一个用lateinit修饰的属性做初始化，而基础类型是没有null类型，所以无法使用lateinit。
     */

    private lateinit var loadStateService: LoadService<Any>

    private val articleAdapter: DiscoveryArticleAdapter by lazy {
        DiscoveryArticleAdapter(
            arrayListOf(),
            true
        )
    }

    override fun getLayoutId() = R.layout.fragment_main_home

    override fun initView(view: View, savedInstanceState: Bundle?) {
        AuraLogger.d("initView")
        //状态页配置
        loadStateService = loadStateServiceInit(swipeRefreshLayout) {
            //点击重试时触发的操作
            loadStateService.showLoading()
            viewModel.getBannerData()
            viewModel.getHomeData(true)
        }

        // 初始化顶部的ActionBar
        // run函数和apply函数很像，只不过run函数是使用最后一行的返回，apply返回当前自己的对象。
        // run函数实际上可以说是let和with两个函数的结合体，run函数只接收一个lambda函数为参数，
        // 以闭包形式返回，返回值为最后一行的值或者指定的return的表达式。
        toolbar.run {
            init("首页")
            inflateMenu(R.menu.main_toolbar_menu)
        }

        // 默认当前这个对象作为闭包的it参数，返回值是函数里面最后一行，或者指定return;
        // let扩展函数实际上是一个作用域函数，当你需要去定义一个变量在一个特定的作用域范围内，
        // let函数的是一个不错的选择；let函数另一个作用就是可以避免写一些判断null的操作。
        // 场景一: 最常用的场景就是使用let函数处理需要针对一个可null的对象统一做判空处理。
        // 场景二: 然后就是需要去明确一个变量所处特定的作用域范围内可以使用
        swipeRecyclerView.init(LinearLayoutManager(context), articleAdapter).let {
            //因为首页要添加轮播图，所以我设置了firstNeedTop字段为false,即第一条数据不需要设置间距
            it.addItemDecoration(SpaceItemDecoration(0, DisplayHelper.dip2px(8f), false))
            it.addExtFooterView(SwipeRecyclerView.LoadMoreListener {
                viewModel.getHomeData(false)
            })
        }

        //初始化 SwipeRefreshLayout
        swipeRefreshLayout.init {
            //触发刷新监听时请求数据
            viewModel.getHomeData(true)
        }

    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun initObserver(savedInstanceState: Bundle?) {
        viewModel.run {
            //监听首页文章列表请求的数据变化
            homeDataState.observe(viewLifecycleOwner, Observer {
                //设值 新写了个拓展函数，搞死了这个恶心的重复代码
                loadListData(
                    it,
                    articleAdapter,
                    loadStateService,
                    swipeRecyclerView,
                    swipeRefreshLayout
                )
            })

            //监听轮播图请求的数据变化
            bannerData.observe(viewLifecycleOwner, { resultState ->
                Log.d(TAG, "initObserver() called with: resultState = $resultState")
                parseState(resultState, { data ->
                    Log.d(TAG, "initObserver() called with: data = $data")
                    //请求轮播图数据成功，添加轮播图到headView ，如果等于0说明没有添加过头部，添加一个
                    if (swipeRecyclerView.headerCount == 0) {
                        val headView = LayoutInflater.from(context)
                            .inflate(R.layout.layout_include_bannner_view_common, null).apply {
                                findViewById<BannerViewPager<BannerModel, HomeBannerViewHolder>>(
                                    R.id.banner_view
                                ).apply {
                                    adapter = HomeBannerAdapter()
                                    setLifecycleRegistry(lifecycle)
                                    setOnPageClickListener {
                                        // TODO 跳转二级页面
                                    }
                                    create(data)
                                }
                            }
                        swipeRecyclerView.addHeaderView(headView)
                        swipeRecyclerView.scrollToPosition(0)
                        loadStateService.showSuccess()
                    }
                })
            })
        }

    }


    private fun loadListData(
        data: ListDataStateWrapper<ArticleModel>?,
        baseQuickAdapter: DiscoveryArticleAdapter,
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

    override fun lazyLoadData() {
        //设置界面 加载中
        loadStateService.showLoading()
        //请求轮播图数据
        viewModel.getBannerData()
        //请求文章列表数据
        viewModel.getHomeData(true)
    }
}
