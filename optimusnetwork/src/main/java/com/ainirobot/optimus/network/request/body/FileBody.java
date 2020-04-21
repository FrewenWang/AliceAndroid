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

import com.ainirobot.optimus.network.request.RequestBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileBody implements RequestBody {

    private final File mFile;
    private final String mContentType;
    private final String mFileName;

    public FileBody(File file, String contentType) {
        this.mContentType = contentType;
        this.mFile = file;
        this.mFileName = file.getName();
    }

    public FileBody(File file) {
        this(file, null);
    }

    @Override
    public String getContentType() {
        return mContentType;
    }

    @Override
    public long getContentLength() {
        return mFile.length();
    }

    public String getFileName() {
        return mFileName;
    }

    @Override
    public void writeTo(OutputStream out) throws IOException {
        FileInputStream fis = new FileInputStream(mFile);
        try {
            byte[] buffer = new byte[4096];
            int length;
            while ((length = fis.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fis.close();
        }
    }
}
