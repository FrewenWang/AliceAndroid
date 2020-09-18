package com.frewen.android.demo.performance;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * @filename: PerformanceAop
 * @introduction: 进行方法耗时的时长的相关逻辑
 * @author: Frewen.Wong
 * @time: 2020/9/17 10:49
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 *
 *         添加依赖之后报错：
 *         java.lang.RuntimeException: Unable to instantiate application com.frewen.android.demo.app.NyxAndroidApp:
 *         java.lang.ClassNotFoundException: Didn't find class "com.frewen.android.demo.app.NyxAndroidApp"
 *         on path: DexPathList[[zip file "/data/app/com.frewen.android.demo.debug-KA_ioiR-yZ59fKR3_yfkZg==/base.apk"],
 *         nativeLibraryDirectories=[/data/app/com.frewen.android.demo.debug-KA_ioiR-yZ59fKR3_yfkZg==/lib/x86,
 *         /data/app/com.frewen.android.demo.debug-KA_ioiR-yZ59fKR3_yfkZg==/base.apk!/lib/x86, /system/lib, /system/product/lib, /system/vendor/lib]]
 *  出现这个问题，可能是由于项目分包的问题导致的：
 *          aspectjx {
 *             //false 关闭AspectJX功能  true 开启
 *             enabled true
 *             include 'com.frewen.android.demo.app.*' // 'com.frewen.android.demo.app.*'
 *
 *             //排除所有package路径中包含`android.support`的class文件及库（jar文件）
 *             exclude 'com.alibaba'
 *             exclude 'android.support'
 *             exclude 'org.apache.weex'
 *             exclude 'com.aliyun'
 *             exclude 'com.taobao'
 *             exclude 'alibaba'
 *         }
 *  我们需要做切面的类Include 进来。
 *  但是貌似我们做了之后，不再报错，但是代码貌似不生效 （TODO 需要继续研究）,没发现问题
 *
 *
 *
 *
 * 使用AOP的优点
 * 1、无侵入性
 * 2、修改方便
 * 这种方式的优雅的获取方法耗时的方式
 * AOP的理解及使用
 */
@Aspect
public class PerformanceAop {
    private static final String TAG = "PerformanceAop";

    /**
     * 代码之前和代码执行之后都插入相应的代码
     */
    @Around("call(* com.frewen.android.demo.app.NyxAndroidApp.**(..))")
    public void getMethodDuration(ProceedingJoinPoint joinPoint) {
        long time = System.currentTimeMillis();
        Log.e(TAG, "FMsg:getMethodDuration: " + (System.currentTimeMillis() - time));
        try {
            joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        Log.e(TAG, "FMsg:getMethodDuration: " + (System.currentTimeMillis() - time));
    }
}
