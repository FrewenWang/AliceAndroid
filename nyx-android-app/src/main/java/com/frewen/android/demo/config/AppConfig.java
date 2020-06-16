package com.frewen.android.demo.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.frewen.android.demo.bean.NavigationDestination;
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

    private static HashMap<String, NavigationDestination> sNavigationConfig;

    public static HashMap<String, NavigationDestination> getNavigationConfig() {
        if (sNavigationConfig == null) {
            String content = FileUtils.readFromAsset(AuraToolKits.getAppContext(), "destination.json");
            sNavigationConfig = JSON.parseObject(content, new TypeReference<HashMap<String, NavigationDestination>>() {
            });
        }
        return sNavigationConfig;
    }
}
