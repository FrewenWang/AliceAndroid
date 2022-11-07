package com.frewen.android.demo.jetpack.navigation;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.Map;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
 *         主要是修改里面的@see navigation方法
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/6/19 21:14
 * @copyright Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
@Navigator.Name("fragment")
public class AuraFragmentNavigator extends FragmentNavigator {
    private static final String TAG = "AuraFragmentNavigator";
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
     * 我们要修改成我们的实现方案.最主要就是修改navigation方法
     *
     * @param destination
     * @param args
     * @param navOptions
     * @param navigatorExtras
     */
    @Nullable
    @Override
    public NavDestination navigate(@NonNull Destination destination, @Nullable Bundle args, @Nullable NavOptions navOptions, @Nullable Navigator.Extras navigatorExtras) {

        if (mManager.isStateSaved()) {
            Log.i(TAG, "Ignoring navigate() call: FragmentManager has already"
                    + " saved its state");
            return null;
        }

        // 查看我们的所要导航的Fragment的className
        String className = destination.getClassName();
        if (className.charAt(0) == '.') {
            className = mContext.getPackageName() + className;
        }
        // 通过反射进行实例化Fragment
//        final Fragment frag = instantiateFragment(mContext, mManager,
//                className, args);
//        frag.setArguments(args);


        final FragmentTransaction ft = mManager.beginTransaction();

        int enterAnim = navOptions != null ? navOptions.getEnterAnim() : -1;
        int exitAnim = navOptions != null ? navOptions.getExitAnim() : -1;
        int popEnterAnim = navOptions != null ? navOptions.getPopEnterAnim() : -1;
        int popExitAnim = navOptions != null ? navOptions.getPopExitAnim() : -1;
        if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
            enterAnim = enterAnim != -1 ? enterAnim : 0;
            exitAnim = exitAnim != -1 ? exitAnim : 0;
            popEnterAnim = popEnterAnim != -1 ? popEnterAnim : 0;
            popExitAnim = popExitAnim != -1 ? popExitAnim : 0;
            ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim);
        }


        /// ft.replace(mContainerId, frag); /// 注释掉，我们不再进行Fragment替换
        // 而是替换成：
        final Fragment fragment = mManager.getPrimaryNavigationFragment();
        if (null != fragment) {
            ft.hide(fragment);
        }

        Fragment frag = null;
        String tag = String.valueOf(destination.getId());
        frag = mManager.findFragmentByTag(tag);

        if (null != frag) {
            ft.show(frag);
        } else {
            frag = instantiateFragment(mContext, mManager,
                    className, args);
            frag.setArguments(args);
            ///
            ft.add(mContainerId, frag, tag);
        }

        // 我们来设置下一个显示的Fragment
        ft.setPrimaryNavigationFragment(frag);

        final @IdRes int destId = destination.getId();

        // 下面我们要获取mBackStack对象,由于这个变量在FragmentNavigator是私有的
        // 我们是无法获取的，所以我们可以通过反射类获取mBackStack
        ArrayDeque<Integer> mBackStack = null;

        try {
            // 获取Class对象的一种方式，使用.class语法
            Class clazz = FragmentNavigator.class;
            Field backStackField = clazz.getDeclaredField("mBackStack");
            backStackField.setAccessible(true);
            mBackStack = (ArrayDeque<Integer>) backStackField.get(this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        final boolean initialNavigation = mBackStack.isEmpty();
        // TODO Build first class singleTop behavior for fragments
        final boolean isSingleTopReplacement = navOptions != null && !initialNavigation
                && navOptions.shouldLaunchSingleTop()
                && mBackStack.peekLast() == destId;

        boolean isAdded;
        if (initialNavigation) {
            isAdded = true;
        } else if (isSingleTopReplacement) {
            // Single Top means we only want one instance on the back stack
            if (mBackStack.size() > 1) {
                // If the Fragment to be replaced is on the FragmentManager's
                // back stack, a simple replace() isn't enough so we
                // remove it from the back stack and put our replacement
                // on the back stack in its place
                mManager.popBackStack(
                        generateBackStackName(mBackStack.size(), mBackStack.peekLast()),
                        FragmentManager.POP_BACK_STACK_INCLUSIVE);
                ft.addToBackStack(generateBackStackName(mBackStack.size(), destId));
            }
            isAdded = false;
        } else {
            ft.addToBackStack(generateBackStackName(mBackStack.size() + 1, destId));
            isAdded = true;
        }
        if (navigatorExtras instanceof Extras) {
            Extras extras = (Extras) navigatorExtras;
            for (Map.Entry<View, String> sharedElement : extras.getSharedElements().entrySet()) {
                ft.addSharedElement(sharedElement.getKey(), sharedElement.getValue());
            }
        }
        ft.setReorderingAllowed(true);
        ft.commit();
        // The commit succeeded, update our view of the world
        if (isAdded) {
            mBackStack.add(destId);
            return destination;
        } else {
            return null;
        }
    }

    @NonNull
    private String generateBackStackName(int backStackIndex, int destId) {
        return backStackIndex + "-" + destId;
    }
}
