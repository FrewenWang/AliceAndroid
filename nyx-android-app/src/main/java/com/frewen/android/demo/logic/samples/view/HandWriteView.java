package com.frewen.android.demo.logic.samples.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;

import com.frewen.android.demo.R;

import androidx.annotation.Nullable;

/**
 * @filename: HandWriteView
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/10/8 19:17
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class HandWriteView extends View {
    private final Bitmap originalBitmap;
    private final Bitmap newBitmap;

    public HandWriteView(Context context) {
        this(context, null);
    }

    public HandWriteView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HandWriteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /// 使用BitmapFactory创建出Bitmap
        originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test_avatar);
        // 通过原始的BitMap来创建新的Bitmap
        newBitmap = Bitmap.createBitmap(originalBitmap);
    }
}
