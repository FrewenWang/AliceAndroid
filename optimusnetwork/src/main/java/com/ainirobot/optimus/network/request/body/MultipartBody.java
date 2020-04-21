/*
 * Copyright (C) 2017 OrionStar Technology Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ainirobot.optimus.network.request.body;

import android.text.TextUtils;
import android.util.Log;

import com.ainirobot.optimus.network.request.RequestBody;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MultipartBody implements RequestBody {
    private static final String TAG = "MultipartBody";
    private static final byte[] BOUNDARY = UUID.randomUUID().toString().getBytes();
    private static final byte[] CRLF = {'\r', '\n'};
    private static final byte[] DASHDASH = {'-', '-'};
    private static final byte[] COLONSPACE = {':', ' '};

    private List<Part> mParts = new ArrayList<>();

    @Override
    public long getContentLength() {
        long contentLength = 0;
        for (Part part : mParts) {
            contentLength += part.body.getContentLength();
        }
        return contentLength;
    }

    @Override
    public String getContentType() {
        return "multipart/form-data; boundary=" + new String(BOUNDARY);
    }

    @Override
    public void writeTo(OutputStream out) throws IOException {
        for (Part part : mParts) {
            HashMap<String, String> headers = part.headers;
            RequestBody body = part.body;

            out.write(DASHDASH);
            out.write(BOUNDARY);
            out.write(CRLF);

            if (!headers.isEmpty()) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    writeHeader(out, entry.getKey(), entry.getValue());
                }
            }

            String contentType = body.getContentType();
            if (contentType != null) {
                writeHeader(out, "Content-Type", contentType);
            }

            long contentLength = body.getContentLength();
            if (contentLength != -1) {
                writeHeader(out, "Content-Length", String.valueOf(contentLength));
            }
            out.write(CRLF);
            body.writeTo(out);
            out.write(CRLF);
        }

        out.write(DASHDASH);
        out.write(BOUNDARY);
        out.write(DASHDASH);
        out.write(CRLF);
    }

    private void writeHeader(OutputStream out, String key, String value) throws IOException {
        out.write(key.getBytes());
        out.write(COLONSPACE);
        out.write(value.getBytes());
        out.write(CRLF);
    }

    public void addPart(String name, RequestBody body) {
        addPart(name, null, body);
    }

    public void addJson(String json) {

        if (TextUtils.isEmpty(json)) {
            Log.e(TAG, "can't add a null object to Multipartbody!");
            return;
        }

        try {
            JSONObject jsonObj = new JSONObject(json);
            Iterator it = jsonObj.keys();
            while (it.hasNext()) {
                String key = (String) it.next();
                String value = jsonObj.optString(key);
                addPart(key, null, new StringBody(value));
            }
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }

    }

    public void addPart(String name, HashMap<String, String> headers, RequestBody body) {
        StringBuilder disposition = new StringBuilder("form-data; name=");
        appendQuotedString(disposition, name);

        if (body instanceof FileBody) {
            String filename = ((FileBody) body).getFileName();
            disposition.append("; filename=");
            appendQuotedString(disposition, filename);
        }

        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put("Content-Disposition", disposition.toString());
        mParts.add(new Part(headers, body));
    }

    public void removePart(String name, RequestBody body) {
        StringBuilder disposition = new StringBuilder("form-data; name=");
        appendQuotedString(disposition, name);

        if (body instanceof FileBody) {
            String filename = ((FileBody) body).getFileName();
            disposition.append("; filename=");
            appendQuotedString(disposition, filename);
        }

        Iterator<Part> iterator = mParts.iterator();
        while (iterator.hasNext()) {
            MultipartBody.Part part = iterator.next();
            if (part.headers.containsValue(disposition.toString())) {
                iterator.remove();
                break;
            }
        }
    }

    private final class Part {

        private final HashMap<String, String> headers;
        private final RequestBody body;

        private Part(HashMap<String, String> headers, RequestBody body) {
            this.body = body;
            this.headers = headers;
        }

    }

    private StringBuilder appendQuotedString(StringBuilder target, String key) {
        target.append('"');
        for (int i = 0, len = key.length(); i < len; i++) {
            char ch = key.charAt(i);
            switch (ch) {
                case '\n':
                    target.append("%0A");
                    break;
                case '\r':
                    target.append("%0D");
                    break;
                case '"':
                    target.append("%22");
                    break;
                default:
                    target.append(ch);
                    break;
            }
        }
        target.append('"');
        return target;
    }
}
