package com.frewen.android.demo.di.components

import android.app.Application
import com.frewen.android.demo.app.NyxAndroidApp
import com.frewen.android.demo.di.modules.ActivityBindModule
import com.frewen.android.demo.di.modules.MyAppModule
import com.frewen.aura.framework.di.scope.AppScope
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

/**
 * @filename: MyAppComponent
 * @introduction:
 *      Component也是一个注解类，一个类要想是Component，必须用Component注解来标注该类。
 *      并且该类是接口或抽象类。
 *      Component需要引用到目标类的实例，Component会查找目标类中用Inject注解标注的属性，
 *      查找到相应的属性后会接着查找该属性对应的用Inject标注的构造函数（这时候就发生联系了），
 *      剩下的工作就是初始化该属性的实例并把实例进行赋值。因此我们也可以给Component叫另外一个名字注入器（Injector）
 * @author: Frewen.Wong
 * @time: 2020/4/7 12:52
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
@AppScope
@Component(modules = [
    // 用于绑定扩展的组件，如v4
    // 我们使用的是支持库（v4库）的 Fragment
    // 接入后可以使用  AndroidInjection.inject(activity) 和  AndroidSupportInjection.inject(f)
    // 组件集成AppModule
    AndroidSupportInjectionModule::class,
    MyAppModule::class,
    ActivityBindModule::class
])
interface MyAppComponent {
    /**
     * 通过 @Component.Builder 增加builder方法，提供Application 注入方法。
     */
    @Component.Builder
    interface Builder {
        //@BindsInstance注解的作用，只能在 Component.Builder 中使用
        //创建 Component 的时候绑定依赖实例
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): MyAppComponent
    }

    fun inject(app: NyxAndroidApp)

}