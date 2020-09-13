package com.frewen.android.demo.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.frewen.android.demo.di.annotation.ViewModelKey
import com.frewen.android.demo.samples.tiktok.fragments.HomeViewModel
import com.frewen.android.demo.ui.discovery.DiscoveryViewModel
import com.frewen.android.demo.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @filename: MyAppModule
 * @introduction: 我们没有使用AuraFrameWork里面提供的
 * @see AppMudule
 * 而是我们自己来实现的MyAppModule 是否能考虑扩展？？
 * @author: Frewen.Wong
 * @time: 2020/4/7 12:54
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
@Suppress("unused")
@Module
abstract class ViewModelModule {

    /**
     * ViewModelFactory
     *
     * @Binds 和 @Provider的作用相差不大，区别在于@Provider需要写明具体的实现，
     * 而@Binds只是告诉Dagger2谁是谁实现的
     *
     * 错误: @Binds methods' parameter type must be assignable to the return type
     * public abstract androidx.lifecycle.ViewModelProvider.Factory bindViewModelFactory(@org.jetbrains.annotations.NotNull()
     * 解决方法：
     * 注入的对象，需要传入的参数factory: ViewModelFactory
     * 所以需要进行factory的注入
     * ViewModelProvider.Factory
     */
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    /**
     *
     */
    @Binds
    @IntoMap
    @ViewModelKey(DiscoveryViewModel::class)
    abstract fun bindDiscoveryViewModel(discoveryViewModel: DiscoveryViewModel): ViewModel

}
