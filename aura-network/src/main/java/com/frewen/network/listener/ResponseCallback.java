package com.frewen.network.listener;

import com.frewen.network.response.Response;

/**
 * @filename: ResponseCallback
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/6/20 10:45
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public abstract class ResponseCallback<T> {

    public void onSuccess(Response<T> response) {
    }

    public void onError(Response<T> response) {
    }

    public void onCacheSuccess(Response<T> response) {
    }
}
