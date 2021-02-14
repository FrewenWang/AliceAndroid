package com.frewen.network.function;

import com.frewen.network.response.exception.AuraNetException;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * @filename: ApiHttpErrorFunction
 * @author: Frewen.Wong
 * @time: 12/13/20 10:27 AM
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class ApiHttpErrorFunction<Data> implements Function<Throwable, Observable<Data>> {
    @Override
    public Observable<Data> apply(@NonNull Throwable throwable) throws Exception {
        return Observable.error(AuraNetException.handleException(throwable));
    }
}
