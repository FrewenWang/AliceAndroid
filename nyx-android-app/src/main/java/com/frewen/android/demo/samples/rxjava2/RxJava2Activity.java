package com.frewen.android.demo.samples.rxjava2;

import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.frewen.android.demo.BuildConfig;
import com.frewen.android.demo.R;
import com.frewen.android.demo.adapter.FragmentPagerViewAdapter;
import com.frewen.android.demo.fragments.CardImageFragment;
import com.frewen.aura.framework.ui.BaseButterKnifeActivity;
import com.frewen.aura.toolkits.display.DisplayHelper;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;

/**
 * RxJava2Activity
 */
public class RxJava2Activity extends BaseButterKnifeActivity {
    @BindView(R.id.home_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.home_tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.home_appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.home_viewPager)
    ViewPager mViewPager;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void initView(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {// 4.4 以上版本
            // 设置 Toolbar 高度为 80dp，适配状态栏
            ViewGroup.LayoutParams layoutParams = mToolbarTitle.getLayoutParams();
            //layoutParams.height = ScreenUtil.dip2px(this,ScreenUtil.getStatusBarHeight(this));
            layoutParams.height = DisplayHelper.dip2px(this, 80);
            mToolbarTitle.setLayoutParams(layoutParams);
        }

        initToolBar(mToolbar, false, "");


        String[] titles = {"操作符", "示例"};
        fragments.add(OperatorsFragment.newInstance());
        fragments.add(RxUseCaseFragment.newInstance());
        mViewPager.setAdapter(new FragmentPagerViewAdapter(titles, fragments, getSupportFragmentManager(), fragments.size()));
        mTabLayout.setupWithViewPager(mViewPager);

    }

    private void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUpEnabled);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_rx_java2;
    }

    @Override
    protected boolean enableTranslucentStatusBar() {
        return true;
    }
}


/**
 * Behavior subclass com.frewen.android.demo.samples.view.behavior.FloatingActionBtnBehavior
 * // 进行performLaunchActivity启动Activity
 * at android.app.ActivityThread.performLaunchActivity(ActivityThread.java:3270)
 * // ActivityThread的进行Activity启动的相关逻辑
 * at android.app.ActivityThread.handleLaunchActivity(ActivityThread.java:3409)
 * at android.app.servertransaction.LaunchActivityItem.execute(LaunchActivityItem.java:83)
 * at android.app.servertransaction.TransactionExecutor.executeCallbacks(TransactionExecutor.java:135)
 * at android.app.servertransaction.TransactionExecutor.execute(TransactionExecutor.java:95)
 * // ActivityThread$H匿名内部类H的消息处理
 * at android.app.ActivityThread$H.handleMessage(ActivityThread.java:2016)
 * // Handler的信息分发
 * at android.os.Handler.dispatchMessage(Handler.java:107)
 * /// 主线程的Looper的调用
 * at android.os.Looper.loop(Looper.java:214)
 * at android.app.ActivityThread.main(ActivityThread.java:7356)
 * at java.lang.reflect.Method.invoke(Native Method)
 * at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:492)
 * at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:930)
 */
