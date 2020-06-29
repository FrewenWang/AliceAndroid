package com.frewen.network.request;

import com.frewen.network.response.Response;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * @filename: GetRequest
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2019/4/15 0015 下午6:19
 * @copyright: Copyright ©2019 Frewen.Wong. All Rights Reserved.
 */
public class GetRequest extends Request<GetRequest> {

    public GetRequest(String url) {
        super(url);
    }

//    public <T> Observable<T> execute(Class<T> clazz) {
//        return execute(new CallClazzProxy<Response<T>, T>(clazz) {
//        });
//    }
//
//    public <T> Observable<T> execute(Type type) {
//        return execute(new CallClazzProxy<Response<T>, T>(type) {
//        });
//    }
//
//    public <T> Observable<T> execute(CallClazzProxy<? extends Response<T>, T> proxy) {
//        return build().generateRequest();
//    }

    /**
     * 调用Get网络请求
     */
    @Override
    protected Observable<ResponseBody> generateRequest() {
        return mApiService.get(url, params.urlParamsMap);
    }
}
