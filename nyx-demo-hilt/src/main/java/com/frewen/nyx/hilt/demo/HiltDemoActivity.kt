package com.frewen.nyx.hilt.demo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.frewen.nyx.hilt.demo.data.DataSource
import com.frewen.nyx.hilt.demo.data.UserInfo
import com.frewen.nyx.hilt.demo.module.InMemoryLogger
import com.frewen.nyx.hilt.demo.navigation.DemoNavigator
import com.frewen.nyx.hilt.demo.navigation.Screens
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * 第二步：将依赖项注入 Android 类
 *
 * 在 Application 类中设置了 Hilt 且有了应用级组件后，Hilt 可以为带有 @AndroidEntryPoint 注释的其他 Android 类提供依赖项：
 *
 * Hilt 目前支持以下 Android 类：
 *      Application（通过使用 @HiltAndroidApp）
 *      Activity
 *      Fragment
 *      View
 *      Service
 *      BroadcastReceiver
 *  如果您使用 @AndroidEntryPoint 为某个Android 类添加注释，则还必须为依赖于该类的Android类添加注释。
 *  例如，如果您为某个 Fragment添加注释，则还必须为使用该Fragment的所有Activity 添加注释。
 *      注意：在 Hilt 对 Android 类的支持方面还要注意以下几点：
 *      Hilt 仅支持扩展 ComponentActivity 的 Activity，如 AppCompatActivity。
 *      Hilt 仅支持扩展 androidx.Fragment 的 Fragment。
 *      Hilt 不支持保留的 Fragment。
 *
 *  那这个到底是为什么呢？？ TODO
 *
 *  那么如果不这个干，会出现什么问题呢？？  TODO
 *
 *
 *  @AndroidEntryPoint 会为项目中的每个Android 类生成一个单独的Hilt组件。
 *  这些组件可以从它们各自的父类接收依赖项，如组件层次结构中所述。
 *
 *  那么我们在HiltDemoActivity的中添加AndroidEntryPoint注解。
 *  并且所依赖的组件依赖的UserInfo加上@Inject注解。那么就可以进行依赖注入吗？？
 *
 *  Caused by: java.lang.IllegalStateException:
 *  Hilt Activity must be attached to an @AndroidEntryPoint Application.
 *  Found: class com.frewen.nyx.hilt.demo.NyxHiltApp
 *
 *  很遗憾。我们添加了@AndroidEntryPoint还是不能进行依赖注入。给我上报上面的错误
 *
 *  Hilt 注入的类可以有同样使用注入的其他基类。
 *  如果这些类是抽象类，则它们不需要 @AndroidEntryPoint 注释。
 */
@AndroidEntryPoint
class HiltDemoActivity : AppCompatActivity() {
    /**
     *   java.lang.RuntimeException: Unable to start activity
     *   ComponentInfo{com.frewen.nyx.hilt.demo/com.frewen.nyx.hilt.demo.HiltDemoActivity}:
     *   kotlin.UninitializedPropertyAccessException: lateinit property user has not been initialized
     *
     *   所以我们仅仅@Inject还是达不到依赖注入的功能
     */
    @Inject
    lateinit var user: UserInfo

    /**
     * 这个地方，我们不在使用下面的provider的方式来进行主动实例化
     * 我们使用依赖注入，但是有个问题：DemoNavigator是一个接口，无法进行实例化
     *  所以我们无法通过在构造函数上加@Inject注入它，我们应该主动向Hilt提供绑定信息，
     *  注意！！！！这个地方@Binds就登场了！！！！
     * 使用 @Binds 注入接口实例方法是在Hilt模块内创建一个带有@Binds 注释的抽象函数。
     *
     * 注意！！！！！！
     * 这个依赖注入的对象，不能使用private修改，否则会报错：
     * 错误: Dagger does not support injection into private fields
     *   private com.frewen.nyx.hilt.demo.navigation.DemoNavigator navigator;
     */
    @Inject
    lateinit var navigator: DemoNavigator

    /**
     * 接下来，我们要就要进行注入DataSource这个对象了。
     * 同样，@Inject的注解，所以我们还是找他们的@Inject的构造函数
     * 但是很不幸的，他又是一份DataSource接口，我们可能需要创建一个Module，同步@Binds查找
     *
     * 所以下面，我们就创建一个DataSourceModule吧。
     *
     * 我们防止依赖注入迷失，我们需要加上限定修饰符注解。因为两个方法都已经加上了限定符，所以如果这个地方不加：
     *  错误: [Dagger/MissingBinding] com.frewen.nyx.hilt.demo.data.DataSource
     *  cannot be provided without an @Provides-annotated method.
     */
    @InMemoryLogger
    @Inject
    lateinit var logger: DataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hilt_demo)

        /**
         * navigator我们原来通过provideNavigator来进行new生成的。
         * 所以现在我们通过依赖注入进行生成这个对象
         */
        // navigator = (applicationContext as NyxHiltApp).roomServiceProvider.provideNavigator(this)

        if (savedInstanceState == null) {
            navigator.navigateTo(R.id.main_container, Screens.BUTTONS)
        }

        initUser()
    }

    private fun initUser() {
        print(user.id)
        print(user.name)
        print(user.age)

        Log.i("HiltDemoActivity", "${user.id},${user.name},${user.age}")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        /// 判断Fragment的回退栈的数量
        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        }
    }
}