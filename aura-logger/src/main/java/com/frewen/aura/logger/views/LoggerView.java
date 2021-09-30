package com.frewen.aura.logger.views;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.widget.ScrollView;
import android.widget.TextView;

import com.frewen.aura.logger.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * @filename: LoggerView
 * @author: Frewen.Wong
 * @time: 2021/8/10 23:22
 * @version: 1.0.0
 * @introduction: 用于打印大量Logg的自定义View
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
public class LoggerView extends ConstraintLayout {

    private TextView mLogText;
    private ScrollView mScrollView;
    private Handler mMainHandler;
    private boolean mIsFollow = true;

    public LoggerView(@NonNull Context context) {
        this(context, null);
    }

    public LoggerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoggerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.layout_logger_view, this);
        mLogText = this.findViewById(R.id.tv_layout_log);
        mScrollView = this.findViewById(R.id.view_layout_scroll);
        mMainHandler = new Handler(Looper.getMainLooper());

        this.findViewById(R.id.img_log_layout_follow).setSelected(this.mIsFollow);
        this.findViewById(R.id.img_log_layout_follow).setOnClickListener(v -> {
            v.setSelected(!v.isSelected());
            follow(v.isSelected());
        });

        this.findViewById(R.id.view_log_layout_clear).setOnClickListener(v -> {
            clear();
        });
    }


    private void clear() {
        mLogText.setText("");
    }

    private void follow(boolean follow) {
        mIsFollow = follow;
        if (mIsFollow) {
            postDelayed(() -> mScrollView.fullScroll(ScrollView.FOCUS_DOWN), 250);
        }
    }

}
