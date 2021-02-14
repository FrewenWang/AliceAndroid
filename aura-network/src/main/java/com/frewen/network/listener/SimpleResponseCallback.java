package com.frewen.network.listener;

import com.frewen.network.response.AuraNetResponse;

/**
 * @filename: SimpleResponseCallback
 * @author: Frewen.Wong
 * @time: 12/12/20 10:25 PM
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public abstract class SimpleResponseCallback<T> extends AbsResponseCallback<T> {

    @Override
    public void onStart() {

    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onCacheSuccess(AuraNetResponse<T> response) {

    }
}
