package com.frewen.android.demo.business.samples.view;

import android.os.Bundle;
import android.util.Log;

import com.frewen.android.demo.R;
import com.frewen.android.demo.business.adapter.FragmentPagerViewAdapter;
import com.frewen.android.demo.business.samples.navigation.annotation.fragments.CardImageFragment;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * https://github.com/JaynmBo/MaterialDesign-master
 */
public class CollapsingToolbarLayoutActivity extends AppCompatActivity {
    private static final String TAG = "CollapsingToolbarLayout";
    private String mTitles[] = {"动态", "专栏", "沸点", "分享", "更多"};

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    /**
     * ViewPager
     */
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    /**
     * appBarLayout
     */
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;


    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 鼠标停留在R.layout.activity_collapsing_toolbar_layout布局上面，
        // 然后右键Generate生成ButterKnife注解
        setContentView(R.layout.activity_collapsing_toolbar_layout);
        ButterKnife.bind(this);


        initViewPager();

        initAppBarLayout();

        initTabLayout();
    }

    private void initTabLayout() {
        // 设置TabLayout和ViewPager绑定
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener
                (tabLayout));
        // 当我们进行TabLayout的切换的时候，进行View
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener
                (viewPager));
        //// 添加TabLayout的Tab
        for (int i = 0; i < mTitles.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(mTitles[i]));
        }
    }

    private void initAppBarLayout() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d(TAG, "FMsg:onOffsetChanged() called verticalOffset = [" + verticalOffset + "]");
            }
        });
    }

    private void initViewPager() {
        fragments.add(CardImageFragment.newInstance());
        fragments.add(CardImageFragment.newInstance());
        fragments.add(CardImageFragment.newInstance());
        fragments.add(CardImageFragment.newInstance());
        fragments.add(CardImageFragment.newInstance());


        viewPager.setAdapter(new FragmentPagerViewAdapter(mTitles, fragments, getSupportFragmentManager(), fragments.size()) {
            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return mTitles[position];
            }
        });
    }
}