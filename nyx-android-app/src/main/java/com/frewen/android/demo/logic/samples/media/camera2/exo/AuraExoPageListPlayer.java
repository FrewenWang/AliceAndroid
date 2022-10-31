package com.frewen.android.demo.logic.samples.media.camera2.exo;

import android.content.Context;
import android.view.LayoutInflater;

import com.frewen.android.demo.R;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;

/**
 * @filename: AuraExoPlayer
 * @author: Frewen.Wong
 * @time: 11/22/20 5:22 PM
 * @version: 1.0.0
 * @introduction: 视频播放列表页面的ExoPlayer播放器
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class AuraExoPageListPlayer {
    public SimpleExoPlayer mSimpleExoPlayer;
    public PlayerView playerView;
    public PlayerControlView controlView;
    public String playUrl;

    /**
     * 构造函数
     */
    public AuraExoPageListPlayer(Context context) {
        //创建exoplayer播放器实例。 目前这种方法已经过时，ExoPlayer给我们提供了建造者模式来实例化
        // exoPlayer = ExoPlayerFactory.newSimpleInstance(context,
        //         //视频每一这的画面如何渲染,实现默认的实现类
        //         new DefaultRenderersFactory(context),
        //         //视频的音视频轨道如何加载,使用默认的轨道选择器
        //         new DefaultTrackSelector(),
        //         //视频缓存控制逻辑,使用默认的即可
        //         new DefaultLoadControl());
        mSimpleExoPlayer = new SimpleExoPlayer.Builder(context).build();

        //加载咱们布局层级优化之后的能够展示视频画面的View
        playerView = (PlayerView) LayoutInflater.from(context).inflate(R.layout.layout_exo_player_view, null, false);

        //加载咱们布局层级优化之后的视频播放控制器
        controlView = (PlayerControlView) LayoutInflater.from(context).inflate(R.layout.layout_exo_player_contorller_view, null, false);

        //别忘记 把播放器实例 和 playerView，controlView相关联
        //如此视频画面才能正常显示,播放进度条才能自动更新
        playerView.setPlayer(mSimpleExoPlayer);
        controlView.setPlayer(mSimpleExoPlayer);

    }

    public void release() {
        if (mSimpleExoPlayer != null) {
            mSimpleExoPlayer.setPlayWhenReady(false);
            mSimpleExoPlayer.stop(true);
            mSimpleExoPlayer.release();
            mSimpleExoPlayer = null;
        }

        if (playerView != null) {
            playerView.setPlayer(null);
            playerView = null;
        }

        if (controlView != null) {
            controlView.setPlayer(null);
            // controlView.setVisibilityListener(null);
            controlView = null;
        }
    }

    /**
     * 切换与播放器exoplayer 绑定的exoplayerView。用于页面切换视频无缝续播的场景
     *
     * @param newPlayerView
     * @param attach
     */
    public void switchPlayerView(PlayerView newPlayerView, boolean attach) {
        playerView.setPlayer(attach ? null : mSimpleExoPlayer);
        newPlayerView.setPlayer(attach ? mSimpleExoPlayer : null);
    }
}
