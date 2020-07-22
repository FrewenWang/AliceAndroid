package com.frewen.android.demo.mvp.contract;

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
    /**
     * View
     */
    interface View extends BaseView {
        /**
         * @param success
         */
        void finishLoadMore(boolean success);

        /**
         *
         */
        void finishLoadMoreWithNoMoreData();

        /**
         *
         */
        void resetNoMoreData();

        /**
         * @param success
         */
        void finishRefresh(boolean success);

    }

    /**
     *
     */
    interface Presenter {
        /**
         * getBannerImages
         * @return
         */
        List<Integer> getBannerImages();

        /**
         * onLoadMore
         */
        void onLoadMore();

        /**
         * onRefresh
         */
        void onRefresh();
    }
}
