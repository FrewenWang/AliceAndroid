package com.frewen.demo.library.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.frewen.aura.toolkits.kotlin.ext.autoCleared
import com.frewen.demo.library.ui.holder.AuraDataBindingComponent
import javax.inject.Inject

/**
 * @filename: BaseDataBindingFragment
 * @introduction: 带有DataBinding功能的Fragment基类
 * @author: Frewen.Wong
 * @time: 2020/7/23 07:54
 * @version: 1.0.0
 *      完成基础功能设计
 * @version: 1.0.1
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
abstract class BaseDataBindingFragment<VDB : ViewDataBinding, VM : ViewModel> : Fragment() {
    /**
     * 根据Fragment动态清理和获取binding对象
     */
    var binding by autoCleared<VDB>()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                getLayoutId(),
                container,
                false,
                AuraDataBindingComponent())
        // 目前此方法已经过时，我们使用其他的实现方法
        //viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModelClass())
        viewModel = ViewModelProvider(this, viewModelFactory).get(getViewModelClass())

        return binding?.root
    }

    abstract fun getViewModelClass(): Class<VM>

    /**
     * 抽象方法，供子类传入LayoutID
     */
    abstract fun getLayoutId(): Int

}