package com.frewen.network.listener;

import com.frewen.network.response.Response;
import com.frewen.network.response.exception.AuraException;

/**
 * @filename: ResponseCallback
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/6/20 10:45
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public abstract class ResponseCallback<Data> {

    public abstract void onStart();

    public abstract void onCompleted();

    public void onSuccess(Response<Data> response) {

    }

    public void onError(AuraException exception) {

    }

    public void onCacheSuccess(Response<Data> response) {
    }
}
