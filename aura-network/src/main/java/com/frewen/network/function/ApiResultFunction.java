package com.frewen.network.function;


import android.text.TextUtils;

import com.frewen.network.response.Response;
import com.frewen.network.utils.CommonUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * @filename: ApiReponseFunction
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/8/2 10:27
 * @version: 1.0.0
 * @introduction: 这个类是继承自RxJava2中的转换Function
 *         我们主要是在RxJava2中的map方法里面使用
 *
 *         RxJava2的Map方法：
 *         map基本作用就是将一个Observable通过某种函数关系，转换为另一种Observable，
 *         这个类就是把我们的ResponseBody数据变成了BaseResponse<Data>类型。
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
@SuppressWarnings("unchecked")
public class ApiResultFunction<T> implements Function<ResponseBody, Response<T>> {

    protected Type type;
    protected Gson gson;

    public ApiResultFunction(Type type) {
        gson = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .serializeNulls()
                .create();
        this.type = type;
    }

    @Override
    public Response<T> apply(@NonNull ResponseBody responseBody) throws Exception {
        Response<T> apiResult = new Response<>();
        apiResult.setCode(-1);
        if (type instanceof ParameterizedType) {//自定义ApiResult
            final Class<T> cls = (Class) ((ParameterizedType) type).getRawType();
            if (Response.class.isAssignableFrom(cls)) {
                final Type[] params = ((ParameterizedType) type).getActualTypeArguments();
                final Class clazz = CommonUtils.getClass(params[0], 0);
                final Class rawType = CommonUtils.getClass(type, 0);
                try {
                    String json = responseBody.string();
                    //增加是List<String>判断错误的问题
                    if (!List.class.isAssignableFrom(rawType) && clazz.equals(String.class)) {
                        apiResult.setData((T) json);
                        apiResult.setCode(0);
                       /* final Type type = Utils.getType(cls, 0);
                        ApiResult result = gson.fromJson(json, type);
                        if (result != null) {
                            apiResult = result;
                            apiResult.setData((T) json);
                        } else {
                            apiResult.setMsg("json is null");
                        }*/
                    } else {
                        Response result = gson.fromJson(json, type);
                        if (result != null) {
                            apiResult = result;
                        } else {
                            apiResult.setMsg("json is null");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    apiResult.setMsg(e.getMessage());
                } finally {
                    responseBody.close();
                }
            } else {
                apiResult.setMsg("ApiResult.class.isAssignableFrom(cls) err!!");
            }
        } else {//默认Apiresult
            try {
                final String json = responseBody.string();
                final Class<T> clazz = CommonUtils.getClass(type, 0);
                if (clazz.equals(String.class)) {
                    //apiResult.setData((T) json);
                    //apiResult.setCode(0);
                    final Response result = parseApiResult(json, apiResult);
                    if (result != null) {
                        apiResult = result;
                        apiResult.setData((T) json);
                    } else {
                        apiResult.setMsg("json is null");
                    }
                } else {
                    final Response result = parseApiResult(json, apiResult);
                    if (result != null) {
                        apiResult = result;
                        if (apiResult.getData() != null) {
                            T data = gson.fromJson(apiResult.getData().toString(), clazz);
                            apiResult.setData(data);
                        } else {
                            apiResult.setMsg("ApiResult's data is null");
                        }
                    } else {
                        apiResult.setMsg("json is null");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                apiResult.setMsg(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                apiResult.setMsg(e.getMessage());
            } finally {
                responseBody.close();
            }
        }
        return apiResult;
    }

    /**
     * 网络请求结果的缓存处理逻辑
     *
     * @param response
     */
    private Response<T> parseResponseWithCache(Response<T> response) {
        if (response.isSuccess() && response.getData() != null) {

        }
        return null;
    }

    private Response parseApiResult(String json, Response apiResult) throws JSONException {
        if (TextUtils.isEmpty(json))
            return null;
        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject.has("code")) {
            apiResult.setCode(jsonObject.getInt("code"));
        }
        if (jsonObject.has("data")) {
            apiResult.setData(jsonObject.getString("data"));
        }
        if (jsonObject.has("msg")) {
            apiResult.setMsg(jsonObject.getString("msg"));
        }
        return apiResult;
    }
}
