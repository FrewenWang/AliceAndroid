package com.frewen.android.demo.logic.samples.dagger2;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @filename: SingletonModule
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/4/22 11:46
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
@Module
public class SingletonModule {
    @Provides
    @Singleton
    SingletonData provideSingletonData() {
        return new SingletonData();
    }

}
