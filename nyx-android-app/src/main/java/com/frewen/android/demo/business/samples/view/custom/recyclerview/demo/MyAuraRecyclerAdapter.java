package com.frewen.android.demo.business.samples.view.custom.recyclerview.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.frewen.android.demo.R;
import com.frewen.android.demo.business.samples.view.custom.recyclerview.AuraAdapter;
import com.frewen.android.demo.business.samples.view.custom.recyclerview.AuraViewHolder;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * @filename: MyAuraRecylerAdapter
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/9/28 17:10
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class MyAuraRecyclerAdapter extends AuraAdapter<MyAuraRecyclerAdapter.MyAuraViewHolder> {


    private final Context mContext;
    private final List<String> mDataList;

    public MyAuraRecyclerAdapter(Context context, List<String> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
    }

    /**
     * 滑动的过程中可能也可能会调用onCreateViewHolder
     * 比如说之前的从未出现的Item类型布局被显示出来，可以调用MyAuraViewHolder
     *
     * @param parent
     * @param viewType
     */
    @NonNull
    @Override
    public MyAuraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_textview, parent, false);
        return new MyAuraViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAuraViewHolder holder, int position) {
        holder.mTextView.setText(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemHeight(int index) {
        return 200;
    }

    /**
     * 自定义的ViewHolder的逻辑
     */
    public static final class MyAuraViewHolder extends AuraViewHolder {
        final TextView mTextView;

        public MyAuraViewHolder(View view) {
            super(view);

            mTextView = view.findViewById(R.id.id_item_list_title);
        }
    }
}
