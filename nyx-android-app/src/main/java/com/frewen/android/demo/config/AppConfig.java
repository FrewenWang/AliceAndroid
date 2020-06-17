package com.frewen.android.demo.config;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.frewen.android.demo.bean.NavigationDestination;
import com.frewen.android.demo.navigation.BottomNavigationBar;
import com.frewen.aura.toolkits.common.FileUtils;
import com.frewen.aura.toolkits.core.AuraToolKits;

import java.util.HashMap;

/**
 * @filename: AppConfig
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/6/16 07:36
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class AppConfig {
    private static final String TAG = "AppConfig";
    private static HashMap<String, NavigationDestination> sDestConfig;
    private static BottomNavigationBar sBottomBar;

    public static BottomNavigationBar getBottomBarConfig() {
        if (sBottomBar == null) {
            String content = FileUtils.readFromAsset(AuraToolKits.getAppContext(), "main_tabs_config.json");
            Log.d(TAG, "content == " + content);
            sBottomBar = JSON.parseObject(content, BottomNavigationBar.class);
        }
        return sBottomBar;
    }

    public static HashMap<String, NavigationDestination> getNavigationConfig() {
        if (sDestConfig == null) {
            String content = FileUtils.readFromAsset(AuraToolKits.getAppContext(), "destination.json");
            sDestConfig = JSON.parseObject(content, new TypeReference<HashMap<String, NavigationDestination>>() {
            });
        }
        return sDestConfig;
    }
}