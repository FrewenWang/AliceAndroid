package com.frewen.android.demo.ui.recommend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frewen.android.aura.annotations.FragmentDestination;
import com.frewen.aura.framework.fragment.BaseFragment;


/**
 * @filename: RecommendFragment
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/7/19 23:36
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
@FragmentDestination(pageUrl = "main/tabs/recommend", asStarter = false)
public class RecommendFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return 0;
    }
}
