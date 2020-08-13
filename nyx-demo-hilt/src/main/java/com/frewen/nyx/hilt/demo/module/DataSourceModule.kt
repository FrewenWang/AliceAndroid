package com.frewen.nyx.hilt.demo.module

import com.frewen.nyx.hilt.demo.data.DataSource
import com.frewen.nyx.hilt.demo.data.LoggerDBDataSource
import com.frewen.nyx.hilt.demo.data.LoggerInMemoryDataSource
import com.frewen.nyx.hilt.demo.navigation.DemoNavigator
import com.frewen.nyx.hilt.demo.navigation.DemoNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Qualifier


@Qualifier
annotation class InMemoryLogger

@Qualifier
annotation class DatabaseLogger

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
abstract class DataSourceModule {

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
    @DatabaseLogger
    @Binds
    abstract fun bindDBDataSourceModule(dataSource: LoggerDBDataSource): DataSource

    /**
     * 我们看DataSourceModule中的Binds方法
     *
     * 但是，我们看这个方法，有两个返回值是DataSource的@Binds方法
     * 那么我们该使用哪个呢？？
     * 我们不知道，Hilt也不知道啊。。。到底我们该使用哪个实现呢？？
     * 如果熟悉Dagger2的同学可能会可能就了解一些了。这就叫依赖注入迷失。
     *  Cannot have more than one binding method with the same name in a single module
     *   public abstract com.frewen.nyx.hilt.demo.data.DataSource bindDataSourceModule(@org.jetbrains.annotations.NotNull()
     *
     *  一个Module的名字不能相同
     *
     *  [Dagger/DuplicateBindings] com.frewen.nyx.hilt.demo.data.DataSource is bound multiple times:
     *   public abstract static class ApplicationC implements NyxHiltApp_GeneratedInjector,
     *
     *   如果此时，我们运行程序，就会报上面的错误，
     * 那么怎么解决呢？？
     *
     * Hilt和Dagger2一样。也是使用@Qualifier限定符来解决这个问题。
     *
     * 因此，我们定义两个限定符注解。来标记这两个返回值相同的@Binds函数
     *   @Qualifier
     *   annotation class InMemoryLogger
     *   @Qualifier
     *   annotation class DatabaseLogger
     *
     * 这个，这两个返回值相同的方法，就各自有了能够区分彼此的的身份。
     *
     */
    @InMemoryLogger
    @Binds
    abstract fun bindMemoryDataSourceModule(dataSource: LoggerInMemoryDataSource): DataSource


}