/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.app;

import android.annotation.UnsupportedAppUsage;
import android.annotation.WorkerThread;
import android.annotation.Nullable;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;

/**
 * IntentService is a base class for {@link Service}s that handle asynchronous
 * requests (expressed as {@link Intent}s) on demand.  Clients send requests
 * through {@link android.content.Context#startService(Intent)} calls; the
 * service is started as needed, handles each Intent in turn using a worker
 * thread, and stops itself when it runs out of work.
 *
 * <p>This "work queue processor" pattern is commonly used to offload tasks
 * from an application's main thread.  The IntentService class exists to
 * simplify this pattern and take care of the mechanics.  To use it, extend
 * IntentService and implement {@link #onHandleIntent(Intent)}.  IntentService
 * will receive the Intents, launch a worker thread, and stop the service as
 * appropriate.
 *
 * <p>All requests are handled on a single worker thread -- they may take as
 * long as necessary (and will not block the application's main loop), but
 * only one request will be processed at a time.
 *
 * <p class="note"><b>Note:</b> IntentService is subject to all the
 * <a href="/preview/features/background.html">background execution limits</a>
 * imposed with Android 8.0 (API level 26). In most cases, you are better off
 * using {@link android.support.v4.app.JobIntentService}, which uses jobs
 * instead of services when running on Android 8.0 or higher.
 * </p>
 *
 * <div class="special reference">
 * <h3>Developer Guides</h3>
 * <p>For a detailed discussion about how to create services, read the
 * <a href="{@docRoot}guide/components/services.html">Services</a> developer
 * guide.</p>
 * </div>
 * IntentService是一种特殊的Service，它继承了Service并且它是一个抽象类，因此必须创建它的子类才能使用IntentService。
 * IntentService可用于执行后台耗时的任务，当任务执行后它会自动停止，同时由于IntentService是服务的原因，
 * 这导致它的优先级比单纯的线程要高很多，所以IntentService比较适合执行一些高优先级的后台任务，因为它优先级高不容易被系统杀死。
 * @see android.support.v4.app.JobIntentService
 * @see android.os.AsyncTask
 */
public abstract class IntentService extends Service {
    private volatile Looper mServiceLooper;
    @UnsupportedAppUsage
    private volatile ServiceHandler mServiceHandler;
    private String mName;
    private boolean mRedelivery;

    /**
     * ServiceHandler其实就是一个Handler.
     *  他是使用HandlerThread的线程里面的Lopper来作为消息遍历引擎
     */
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            // IntentService启动的时候发送的Intent信息会在这里交给onHandleIntent处理
            // 这个一个抽象方法，留给子类去实现，由用户去进行自行处理
            onHandleIntent((Intent)msg.obj);
            // 执行完任务之后，调用stopSelf停止IntentService
            // 之所以采用stopSelf(int startId)而不是stopSelf()来停止服务，
            // 那是因为stopSelf()会立刻停止服务，而这个时候可能还有其他消息未处理，
            // stopSelf(int startId)则会等待所有的消息都处理完毕后才终止服务。

