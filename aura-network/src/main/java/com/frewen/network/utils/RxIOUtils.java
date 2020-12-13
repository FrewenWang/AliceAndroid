package com.frewen.network.utils;

import com.frewen.network.function.ApiHttpErrorFunction;
import com.frewen.network.function.ApiResultParserFunction;
import com.frewen.network.logger.Logger;
import com.frewen.network.response.Response;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @filename: RxIOUtils
 * @introduction: RxJava的线程调度器
 * @author: Frewen.Wong
 * @time: 2020/8/9 11:27
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class RxIOUtils {
    private static final String TAG = "RxIOUtils";

    public static <Data> ObservableTransformer<Response<Data>, Data> _io_main() {
        return new ObservableTransformer<Response<Data>, Data>() {
            @NonNull
            @Override
            public ObservableSource<Data> apply(@NonNull Observable<Response<Data>> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new ApiResultParserFunction<Data>())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {
                                Logger.i(TAG, "_io_main+++doOnSubscribe+++" + disposable.isDisposed());
                            }
                        }).doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                Logger.i(TAG, "_io_main+++doFinally+++");
                            }
                        })
                        .onErrorResumeNext(new ApiHttpErrorFunction<Data>());
            }
        };
    }


    public static <Data> ObservableTransformer<Response<Data>, Data> _main() {
        return new ObservableTransformer<Response<Data>, Data>() {
            @NonNull
            @Override
            public ObservableSource<Data> apply(@NonNull Observable<Response<Data>> upstream) {
                return upstream.map(new ApiResultParserFunction<Data>())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {
                                Logger.i(TAG, "_main+++doOnSubscribe+++" + disposable.isDisposed());
                            }
                        }).doFinally(new Action() {
                            @Override
                            public void run() throws Exception {
                                Logger.i(TAG, "_main+++doFinally+++");
                            }
                        })
                        .onErrorResumeNext(new ApiHttpErrorFunction<Data>());
            }
        };
    }

}
