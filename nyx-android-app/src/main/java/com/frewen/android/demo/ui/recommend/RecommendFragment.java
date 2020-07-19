package com.frewen.android.demo.ui.recommend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frewen.android.demo.app.MyApp;
import com.frewen.android.demo.mvp.contract.RecommendContract;
import com.frewen.aura.framework.di.component.DaggerActivityComponent;
import com.frewen.aura.framework.fragment.BaseMvpFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @filename: RecommendFragment
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/7/19 23:36
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class RecommendFragment extends BaseMvpFragment<RecommendContract.Presenter> implements RecommendContract.View {

    /**
     * 生成页面View返回
     *
     * @param inflater
     * @param container
     * @param b
     */
    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, boolean b) {
        return null;
    }

    /**
     * 依赖注入框架的使用
     */
    @Override
    protected void initInject() {
       
    }

    /**
     * View的初始化。这个方法的回调是在onViewCreated
     */
    @Override
    protected void initView() {

    }


    @Override
    public void finishLoadMore(boolean success) {

    }

    @Override
    public void finishLoadMoreWithNoMoreData() {

    }

    @Override
    public void resetNoMoreData() {

    }

    @Override
    public void finishRefresh(boolean success) {

    }


    @Override
    public void showLoading(@NotNull String loadingTips) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(int errorCode, @Nullable String errorMsg) {

    }
}
