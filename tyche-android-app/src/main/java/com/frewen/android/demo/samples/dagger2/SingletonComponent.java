package com.frewen.android.demo.samples.dagger2;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @filename: SingletonComponent
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/4/22 11:45
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
@Singleton
@Component(modules = SingletonModule.class)
public interface SingletonComponent {

    /**
     * 主要是：如果是不用的SingletonComponent所有需要注入的Activity则需要注释掉
     * 不然报错：
     * e: /Users/frewen/03.ProgramStudy/03.Android/01.WorkSpace/HelloAndroid/tyche-android-app/src/main/java/com/frewen/android
     * /demo/samples/dagger2/SingletonComponent.java:16: error: [Dagger/MissingBinding] @javax.inject.Named("noParam") com
     * .frewen.android.demo.samples.dagger2.data.InjectData cannot be provided without an @Provides-annotated method.
     * public interface SingletonComponent {
     * ^
     * @param activity
     * @javax.inject.Named("noParam") com.frewen.android.demo.samples.dagger2.data.InjectData is injected at
     *         com.frewen.android.demo.samples.dagger2.Dagger2Activity.mInjectData1
     *         com.frewen.android.demo.samples.dagger2.Dagger2Activity is injected at
     *         com.frewen.android.demo.samples.dagger2.SingletonComponent.injectDagger2Activity(com.frewen.android.demo
     *         .samples.dagger2.Dagger2Activity)
     *         The following other entry points also depend on it:
     *         com.frewen.android.demo.samples.dagger2.SingletonComponent.injectDagger2DetailActivity(com.frewen.android.demo
     *         .samples.dagger2.Dagger2Activity)
     *         依赖实例注入
     */
//    void injectDagger2Activity(Dagger2Activity activity);

    /**
     * 依赖实例注入
     * @param activity
     */
//    void injectDagger2DetailActivity(Dagger2Activity activity);
}
