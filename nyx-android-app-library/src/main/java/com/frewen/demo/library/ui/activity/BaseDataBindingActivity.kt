package com.frewen.demo.library.ui.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.frewen.aura.framework.ui.BaseActivity
import com.frewen.demo.library.mvvm.viewmodel.getViewModelClass
import com.frewen.demo.library.ui.holder.AuraDataBindingComponent
import com.frewen.demo.library.viewmodel.BaseViewModel

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
abstract class BaseDataBindingActivity<VDB : ViewDataBinding, VM : BaseViewModel> : BaseActivity() {

    lateinit var mViewModel: VM

    lateinit var mDataBinding: VDB


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 创建ViewDataBinding
        createViewDataBinding()
        // 实例化ViewModel
        mViewModel = createViewModel()
    }

    private fun createViewModel(): VM {
        return ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory(application)
        ).get(getViewModelClass(this, 1))
    }


    private fun createViewDataBinding() {
        // 通过DataBindingUtil来实例化mDataBinding对象
        mDataBinding = DataBindingUtil.setContentView(this, getContentViewId(), AuraDataBindingComponent())
        mDataBinding.lifecycleOwner = this
    }

    /**
     *
     */
    abstract fun getContentViewId(): Int


}