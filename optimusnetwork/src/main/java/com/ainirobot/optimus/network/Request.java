package com.ainirobot.optimus.network;

import android.text.TextUtils;
import android.util.Log;

import com.ainirobot.optimus.network.request.RequestBody;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @filename: Request
 * @introduction: 构建网络请求Request
 * @author: Frewen.Wong
 * @time: 2019/4/13 11:59
 * Copyright ©2018 Frewen.Wong. All Rights Reserved.
 */
public class Request {
    private static final String TAG = "Request";
    private final String mUrl;
    private final String mMethod;
    private final String mSessionId;
    private HashMap<String, String> mHeaders;
    private HashMap<String, String> mUrlParams;
    private final RequestBody mBody;
    private final Object mTag;


    private Request(Builder builder) {
        this.mUrl = builder.url;
        this.mMethod = builder.method;
        this.mSessionId = builder.session;
        this.mHeaders = builder.headers;
        this.mUrlParams = builder.urlParams;
        this.mBody = builder.body;
        this.mTag = (builder.tag != null) ? builder.tag : this;
        Log.d(TAG, "tag:" + this.mTag);
    }

    public String getUrl() {
        return mUrl;
    }

    public String getMethod() {
        return mMethod;
    }

    public String getSessionId() {
        return mSessionId;
    }

    public HashMap<String, String> getUrlParams() {
        return mUrlParams;
    }

    public HashMap<String, String> getHeaders() {
        return mHeaders;
    }

    public RequestBody getBody() {
        return mBody;
    }

    public Object getTag() {
        return mTag;
    }

    public static class Builder {
        public static final String GET = "GET";
        public static final String POST = "POST";

        private String url;
        private String method;
        private String session;
        private HashMap<String, String> headers;
        private HashMap<String, String> urlParams;
        private RequestBody body;
        private Object tag;

        public Builder() {
            this.method = GET;
            headers = new HashMap<>();
            urlParams = new HashMap<>();
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder urlParam(String key, String value) {
            urlParams.put(key, value);
            return this;
        }

        /**
         * URL请求的Json参数
         *
         * @param jsonParam
         * @return
         */
        public Builder urlJsonParam(String jsonParam) {
            if (TextUtils.isEmpty(jsonParam)) {
                Log.e(TAG, "can't put a null object to url param!");
                return this;
            }

            try {
                JSONObject jsonObj = new JSONObject(jsonParam);
                Iterator it = jsonObj.keys();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    String value = jsonObj.optString(key);
                    urlParams.put(key, value);
                }
            } catch (Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
            return this;
        }

        public Builder session(String session) {
            this.session = session;
            return this;
        }

        public Builder header(String key, String value) {
            headers.put(key, value);
            return this;
        }

        public Builder get() {
            return method(GET, null);
        }

        public Builder post(RequestBody body) {
            return method(POST, body);
        }

        private Builder method(String method, RequestBody body) {
            if (TextUtils.isEmpty(method)) {
                throw new IllegalArgumentException("method is empty");
            }
            this.method = method;
            this.body = body;
            return this;
        }

        /**
         * It can be used later to cancel the request
         */
        public Builder tag(Object tag) {
            this.tag = tag;
            return this;
        }

        public Request build() {
            return new Request(this);
        }

    }
}
