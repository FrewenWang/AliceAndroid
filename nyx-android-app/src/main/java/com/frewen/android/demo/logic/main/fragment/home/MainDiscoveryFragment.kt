package com.frewen.android.demo.logic.main.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.FragmentMainDiscoveryBinding
import com.frewen.android.demo.ktx.ext.parseState
import com.frewen.android.demo.mvvm.viewmodel.MainDiscoveryViewModel
import com.frewen.demo.library.ui.fragment.BaseDataBindingFragment

/**
 * @filename: MainHomeFragment
 * @author: Frewen.Wong
 * @time: 2/6/21 5:21 PM
 * @version: 1.0.0
 * @introduction:  Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
class MainDiscoveryFragment : BaseDataBindingFragment<MainDiscoveryViewModel, FragmentMainDiscoveryBinding>() {
    
    /**
     * 微信公众号的集合对象，通过arrayListOf()来实例化一个空集合
     */
    private var mWXArticleList: ArrayList<String> = arrayListOf()
    
    companion object {
        /**
         * 如果想要让Java代码也能调用这个伴生对象的方法
         * 需要加上@JvmStatic
         */
        @JvmStatic
        fun newInstance() = MainDiscoveryFragment()
    }
    
    
    override fun getLayoutId() = R.layout.fragment_main_discovery
    
    override fun initView(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "initView() called with: view = $view, savedInstanceState = $savedInstanceState")
    }
    
    override fun initData(savedInstanceState: Bundle?) {
        Log.d(TAG, "initData() called")
        viewModel.requestDiscoveryTitleData()
    }
    
    override fun initObserver(savedInstanceState: Bundle?) {
        Log.d(TAG, "initObserver() called ")
        viewModel.wxArticleTitleData.observe(viewLifecycleOwner, Observer() { data ->
            Log.d(TAG, "initObserver() called with: data = $data")
            parseState(data, {
        
            }, {
        
            })
        })
    }
    
}