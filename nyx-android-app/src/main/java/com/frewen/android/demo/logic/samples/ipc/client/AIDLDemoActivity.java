package com.frewen.android.demo.logic.samples.ipc.client;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.frewen.android.demo.R;
import com.frewen.android.demo.logic.samples.ipc.remote.aidl.IOnNewTicketArrivedListener;
import com.frewen.android.demo.logic.samples.ipc.remote.aidl.IRemoteServiceInterface;
import com.frewen.android.demo.logic.samples.ipc.remote.aidl.RemoteService;
import com.frewen.android.demo.logic.samples.ipc.remote.aidl.RemoteTicket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Frewen.Wong
 */
public class AIDLDemoActivity extends AppCompatActivity {
    private static final String TAG = "T:AIDLDemoActivity";
    private static final int MESSAGE_NEW_TICKET_ARRIVED = 1;

    private IRemoteServiceInterface mRemoteServiceInterface;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_NEW_TICKET_ARRIVED:
                    Log.d(TAG, "receive new Ticket :" + msg.obj);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    };

    /**
     * Binder是可能意外死亡的，这往往是由于服务端进程意外停止了，这时我们需要重新连接服务
     * 有两种方法：
     * 第一种方法是给Binder设置DeathRecipient监听，当Binder死亡时，我们会收到binderDied方法的回调，在binderDied方法中我们可以重连远程服务
     * 另一种方法是在onServiceDisconnected中重连远程服务
     * 两种方法我们可以随便选择一种来使用，
     * 它们的区别在于：
     * onServiceDisconnected在客户端的UI线程中被回调，
     * 而binderDied在客户端的Binder线程池中被回调。
     * 也就是说，在binderDied方法中我们不能访问UI，这就是它们的区别
     */
    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.d(TAG, "binderDied currentThread:" + Thread.currentThread().getName());
            if (mRemoteServiceInterface == null) {
                return;
            }
            mRemoteServiceInterface.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mRemoteServiceInterface = null;
            // TODO:这里重新绑定远程Service
        }
    };

    /**
     * 我们知道:
     *
     * 客户端调用远程服务的方法，被调用的方法运行在服务端的Binder线程池中， 同时客户端线程会被挂起.
     *
     * 这个时候如果服务端方法执行比较耗时，就会导致客户端线程长时间地阻塞在这里，
     * 而如果这个客户端线程是UI线程的话，就会导致客户端ANR，这当然不是我们想要看到的。
     *
     * 因此，如果我们明确知道某个远程方法是耗时的，那么就要避免在客户端的UI线程中去访问远程方法。
     * 由于客户端的onServiceConnected和onService Disconnected方法都运行在UI线程中，
     * 所以也不可以在它们里面直接调用服务端的耗时方法
     */
    private void getAllTicketList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (null == mRemoteServiceInterface) {
                        return;
                    }
                    Log.i(TAG, "query ticketList list, list type: Begin");
                    List<RemoteTicket> ticketList = mRemoteServiceInterface.getTicketList();

                    Log.i(TAG, "query ticketList list, list type:"
                            + ticketList.getClass().getCanonicalName());
                    Log.i(TAG, "query ticketList list, list type:"
                            + Thread.currentThread().getName());
                    Log.i(TAG, "query ticketList list:" + ticketList.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private IOnNewTicketArrivedListener mOnNewBookArrivedListener = new IOnNewTicketArrivedListener.Stub() {
        /**
         * 客户端的IOnNewTicketArrivedListener中的onNewTicketArrived方法运行在客户端的Binder线程池中，
         * 所以不能在它里面去访问UI相关的内容，如果要访问UI，请使用Handler切换到UI线程，
         * 这一点在前面的代码实例中已经有所体现，这里就不再详细描述了。
         * @param newTicket
         * @throws RemoteException
         */
        @Override
        public void onNewTicketArrived(RemoteTicket newTicket) throws RemoteException {
            Log.d(TAG, "FMsg:onNewTicketArrived() called with: thread = [" + Thread.currentThread().getName() + "]");
            mHandler.obtainMessage(MESSAGE_NEW_TICKET_ARRIVED, newTicket)
                    .sendToTarget();
        }

        /**
         * 暂时不要重写这个方法，返回null。
         * 否则会报空指针异常
         * Attempt to invoke interface method
         * 'void android.os.IBinder.linkToDeath(android.os.IBinder$DeathRecipient, int)'
         * on a null object reference
         * @return
         */
        /*@Override
        public IBinder asBinder() {
            return null;
        }*/
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl_demo);

        //第一步：通过intent来绑定远程的Service
        Intent intent = new Intent(this, RemoteService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }


    /**
     * 绑定远程Service之后的ServiceConnection回调
     *
     * ServiceConnection是我们来进行绑定远程Service的时候的结果回调
     * 需要主要的是。onServiceConnected、onServiceDisconnected的方法都是运行在客户端的UI主线程中的
     * 所以在这个方法我们我们也不能调用mRemoteServiceInterface的耗时方法，因为这个时候客户端的主线程会被挂起。
     *
     * AIDL通信中最重要的通信句柄是IBinder对象
     * 而针对这个对象，我们需要重点看，客户端是怎么拿到这个对象的。然后服务端是怎么提供这个对象。
     *
     * 1、这里我们看客户端是怎么拿到的。 这个地方是通过ServiceConnection的回调中onServiceConnected。也就是说客户端必须先Bind服务端的Service
     * 然后在服务端的onServiceConnected的回调中，来获取IRemoteServiceInterface.Stub.asInterface(service);
     */
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "FMsg:onServiceConnected called with: name = [" + name.getShortClassName()
                    + "], service = [" + service
                    + "], threadName = [" + Thread.currentThread().getName()
                    + "]");
            // 获取远程Service接口对象
            IRemoteServiceInterface serviceInterface = IRemoteServiceInterface.Stub.asInterface(service);
            mRemoteServiceInterface = serviceInterface;

            try {
                mRemoteServiceInterface.asBinder().linkToDeath(mDeathRecipient, 0);
                // 我们通过Binder对象注册监听新票的出来的逻辑
                mRemoteServiceInterface.registerListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            /**
             * 从Binder接口对象里面获取购票列表
             * 请注意：下面这个方法如果是一个耗时任务的话。则需要再子线程里面完成
             */
            getAllTicketList();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //远程Service接口对象置为null
            mRemoteServiceInterface = null;
            Log.d(TAG, "FMsg:onServiceDisconnected() called with: name = [" + name + "],thread = " + Thread.currentThread().getName());
        }

        @Override
        public void onBindingDied(ComponentName name) {
            Log.d(TAG, "FMsg:onBindingDied() called with: name = [" + name + "]");
        }

        @Override
        public void onNullBinding(ComponentName name) {
            Log.d(TAG, "FMsg:onNullBinding() called with: name = [" + name + "]");
        }
    };

    @Override
    protected void onDestroy() {
        if (mRemoteServiceInterface != null
                && mRemoteServiceInterface.asBinder().isBinderAlive()) {
            try {
                Log.i(TAG, "unregister listener:" + mOnNewBookArrivedListener);
                mRemoteServiceInterface
                        .unregisterListener(mOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        // Activity在销毁的时候，不要忘记进行远程Service的解绑
        if (null != mConnection) {
            unbindService(mConnection);
        }
        super.onDestroy();
    }

    /**
     * 重新获取所有门票列表
     *
     * @param view
     */
    public void refreshTicketsList(View view) {
        getAllTicketList();
    }

    public void addTicket(View view) {
        int ticketId = new Random().nextInt(1000);
        Map<String, String> place2List = new HashMap<>(16);
        place2List.put("10月12日", "南京");
        place2List.put("3月21日", "香港");
        RemoteTicket remoteTicket = new RemoteTicket(ticketId, "周杰伦无与伦比演唱会买票", 1050, place2List);

        if (null != mRemoteServiceInterface) {
            try {
                mRemoteServiceInterface.addTicket(remoteTicket);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
