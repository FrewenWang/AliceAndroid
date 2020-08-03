package com.frewen.function;


import com.frewen.network.response.Response;
import com.frewen.network.utils.CommonUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * @filename: ApiReponseFunction
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/8/2 10:27
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class ApiResponseFunction<Data> implements Function<ResponseBody, Response<Data>> {

    protected Type type;
    protected Gson gson;

    public ApiResponseFunction(Type type) {
        gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .serializeNulls()
                .create();
        this.type = type;
    }


    @Override
    public Response<Data> apply(ResponseBody responseBody) throws Exception {
        Response<Data> apiResult = new Response<>();
        apiResult.setCode(-1);
        if (type instanceof ParameterizedType) {//自定义ApiResult
            final Class<Data> cls = (Class) ((ParameterizedType) type).getRawType();
            if (Response.class.isAssignableFrom(cls)) {
                final Type[] params = ((ParameterizedType) type).getActualTypeArguments();
                final Class clazz = CommonUtils.getClass(params[0], 0);
//                final Class rawType = Utils.getClass(type, 0);
            }
        }

        return null;
    }
}
