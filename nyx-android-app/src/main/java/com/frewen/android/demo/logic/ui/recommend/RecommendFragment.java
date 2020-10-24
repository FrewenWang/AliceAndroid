package com.frewen.android.demo.logic.ui.recommend;

import com.frewen.android.aura.annotations.FragmentDestination;
import com.frewen.android.demo.R;
import com.frewen.aura.framework.fragment.BaseViewFragment;


/**
 * @filename: RecommendFragment
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/7/19 23:36
 * @version: 1.0.0
 * @copyright: Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
@FragmentDestination(pageUrl = "main/tabs/recommend", asStarter = false)
public class RecommendFragment extends BaseViewFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_recommend;
    }
}
