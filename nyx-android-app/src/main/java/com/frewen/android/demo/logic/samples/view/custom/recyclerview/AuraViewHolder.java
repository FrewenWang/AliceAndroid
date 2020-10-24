package com.frewen.android.demo.logic.samples.view.custom.recyclerview;

import android.view.View;

/**
 * @filename: AuraViewHolder
 * @introduction: RecyclerView的View持有器。主要用来参与View缓存的相关逻辑
 * @author: Frewen.Wong
 * @time: 2020/9/28 14:14
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public abstract class AuraViewHolder {

    public View itemView;
    int mItemViewType = -1;

    public AuraViewHolder(View view) {
        if (view == null) {
            throw new IllegalArgumentException("AuraViewHolder itemView may not be null");
        }
        this.itemView = view;
    }

    public int getItemViewType() {
        return this.mItemViewType;
    }

    public void setItemViewType(int itemViewType) {
        this.mItemViewType = itemViewType;
    }

    /**
     * TODO 这个方法不太理解 已经被绑定到
     * @return
     */
    public boolean isAttachedToTransitionOverlay() {
        return false;
    }

    /**
     * TODO ViewHolder的重置操作
     */
    public void resetInternal() {

    }
}
