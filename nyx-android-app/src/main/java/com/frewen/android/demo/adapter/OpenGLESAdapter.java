package com.frewen.android.demo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.frewen.android.demo.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @filename: OpenGLESAdapter
 * @author: Frewen.Wong
 * @time: 2021/8/1 15:06
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
public class OpenGLESAdapter extends RecyclerView.Adapter<OpenGLESAdapter.MyViewHolder> implements View.OnClickListener {
    private List<String> mTitles;
    private Context mContext;
    private int mSelectIndex = 0;
    private OnItemClickListener mOnItemClickListener = null;

    public OpenGLESAdapter(Context context, List<String> titles) {
        mContext = context;
        mTitles = titles;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_action_bar_menu_list, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        holder.mTitle.setText(mTitles.get(position));
        if (position == mSelectIndex) {
            holder.mRadioButton.setChecked(true);
            holder.mTitle.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        } else {
            holder.mRadioButton.setChecked(false);
            holder.mTitle.setText(mTitles.get(position));
            holder.mTitle.setTextColor(Color.GRAY);
        }
        holder.itemView.setTag(position);
    }

    public void setSelectIndex(int index) {
        mSelectIndex = index;
    }

    public int getSelectIndex() {
        return mSelectIndex;
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        RadioButton mRadioButton;
        TextView mTitle;

        public MyViewHolder(View itemView) {
            super(itemView);
            mRadioButton = itemView.findViewById(R.id.radio_btn);
            mTitle = itemView.findViewById(R.id.item_title);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}


