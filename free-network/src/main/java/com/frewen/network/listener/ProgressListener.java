package com.frewen.network.listener;

/**
 * @filename: ProgressListener
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2019/4/15 0015 下午5:43
 * Copyright ©2019 Frewen.Wong. All Rights Reserved.
 */
public interface ProgressListener {
    /**
     * 回调进度
     *
     * @param bytesWritten  当前读取响应体字节长度
     * @param contentLength 总长度
     * @param done          是否读取完成
     */
    void onProgress(long bytesWritten, long contentLength, boolean done);
}
