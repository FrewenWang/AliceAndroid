package com.frewen.android.demo.jetpack.navigation.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MenuItem;

import com.frewen.android.demo.R;
import com.frewen.android.demo.logic.model.NavigationDestination;
import com.frewen.android.demo.constant.AppConfig;
import com.frewen.android.demo.jetpack.navigation.BottomNavigationBar;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

import java.util.List;

/**
 * @filename: AuraBottomNavigationView
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/6/16 23:20
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class AuraBottomNavigationView extends BottomNavigationView {

    private static int[] sIcons = new int[]{R.drawable.ic_main_home, R.drawable.ic_main_recommend, R.drawable.ic_main_publish, R.drawable.ic_main_discovery, R.drawable.ic_main_profile};
    private BottomNavigationBar config;

    public AuraBottomNavigationView(Context context) {
        this(context, null);
    }

    public AuraBottomNavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("RestrictedApi")
    public AuraBottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        config = AppConfig.getBottomBarConfig();

        int[][] state = new int[2][];
        state[0] = new int[]{android.R.attr.state_selected};
        state[1] = new int[]{};
        int[] colors = new int[]{Color.parseColor(config.getActiveColor()), Color.parseColor(config.getInActiveColor())};
        ColorStateList stateList = new ColorStateList(state, colors);
        setItemTextColor(stateList);
        setItemIconTintList(stateList);
        //LABEL_VISIBILITY_LABELED:设置按钮的文本为一直显示模式
        //LABEL_VISIBILITY_AUTO:当按钮个数小于三个时一直显示，或者当按钮个数大于3个且小于5个时，被选中的那个按钮文本才会显示
        //LABEL_VISIBILITY_SELECTED：只有被选中的那个按钮的文本才会显示
        //LABEL_VISIBILITY_UNLABELED:所有的按钮文本都不显示
        setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
        List<BottomNavigationBar.TabsBean> tabs = config.getTabs();
        for (BottomNavigationBar.TabsBean tab : tabs) {
            if (!tab.isEnable()) {
                continue;
            }
            int itemId = getItemId(tab.getPageUrl());
            if (itemId < 0) {
                continue;
            }
            MenuItem menuItem = getMenu().add(0, itemId, tab.getIndex(), tab.getTitle());
            menuItem.setIcon(sIcons[tab.getIndex()]);
        }

        //此处给按钮icon设置大小
        int index = 0;
        for (BottomNavigationBar.TabsBean tab : config.getTabs()) {
            if (!tab.isEnable()) {
                continue;
            }

            int itemId = getItemId(tab.getPageUrl());
            if (itemId < 0) {
                continue;
            }

            int iconSize = dp2Px(tab.getSize());
            BottomNavigationMenuView menuView = (BottomNavigationMenuView) getChildAt(0);
            BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(index);
            itemView.setIconSize(iconSize);
            if (TextUtils.isEmpty(tab.getTitle())) {
                int tintColor = TextUtils.isEmpty(tab.getTintColor()) ? Color.parseColor("#ff678f") : Color.parseColor(tab.getTintColor());
                itemView.setIconTintList(ColorStateList.valueOf(tintColor));
                //禁止掉点按时 上下浮动的效果
                itemView.setShifting(false);

                /**
                 * 如果想要禁止掉所有按钮的点击浮动效果。
                 * 那么还需要给选中和未选中的按钮配置一样大小的字号。
                 *
                 *  在MainActivity布局的AppBottomBar标签增加如下配置，
                 *  @style/active，@style/inActive 在style.xml中
                 *  app:itemTextAppearanceActive="@style/active"
                 *  app:itemTextAppearanceInactive="@style/inActive"
                 */
            }
            index++;
        }

        //底部导航栏默认选中项
        if (config.getSelectTab() != 0) {
            BottomNavigationBar.TabsBean selectTab = config.getTabs().get(config.getSelectTab());
            if (selectTab.isEnable()) {
                int itemId = getItemId(selectTab.getPageUrl());
                //这里需要延迟一下 再定位到默认选中的tab
                //因为 咱们需要等待内容区域,也就NavGraphBuilder解析数据并初始化完成，
                //否则会出现 底部按钮切换过去了，但内容区域还没切换过去
                post(() -> setSelectedItemId(itemId));
            }
        }
    }

    private int dp2Px(int dpValue) {
        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        return (int) (metrics.density * dpValue + 0.5f);
    }

    private int getItemId(String pageUrl) {
        NavigationDestination destination = AppConfig.getNavigationConfig().get(pageUrl);
        if (destination == null)
            return -1;
        return destination.getId();
    }
}
