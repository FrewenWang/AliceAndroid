package com.frewen.network.request;

import com.frewen.network.BaseResponse;

import io.reactivex.Observable;

/**
 * @filename: GetRequest
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2019/4/15 0015 下午6:19
 * Copyright ©2019 Frewen.Wong. All Rights Reserved.
 */
public class GetRequest extends BaseRequest<GetRequest> {

    public GetRequest(String url) {
        super(url);
    }

    public <T> Observable<T> execute(Class<T> clazz) {
        return null;
    }
}
