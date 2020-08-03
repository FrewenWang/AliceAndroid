package com.frewen.network.request;

import com.frewen.callback.CallClazzProxy;
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

    public <Data> Observable<Data> execute(Class<Data> clazz) {
        return execute(new CallClazzProxy<Response<Data>, Data>(clazz) {
        });
    }

    public <Data> Observable<Data> execute(CallClazzProxy<? extends Response<Data>, Data> proxy) {
        return build().generateRequest()
                .map( )
    }

    /**
     * 调用Get网络请求.传入的
     */
    @Override
    protected Observable<ResponseBody> generateRequest() {
        return mApiService.get(url, params.urlParamsMap);
    }
}
