package com.frewen.android.demo.samples.view.custom.recyclerview;

import java.util.ArrayList;

/**
 * @filename: AuraRecycler
 * @introduction: 回收者对象
 * @author: Frewen.Wong
 * @time: 2020/9/28 16:29
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public final class AuraRecycler {

    final ArrayList<AuraViewHolder> mAttachedScrap = new ArrayList<>();

    /**
     * RecyclerView的回收池
     */
    AuraRecycledViewPool mRecyclerPool;


    AuraRecycledViewPool getRecycledViewPool() {
        if (mRecyclerPool == null) {
            mRecyclerPool = new AuraRecycledViewPool();
        }
        return mRecyclerPool;
    }

    public void clear() {
        mAttachedScrap.clear();
        // recycleAndClearCachedViews();
    }
}
