package com.frewen.android.demo.business.samples.animation.v1;

import com.frewen.android.demo.logic.samples.animation.surface.FrameSurfaceView;

/**
 * @filename: IFrameAnimView
 * @author: Frewen.Wong
 * @time: 2020/12/16 1:30 PM
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public interface IFrameAnimView {

    void startAnimalByTheme(String assertPath);

    void startAnim(String assertPath);

    void stop();

    void setRepeatMode(int repeatMode);

    void setCounts(int count);

    void setAnimationCallback(AnimationCallback cb);

    void release();

    interface AnimationCallback {
        void onFinished();
    }

}
