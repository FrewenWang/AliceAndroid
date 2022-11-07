package com.frewen.android.demo.business.samples.view.custom.recyclerview;

import android.view.View;

/**
 * @filename: MeasureSpecUtils
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/9/28 14:42
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class MeasureSpecUtils {

    /**
     * 根据MeasureSpec选择合适的尺寸返回
     *
     * @param spec
     * @param desired
     * @param min
     */
    public static int chooseSize(int spec, int desired, int min) {
        final int mode = View.MeasureSpec.getMode(spec);
        final int size = View.MeasureSpec.getSize(spec);
        // 根据测量模式，我们来计算测量尺寸
        // 主要是针对AT_MOST 处理一下Wrap_Content的逻辑操作
        switch (mode) {
            case View.MeasureSpec.EXACTLY:
                return size;
            case View.MeasureSpec.AT_MOST:
                return Math.min(size, Math.max(desired, min));
            case View.MeasureSpec.UNSPECIFIED:
            default:
                return Math.max(desired, min);
        }
    }

}
