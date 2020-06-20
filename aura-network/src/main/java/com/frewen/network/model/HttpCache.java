package com.frewen.network.model;

/**
 * @filename: HttpCache
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/6/20 10:10
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public enum HttpCache {
    //仅仅只访问本地缓存，即便本地缓存不存在，也不会发起网络请求
    CACHE_ONLY,
    // 先访问缓存，同时发起网络请求，成功后缓存到本地
    CACHE_FIRST,
    //只访问网络，不进行任何存储
    NET_ONLY,
    // 先访问网络。再缓存到本地
    NET_CACHE
}
