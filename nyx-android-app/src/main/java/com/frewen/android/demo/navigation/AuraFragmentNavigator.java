package com.frewen.android.demo.navigation;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigator;
import androidx.navigation.fragment.FragmentNavigator;

/**
 * @filename: AuraFragmentNavigator
 *         ft.replace(mContainerId, frag);
 *         ft.setPrimaryNavigationFragment(frag);
 * @introduction: Navigation远框架中，原生的Navigation的用的replace的方法。如上
 *         这样会导致Fragment的生命周期进行重建
 *         所以我们来重写一下他的实现，修改成show和hide的实现方式
 * @author: Frewen.Wong
 * @time: 2020/6/17 22:50
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class AuraFragmentNavigator extends FragmentNavigator {

    private final Context mContext;
    private final FragmentManager mManager;
    private final int mContainerId;

    public AuraFragmentNavigator(@NonNull Context context, @NonNull FragmentManager manager, int containerId) {
        super(context, manager, containerId);
        mContext = context;
        mManager = manager;
        mContainerId = containerId;
    }

    /**
     * 我们要修改成我们的实现方案
     * @param destination
     * @param args
     * @param navOptions
     * @param navigatorExtras
     * @return
     */
    @Nullable
    @Override
    public NavDestination navigate(@NonNull Destination destination, @Nullable Bundle args, @Nullable NavOptions navOptions, @Nullable Navigator.Extras navigatorExtras) {
        return super.navigate(destination, args, navOptions, navigatorExtras);
    }
}
