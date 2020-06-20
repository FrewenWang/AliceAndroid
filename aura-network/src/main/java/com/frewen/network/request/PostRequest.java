package com.frewen.network.request;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * @filename: PostRequest
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/6/20 11:11
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class PostRequest extends Request {

    public PostRequest(String url) {
        super(url);
    }

    @Override
    protected Observable<ResponseBody> generateRequest() {
        return null;
    }

    public <T> Observable<T> execute(Class<T> clazz) {
        return null;
    }
}
