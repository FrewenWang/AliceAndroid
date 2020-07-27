package com.frewen.demo.library.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.frewen.aura.framework.fragment.BaseFragment
import com.frewen.aura.toolkits.kotlin.ext.autoCleared
import com.frewen.demo.library.ui.holder.AuraDataBindingComponent

/**
 * @filename: BaseDataBindingFragment
 * @introduction: 带有DataBinding功能的Fragment基类
 * @author: Frewen.Wong
 * @time: 2020/7/23 07:54
 * @version: 1.0.0
 *      完成基础功能设计
 * @version: 1.0.1
 *
 *
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
abstract class BaseDataBindingFragment<VDB : ViewDataBinding> : BaseFragment() {

    /**
     * 根据Fragment动态清理和获取binding对象
     */
    var binding by autoCleared<VDB>()

    /**
     * BaseDataBindingFragment来实现BaseFragment的createView。
     * 我们在这里面进行View的创建
     */
    override fun createView(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean): View? {
        binding = DataBindingUtil.inflate(
                inflater,
                getLayoutId(),
                container,
                false,
                AuraDataBindingComponent())
        return binding?.root
    }

    /**
     * 抽象方法，供子类传入LayoutID
     */
    abstract fun getLayoutId(): Int

}