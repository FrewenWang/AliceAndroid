package com.frewen.demo.library.ui.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.frewen.aura.framework.mvvm.vm.BaseViewModel
import com.frewen.aura.framework.ui.BaseActivity
import com.frewen.demo.library.mvvm.viewmodel.getViewModelClass
import com.frewen.demo.library.ui.holder.AuraDataBindingComponent

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
abstract class BaseDataBindingActivity<VM : BaseViewModel, VDB : ViewDataBinding> : BaseActivity() {
    
    lateinit var mViewModel: VM
    
    lateinit var mDataBinding: VDB
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 创建ViewDataBinding
        createViewDataBinding()
        // 实例化ViewModel
        mViewModel = createViewModel()
    }
    
    /**
     * BaseDataBindingActivity中的createViewModel调用的是
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this).get(getViewModelClass(this, 0))
    }
    
    
    private fun createViewDataBinding() {
        // 通过DataBindingUtil来实例化mDataBinding对象
        // TODO 我们需要好好学习一下AuraDataBindingComponent
        mDataBinding = DataBindingUtil.setContentView(this, getContentViewId(), AuraDataBindingComponent())
        mDataBinding.lifecycleOwner = this
    }
    
    /**
     *
     */
    abstract fun getContentViewId(): Int
    
    
}