package com.ainirobot.optimus.network;

import android.text.TextUtils;
import android.util.Log;

import com.ainirobot.optimus.network.constant.ErrorCode;
import com.ainirobot.optimus.network.request.RequestBody;
import com.ainirobot.optimus.network.utils.HttpUtils;
import com.ainirobot.optimus.network.utils.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

/**
 * @filename: NetworkRunnable
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2019/4/13 11:57
 * Copyright ©2018 Frewen.Wong. All Rights Reserved.
 */
public class NetworkRunnable implements Runnable {
    private static final String TAG = "NetworkRunnable";
    private final OptimusNetClient mClient;
    private final Request mRequest;
    private final RequestListener mCallback;
    private boolean isCanceled = false;

    public NetworkRunnable(OptimusNetClient client,
                           Request request, RequestListener callback) {
        this.mClient = client;
        this.mRequest = request;
        this.mCallback = callback;
    }

    public void cancel() {
        isCanceled = true;
    }

    public Object getTag() {
        return mRequest.getTag();
    }


    @Override
    public void run() {
        if (!isCanceled) {
            performRequest();
            return;
        }

        if (mCallback != null) {
            mCallback.onFailure(ErrorCode.CODE_ERROR_THREAD_CANCELED, "thread cancel");
        }
    }

    /**
     * 执行网络请求
     */
    private void performRequest() {
        String url = mRequest.getUrl();
        String method = mRequest.getMethod();
        String sessionId = mRequest.getSessionId();
        RequestBody body = mRequest.getBody();
        Map<String, String> headers = mRequest.getHeaders();
        Map<String, String> params = mRequest.getUrlParams();

        OutputStream out = null;
        InputStream in = null;
        HttpURLConnection urlConnection;
        try {
            StringBuffer stringBuffer = new StringBuffer();
            Set<Map.Entry<String, String>> entites = params.entrySet();
            int i = 0;
            for (Map.Entry<String, String> entry : entites) {
                if (i > 0) {
                    stringBuffer.append("&");
                }
                String key = entry.getKey();
                String value = URLEncoder.encode(entry.getValue(), "UTF-8");
                stringBuffer.append(key).append("=").append(value);
                i++;
            }
            if (!TextUtils.isEmpty(stringBuffer.toString())) {
                url += "?" + stringBuffer.toString();
            }

            if (HttpUtils.connectionIsHttps(url)) {
                urlConnection = (HttpsURLConnection) new URL(url).openConnection();
            } else {
                urlConnection = (HttpURLConnection) new URL(url).openConnection();
            }

            if (method.equals(Request.Builder.POST)) {
                urlConnection.setDoOutput(true);
                urlConnection.setDoInput(true);
            }

            urlConnection.setUseCaches(false);
            urlConnection.setRequestMethod(method);

            if (!TextUtils.isEmpty(sessionId)) {
                urlConnection.setRequestProperty("Cookie", sessionId);
            }

            for (Map.Entry<String, String> header : headers.entrySet()) {
                urlConnection.setRequestProperty(header.getKey(), header.getValue());
            }

            if (body != null) {
                urlConnection.setRequestProperty("Content-Type", body.getContentType());
                out = urlConnection.getOutputStream();
                body.writeTo(out);
                out.flush();
            }

            if (isCanceled) {
                if (mCallback != null) {
                    mCallback.onFailure(ErrorCode.CODE_ERROR_USER_CANCEL, "user cancel");
                }
                return;
            }
            // 网络请求返回码
            int responseCode = urlConnection.getResponseCode();

            if (mCallback != null) {
                Map<String, List<String>> responseHeaders = urlConnection.getHeaderFields();
                long contentLength = urlConnection.getContentLength();
                String contentType = urlConnection.getContentType();
                String contentEncoding = urlConnection.getContentEncoding();
                String responseMessage = urlConnection.getResponseMessage();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    in = urlConnection.getInputStream();
                }

                OriginalResponse response = new OriginalResponse(responseCode, in, contentLength);
                response.setContentType(contentType);
                response.setHeaders(responseHeaders);
                response.setContentEncoding(contentEncoding);
                response.setResponseMessage(responseMessage);
                int status = response.getStatusCode();
                Log.d(TAG, "response status code:" + status);
                if (status == HttpURLConnection.HTTP_OK) {
                    mCallback.onSuccess(response);
                } else {
                    mCallback.onFailure(status, response.getResponseMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (mCallback != null) {
                mCallback.onFailure(ErrorCode.CODE_ERROR_IO_EXCEPTION, e.getMessage());
            }
        } finally {
            IOUtils.closeAll(out, in);
            mClient.getDispatcher().finished(this);
        }
    }
}
