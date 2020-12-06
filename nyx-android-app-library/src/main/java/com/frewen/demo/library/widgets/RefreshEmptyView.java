package com.frewen.demo.library.widgets;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.frewen.demo.library.R;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * @filename: RefreshEmptyView
 * @author: Frewen.Wong
 * @time: 12/6/20 8:20 PM
 * @version: 1.0.0
 * @introduction: 下拉刷新的没有获取到数据的空布局显示
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class RefreshEmptyView extends LinearLayout {

    private ImageView icon;
    private TextView title;
    private Button action;

    public RefreshEmptyView(Context context) {
        this(context, null);
    }

    public RefreshEmptyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshEmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RefreshEmptyView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int style) {
        super(context, attrs, defStyleAttr, style);
        initView(context);
    }


    private void initView(Context context) {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        LayoutInflater.from(context).inflate(R.layout.layout_refresh_empty_view, this, true);
        icon = findViewById(R.id.empty_icon);
        title = findViewById(R.id.empty_text);
        action = findViewById(R.id.empty_action);
    }

    public void setEmptyIcon(@DrawableRes int iconRes) {
        icon.setImageResource(iconRes);
    }

    public void setTitle(String text) {
        if (TextUtils.isEmpty(text)) {
            title.setVisibility(GONE);
        } else {
            title.setText(text);
            title.setVisibility(VISIBLE);
        }
    }

    public void setButton(String text, View.OnClickListener listener) {
        if (TextUtils.isEmpty(text)) {
            action.setVisibility(GONE);
        } else {
            action.setText(text);
            action.setVisibility(VISIBLE);
            action.setOnClickListener(listener);
        }

    }
}
