package com.frewen.demo.library.mvvm.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.frewen.demo.library.utils.ViewHelper;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;

/**
 * @filename: CircleImageView
 * @author: Frewen.Wong
 * @time: 2020/11/7 14:06
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class CircleImageView extends AppCompatImageView {


    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewHelper.setViewOutline(this, attrs, defStyleAttr, 0);
        }
    }

    /**
     * 当我们使用DataBinding的时候，有些自定义View的自定义属性我们无法使用DataBinding的表达式来进行复制
     * 所以谷歌提供给我们的BindingAdapter用来，用来标记一个当我们设置对应的属性
     * Android会帮我注定调用的适配器的方法，然后设置对应的属性参数的设置
     */
    @BindingAdapter(value = {"image_url", "is_circle"}, requireAll = true)
    public static void setImageUrl(CircleImageView imageView, String imageUrl, boolean isCircle) {
        RequestBuilder<Drawable> builder = Glide.with(imageView).load(imageUrl);
        if (isCircle) {
            builder.transform(new CircleCrop());
        }

        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        // TODO  这是是干什么的？？
        if (layoutParams != null && layoutParams.width > 0 && layoutParams.height > 0) {
            builder.override(layoutParams.width, layoutParams.height);
        }

        builder.into(imageView);
    }
}
