package com.frewen.android.demo.logic.samples.designpattern.chainresponsibility

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast

/**
 * BroadcastReceiver也就是“广播接收者”的意思，顾名思义，它就是用来接收来自系统和应用中的广播。
 *
 *  广播的用途：
 * 在Android系统中，广播体现在方方面面，
 * 例如当开机完成后系统会产生一条广播，接收到这条广播就能实现开机启动服务的功能；
 * 当网络状态改变时系统会产生一条广播，接收到这条广播就能及时地做出提示和保存数据等操作；
 * 当电池电量改变时，系统会产生一条广播，接收到这条广播就能在电量低时告知用户及时保存进度，等等。
 *
 *
 */
class FirstBroadCastReceiver : BroadcastReceiver() {
    companion object {
        private const val TAG = "FirstBroadCastReceiver"
    }

    /**
     * 在onReceive方法内，我们可以获取随广播而来的Intent中的数据，这非常重要，就像无线电一样，包含很多有用的信息。
     * 当BroadcastReceiver接收Intent广播时，将调用此方法。在这段时间内，您可以使用BroadcastReceiver上的其他方法来查看/修改当前结果值。
     * 除非您使用*{@link android.content.Context＃registerReceiver（BroadcastReceiver， IntentFilter，String，android.os.Handler）
     * 明确要求将其安排在不同的线程上，否则始终在其进程的主线程中调用此方法。
     * 当它在主线程上运行时，您永远不要在其中执行长时间运行的操作（在允许接收方被阻止并被杀死之前，系统允许10秒的超时时间）。
     */
    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive() called with: context = $context, intent = $intent")
        // 获取Intent中附加的限制值
        val limit = intent.getIntExtra("limit", -1001)

        if (limit == 1000) {
            // 获取Intent中附加的字符串消息并Toast
            val msg = intent.getStringExtra("msg")
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            // 终止广播
            abortBroadcast()
        } else {
            // 添加信息发送给下—个Receiver
            val b = Bundle()
            b.putString("new", "Message from FirstReceiver")
            setResultExtras(b);
        }
    }

    /**
     * 在创建完我们的BroadcastReceiver之后，还不能够使它进入工作状态，我们需要为它注册一个指定的广播地址。
     * 没有注册广播地址的BroadcastReceiver就像一个缺少选台按钮的收音机，虽然功能俱备，但也无法收到电台的信号。
     * 下面我们就来介绍一下如何为BroadcastReceiver注册广播地址。
     *
     * 静态注册
     * 静态注册是在AndroidManifest.xml文件中配置的，我们就来为MyReceiver注册一个广播地址：
     * <intent-filter>
     *       <action android:name="android.intent.action.FIRST_BROADCAST" />
     *       <category android:name="android.intent.category.DEFAULT" />
     *</intent-filter>
     *
     * 动态注册
     * 动态注册需要在代码中动态的指定广播地址并注册，通常我们是在Activity或Service注册一个广播，
     * 下面我们就来看一下注册的代码：
     * MyReceiver receiver = new MyReceiver();
     * IntentFilter filter = new IntentFilter();
     * filter.addAction("android.intent.action.MY_BROADCAST");
     * registerReceiver(receiver, filter);
     *
     * 注意，registerReceiver是android.content.ContextWrapper类中的方法，Activity和Service都继承了ContextWrapper，
     * 所以可以直接调用。在实际应用中，我们在Activity或Service中注册了一个BroadcastReceiver，
     * 当这个Activity或Service被销毁时如果没有解除注册，系统会报一个异常，提示我们是否忘记解除注册了。
     * 所以，记得在特定的地方执行解除注册操作：
     *      @Override
     *      protected void onDestroy() {
     *          super.onDestroy();
     *          unregisterReceiver(receiver);
     *      }
     */


}
