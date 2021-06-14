package com.frewen.android.demo.logic.main.fragment.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.FragmentMainDiscoveryBinding
import com.frewen.android.demo.ktx.ext.bindViewPager2
import com.frewen.android.demo.ktx.ext.parseState
import com.frewen.android.demo.logic.main.fragment.discovery.MainDiscoveryPageFragment
import com.frewen.android.demo.mvvm.viewmodel.MainDiscoveryViewModel
import com.frewen.demo.library.ktx.ext.initOnFragment
import com.frewen.demo.library.ui.fragment.BaseDataBindingFragment
import kotlinx.android.synthetic.main.layout_include_top_indicator_view_pager2.*

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
    
    /**
     *
     */
    private var fragments: ArrayList<Fragment> = arrayListOf()
    
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
        
        //初始化viewpager2
        view_pager2.initOnFragment(this, fragments)
        //初始化 magic_indicator
        magic_indicator.bindViewPager2(view_pager2, mWXArticleList)
    }
    
    override fun initData(savedInstanceState: Bundle?) {
        Log.d(TAG, "initData() called")
        viewModel.requestDiscoveryTitleData()
    }
    
    /**
     * 我们在Fragment中创建Observer监听器。
     * 在这个方法里面。我们调用MutableLiveData数据的observe()方法
     * 这个方法我们传入viewLifecycleOwner也就是当前生命周期组件。也就是是Fragment
     */
    override fun initObserver(savedInstanceState: Bundle?) {
        Log.d(TAG, "initObserver() called ")
        viewModel.wxArticleTitleData.observe(viewLifecycleOwner, Observer() { data ->
            Log.d(TAG, "initObserver() called with: data = $data")
            parseState(data, { it ->
                mWXArticleList.addAll(it.map { it.name })
                it.forEach { classify ->
                    fragments.add(MainDiscoveryPageFragment.newInstance(classify.id))
                }
                magic_indicator.navigator.notifyDataSetChanged()
                // 更新ViewPager的数据
                view_pager2.adapter?.notifyDataSetChanged()
                view_pager2.offscreenPageLimit = fragments.size
            })
        })
    }
    
}