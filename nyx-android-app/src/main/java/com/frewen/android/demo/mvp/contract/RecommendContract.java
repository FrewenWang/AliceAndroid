package com.frewen.android.demo.mvp.contract;

import com.frewen.aura.framework.mvp.presenter.BasePresenter;
import com.frewen.aura.framework.mvp.view.BaseView;

import java.util.List;

/**
 * @filename: RecommendContract
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/7/19 23:20
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public interface RecommendContract {

    interface View extends BaseView {

        void finishLoadMore(boolean success);

        void finishLoadMoreWithNoMoreData();

        void resetNoMoreData();

        void finishRefresh(boolean success);

    }

    interface Presenter extends BasePresenter<View> {

        List<Integer> getBannerImages();

        void onLoadMore();

        void onRefresh();
    }
}
