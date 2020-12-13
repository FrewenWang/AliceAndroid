package com.frewen.network.request;

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
     * 执行网络请求的Get请求
     *
     * @param listener 传入参数的是响应结果监听回调
     * @param <T>
     */
    public <T> Observable<T> execute(AbsResponseCallback listener) {
        return execute(new CallClazzProxy<Response<T>, T>(listener) {
        });
    }

    public <T> Observable<T> execute(CallClazzProxy<? extends Response<T>, T> proxy) {
        return build().generateRequest()   // 调用Retrofit2的方法返回一个ResponseBody被监听者对象
                .map(new ApiResultFunction<>());
        // .compose(isSyncRequest ? RxIOUtils.<Data>_main() : RxIOUtils.<Data>_io_main());
    }

    /**
     * 调用Get网络请求.传入的
     */
    @Override
    protected Observable<ResponseBody> generateRequest() {
        return mApiService.get(pathUrl, params.urlParamsMap);
    }
}
