package com.frewen.android.demo.app.taskstarter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;

import com.frewen.aura.framework.taskstarter.BaseLaunchTask;
import com.taobao.android.dexposed.DexposedBridge;
import com.taobao.android.dexposed.XC_MethodHook;
import com.taobao.android.dexposed.XposedHelpers;

/**
 * @filename: DexposedInitTask
 * @author: Frewen.Wong
 * @time: 2020/11/8 22:48
 * @version: 1.0.0
 * @introduction: 实例化Dexposed的AOP开源框架初始化的Task
 *         https://github.com/alibaba/dexposed
 *
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class DexposedInitTask extends BaseLaunchTask {
    @Override
    public void execute() {
        initDexposed();
    }

    @Override
    public boolean needWait() {
        return false;
    }


    /**
     * 我们在使用淘宝的AOP的框架的时候，出现了下面的问题：
     *
     * /Users/frewen/03.ProgramStudy/04.Android/01.WorkSpace/HelloAndroid/nyx-android-app/src/main/AndroidManifest.xml:38:9-35 Error:
     * Attribute application@allowBackup value=(true) from AndroidManifest.xml:38:9-35
     * is also present at [com.taobao.android:dexposed:0.1.1] AndroidManifest.xml:11:18-45 value=(false).
     * Suggestion: add 'tools:replace="android:allowBackup"' to <application> element at AndroidManifest.xml:36:5-176:19 to override.
     *
     * 从错误信息分析：
     * 是淘宝的Dexposed框架里面设置allowBackup为false,而我们的项目的中allowBackup为true。产生了冲突
     *
     *  <application
     *         android:name=".app.NyxAndroidApp"
     *         android:allowBackup="false"
     *         android:icon="@mipmap/ic_launcher"
     *         android:label="@string/app_name"
     *         android:networkSecurityConfig="@xml/network_security_config"
     *         android:roundIcon="@mipmap/ic_launcher_round"
     *         android:supportsRtl="true"
     *         tools:replace="android:allowBackup"
     * 我们
     */
    private void initDexposed() {
        Log.d(TAG, "FMsg:initDexposed() called canDexposed");
        // Check whether current device is supported (also initialize Dexposed framework if not yet)
        if (!DexposedBridge.canDexposed(mContext)) {
            return;
        }
        // Use Dexposed to kick off AOP stuffs.
        Log.d(TAG, "FMsg:initDexposed() called");
        // 基础使用:
        //在出现Activity.onCreate（Bundle）之前和之后附加一段代码。
        // Target class, method with parameter types, followed by the hook callback (XC_MethodHook).
        DexposedBridge.findAndHookMethod(Activity.class, "onCreate", Bundle.class, new XC_MethodHook() {
            // To be invoked before Activity.onCreate().
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                // "thisObject" keeps the reference to the instance of target class.
                super.beforeHookedMethod(param);
                Activity instance = (Activity) param.thisObject;
                // The array args include all the parameters.
                // 获取onCreate的Bundle参数
                Bundle bundle = (Bundle) param.args[0];
                Intent intent = new Intent();
                // XposedHelpers provide useful utility methods.
                XposedHelpers.setObjectField(param.thisObject, "mIntent", intent);

                // Calling setResult() will bypass the original method body use the result as method return value directly.
                if (bundle.containsKey("return")) {
                    param.setResult(null);
                }
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                super.afterHookedMethod(param);

            }
        });


        // 我们进行切面分析，然后获取IPC通信的耗时检测
        try {
            // 我们进行
            DexposedBridge.findAndHookMethod(Class.forName("android.os.BinderProxy"), "transact",
                    int.class, Parcel.class, Parcel.class, int.class, new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            Log.i(TAG, "BinderProxy beforeHookedMethod " + param.thisObject.getClass().getSimpleName()
                                    + "\n" + Log.getStackTraceString(new Throwable()));
                            super.beforeHookedMethod(param);
                        }
                    });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
