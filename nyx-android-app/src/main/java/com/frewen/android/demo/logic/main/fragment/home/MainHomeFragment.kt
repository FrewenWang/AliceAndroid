package com.frewen.android.demo.logic.main.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.frewen.android.demo.R
import com.frewen.android.demo.adapter.holder.HomeBannerViewHolder
import com.frewen.android.demo.databinding.FragmentMainHomeBinding
import com.frewen.android.demo.logic.adapter.HomeArticleAdapter
import com.frewen.android.demo.logic.adapter.HomeBannerAdapter
import com.frewen.android.demo.logic.model.ArticleBean
import com.frewen.android.demo.logic.model.BannerModel
import com.frewen.android.demo.mvvm.viewmodel.MainHomeViewModel
import com.frewen.aura.toolkits.utils.ToastUtils
import com.frewen.demo.library.ktx.extention.init
import com.frewen.demo.library.network.ResultState
import com.frewen.demo.library.ui.fragment.BaseDataBindingFragment
import com.frewen.network.response.exception.AuraNetException
import com.zhpan.bannerview.BannerViewPager
import kotlinx.android.synthetic.main.layout_include_recyclerview_common.*
import kotlinx.android.synthetic.main.layout_include_top_toolbar_common.*
import java.util.ArrayList

/**
 * @filename: MainHomeFragment
 * @author: Frewen.Wong
 * @time: 2/6/21 5:21 PM
 * @version: 1.0.0
 * @introduction:  Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
class MainHomeFragment : BaseDataBindingFragment<MainHomeViewModel, FragmentMainHomeBinding>() {

    private val homeArticleAdapter: HomeArticleAdapter by lazy { HomeArticleAdapter(arrayListOf(), true) }

    companion object {
        /**
         * 如果想要让Java代码也能调用这个伴生对象的方法
         * 需要加上@JvmStatic
         */
        @JvmStatic
        fun newInstance() = MainHomeFragment()
    }

    override fun getLayoutId() = R.layout.fragment_main_home

    override fun initView(view: View, savedInstanceState: Bundle?) {

        initToolBar()

        initRecyclerView()
    }

    /**
     * 初始化Banner数据
     */
    override fun initData(savedInstanceState: Bundle?) {
        viewModel.getBannerData()
    }

    override fun initObserver(savedInstanceState: Bundle?) {
        viewModel.run {
            bannerData.observe(viewLifecycleOwner, Observer { resultState ->
                 Log.e(TAG, "initObserver() called with: resultState = $resultState")
                parseState(resultState, { data ->
                    //请求轮播图数据成功，添加轮播图到HeadView ，如果等于0说明没有添加过头部，添加一个
                    if (recyclerView.headerCount == 0) {
                        val headView = LayoutInflater.from(context).inflate(R.layout.layout_include_bannner_view_common, null).apply {
                            findViewById<BannerViewPager<BannerModel, HomeBannerViewHolder>>(R.id.banner_view).apply {
                                adapter = HomeBannerAdapter()
                                setLifecycleRegistry(lifecycle)
                                create(data)
                            }
                        }
                        recyclerView.addHeaderView(headView)
                        recyclerView.scrollToPosition(0)
                    }
                })

            })
        }
    }

    /**
     * 我们解析返回的回调的结果的State的结果
     */
    private fun <T> parseState(resultState: ResultState<T>,
                               onSuccess: (T) -> Unit,
                               onError: ((AuraNetException) -> Unit)? = null,
                               onLoading: (() -> Unit)? = null) {
        when (resultState) {
            is ResultState.Loading -> {
                ToastUtils.showLong("正在请求数据中")
                onLoading?.invoke()
            }
            is ResultState.Success -> {
                onSuccess(resultState.data)
            }
            is ResultState.Error -> {
                onError?.run { this(resultState.error) }
            }
            else -> {
                ToastUtils.showLong("正在请求数据中")
            }
        }
    }


    private fun initRecyclerView() {
        recyclerView.init(LinearLayoutManager(context), homeArticleAdapter)
    }

    private fun initToolBar() {
        //初始化 toolbar
        toolbar.run {
            title = getString(R.string.title_home)
            inflateMenu(R.menu.main_toolbar_menu)
            setOnMenuItemClickListener {
                ToastUtils.showShort("点击搜索")
                true
            }
        }
    }

}