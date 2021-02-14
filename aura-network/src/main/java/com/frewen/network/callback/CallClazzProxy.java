package com.frewen.network.callback;

import com.frewen.network.response.AuraNetResponse;
import com.frewen.network.utils.CommonUtils;
import com.google.gson.internal.$Gson$Types;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;

/**
 * @filename: CallClazzProxy
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/8/2 10:13
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class CallClazzProxy<T extends AuraNetResponse<R>, R> implements IType {

    private Type type;


    public CallClazzProxy(Type type) {
        this.type = type;
    }

    public Type getCallType() {
        return type;
    }


    @Override
    public Type getType() {//CallClazz代理方式，获取需要解析的Type
        Type typeArguments = null;
        if (type != null) {
            typeArguments = type;
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
