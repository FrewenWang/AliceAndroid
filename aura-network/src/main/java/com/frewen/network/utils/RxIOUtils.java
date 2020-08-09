package com.frewen.network.utils;

import android.util.Log;

import com.frewen.network.response.Response;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
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

    public static <Upstream, Downstream> ObservableTransformer<Upstream, Downstream> io_main() {
        return null;
    }


    public static <T> ObservableTransformer<Response<T>, T> _io_main() {
        return null;
    }


    public static <T> ObservableTransformer<Response<T>, T> _main() {
        return null;
    }

}
