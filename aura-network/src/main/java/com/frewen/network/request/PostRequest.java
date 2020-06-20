package com.frewen.network.request;

import io.reactivex.Observable;

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

    public <T> Observable<T> execute(Class<T> clazz) {
        return null;
    }
}
