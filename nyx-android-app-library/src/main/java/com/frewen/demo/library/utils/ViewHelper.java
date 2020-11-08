package com.frewen.demo.library.utils;

import android.annotation.TargetApi;
import android.content.res.TypedArray;
import android.graphics.Outline;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.frewen.demo.library.R;

import androidx.annotation.RequiresApi;

/**
 * @filename: ViewHelper
 * @author: Frewen.Wong
 * @time: 2020/11/7 14:08
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class ViewHelper {
    public static final int RADIUS_ALL = 0;
    public static final int RADIUS_LEFT = 1;
    public static final int RADIUS_TOP = 2;
    public static final int RADIUS_RIGHT = 3;
    public static final int RADIUS_BOTTOM = 4;

    /**
     * 表示仅在给定的API级别或更高级别上调用带注释的元素。
     *
     * @param view
     * @param attributes
     * @param defStyleAttr
     * @param defStyleRes
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setViewOutline(View view, AttributeSet attributes, int defStyleAttr, int defStyleRes) {
        TypedArray array = view.getContext().obtainStyledAttributes(attributes, R.styleable.viewOutLineStrategy, defStyleAttr, defStyleRes);
        int radius = array.getDimensionPixelSize(R.styleable.viewOutLineStrategy_clip_radius, 0);
        int hideSide = array.getInt(R.styleable.viewOutLineStrategy_clip_side, 0);
        array.recycle();
        setViewOutline(view, radius, hideSide);
    }

    /**
     * 设置View的外部边线
     *
     * @param owner      View宿主
     * @param radius     圆角的半径
     * @param radiusSide 外面带边线的方位
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setViewOutline(View owner, final int radius, final int radiusSide) {
        owner.setOutlineProvider(new ViewOutlineProvider() {
            @TargetApi(21)
            @Override
            public void getOutline(View view, Outline outline) {
                // 获取View的宽高
                int w = view.getWidth(), h = view.getHeight();

                if (w == 0 || h == 0) {
                    return;
                }

                // 如果不是所有的都描边
                if (radiusSide != RADIUS_ALL) {
                    // 标记View的左上、右下的坐标
                    int left = 0, top = 0, right = w, bottom = h;
                    if (radiusSide == RADIUS_LEFT) {
                        right += radius;
                    } else if (radiusSide == RADIUS_TOP) {
                        bottom += radius;
                    } else if (radiusSide == RADIUS_RIGHT) {
                        left -= radius;
                    } else if (radiusSide == RADIUS_BOTTOM) {
                        top -= radius;
                    }
                    // 将轮廓设置为由输入矩形和角半径定义的圆角矩形。
                    outline.setRoundRect(left, top, right, bottom, radius);
                    return;
                }
                int top = 0, bottom = h, left = 0, right = w;
                if (radius <= 0) {
                    outline.setRect(left, top, right, bottom);
                } else {
                    outline.setRoundRect(left, top, right, bottom, radius);
                }
            }
        });
        owner.setClipToOutline(radius > 0);
        owner.invalidate();
    }
}
