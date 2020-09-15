/*
 * Copyright (C) 2015 The Android Open Source Project
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

package android.view;

import android.os.Handler;

import com.android.internal.util.GrowingArrayUtils;

/**
 * Class used to enqueue pending work from Views when no Handler is attached.
 *
 * @hide Exposed for test framework only.
 */
public class HandlerActionQueue {
    private HandlerAction[] mActions;
    private int mCount;

    // frameworks/base/core/java/android/view/HandlerActionQueue.java
    public void post(Runnable action) {
        postDelayed(action, 0);
    }

    // frameworks/base/core/java/android/view/HandlerActionQueue.java
    public void postDelayed(Runnable action, long delayMillis) {
        final HandlerAction handlerAction = new HandlerAction(action, delayMillis);

        synchronized (this) {
            // HandlerAction对象也是LazyInit的
            if (mActions == null) {
                mActions = new HandlerAction[4];
            }
            //这里其实也非常明显，首先HandlerActionQueue会将进来的Runnable对象封装成HandlerAction。
            // 最后将HandlerAction对象加入到本地的mActions(即HandlerAction数组中)缓存起来。
            mActions = GrowingArrayUtils.append(mActions, mCount, handlerAction);
            mCount++;
            /// 到这里其实调用就结束了，那么什么时候才会执行到呢？
        }
    }

    public void removeCallbacks(Runnable action) {
        synchronized (this) {
            final int count = mCount;
            int j = 0;

            final HandlerAction[] actions = mActions;
            for (int i = 0; i < count; i++) {
                if (actions[i].matches(action)) {
                    // Remove this action by overwriting it within
                    // this loop or nulling it out later.
                    continue;
                }

                if (j != i) {
                    // At least one previous entry was removed, so
                    // this one needs to move to the "new" list.
                    actions[j] = actions[i];
                }

                j++;
            }

            // The "new" list only has j entries.
            mCount = j;

            // Null out any remaining entries.
            for (; j < count; j++) {
                actions[j] = null;
            }
        }
    }

    /**
     * 这里其实就是读取HandlerAction数组并通过View中传递过来的mHandler执行postDelay的行为。
     * 其实不论是在什么时机调用post最终都会用到mAttachInfo的mHandler对象来发送消息到MainLooper中。
     * 虽然知道view.post的任务最终的一定会通过AttachInfo的mHandler对象post出去，但是他是哪里来的呢？
     * @param handler
     */
    // frameworks/base/core/java/android/view/HandlerActionQueue.java
    public void executeActions(Handler handler) {
        synchronized (this) {
            // 遍历很多actions
            final HandlerAction[] actions = mActions;
            /// 进行Action的遍历。将所有的Actions取出来
            for (int i = 0, count = mCount; i < count; i++) {
                final HandlerAction handlerAction = actions[i];
                /// 调用handler的postDelay的方法
                handler.postDelayed(handlerAction.action, handlerAction.delay);
            }

            mActions = null;
            mCount = 0;
        }
    }

    public int size() {
        return mCount;
    }

    public Runnable getRunnable(int index) {
        if (index >= mCount) {
            throw new IndexOutOfBoundsException();
        }
        return mActions[index].action;
    }

    public long getDelay(int index) {
        if (index >= mCount) {
            throw new IndexOutOfBoundsException();
        }
        return mActions[index].delay;
    }

    private static class HandlerAction {
        final Runnable action;
        final long delay;

        public HandlerAction(Runnable action, long delay) {
            this.action = action;
            this.delay = delay;
        }

        public boolean matches(Runnable otherAction) {
            return otherAction == null && action == null
                    || action != null && action.equals(otherAction);
        }
    }
}
