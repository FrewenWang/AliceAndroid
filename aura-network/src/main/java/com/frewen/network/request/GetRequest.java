package com.frewen.network.request;

import com.frewen.network.api.BaseApiService;
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

    public <T> Observable<T> execute(Class<? extends BaseApiService> service) {
        return null;

    }

    @Override
    protected Observable<ResponseBody> generateRequest() {
        return apiService.get(url, params.urlParamsMap);
    }
}
