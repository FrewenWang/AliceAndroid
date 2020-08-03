package com.frewen.android.demo.samples.view;

import android.os.Bundle;

import com.frewen.android.demo.R;
import com.frewen.android.demo.fragments.CardImageFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * https://github.com/JaynmBo/MaterialDesign-master
 */
public class CollapsingToolbarLayoutActivity extends AppCompatActivity {

    /**
     * ViewPager
     */
    @BindView(R.id.viewPager)
    ViewPager viewPager;


    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 鼠标停留在R.layout.activity_collapsing_toolbar_layout布局上面，
        // 然后右键Generate生成ButterKnife注解
        setContentView(R.layout.activity_collapsing_toolbar_layout);
        ButterKnife.bind(this);


        initViewPager();
    }

    private void initViewPager() {
        fragments.add(CardImageFragment.newInstance());
        fragments.add(CardImageFragment.newInstance());
        fragments.add(CardImageFragment.newInstance());
        fragments.add(CardImageFragment.newInstance());
        fragments.add(CardImageFragment.newInstance());
    }
}