package com.ainirobot.optimus.network;

import android.text.TextUtils;

import com.ainirobot.optimus.network.utils.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @filename: OriginalResponse
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2019/4/13 12:24
 * Copyright ©2018 Frewen.Wong. All Rights Reserved.
 */
public class OriginalResponse implements Closeable {
    private final int statusCode;
    private final InputStream content;
    private final long contentLength;
    private Map<String, List<String>> headers;
    private String contentType;
    private String contentEncoding;
    private String responseMessage;

    /**
     * 网络请求返回接口
     *
     * @param statusCode    返回码
     * @param content       返回内容
     * @param contentLength 返回内容长度
     */
    public OriginalResponse(int statusCode, InputStream content, long contentLength) {
        this.statusCode = statusCode;
        this.content = content;
        this.contentLength = contentLength;
        this.contentEncoding = "UTF-8";
    }


    public int getStatusCode() {
        return statusCode;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public String getHeaderField(String key) {
        List<String> sessionList = headers.get(key);
        return sessionList != null ? sessionList.get(0) : null;
    }

    public InputStream getContent() {
        return content;
    }

    public String getContentString() {
        if (content == null) {
            return null;
        }
        byte[] buffer = new byte[4096];
        int len;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            while ((len = content.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            return new String(out.toByteArray(), contentEncoding);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(content);
        }
        return null;
    }

    public long getContentLength() {
        return contentLength;
    }

    public String getContentType() {
        return contentType;
    }

    public String getContentEncoding() {
        return contentEncoding;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setContentEncoding(String contentEncoding) {
        if (TextUtils.isEmpty(contentEncoding)) {
            return;
        }
        this.contentEncoding = contentEncoding;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    @Override
    public void close() throws IOException {
        IOUtils.close(content);
    }
}
