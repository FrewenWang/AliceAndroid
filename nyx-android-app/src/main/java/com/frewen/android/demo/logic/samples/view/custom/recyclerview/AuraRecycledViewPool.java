package com.frewen.android.demo.logic.samples.view.custom.recyclerview;

import android.util.SparseArray;

import java.util.ArrayList;

import androidx.annotation.Nullable;

import static com.frewen.android.demo.BuildConfig.DEBUG;

/**
 * @filename: AuraRecycledViewPool
 * @introduction: AuraRecycledViewPool的回收池
 *         RecycledViewPool使您可以在多个RecyclerView之间共享视图。
 *         如果要在RecyclerViews中回收视图，请创建RecycledViewPool实例并使用{@link AuraRecyclerView＃setRecycledViewPool（RecycledViewPool）}。
 * @author: Frewen.Wong
 * @time: 2020/9/28 15:16
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class AuraRecycledViewPool {
    /**
     * 最多的被别缓存的View
     */
    private static final int DEFAULT_MAX_SCRAP = 5;

    /**
     * 为什么要有两个集合？？？
     *
     * 默认的报废数据
     */
    static class ScrapData {
        final ArrayList<AuraViewHolder> mScrapHeap = new ArrayList<>();
        /// 最大的报废数据
        int mMaxScrap = DEFAULT_MAX_SCRAP;
        long mCreateRunningAverageNs = 0;
        long mBindRunningAverageNs = 0;
    }

    // 性能比如ArrayList要好很多
    // 为什么要有两个集合？？？
    // 根据Type类型可能会缓存不同的View
    SparseArray<ScrapData> mScrap = new SparseArray<>();

    @Nullable
    public AuraViewHolder getRecycledView(int viewType) {
        /// 我们根据View的Type类型的来获取一维数组的缓存的废料View集合
        final ScrapData scrapData = mScrap.get(viewType);
        if (scrapData != null && !scrapData.mScrapHeap.isEmpty()) {
            final ArrayList<AuraViewHolder> scrapHeap = scrapData.mScrapHeap;
            // 我们来遍历scrapHeap的所有缓存的View集合
            for (int i = scrapHeap.size() - 1; i >= 0; i--) {
                if (!scrapHeap.get(i).isAttachedToTransitionOverlay()) {
                    return scrapHeap.remove(i);
                }
            }
        }
        return null;
    }

    public void putRecycledView(AuraViewHolder scrap) {
        // 通过传入的scrap废料对象（也就是要缓存的ViewHolder对象），来获取viewType的View类型
        final int viewType = scrap.getItemViewType();

        final ArrayList<AuraViewHolder> scrapHeap = getScrapDataForType(viewType).mScrapHeap;

        if (mScrap.get(viewType).mMaxScrap <= scrapHeap.size()) {
            return;
        }
        if (DEBUG && scrapHeap.contains(scrap)) {
            throw new IllegalArgumentException("this scrap item already exists");
        }
        scrap.resetInternal();
        scrapHeap.add(scrap);
    }

    /**
     * 根据View的Type类型获取ScrapData回收View的数据对象
     *
     * @param viewType
     */
    private ScrapData getScrapDataForType(int viewType) {
        ScrapData scrapData = mScrap.get(viewType);
        // 首先，我们在二维数组中先查询一下
        if (scrapData == null) {
            // 如果没查到的话，我们来实例化
            scrapData = new ScrapData();
            mScrap.put(viewType, scrapData);
        }
        return scrapData;
    }
}
