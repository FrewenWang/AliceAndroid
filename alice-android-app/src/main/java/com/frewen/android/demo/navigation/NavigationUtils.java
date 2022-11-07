package com.frewen.android.demo.navigation;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import com.frewen.demo.library.utils.BundleUtils;

import androidx.annotation.IdRes;

/**
 * @filename: NavigationUtils
 * @author: Frewen.Wong
 * @time: 11/29/20 6:15 PM
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class NavigationUtils {
    private static final String TAG = "NavigationUtils";

    /**
     * 切换到到对应的Fragment
     *
     * @param newFragment
     * @param bundle
     */
    public static synchronized void switchToFragment(
            Activity context,
            Fragment newFragment, Bundle bundle,
            @IdRes int resId) {
        Log.d(TAG, "switchTo fragment:" + newFragment.getClass().getSimpleName());
        // 启动Fragment事务
        FragmentTransaction transaction = context.getFragmentManager().beginTransaction();
        // 获取当前布局上原有的Fragment
        Fragment oldFragment = context.getFragmentManager().findFragmentById(resId);

        if (oldFragment != null) {
            // 获取索要切换的新的Fragment和老的Fragment是不是一致
            if (oldFragment.getClass().equals(newFragment.getClass())) {
                // 获取老的参数
                Bundle oldBundle = oldFragment.getArguments();
                boolean equal = BundleUtils.isBundlesEqual(oldBundle, bundle);
                Log.d(TAG, "isBundlesEqual : " + equal);
                if (equal) {
                    Log.d(TAG, "same to old fragment , return");
                    return;
                }
            }
        }

        if (bundle != null) {
            try {
                newFragment.setArguments(bundle);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }

        transaction.replace(resId, newFragment);
        transaction.commitAllowingStateLoss();

    }
}
