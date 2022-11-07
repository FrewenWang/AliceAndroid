package com.frewen.nyx.hilt.demo.module

import com.frewen.nyx.hilt.demo.navigation.DemoNavigator
import com.frewen.nyx.hilt.demo.navigation.DemoNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

/**
 * @filename: NavigationModule
 * @introduction:
 *      这个地方，我们定义了一个abstract class NavigationModule{] 这样一个Hilt模块
 *      这个模块我们必须加两个注解：
 *                  @InstallIn(ActivityComponent::class)
 *                  @Module
 *
 *     而Hilt模块内创建会有带有@Binds注解的抽象函数。
 *
 * @author: Frewen.Wong
 * @time: 2020/8/13 00:06
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
@InstallIn(ActivityComponent::class)
@Module
abstract class NavigationModule {

    /**
     *@Binds 注解会告知Hilt在需要提供接口的实例时要使用哪种实现。
     * 我们重点看这个抽象函数的入参和返回值
     *
     *  函数参数会告知 Hilt 要提供哪种实现。
     *  函数返回类型会告知 Hilt 函数提供哪个接口的实例。
     *
     *  所以，这个方法就很明了了。这个方法提供的是一个DemoNavigator实例
     *  那么他绑定的实现是哪个实现呢？？
     *  就是DemoNavigatorImpl的实现，我们看他的构造函数使用@Inject注解注释的。
     *  好的，这个实例就可以正确返回了。
     */
    @Binds
    abstract fun bindNavigator(impl: DemoNavigatorImpl): DemoNavigator


}