            // 一般来说，stopSelf(int startId)在尝试停止服务之前会判断最近启动服务的次数是否和startId相等，
            // 如果相等就立刻停止服务，不相等则不停止服务，这个策略可以从AMS的stopServiceToken方法的实现中找到依据，
            // 读者感兴趣的话可以自行查看源码实现。
            stopSelf(msg.arg1);
        }
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public IntentService(String name) {
        super();
        mName = name;
    }

    /**
     * Sets intent redelivery preferences.  Usually called from the constructor
     * with your preferred semantics.
     *
     * <p>If enabled is true,
     * {@link #onStartCommand(Intent, int, int)} will return
     * {@link Service#START_REDELIVER_INTENT}, so if this process dies before
     * {@link #onHandleIntent(Intent)} returns, the process will be restarted
     * and the intent redelivered.  If multiple Intents have been sent, only
     * the most recent one is guaranteed to be redelivered.
     *
     * <p>If enabled is false (the default),
     * {@link #onStartCommand(Intent, int, int)} will return
     * {@link Service#START_NOT_STICKY}, and if the process dies, the Intent
     * dies along with it.
     */
    public void setIntentRedelivery(boolean enabled) {
        mRedelivery = enabled;
    }

    /**
     * 实现上，IntentService封装了HandlerThread和Handler，这一点可以从它的onCreate方法中看出来，如下所示。
     * 当IntentService被第一次启动时，它的onCreate方法会被调用，onCreate方法会创建一个HandlerThread
     * 然后使用它的Looper来构造一个Handler对象mServiceHandler
     * 这样通过mServiceHandler发送的消息最终都会在HandlerThread中执行
     */
    @Override
    public void onCreate() {
        // TODO: It would be nice to have an option to hold a partial wakelock
        // during processing, and to have a static startService(Context, Intent)
        // method that would launch the service & hand off a wakelock.

        super.onCreate();
        // 实例化HanlderThread的对象。线程的名称是我们传入的名称
        HandlerThread thread = new HandlerThread("IntentService[" + mName + "]");
        // 启动对象线程
        thread.start();
        // 获取这个HanlderThread的线程的Looper对象
        mServiceLooper = thread.getLooper();
        // 传入当前Looper对象然后实例化Handler对象
        // 注意：使用哪个线程的Looper对象创建的Handler，那么事件就会异步的传递给哪个线程
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    /**
     * IntentService仅仅是通过mServiceHandler发送了一个消息，这个消息会在HandlerThread中被处理。
     * mServiceHandler收到消息后，会将Intent对象传递给onHandleIntent方法去处理。
     * 注意这个Intent对象的内容和外界的startService(intent)中的intent的内容是完全一致的，
     * 通过这个Intent对象即可解析出外界启动IntentService时所传递的参数，
     * 通过这些参数就可以区分具体的后台任务，这样在onHandleIntent方法中就可以对不同的后台任务做处理了。
     * @param intent
     * @param startId
     */
    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        // 从消息对象里面取出一个消息。从意图消息消息里面
        Message msg = mServiceHandler.obtainMessage();
        // 将启动Service传入的intent封装成消息发出
        msg.arg1 = startId;
        msg.obj = intent;
        // Handler对象发送这个消息
        mServiceHandler.sendMessage(msg);
    }

    /**
     * You should not override this method for your IntentService. Instead,
     * override {@link #onHandleIntent}, which the system calls when the IntentService
     * receives a start request.
     * @see android.app.Service#onStartCommand
     */
    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        // 继而调用的就是这个onStart方法
        onStart(intent, startId);
        // 这个地方判断是否重传Intent
        // START_REDELIVER_INTENT重传Intent。使用这个返回值时，如果在执行完onStartCommand后，
        // 服务被异常kill掉，系统会自动重启该服务，并将Intent的值传入。
        // 如果不需要重传Intent。则使用START_NOT_STICKY，不再重启服务
        return mRedelivery ? START_REDELIVER_INTENT : START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        mServiceLooper.quit();
    }

    /**
     * Unless you provide binding for your service, you don't need to implement this
     * method, because the default implementation returns null.
     * @see android.app.Service#onBind
     */
    @Override
    @Nullable
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * This method is invoked on the worker thread with a request to process.
     * Only one Intent is processed at a time, but the processing happens on a
     * worker thread that runs independently from other application logic.
     * So, if this code takes a long time, it will hold up other requests to
     * the same IntentService, but it will not hold up anything else.
     * When all requests have been handled, the IntentService stops itself,
     * so you should not call {@link #stopSelf}.
     *
     * @param intent The value passed to {@link
     *               android.content.Context#startService(Intent)}.
     *               This may be null if the service is being restarted after
     *               its process has gone away; see
     *               {@link android.app.Service#onStartCommand}
     *               for details.
     *
     */
    @WorkerThread
    protected abstract void onHandleIntent(@Nullable Intent intent);
}
