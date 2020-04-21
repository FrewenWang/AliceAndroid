package com.ainirobot.optimus.network;

/**
 * @filename: RequestListener
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2019/4/13 12:13
 * Copyright ©2018 Frewen.Wong. All Rights Reserved.
 */
public interface RequestListener {
    /**
     * @param originalResopnse
     */
    void onSuccess(OriginalResponse originalResopnse);

    /**
     * @param errCode
     * @param errMsg
     */
    void onFailure(int errCode, String errMsg);
}
