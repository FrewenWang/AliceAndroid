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
public class ApiResponseFunction<Data> implements Function<ResponseBody, Response<Data>> {

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
        Response<Data> dataResponse = new Response<>();
        dataResponse.setCode(-1);
        if (type instanceof ParameterizedType) {//自定义ApiResult
            final Class<Data> cls = (Class) ((ParameterizedType) type).getRawType();
            if (Response.class.isAssignableFrom(cls)) {
                final Type[] params = ((ParameterizedType) type).getActualTypeArguments();
                final Class clazz = CommonUtils.getClass(params[0], 0);
                final Class rawType = CommonUtils.getClass(type, 0);

                try {
                    String json = responseBody.string();
                    //增加是List<String>判断错误的问题
                    if (!List.class.isAssignableFrom(rawType) && clazz.equals(String.class)) {
                        dataResponse.setData((Data) json);
                        dataResponse.setCode(0);
                       /* final Type type = Utils.getType(cls, 0);
                        ApiResult result = gson.fromJson(json, type);
                        if (result != null) {
                            apiResult = result;
                            apiResult.setData((T) json);
                        } else {
                            apiResult.setMsg("json is null");
                        }*/
                    } else {
                        Response response = gson.fromJson(json, type);
                        if (response != null) {
                            dataResponse = response;
                        } else {
                            dataResponse.setMsg("json is null");
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    dataResponse.setMsg(ex.getMessage());
                } finally {
                    responseBody.close();
                }
            }
        } else {
            try {
                final String json = responseBody.string();
                final Class<Data> clazz = CommonUtils.getClass(type, 0);
                if (clazz.equals(String.class)) {
                    //apiResult.setData((T) json);
                    //apiResult.setCode(0);
                    final Response result = parseApiResult(json, dataResponse);
                    if (result != null) {
                        dataResponse = result;
                        dataResponse.setData((Data) json);
                    } else {
                        dataResponse.setMsg("json is null");
                    }
                } else {
                    final Response result = parseApiResult(json, dataResponse);
                    if (result != null) {
                        dataResponse = result;
                        if (dataResponse.getData() != null) {
                            Data data = gson.fromJson(dataResponse.getData().toString(), clazz);
                            dataResponse.setData(data);
                        } else {
                            dataResponse.setMsg("ApiResult's data is null");
                        }
                    } else {
                        dataResponse.setMsg("json is null");
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                dataResponse.setMsg(e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                dataResponse.setMsg(e.getMessage());
            } finally {
                responseBody.close();
            }
        }

        dataResponse = parseResponseWithCache(dataResponse);

        return dataResponse;
    }

    /**
     * 网络请求结果的缓存处理逻辑
     *
     * @param response
     * @return
     */
    private Response<Data> parseResponseWithCache(Response<Data> response) {
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
