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
     * 依赖实例注入
     * @param activity
     */
    void injectDagger2Activity(Dagger2Activity activity);

    /**
     * 依赖实例注入
     * @param activity
     */
    void injectDagger2DetailActivity(Dagger2Activity activity);
}
