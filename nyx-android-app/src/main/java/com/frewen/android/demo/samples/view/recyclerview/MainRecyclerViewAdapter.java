package com.frewen.android.demo.samples.view.recyclerview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.frewen.android.demo.samples.view.RecyclerViewDemoActivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @filename: MainRecyclerViewAdapter
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/7/29 23:09
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MainRecyclerViewHolder> {

    public MainRecyclerViewAdapter(Context context) {
        
    }

    /**
     * 进行Inflater的ItemView的。然后将View传入MainRecyclerViewHolder构造函数
     * 返回MainRecyclerViewHolder对象
     *
     * @param parent
     * @param viewType
     */
    @NonNull
    @Override
    public MainRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    /**
     * 通过Holder对象。以及Item的position的。
     * 然后通过ViewHolder对象获取对应的Item的子View
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MainRecyclerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MainRecyclerViewHolder extends RecyclerView.ViewHolder {

        public MainRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}


