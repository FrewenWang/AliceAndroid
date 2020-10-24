package com.frewen.android.demo.logic.ui.fragments;

import android.os.Bundle;

import com.frewen.android.demo.R;
import com.frewen.android.demo.logic.adapter.CardImageAdapter;
import com.frewen.android.demo.logic.model.ArticleCommonBean;
import com.frewen.aura.framework.fragment.BaseButterKnifeFragment;
import com.frewen.aura.toolkits.common.FileUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;

/**
 * @filename: CardImageFragment
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/8/3 19:14
 * Copyright ©2019 Frewen.Wong. All Rights Reserved.
 */
public class CardImageFragment extends BaseButterKnifeFragment {
    /**
     * RecyclerView
     */
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private CardImageAdapter adapter;

    /**
     * @return
     */
    public static CardImageFragment newInstance() {
        Bundle args = new Bundle();
        CardImageFragment fragment = new CardImageFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_card_image;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String json = FileUtils.readFromAsset(getContext(), "message.json");
        // 针对Json生成List对象的泛型对象处理
        List<ArticleCommonBean> articleBeans = new Gson().fromJson(json, new TypeToken<List<ArticleCommonBean>>() {
        }.getType());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.adapter = new CardImageAdapter(getActivity(), articleBeans);
        recyclerView.setAdapter(adapter);
    }
}