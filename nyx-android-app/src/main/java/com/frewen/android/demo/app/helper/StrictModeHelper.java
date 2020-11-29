package com.frewen.android.demo.app.helper;

import android.os.StrictMode;

import com.frewen.android.demo.BuildConfig;

/**
 * @filename: StrictModeHelper
 * @author: Frewen.Wong
 * @time: 11/22/20 8:41 PM
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class StrictModeHelper {
    /**
     * 初始化严格模式
     * StrictMode是Android提供的一个开发工具，
     * 用于检测一些异常的操作，以便开发者进行修复。StrictMode可以监控以下问题，
     * 不应该在应用主线程中完成的工作，包括磁盘读写、网络访问等。
     * 内存泄露，包括Activity泄露、SQLite泄露、未正确释放的对象等。
     *
     * 开启StrictMode需要进行两方面的设置：ThreadPolicy和VmPolicy。
     * 两种策略中以“detect”开头命名的方法代表需要检测的问题，
     * 以“penalty”开头命名地 方法代表探测到问题后的处理方式。
     *
     * 当在设备端通过设置打开严格模式时，出现违规操作时屏幕会闪烁。
     * 设置-》开发人员选项-》监控-》启用严格模式
     * 当在策略中设置penaltyDropBox() 时，出现违规操作时会在/data/system/dropbox/下生成文件。
     * 文件包括system_app_strictmode 和 data_app_strictmode两种，内容包括问题发生时的堆栈和进程相关信息。
     */
    public static void init() {
        if (BuildConfig.DEBUG) {
            StrictMode
                    .setThreadPolicy(new StrictMode.ThreadPolicy
                            .Builder()
                            .detectAll()                    //检测所有潜在的问题
                            .detectCustomSlowCalls()        //检测慢速调用
                            // .detectDiskReads()           //检测磁盘读操作
                            .detectDiskWrites()             //检测磁盘写操作
                            .detectNetwork()                //检测网络操作
                            //.detectResourceMismatches()     //检测定义资源类型和getter调用之间的不匹配
                            //.detectUnbufferedIo()           //检测未缓存的I/O操作
                            //.penaltyDeath()                 //检测到问题后crash整个进程
                            //.penaltyDeathOnNetwork()        //检测到问题后crash任何使用网络的进程
                            .penaltyLog()                     //检测到问题后记录到系统日志中。
                            .penaltyDropBox()                 //检测到问题后将堆栈和数据写到DropBox中
                            //.penaltyDialog()                  //检测到问题后弹出对话框
                            .penaltyFlashScreen()             //检测到问题后闪烁屏幕
                            .build());
            StrictMode
                    .setVmPolicy(new StrictMode.VmPolicy
                            .Builder()
                            .detectAll()                    //检测所有潜在的问题
                            //.detectContentUriWithoutPermission()//检测未设置读写权限的"content://Uri"传输
                            .detectLeakedClosableObjects()          //检测对象未正常关闭。
                            .detectActivityLeaks()                  //检测Activity内存泄露
                            .detectLeakedSqlLiteObjects()           //检测SQLite对象未正常关闭
                            .detectLeakedRegistrationObjects()      //检测BroadcastReceiver或ServiceConnection在Context拆卸时发生的泄露
                            //setClassInstanceLimit(Class class, int instanceLimit) //设置同时在内存中存储一个类实例的上限。
                            //.penaltyDeath()                         //检测到问题后crash整个进程
                            //.penaltyDeathOnCleartextNetwork()       //检测到问题后crash任何使用网络的进程
                            //.penaltyDeathOnFileUriExposure()      //当“file://Uri"暴露在应用之外时crash整个进程
                            .penaltyDropBox()                       //检测到问题后将堆栈和数据写到DropBox中
                            .penaltyLog()                       //检测到问题后记录到系统日志中。
                            .build());
        }

    }
}
