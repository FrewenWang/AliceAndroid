package com.frewen.android.demo.logic.samples.dagger2;

import dagger.Component;

/**
 * @filename: Dagger2Component
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/4/29 11:49
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
@Component(modules = Dagger2Module.class)
public interface Dagger2Component {

    /**
     * 依赖实例注入
     * @param activity
     */
    void injectDagger2Activity(Dagger2Activity activity);
}
