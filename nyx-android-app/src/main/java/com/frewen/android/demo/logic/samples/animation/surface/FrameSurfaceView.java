package com.frewen.android.demo.logic.samples.animation.surface;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

public class FrameSurfaceView extends BaseSurfaceView {
    private List<Integer> bitmaps = new ArrayList<>();

    public FrameSurfaceView(Context context) {
        super(context);
    }

    public FrameSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FrameSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setDuration(int duration) {
        int frameDuration = duration / bitmaps.size();
        setFrameDuration(frameDuration);
    }


    @Override
    protected void onFrameDraw(Canvas canvas) {

    }

    @Override
    protected void onFrameDrawFinish() {

    }

}
