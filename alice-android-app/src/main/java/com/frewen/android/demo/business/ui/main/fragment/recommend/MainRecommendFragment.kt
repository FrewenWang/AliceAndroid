package com.frewen.android.demo.business.ui.main.fragment.recommend

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.FragmentMainRecommendBinding
import com.frewen.android.demo.ktx.ext.*
import com.frewen.android.demo.business.loadstate.ErrorCallback
import com.frewen.demo.library.ktx.ext.initOnFragment
import com.frewen.demo.library.ui.fragment.BaseDataBindingFragment
import com.kingja.loadsir.core.LoadService
import kotlinx.android.synthetic.main.layout_include_top_indicator_view_pager2.*

/**
 * @filename: MainHomeFragment
 * @author: Frewen.Wong
 * @time: 2/6/21 5:21 PM
 * @version: 1.0.0
 * @introduction:  Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
class MainRecommendFragment :
    BaseDataBindingFragment<MainRecommendViewModel, FragmentMainRecommendBinding>() {

    companion object {
        /**
         * 如果想要让Java代码也能调用这个伴生对象的方法
         * 需要加上@JvmStatic
         */
        @JvmStatic
        fun newInstance() = MainRecommendFragment()
    }

    /**
     * 界面状态管理者
     */
    private lateinit var loadsir: LoadService<Any>

    /** fragment集合 */
    var fragments: ArrayList<Fragment> = arrayListOf()

    /** 标题集合 */
    var mDataList: ArrayList<String> = arrayListOf()

    override fun getLayoutId() = R.layout.fragment_main_recommend

    override fun initView(view: View, savedInstanceState: Bundle?) {
        //状态页配置
        loadsir = loadStateServiceInit(view_pager2) {
            //点击重试时触发的操作
            loadsir.showLoading()
            viewModel.getProjectTitleData()
        }
        //初始化viewpager2
        view_pager2.initOnFragment(this, fragments)
        //初始化 magic_indicator
        magic_indicator.bindViewPager2(view_pager2, mDataList)
    }


    override fun initData(savedInstanceState: Bundle?) {
        //设置界面 加载中
        loadsir.showLoading()
        //请求标题数据
        viewModel.getProjectTitleData()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun initObserver(savedInstanceState: Bundle?) {
        viewModel.titleData.observe(viewLifecycleOwner, Observer { data ->
            parseState(data, { it ->
                mDataList.clear()
                fragments.clear()
                mDataList.add("最新项目")
                mDataList.addAll(it.map { it.name })
                fragments.add(RecommendChildFragment.newInstance(0, true))
                it.forEach { classify ->
                    fragments.add(RecommendChildFragment.newInstance(classify.id, false))
                }
                magic_indicator.navigator.notifyDataSetChanged()
                view_pager2.adapter?.notifyDataSetChanged()
                view_pager2.offscreenPageLimit = fragments.size
                loadsir.showSuccess()
            }, {
                //请求项目标题失败
                loadsir.showCallback(ErrorCallback::class.java)
                loadsir.setErrorText(it.errorMsg)
            })
        })
    }

}