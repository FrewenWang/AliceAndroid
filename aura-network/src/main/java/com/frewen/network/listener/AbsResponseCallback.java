package com.frewen.network.listener;

import com.frewen.network.callback.IType;
import com.frewen.network.response.AuraNetResponse;
import com.frewen.network.response.exception.AuraNetException;
import com.frewen.network.utils.CommonUtils;

import java.lang.reflect.Type;

/**
 * @filename: ResponseCallback
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/6/20 10:45
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public abstract class AbsResponseCallback<Data> implements IType {
    /**
     * 请求开始
     */
    public abstract void onStart();

    /**
     * 请求完成
     */
    public abstract void onCompleted();

    /**
     * 请求成功
     *
     * @param response
     */
    public abstract void onSuccess(AuraNetResponse<Data> response);

    /**
     * 请求出现异常
     *
     * @param exception
     */
    public abstract void onError(AuraNetException exception);

    /**
     * 缓存请求成功
     *
     * @param response
     */
    public abstract void onCacheSuccess(AuraNetResponse<Data> response);

    @Override
    public Type getType() {
        //获取需要解析的泛型T类型
        return CommonUtils.findNeedClass(getClass());
    }

    public Type getRawType() {
        //获取需要解析的泛型T raw类型
        return CommonUtils.findRawType(getClass());
    }
}
