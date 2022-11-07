package com.frewen.android.demo.business.samples.media.camera2.exo;

import android.view.ViewGroup;

/**
 * @filename: IPlayTarget
 * @author: Frewen.Wong
 * @time: 11/22/20 5:09 PM
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public interface IPlayTarget {

    ViewGroup getOwner();

    /**
     * 活跃状态 视频可播放
     */
    void onActive();

    /**
     * 非活跃状态，暂停它
     */
    void inActive();

    /**
     * 判断播放器是否正在播放中
     */
    boolean isPlaying();
}
