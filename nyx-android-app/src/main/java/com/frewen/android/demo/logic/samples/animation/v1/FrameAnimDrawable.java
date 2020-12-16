package com.frewen.android.demo.logic.samples.animation.v1;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

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
    public static final int MODE_REPEAT = 0;
    public static final int MODE_ONCE = 1;
    private String mAssertPath;
    private AssetManager assetManager;
    private AnimationDrawable animationDrawable;
    private Context context;

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
        this.context = context;
        assetManager = context.getAssets();
    }

    @Override
    public void startAnimal(String assertPath) {
        mAssertPath = assertPath;
        Log.d(TAG, "startAnimal assertPath: " + mAssertPath);
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
        animationDrawable.setOneShot(false);
        animationDrawable.start();
    }

    private void initPathList(List<String> images) {
        if (null == animationDrawable) {
            animationDrawable = new AnimationDrawable();
        }
        for (int i = 0; i < images.size(); i++) {
            try {
                Drawable d = Drawable.createFromStream(assetManager.open(mAssertPath + images.get(i)), null);
                animationDrawable.addFrame(d, 200);
            } catch (IOException e) {
                Log.d(TAG, "IOException: " + e.getMessage());
            }
        }

        setImageDrawable(animationDrawable);
    }

    @Override
    public void stop() {

    }
}
