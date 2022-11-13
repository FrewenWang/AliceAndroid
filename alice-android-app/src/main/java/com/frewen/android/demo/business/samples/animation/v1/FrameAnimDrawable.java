package com.frewen.android.demo.business.samples.animation.v1;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.frewen.android.demo.business.samples.animation.surface.FrameSurfaceView;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.AppCompatImageView;

import static com.frewen.android.demo.business.samples.animation.v1.FrameSurfaceViewV1.MODE_REPEAT;

/**
 * @filename: FrameAnimDrawable
 * @author: Frewen.Wong
 * @time: 2020/12/16 1:36 PM
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class FrameAnimDrawable extends AppCompatImageView implements IFrameAnimView {

    private static final String TAG = "FrameAnimDrawable";
    public static final int COUNT_ANIM_REPEAT = -1;

    private static final int DURATION_FRAME = 45;
    private String mAssertPath;
    private AssetManager assetManager;
    private AnimationDrawable mAnimDrawable;
    private int mAnimCount = COUNT_ANIM_REPEAT;
    private AnimationCallback mAnimCallback;
    private int curIndex;
    private Handler mHandler;
    private int mRepeatMode;

    public FrameAnimDrawable(Context context) {
        this(context, null);
    }

    public FrameAnimDrawable(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FrameAnimDrawable(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intiView(context);
    }


    private void intiView(Context context) {
        assetManager = context.getAssets();
        mHandler = new Handler();
    }

    @UiThread
    @Override
    public void startAnimalByTheme(String assertPath) {
        // 获取当前的主题
        startAnim(assertPath);
    }

    @UiThread
    @Override
    public void startAnim(String assertPath) {
        mAssertPath = assertPath;
        setVisibility(View.VISIBLE);
        try {
            stop();
            String[] list = assetManager.list(assertPath);
            List<String> images = Arrays.asList(list);
            initPathList(images);
            startDraw();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始Animation的动画播放
     */
    private void startDraw() {
        if (mAnimCount == COUNT_ANIM_REPEAT || mRepeatMode == MODE_REPEAT) {
            mAnimDrawable.setOneShot(false);
            mAnimDrawable.start();
        } else {
            curIndex = 0;
            mAnimDrawable.start();
            if (null == mHandler) {
                mHandler = new Handler();
            }
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ++curIndex;
                    if (curIndex >= mAnimCount) {
                        stop();
                        if (null != mAnimCallback) {
                            mAnimCallback.onFinished();
                        }
                    } else {
                        mHandler.postDelayed(this,
                                DURATION_FRAME * mAnimDrawable.getNumberOfFrames());
                    }

                }
            }, DURATION_FRAME * mAnimDrawable.getNumberOfFrames());
        }
    }

    private void initPathList(List<String> images) {
        if (null == mAnimDrawable) {
            mAnimDrawable = new AnimationDrawable();
        }
        for (int i = 0; i < images.size(); i++) {
            try {
                Drawable d = Drawable.createFromStream(assetManager.open(
                        mAssertPath + File.separator + images.get(i)), null);
                mAnimDrawable.addFrame(d, DURATION_FRAME);
            } catch (IOException e) {
                Log.d(TAG, "IOException: " + e.getMessage());
            }
        }
        setImageDrawable(mAnimDrawable);
    }

    @Override
    public void stop() {
        if (null != mHandler) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        if (null != mAnimDrawable && mAnimDrawable.isRunning()) {
            mAnimDrawable.stop();
        }
        mAnimDrawable = null;
    }

    @Override
    public void setRepeatMode(int repeatMode) {
        Log.i(TAG, "setRepeatMode:" + repeatMode);
        mRepeatMode = repeatMode;
    }

    @Override
    public void setCounts(int count) {
        mAnimCount = count;
    }

    @Override
    public void setAnimationCallback(AnimationCallback cb) {
        mAnimCallback = cb;
    }

    @Override
    public void release() {
        setVisibility(INVISIBLE);
        stop();
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (GONE == visibility || INVISIBLE == visibility) {
            stop();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        release();
    }
}
