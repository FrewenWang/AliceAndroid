/*
 * Copyright (C) 2006 The Android Open Source Project
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

package android.view.animation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.android.internal.R;
import com.android.internal.view.animation.HasNativeInterpolator;
import com.android.internal.view.animation.NativeInterpolatorFactory;
import com.android.internal.view.animation.NativeInterpolatorFactoryHelper;

/**
 * An interpolator where the rate of change starts out slowly and
 * and then accelerates.
 *
 */
@HasNativeInterpolator
public class AccelerateInterpolator extends BaseInterpolator implements NativeInterpolatorFactory {
    private final float mFactor;
    private final double mDoubleFactor;

    public AccelerateInterpolator() {
        mFactor = 1.0f;
        mDoubleFactor = 2.0;
    }

    /**
     * Constructor
     *
     * @param factor Degree to which the animation should be eased. Seting
     *        factor to 1.0f produces a y=x^2 parabola. Increasing factor above
     *        1.0f  exaggerates the ease-in effect (i.e., it starts even
     *        slower and ends evens faster)
     */
    public AccelerateInterpolator(float factor) {
        mFactor = factor;
        mDoubleFactor = 2 * mFactor;
    }

    public AccelerateInterpolator(Context context, AttributeSet attrs) {
        this(context.getResources(), context.getTheme(), attrs);
    }

    /** @hide */
    public AccelerateInterpolator(Resources res, Theme theme, AttributeSet attrs) {
        TypedArray a;
        if (theme != null) {
            a = theme.obtainStyledAttributes(attrs, R.styleable.AccelerateInterpolator, 0, 0);
        } else {
            a = res.obtainAttributes(attrs, R.styleable.AccelerateInterpolator);
        }

        mFactor = a.getFloat(R.styleable.AccelerateInterpolator_factor, 1.0f);
        mDoubleFactor = 2 * mFactor;
        setChangingConfiguration(a.getChangingConfigurations());
        a.recycle();
    }

    /**
     * 我们看到，在默认情况下，AccelerateInterpolator 的getInterpolation方法中会对input进行乘方操作，
     * 这个input就是流逝的时间百分比，input的取值为0.0f～1.0f，当input逐渐增大时，input*input 的变化范围越来越大，
     * 使得动画的属性值在同一时间段内的变化范围更大，从而实现了加速动画的效果。
     * 例如，动画执行总时间为1秒，使用的是AccelerateInterpolator插值器，
     * 在动画执行了100毫秒时百分比为0.1，那么此时通过插值器的计算，百分比成了0.01；又过了100毫秒，
     * 此时百分比为0.2，经过插值器的计算变为0.04；在执行到300毫秒时，百分比经过插值器计算会变为0.09。
     * 我们看到，在相同的100毫秒内百分比的变化频率逐渐增大，100到200毫秒之间的变化值为0.03，
     * 200到300毫秒之前的变化范围则是0.05，这样在同一时间段内百分比差距越来越大，也就形成了加速的效果。
     *
     * @param input
     */
    public float getInterpolation(float input) {
        // 默认为1.0f,随着时间的推移,变化范围越大
        if (mFactor == 1.0f) {
            return input * input;
        } else {
            return (float) Math.pow(input, mDoubleFactor);
        }
    }

    /** @hide */
    @Override
    public long createNativeInterpolator() {
        return NativeInterpolatorFactoryHelper.createAccelerateInterpolator(mFactor);
    }
}
