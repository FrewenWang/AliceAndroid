package com.frewen.android.demo.samples.view.custom.recyclerview;


import android.view.ViewGroup;

import androidx.annotation.NonNull;

/**
 * @filename: AuraAdapter
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/9/28 14:13
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public abstract class AuraAdapter<VH extends AuraViewHolder> {

    /**
     * 进行创建ViewHolder的回调
     *
     * @param parent
     * @param viewType
     */
    @NonNull
    public abstract VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    /**
     * 进行绑定ViewHolder的回调
     *
     * @param holder
     * @param position
     */
    public abstract void onBindViewHolder(@NonNull VH holder, int position);

    /**
     * 获取Item的数量
     */
    public abstract int getItemCount();

    /**
     * 获取Item的类型
     *
     * @param position
     */
    public int getItemViewType(int position) {
        return 0;
    }

    public abstract int getItemHeight(int index);

}
