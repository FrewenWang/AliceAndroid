package com.frewen.android.demo.business.samples.ipc.server;

import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Handler;
import android.util.Log;

import com.frewen.demo.library.utils.AudioCalUtils;

/**
 * @filename: SettingsContentObserver
 * @author: Frewen.Wong
 * @time: 11/29/20 5:42 PM
 * @version: 1.0.0
 * @introduction: 在 android 中经常会出现改变数据库内容后再去使用数据库更新的内容，
 *         很多人会重新去 query 一遍，但是这样的问题就是程序会特别占内存，
 *         而且有可能会忘关 cursor 而导致程序内存未释放等等。
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class SettingsContentObserver extends ContentObserver {
    private static final String TAG = "SettingsContentObserver";
    private Context context;
    AudioManager manager;
    int lastMusicVol;
    private final Handler handler;

    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public SettingsContentObserver(Context context, Handler handler) {
        super(handler);
        this.context = context;
        this.handler = handler;
        // 获取AudioManager
        manager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        // 获取Music声道的音量
        lastMusicVol = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    /**
     * 如果此观察者有兴趣接收自我更改通知，则返回true。
     */
    @Override
    public boolean deliverSelfNotifications() {
        return super.deliverSelfNotifications();
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        // 获取当前的音量
        int curMusicVol = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
        //
        boolean change = (lastMusicVol != curMusicVol);

        if (change) {
            Log.d(TAG, "music vol change from " + lastMusicVol + " to " + curMusicVol);
            lastMusicVol = curMusicVol;
            // ToastUtil.show(context.getApplicationContext(), AudioCalUtils.getCurAudioIndex(
            //         manager.getStreamVolume(AudioManager.STREAM_MUSIC)));
        }
    }
}
