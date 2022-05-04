package com.frewen.demo.library.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.frewen.aura.framework.fragment.BaseFragment
import com.frewen.aura.toolkits.ktx.ext.autoCleared
import com.frewen.demo.library.mvvm.viewmodel.getViewModelClass
import com.frewen.demo.library.network.ResultState
import com.frewen.demo.library.ui.holder.AuraDataBindingComponent
import com.frewen.network.response.exception.AuraNetException

/**
 * @filename: BaseDataBindingFragment
 * @introduction: 带有DataBinding功能的Fragment基类
 * @author: Frewen.Wong
 * @time: 2020/7/23 07:54
 * @version: 1.0.0
 *      完成基础功能设计的DataBinding的逻辑
 *      主要是是调用DataBindingUtil.inflate的方法来进行布局控件绑定
 * @version:
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
abstract class BaseDataBindingFragment<VM : ViewModel, VDB : ViewDataBinding> : BaseFragment() {
    /**
     * 根据Fragment动态清理和获取binding对象
     */
    var binding by autoCleared<VDB>()

    lateinit var viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            getLayoutId(),
            container,
            false,
            AuraDataBindingComponent()
        )
        binding?.lifecycleOwner = this
        // 目前此方法已经过时，我们使用下面实现方法
        //viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass())
        viewModel = ViewModelProvider(this).get(getViewModelClass(this, 0))

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view, savedInstanceState)

        initObserver(savedInstanceState)

        initData(savedInstanceState)
    }


    /**
     * 抽象方法，供子类传入LayoutID
     */
    abstract fun getLayoutId(): Int

    /**
     * 定义View初始化完成之后的逻辑
     */
    abstract fun initView(view: View, savedInstanceState: Bundle?)

    /**
     * 初始化数据
     */
    abstract fun initData(savedInstanceState: Bundle?)

    /**
     * 初始化ViewModel的监听器的逻辑
     */
    abstract fun initObserver(savedInstanceState: Bundle?)


    /**
     *
     */
    fun showLoading(loadingMessage: String) {

    }


}