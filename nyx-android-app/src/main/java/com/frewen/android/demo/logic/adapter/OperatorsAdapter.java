package com.frewen.android.demo.logic.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.frewen.android.demo.R;
import com.frewen.android.demo.logic.model.ContentData;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * Author: nanchen
 * Email: liushilin520@foxmail.com
 * Date: 2017-06-20  15:03
 */

public abstract class OperatorsAdapter extends BaseQuickAdapter<ContentData, BaseViewHolder> {

    public OperatorsAdapter(@Nullable List<ContentData> data) {
        super(R.layout.item_recycler_view_demo, data);
    }

    @Override
    protected void convert(final BaseViewHolder holder, ContentData item) {
        if (item != null) {
            holder.setText(R.id.tvTitle, item.getTitle())
                    .setText(R.id.tvContent, item.getContent());

            holder.itemView.setOnClickListener(v -> onItemClick(holder.getAdapterPosition()));
        }
    }

    public abstract void onItemClick(int position);
}
