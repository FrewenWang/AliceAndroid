package com.frewen.android.demo.business.samples.rxjava2;

import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

import com.frewen.android.demo.R;
import com.frewen.android.demo.business.adapter.FragmentPagerViewAdapter;
import com.frewen.aura.framework.ui.BaseButterKnifeActivity;
import com.frewen.aura.toolkits.display.DisplayHelper;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import butterknife.BindView;

/**
 * RxJava2Activity
 *
 * 与RxJava 1.x的差异
 *
 * 1、Nulls
 * 这是一个很大的变化，熟悉RxJava 1.x的童鞋一定都知道，
 * 1.x 是允许我们在发射事件的时候传入 null 值的，但现在我们的 2.x 不支持了，不信你试试？
 * 大大的 NullPointerException 教你做人。
 * 这意味着 Observable 不再发射任何值，而是正常结束或者抛出空指针。
 *
 * 2、Flowable
 * 在 RxJava 1.x 中关于介绍 backpressure 部分有一个小小的遗憾，那就是没有用一个单独的类，而是使用 Observable 。
 * 而在 2.x 中 Observable 不支持背压了，将用一个全新的 Flowable 来支持背压。
 *
 * 或许对于背压，有些小伙伴们还不是特别理解，这里简单说一下。
 * 大概就是指在异步场景中，被观察者发送事件的速度远快于观察者的处理速度的情况下，一种告诉上游的被观察者降低发送速度的策略。
 * 感兴趣的小伙伴可以模拟这种情况，在差距太大的时候，我们的内存会猛增，直到OOM。
 * 而我们的 Flowable 一定意义上可以解决这样的问题，但其实并不能完全解决，这个后面可能会提到。
 *
 * 3、Single/Completable/Maybe
 * 其实这三者都差不多，Single 顾名思义，只能发送一个事件，和Observable接受可变参数完全不同。
 * 而Completable侧重于观察结果，而Maybe是上面两种的结合体。
 * 也就是说，当你只想要某个事件的结果（true or false）的时候，你可以使用这种观察者模式。
 *
 * 4、线程调度相关
 * 这一块基本没什么改动，但细心的小伙伴一定会发现，
 * RxJava 2.x 中已经没有了Schedulers.immediate() 这个线程环境，还有Schedulers.test()。
 *
 * 5、Function相关
 * 熟悉 1.x 的小伙伴一定都知道，我们在1.x 中是有Func1，Func2.....FuncN的，但2.x 中将它们移除，
 * 而采用Function 替换了Func1，采用BiFunction 替换了Func 2..N。并且，它们都增加了throws Exception，
 * 也就是说，妈妈再也不用担心我们做某些操作还需要try-catch了。
 *
 * 6、其他操作符相关
 * 如Func1...N 的变化，现在同样用Consumer和BiConsumer对Action1 和Action2进行了替换。
 * 后面的Action都被替换了，只保留了ActionN。
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
 * Behavior subclass com.frewen.android.demo.logic.samples.view.behavior.FloatingActionBtnBehavior
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
