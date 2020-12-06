package com.frewen.demo.library.utils;

import android.os.Bundle;

import java.util.Set;

/**
 * @filename: BundleUtils
 * @author: Frewen.Wong
 * @time: 11/29/20 9:29 PM
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class BundleUtils {

    /**
     * 判断两个Bundle数据是否相同
     *
     * @param old
     * @param current
     */
    public static boolean isBundlesEqual(Bundle old, Bundle current) {
        if (old == null && current == null) {
            return true;
        } else if (old != null && current != null) {
            // old size == current.size here.
            Set<String> oldKeys = old.keySet();
            // 遍历原始数据的Key
            for (String key : oldKeys) {
                // 如果新的Bundle不包含这个Key.则直接返回false
                if (!current.containsKey(key)) {
                    return false;
                }
                // 获取这两个Value
                Object valueOld = old.get(key);
                Object valueCurrent = current.get(key);

                if (valueOld instanceof Bundle && valueCurrent instanceof Bundle &&
                        !isBundlesEqual((Bundle) valueOld, (Bundle) valueCurrent)) {
                    return false;
                } else if (valueOld == null) {
                    if (valueCurrent != null) {
                        return false;
                    }
                } else if (!valueOld.equals(valueCurrent)) {
                    return false;
                }
            }
            return true;
        } else if (old == null) {
            // current is always not null here.
            return current.size() == 0;
        } else {
            // current is null , old is always not null here.
            return old.size() == 0;
        }
    }
}
