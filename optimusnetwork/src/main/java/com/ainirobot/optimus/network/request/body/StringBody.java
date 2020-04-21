package com.ainirobot.optimus.network.request.body;

import com.ainirobot.optimus.network.request.RequestBody;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @filename: StringBody
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2019/4/13 17:04
 * Copyright ©2018 Frewen.Wong. All Rights Reserved.
 */
public class StringBody implements RequestBody {

    private static final String CHARSET = "UTF-8";
    private final String mContentType;
    private final byte[] mContent;
    private String mCharset;

    public StringBody(String content, String contentType, String charset) {
        if (contentType != null) {
            this.mContentType = contentType + "; charset=" + charset;
        } else {
            this.mContentType = null;
        }

        content = (content == null) ? "" : content;
        this.mContent = content.getBytes();
        this.mCharset = charset;
    }

    public StringBody(String content, String contentType) {
        this(content, contentType, CHARSET);
    }

    public StringBody(String content) {
        this(content, null);
    }

    @Override
    public String getContentType() {
        return mContentType;
    }

    @Override
    public long getContentLength() {
        return mContent.length;
    }

    @Override
    public void writeTo(OutputStream out) throws IOException {
        final InputStream in = new ByteArrayInputStream(this.mContent);
        final byte[] buffer = new byte[4096];
        int line = 0;
        while ((line = in.read(buffer)) != -1) {
            out.write(buffer, 0, line);
        }
        out.flush();
    }
}
