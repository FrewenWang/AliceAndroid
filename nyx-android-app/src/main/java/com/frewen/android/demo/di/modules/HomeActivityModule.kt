package com.frewen.android.demo.di.modules

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides

/**
 * @filename: MainActivityModule
 * @introduction:
 * @author: Frewen.Wong
 * @time: 200/4/9 20:16
 * Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
@Module
class HomeActivityModule {

    @Provides
    fun providerMainFragmentList(): List<Fragment> {
        return listOf()
    }
}