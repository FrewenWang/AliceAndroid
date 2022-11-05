package com.frewen.android.demo.logic.samples.media.camera2.exo;

import android.app.Application;
import android.net.Uri;

import com.frewen.android.demo.AliceApp;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSinkFactory;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.util.Util;

import java.util.HashMap;

/**
 * @filename: AuraExtPageListPlayerManager
 * @author: Frewen.Wong
 * @time: 11/22/20 5:43 PM
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class AuraExoPageListPlayerManager {
    /**
     * 我们使用HashMap来进行播放器对象的存储
     */
    private static HashMap<String, AuraExoPageListPlayer> sPageListPlayHashMap = new HashMap<>();
    private static final ProgressiveMediaSource.Factory mediaSourceFactory;

    static {
        Application application = AliceApp.getInstance(AliceApp.class);
        //创建http视频资源如何加载的工厂对象
        DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory(Util.getUserAgent(application, application.getPackageName()));
        //创建缓存，指定缓存位置，和缓存策略,为最近最少使用原则,最大为200m
        Cache cache = new SimpleCache(application.getCacheDir(), new LeastRecentlyUsedCacheEvictor(1024 * 1024 * 200));
        //把缓存对象cache和负责缓存数据读取、写入的工厂类CacheDataSinkFactory 相关联
        CacheDataSinkFactory cacheDataSinkFactory = new CacheDataSinkFactory(cache, Long.MAX_VALUE);

        /**创建能够 边播放边缓存的 本地资源加载和http网络数据写入的工厂类
         * public CacheDataSourceFactory(
         *       Cache cache, 缓存写入策略和缓存写入位置的对象
         *       DataSource.Factory upstreamFactory,http视频资源如何加载的工厂对象
         *       DataSource.Factory cacheReadDataSourceFactory,本地缓存数据如何读取的工厂对象
         *       @Nullable DataSink.Factory cacheWriteDataSinkFactory,http网络数据如何写入本地缓存的工厂对象
         *       @CacheDataSource.Flags int flags,加载本地缓存数据进行播放时的策略,如果遇到该文件正在被写入数据,或读取缓存数据发生错误时的策略
         *       @Nullable CacheDataSource.EventListener eventListener  缓存数据读取的回调
         */
        CacheDataSourceFactory cacheDataSourceFactory = new CacheDataSourceFactory(cache,
                dataSourceFactory,
                new FileDataSourceFactory(),
                cacheDataSinkFactory,
                CacheDataSource.FLAG_BLOCK_ON_CACHE,
                null);

        //最后 还需要创建一个 MediaSource 媒体资源 加载的工厂类
        //因为由它创建的MediaSource 能够实现边缓冲边播放的效果,
        //如果需要播放hls,m3u8 则需要创建DashMediaSource.Factory()
        mediaSourceFactory = new ProgressiveMediaSource.Factory(cacheDataSourceFactory);
    }

    /**
     * 我们通过PageName获取对应的播放器缓存
     *
     * @param pageName
     */
    public static AuraExoPageListPlayer get(String pageName) {
        AuraExoPageListPlayer pageListPlay = sPageListPlayHashMap.get(pageName);
        if (pageListPlay == null) {
            Application application = AliceApp.getInstance(AliceApp.class);
            pageListPlay = new AuraExoPageListPlayer(application);
            sPageListPlayHashMap.put(pageName, pageListPlay);
        }
        return pageListPlay;
    }

    public static void release(String pageName) {
        AuraExoPageListPlayer pageListPlayer = sPageListPlayHashMap.remove(pageName);
        if (pageListPlayer != null) {
            pageListPlayer.release();
        }
    }

    /**
     * 创建MediaSource对象，给播放器进行播放使用
     *
     * @param url
     */
    public static MediaSource createMediaSource(String url) {
        return mediaSourceFactory.createMediaSource(Uri.parse(url));
    }
}