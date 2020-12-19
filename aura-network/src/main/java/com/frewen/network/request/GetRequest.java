package com.frewen.network.request;

import com.frewen.network.callback.CallBackProxy;
import com.frewen.network.callback.CallClazzProxy;
import com.frewen.network.function.ApiResultFunction;
import com.frewen.network.listener.AbsResponseCallback;
import com.frewen.network.response.Response;

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

    /**
     * 传入返回结果对象的Class对象
     *
     * @param clazz
     * @param <T>
     */
    public <T> Observable<T> execute(Class<T> clazz) {
        return execute(new CallClazzProxy<Response<T>, T>(clazz) {
        });
    }

    public <T> Observable<T> execute(CallClazzProxy<? extends Response<T>, T> proxy) {
        return build().generateRequest()
                .map(new ApiResultFunction(proxy.getType()));
    }

    /**
     * 调用Get网络请求.传入的
     */
    @Override
    protected Observable<ResponseBody> generateRequest() {
        return mApiService.get(pathUrl, params.urlParamsMap);
    }
}
