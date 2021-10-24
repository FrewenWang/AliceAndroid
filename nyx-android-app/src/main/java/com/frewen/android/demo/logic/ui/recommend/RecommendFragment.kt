package com.frewen.android.demo.logic.ui.recommend

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.frewen.android.aura.annotations.FragmentDestination
import com.frewen.demo.library.ui.fragment.BaseDataBindingLazyViewFragment
import com.frewen.demo.library.di.injector.Injectable
import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.FragmentMainRecommendBinding
import com.frewen.android.demo.ktx.ext.*
import com.frewen.android.demo.ui.loadstate.ErrorCallback
import com.frewen.demo.library.ktx.ext.initOnFragment
import com.kingja.loadsir.core.LoadService
import kotlinx.android.synthetic.main.layout_include_top_indicator_view_pager2.*

/**
 * @filename: RecommendFragment
 * @introduction: 主页的第二个sheet页面的Fragment布局
 * @author: Frewen.Wong
 * @time: 2020/7/19 23:36
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
@FragmentDestination(pageUrl = "main/tabs/recommend")
class RecommendFragment :
    BaseDataBindingLazyViewFragment<RecommendModel, FragmentMainRecommendBinding>(), Injectable {
    
    private lateinit var loadStateService: LoadService<Any>
    
    /**
     *
     */
    var mDataList: ArrayList<String> = arrayListOf()
    
    /**
     * 声明Fragment集合
     */
    var fragments: ArrayList<Fragment> = arrayListOf()
    
    override fun getLayoutId(): Int {
        return R.layout.fragment_main_discovery
    }
    
    override fun initView(view: View, savedInstanceState: Bundle?) {
        // 初始化加载状态显示服务
        initLoadStateService()
    }
    
    private fun initLoadStateService() {
        //状态页配置
        // TODO 如果转成Java，怎么让Java调用扩展函数
        loadStateService = loadStateServiceInit(view_pager2) {
            //点击重试时触发的操作
            loadStateService.showLoading()
            viewModel.getRecommendData()
        }
        
        //初始化viewpager2
        view_pager2.initOnFragment(this, fragments)
        
        //初始化 magic_indicator
        magic_indicator.bindViewPager2(view_pager2, mDataList)
        
    }
    
    override fun lazyLoadData() {
        //设置界面 加载中
        loadStateService.showLoading()
        viewModel.getRecommendData()
    }
    
    override fun initData(savedInstanceState: Bundle?) {}
    
    override fun initObserver(savedInstanceState: Bundle?) {
        viewModel.recommendTabData.observe(viewLifecycleOwner, Observer { data ->
            parseState(data, {
                mDataList.clear()
                fragments.clear()
                // 添加第一个Fragment布局
                mDataList.add("最新项目")
                mDataList.addAll(it.map { it.name })
                fragments.add(RecommendChildFragment.newInstance(0, true))
                // 然后挨个插入其余的Fragment布局
                it.forEach { classify ->
                    fragments.add(RecommendChildFragment.newInstance(classify.id, false))
                }
                magic_indicator.navigator.notifyDataSetChanged()
                view_pager2.offscreenPageLimit = fragments.size
                view_pager2.offscreenPageLimit = fragments.size
                loadStateService.showSuccess()
                
            }, {
                //请求项目标题失败
                loadStateService.showCallback(ErrorCallback::class.java)
                loadStateService.setErrorText(it.errorMsg)
            })
        })
    }
    
}