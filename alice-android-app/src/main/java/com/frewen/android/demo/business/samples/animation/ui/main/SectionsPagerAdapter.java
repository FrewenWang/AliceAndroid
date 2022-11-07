package com.frewen.android.demo.business.samples.animation.ui.main;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "SectionsPagerAdapter";
    private static final String[] TAB_TITLES = new String[]{"帧动画", "补间动画", "属性动画"};
    private static final Fragment[] TAB_FRAGMENT_PAGES = new Fragment[]{
            FrameAnimationFragment.newInstance(1),
            PlaceholderViewFragment.newInstance(2),
            PropertyAnimationViewFragment.newInstance(3)};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        Log.d(TAG, "FMsg:getItem() called with: position = [" + position + "]");
        return TAB_FRAGMENT_PAGES[position];
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return TAB_TITLES.length;
    }
}