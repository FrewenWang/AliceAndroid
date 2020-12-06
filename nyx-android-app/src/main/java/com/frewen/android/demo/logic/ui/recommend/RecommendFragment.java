package com.frewen.android.demo.logic.ui.recommend;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.frewen.android.aura.annotations.FragmentDestination;
import com.frewen.android.demo.logic.model.RecommendTabEntity;
import com.frewen.android.demo.logic.ui.recommend.content.EyeRecommendFragment;
import com.frewen.demo.library.ui.fragment.BaseViewPager2Fragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;


/**
 * @filename: RecommendFragment
 * @introduction: 我们继承自BaseViewPager2的Fragment，顶部带标题栏的Fragment滑动的逻辑
 * @author: Frewen.Wong
 * @time: 2020/7/19 23:36
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
@FragmentDestination(pageUrl = "main/tabs/recommend")
public class RecommendFragment extends BaseViewPager2Fragment {

    @NotNull
    @Override
    public ArrayList<CustomTabEntity> getTabTitles() {
        ArrayList list = new ArrayList();
        list.add(new RecommendTabEntity("推荐", 0, 0));
        list.add(new RecommendTabEntity("关注", 0, 0));
        list.add(new RecommendTabEntity("视频", 0, 0));
        list.add(new RecommendTabEntity("文字", 0, 0));
        list.add(new RecommendTabEntity("图片", 0, 0));
        return list;
    }

    @NotNull
    @Override
    public Fragment[] getContentFragments() {
        Fragment[] fragments = new Fragment[5];
        fragments[0] = EyeRecommendFragment.Companion.newInstance();
        fragments[1] = EyeRecommendFragment.Companion.newInstance();
        fragments[2] = EyeRecommendFragment.Companion.newInstance();
        fragments[3] = EyeRecommendFragment.Companion.newInstance();
        fragments[4] = EyeRecommendFragment.Companion.newInstance();
        return fragments;
    }
}
