package com.frewen.demo.library.ui.activity;

import android.os.Bundle;

import com.frewen.aura.framework.ui.BaseButterKnifeActivity;

import org.jetbrains.annotations.Nullable;

/**
 * @filename: BaseToolBarActivity
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/9/29 14:59
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public abstract class BaseToolBarActivity extends BaseButterKnifeActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getContentViewId() != 0) {
            initToolBar();
        }
    }

    /**
     * 初始化ToolBar
     */
    protected abstract void initToolBar();
}
