package com.frewen.android.demo.player.exo;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @filename: PageListPlayerDetector
 * @author: Frewen.Wong
 * @time: 12/12/20 6:42 PM
 * @version: 1.0.0
 * @introduction: 进行RecyclerView列表自动列表视频自动播放 检测逻辑
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class PageListPlayerDetector {

    //收集一个个的能够进行视频播放的 对象，面向接口
    private List<IPlayTarget> mTargets = new ArrayList<>();

    private RecyclerView mRecyclerView;

    public PageListPlayerDetector(LifecycleOwner owner, RecyclerView recyclerView) {

    }

}
