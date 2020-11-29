package com.frewen.android.demo.app.helper;

import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.frewen.aura.toolkits.utils.ProcessInfoUtils;
import com.frewen.keepalive.service.KeepALiveService;

/**
 * @filename: KeepAliveHelper
 * @author: Frewen.Wong
 * @time: 11/22/20 8:13 PM
 * @version: 1.0.0
 * @introduction: 系统出于体验和性能的上的考虑，App在退到后台并不会真正的kill这个进程
 *         而是将其缓存起来.
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class KeepAliveHelper {

    private static final String TAG = "KeepAliveHelper";

    /**
     * 进程保活的逻辑流程
     *
     * 进程优先级：
     * 前台进程    关键优先级
     * 可见进程    高优先级
     * 服务进程    高优先级
     * 后台进程    低优先级
     * 空进程      低优先级
     *
     * ➜  ~ adb shell
     * vbox86p:/ #
     * vbox86p:/ #
     * vbox86p:/ #
     * vbox86p:/ # cat sys/module/lowmemorykiller/parameters/minfree
     * 18432,23040,27648,32256,36864,46080
     * 前台进程大小、可见进程、服务进程、后台进程、ContentProvider空进程
     * 我们怎么去进程优先级：http://gityuan.com/2018/05/19/android-process-adj/
     *
     * 怎么查看我们进程的优先级：
     * vbox86p:/ # cat /proc/23032/oom_adj     值越小，说明进程优先级越高，越不容易被干掉。
     *
     * 所以进程保活的目标就是，让我们的应用回到后台，依然能够让oom_adj的值依然保证很小
     *
     * android.app.RemoteServiceException: Bad notification for startForeground: java.lang.RuntimeException:
     * invalid channel for service notification: Notification(channel=my_channel_01 pri=0 contentView=null
     * vibrate=null sound=null defaults=0x0 flags=0x50 color=0x00000000 category=msg vis=PRIVATE)
     * at android.app.ActivityThread$H.handleMessage(ActivityThread.java:1945)
     * at android.os.Handler.dispatchMessage(Handler.java:107)
     * at android.os.Looper.loop(Looper.java:214)
     * at android.app.ActivityThread.main(ActivityThread.java:7356)
     * at java.lang.reflect.Method.invoke(Native Method)
     * at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:492)
     * at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:930)
     */
    public static void init(Application context) {
        String processName = ProcessInfoUtils.getProcessName(context);
        if (processName != null) {
            boolean defaultProcess = processName.equals(context.getPackageName());
            if (defaultProcess) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(new Intent(context, KeepALiveService.class));
                } else {
                    context.startService(new Intent(context, KeepALiveService.class));
                }
            } else if (processName.contains(":live")) {
                Log.i(TAG, "FMsg:attach() called : ");
            }
        }
    }

    /**
     * 1、广播拉活：
     *  在Android的系统7.0以后在广播做了严格限制，目前广播拉活在Android7.0以上，8.0以上基本已经无法完成拉活
     *
     *
     * 2、全家桶拉活：
     *  全家桶应用之间互相拉活
     *
     *  推送其实也是全家桶拉活。
     *
     * 3、白名单保活：
     *
     * 4、系统机制拉活：
     *
     * 5、账户同步方式拉活:
     *  添加账号的操作，我们
     *
     * 6、系统的定时器的去拉活
     *  通过JobScheduler
     *  不建议采用这种方法，这种方式比较流氓，而且比较耗性能。不建议使用
     *
     *
     *
     * 7、双进程守护：
     *  双进程的保活机制，也就是双进程的守护机制
     *
     *
     *
     *
     */


}
