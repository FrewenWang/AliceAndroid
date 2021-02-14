package com.frewen.network.callback;

import com.frewen.network.listener.AbsResponseCallback;
import com.frewen.network.response.AuraNetResponse;
import com.frewen.network.utils.CommonUtils;
import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;

/**
 * @filename: CallBackProxy
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/8/2 10:13
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public abstract class CallBackProxy<T extends AuraNetResponse<R>, R> implements IType {
    /**
     * 网络请求响应监听回调
     */
    AbsResponseCallback<T> mCallBack;

    public CallBackProxy(AbsResponseCallback<T> callBack) {
        mCallBack = callBack;
    }

    public AbsResponseCallback getResponseCallBack() {
        return mCallBack;
    }

    /**
     * CallClazz代理方式，获取需要解析的Type
     */
    @Override
    public Type getType() {
        Type typeArguments = null;
        if (mCallBack != null) {
            Type rawType = mCallBack.getRawType();//如果用户的信息是返回List需单独处理
            if (List.class.isAssignableFrom(CommonUtils.getClass(rawType, 0))
                    || Map.class.isAssignableFrom(CommonUtils.getClass(rawType, 0))) {
                typeArguments = mCallBack.getType();
            } else {
                Type type = mCallBack.getType();
                typeArguments = CommonUtils.getClass(type, 0);
            }
        }

        if (typeArguments == null) {
            typeArguments = ResponseBody.class;
        }

        Type rawType = CommonUtils.findNeedType(getClass());
        if (rawType instanceof ParameterizedType) {
            rawType = ((ParameterizedType) rawType).getRawType();
        }
        return $Gson$Types.newParameterizedTypeWithOwner(null, rawType, typeArguments);
    }
}
