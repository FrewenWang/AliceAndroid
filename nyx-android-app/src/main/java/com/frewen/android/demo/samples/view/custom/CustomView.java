package com.frewen.android.demo.samples.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.frewen.android.demo.R;

import androidx.annotation.Nullable;

/**
 * @filename: CustomView
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/9/27 20:26
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
class CustomView extends View {


    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ActionMode);


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // canvas.drawText();
    }
}
