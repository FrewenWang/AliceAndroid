package com.ainirobot.optimus.network.request;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @filename: RequestBody
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2019/4/13 12:02
 * Copyright ©2018 Frewen.Wong. All Rights Reserved.
 */
public interface RequestBody {
    /**
     * Returns the Content-Type header for this getBody
     */
    String getContentType();

    long getContentLength();

    void writeTo(OutputStream out) throws IOException;
}
