package com.frewen.network.function;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * @filename: RetryExceptionFunction
 * @author: Frewen.Wong
 * @time: 12/13/20 10:19 AM
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class RetryExceptionFunction implements Function<Observable<? extends Throwable>, Observable<?>> {
    /* retry次数*/
    private int count = 0;
    /*延迟*/
    private long delay = 500;
    /*叠加延迟*/
    private long increaseDelay = 3000;

    public RetryExceptionFunction() {

    }

    public RetryExceptionFunction(int count, long delay) {
        this.count = count;
        this.delay = delay;
    }

    public RetryExceptionFunction(int count, long delay, long increaseDelay) {
        this.count = count;
        this.delay = delay;
        this.increaseDelay = increaseDelay;
    }

    @Override
    public Observable<?> apply(@NonNull Observable<? extends Throwable> observable) throws Exception {
        return null;
    }
}
