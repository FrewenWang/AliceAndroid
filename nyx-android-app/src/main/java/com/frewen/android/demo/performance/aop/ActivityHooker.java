package com.frewen.android.demo.performance.aop;


import android.os.Bundle;
import android.util.Log;


/**
 * @filename: ActivityHooker
 * @author: Frewen.Wong
 * @time: 2020/11/9 22:17
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class ActivityHooker {

//    private static final String TAG = "ActivityHooker";
//    public static ActivityRecord sActivityRecord;
//
//    static {
//        sActivityRecord = new ActivityRecord();
//    }
//
//    /**
//     * 目标的Class的
//     *
//     * @param tag
//     * @param msg
//     */
//    @Proxy("i")
//    @TargetClass("android.util.Log")
//    public static int logI(String tag, String msg) {
//        msg = "FrewenLog:" + msg;
//        return (int) Origin.call();
//    }
//
//    /**
//     * 下面我们是向进行Hook住Activity中的onCreate的方法
//     *
//     * @param savedInstanceState
//     *
//     * @Insert 所需要代理的方法
//     * @TargetClass 目标类
//     */
//    @Insert(value = "onCreate", mayCreateSuper = true)
//    @TargetClass(value = "androidx.appcompat.app.FragmentActivity", scope = Scope.ALL)
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        sActivityRecord.mOnCreateTime = System.currentTimeMillis();
//        Origin.callVoid();
//    }
//
//
//    @Insert(value = "onWindowFocusChanged", mayCreateSuper = true)
//    @TargetClass(value = "androidx.appcompat.app.FragmentActivity", scope = Scope.ALL)
//    public void onWindowFocusChanged(boolean hasFocus) {
//        sActivityRecord.mOnWindowsFocusChangedTime = System.currentTimeMillis();
//        long cost = sActivityRecord.mOnWindowsFocusChangedTime - sActivityRecord.mOnCreateTime;
//        Log.d(TAG, "FMsg:onWindowFocusChanged() called with: cost = [" + cost + "]");
//        Origin.callVoid();
//    }


}
