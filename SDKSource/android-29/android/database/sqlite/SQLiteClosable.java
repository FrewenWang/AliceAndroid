/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.database.sqlite;

import android.annotation.UnsupportedAppUsage;
import java.io.Closeable;

/**
 * 关于 SQLiteClosable 完全可以理解成 Java为 I/O提供的 Closeable 标准接口，
 * SQLiteCloseable 就是专门为数据库释放提供的标准 API（SQLiteCloseable 也实现了 Closeable）。
 * An object created from a SQLiteDatabase that can be closed.
 *
 * This class implements a primitive reference counting scheme for database objects.
 */
public abstract class SQLiteClosable implements Closeable {
    @UnsupportedAppUsage
    private int mReferenceCount = 1;

    /**
     * Called when the last reference to the object was released by
     * a call to {@link #releaseReference()} or {@link #close()}.
     */
    protected abstract void onAllReferencesReleased();

    /**
     * Called when the last reference to the object was released by
     * a call to {@link #releaseReferenceFromContainer()}.
     *
     * @deprecated Do not use.
     */
    @Deprecated
    protected void onAllReferencesReleasedFromContainer() {
        onAllReferencesReleased();
    }

    /**
     * Acquires a reference to the object.
     *
     * @throws IllegalStateException if the last reference to the object has already
     * been released.
     */
    public void acquireReference() {
        synchronized(this) {
            //表示数据库已经关闭
            if (mReferenceCount <= 0) {
                throw new IllegalStateException(
                        "attempt to re-open an already-closed object: " + this);
            }
            //引用计数++
            mReferenceCount++;
        }
    }

    /**
     * Releases a reference to the object, closing the object if the last reference
     * was released.
     *
     * @see #onAllReferencesReleased()
     */
    public void releaseReference() {
        boolean refCountIsZero = false;
        synchronized(this) {
            //释放对该对象的引用
            refCountIsZero = --mReferenceCount == 0;
        }
        if (refCountIsZero) {
            //当前不存在任何引用
            //通知关闭
            onAllReferencesReleased();
        }
    }

    /**
     * Releases a reference to the object that was owned by the container of the object,
     * closing the object if the last reference was released.
     *
     * @see #onAllReferencesReleasedFromContainer()
     * @deprecated Do not use.
     */
    @Deprecated
    public void releaseReferenceFromContainer() {
        boolean refCountIsZero = false;
        synchronized(this) {
            refCountIsZero = --mReferenceCount == 0;
        }
        if (refCountIsZero) {
            onAllReferencesReleasedFromContainer();
        }
    }

    /**
     * Releases a reference to the object, closing the object if the last reference
     * was released.
     *
     * Calling this method is equivalent to calling {@link #releaseReference}.
     *
     * @see #releaseReference()
     * @see #onAllReferencesReleased()
     */
    public void close() {
        releaseReference();
    }
}
