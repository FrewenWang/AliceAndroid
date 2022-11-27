package com.frewen.android.demo.di.modules

import com.frewen.android.demo.business.ui.main.MainActivity
import com.frewen.aura.framework.di.scope.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @filename: ActivityBindModule
 * @introduction:
 *
 * 项目中使用到了第三方的类库，第三方类库又不能修改，所以根本不可能把Inject注解加入这些类中，这时我们的Inject就失效了。
 *
 * @author: Frewen.Wong
 * @time: 2020/7/22 23:13
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
@Module
abstract class ActivityBindModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [HomeActivityModule::class, MainFragmentBindModule::class])
    abstract fun homeActivityInjector(): MainActivity

}