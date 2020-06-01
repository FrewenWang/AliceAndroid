package com.frewen.android.demo.samples.dagger2;

import android.util.Log;

import com.frewen.android.demo.samples.dagger2.data.Data;
import com.frewen.android.demo.samples.dagger2.data.InjectData;

import javax.inject.Named;

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
public class Dagger2Module {
    private static final String TAG = "Dagger2Module";

    @Provides
    Data provideData() {
        return new Data();
    }

    /**
     * Provider的优先级是高于Inject的优先级的
     * @return
     */
    @Provides
    @Named("noParam")
    InjectData provideInjectData() {
        InjectData data = new InjectData();
        Log.d(TAG, "FMsg:provideInjectData() called = " + data);
        return data;
    }

    @Provides
    @Named("withParam")
    InjectData provideInjectData2() {
        InjectData data = new InjectData("Frewen.Wang");
        Log.d(TAG, "FMsg:provideInjectData() called = " + data);
        return data;
    }


}
