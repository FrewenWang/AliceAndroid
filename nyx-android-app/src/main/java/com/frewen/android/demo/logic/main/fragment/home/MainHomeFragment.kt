package com.frewen.android.demo.logic.main.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.FragmentMainHomeBinding
import com.frewen.android.demo.logic.adapter.HomeArticleAdapter
import com.frewen.android.demo.mvvm.viewmodel.MainHomeViewModel
import com.frewen.aura.toolkits.utils.ToastUtils
import com.frewen.demo.library.ktx.extention.init
import com.frewen.demo.library.network.ResultState
import com.frewen.demo.library.ui.fragment.BaseDataBindingFragment
import com.frewen.network.response.exception.AuraNetException
import kotlinx.android.synthetic.main.layout_include_recyclerview_common.*
import kotlinx.android.synthetic.main.layout_include_top_toolbar_common.*

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
    
    override fun initData(savedInstanceState: Bundle?) {
        viewModel.getBannerData()
    }
    
    override fun initObserver(savedInstanceState: Bundle?) {
        viewModel.run {
            articleData.observe(viewLifecycleOwner, Observer { resultState ->
                Log.e(TAG, "initObserver() called with: resultState = $resultState")
            })
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