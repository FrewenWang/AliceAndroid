package com.frewen.android.demo.business.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.frewen.android.demo.R;
import com.frewen.android.demo.business.model.ArticleModel;

import java.util.List;

/**
 * @filename: CardImageAdapter
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/8/3 19:27
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class CardImageAdapter extends RecyclerView.Adapter<CardImageAdapter.CardImageViewHolder> {
    private Context mContext;
    private List<ArticleModel> articleCommonBeans;

    public CardImageAdapter(Context mContext, List<ArticleModel> articleBeans) {
        this.mContext = mContext;
        this.articleCommonBeans = articleBeans;
    }

    @NonNull
    @Override
    public CardImageAdapter.CardImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_card_image, parent, false);
        return new CardImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardImageAdapter.CardImageViewHolder holder, int position) {
        ArticleModel articleCommonBean = articleCommonBeans.get(position);

        holder.articleListTitle.setText(articleCommonBean.getTitle());
        holder.articleListSummary.setText(articleCommonBean.getAuthor());
        Glide.with(mContext).load(articleCommonBean.getApkLink()).fitCenter()
                .into(holder.articleListImg);

    }

    @Override
    public int getItemCount() {
        return articleCommonBeans.size();
    }

    public static class CardImageViewHolder extends RecyclerView.ViewHolder {

        public TextView articleListTitle;
        public TextView articleListSummary;
        public ImageView articleListImg;

        public CardImageViewHolder(@NonNull View itemView) {
            super(itemView);

            articleListTitle = itemView.findViewById(R.id.articleListTitle);
            articleListSummary = itemView.findViewById(R.id.articleListSummary);
            articleListImg = itemView.findViewById(R.id.articleListImg);

        }
    }
}
