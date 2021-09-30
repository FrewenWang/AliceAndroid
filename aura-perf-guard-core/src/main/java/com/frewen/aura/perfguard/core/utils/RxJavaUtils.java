package com.frewen.aura.perfguard.core.utils;

import com.frewen.aura.perfguard.core.AuraPerfGuard;
import com.frewen.aura.perfguard.core.engine.IPerfGuardEngine;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * @filename: RxJavaUtils
 * @author: Frewen.Wong
 * @time: 2021/8/15 22:38
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
public class RxJavaUtils {

    private static Scheduler sComputationScheduler;

    /**
     * 获取计算相关的Handler
     */
    public static Scheduler computationScheduler() {
        if (sComputationScheduler != null) {
            return sComputationScheduler;
        }
        return Schedulers.computation();
    }

    /**
     * 针对被监听者添加在计算线程的的订阅和发布
     *
     * @param observable
     * @param <T>
     */
    public static <T> Observable<T> wrapThreadComputationObservable(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.computation()).observeOn(Schedulers.computation());
    }

    public static Observable<IPerfGuardEngine> wrapThreadComputationObservable(@AuraPerfGuard.ModuleName String moduleName) {

        IPerfGuardEngine module = AuraPerfGuard.instance().getModule(moduleName);
        // if (!(module instanceof SubjectSupport)) {
        //     throw new UnexpectException(moduleName + " is not instance of SubjectSupport.");
        // }
        return null;
    }

}
