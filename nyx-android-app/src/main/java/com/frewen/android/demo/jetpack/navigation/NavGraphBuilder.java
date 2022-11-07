package com.frewen.android.demo.jetpack.navigation;

import android.content.ComponentName;

import com.frewen.android.demo.business.model.NavigationDestination;
import com.frewen.android.demo.constant.AppConfig;
import com.frewen.aura.toolkits.common.AppInfoUtils;
import com.frewen.aura.toolkits.core.AuraToolKits;

import java.util.Map;

import androidx.fragment.app.FragmentActivity;
import androidx.navigation.ActivityNavigator;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavGraphNavigator;
import androidx.navigation.NavigatorProvider;
import androidx.navigation.fragment.FragmentNavigator;

/**
 * @filename: NavGraphBuilder
 * @introduction: TODO : 抽时间需要好好学习一下Navigation的框架
 * @author: Frewen.Wong
 * @time: 2020/6/16 09:14
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class NavGraphBuilder {

    public static void build(NavController controller, FragmentActivity activity, int containerId) {
        NavigatorProvider provider = controller.getNavigatorProvider();

        NavGraph navGraph = new NavGraph(new NavGraphNavigator(provider));

        ActivityNavigator activityNavigator = provider.getNavigator(ActivityNavigator.class);
        //        FragmentNavigator fragmentNavigator = provider.getNavigator(FragmentNavigator.class);
        AuraFragmentNavigator fragmentNavigator = new AuraFragmentNavigator(activity, activity.getSupportFragmentManager(), containerId);
        provider.addNavigator(fragmentNavigator);

        Map<String, NavigationDestination> destinationMap = AppConfig.getNavigationConfig();
        /// 遍历注入的Navigation对象
        for (NavigationDestination navigationDestination : destinationMap.values()) {
            if (navigationDestination.isFragment()) {
                FragmentNavigator.Destination destination = fragmentNavigator.createDestination();
                destination.setClassName(navigationDestination.getClassName());
                destination.setId(navigationDestination.getId());
                destination.addDeepLink(navigationDestination.getPageUrl());
                navGraph.addDestination(destination);
            } else {
                ActivityNavigator.Destination destination = activityNavigator.createDestination();
                destination.setId(navigationDestination.getId());
                destination.addDeepLink(navigationDestination.getPageUrl());
                destination.setComponentName(new ComponentName(AppInfoUtils.getPackageName(AuraToolKits.getAppContext())
                        , navigationDestination.getClassName()));
                navGraph.addDestination(destination);
            }

            if (navigationDestination.asStarter()) {
                navGraph.setStartDestination(navigationDestination.getId());
            }
        }

        controller.setGraph(navGraph);
    }
}
