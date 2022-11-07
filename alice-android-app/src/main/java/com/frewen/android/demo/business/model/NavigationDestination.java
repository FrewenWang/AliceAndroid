package com.frewen.android.demo.business.model;

/**
 * @filename: NavigationDestination
 * @introduction: 1、自动生层JavaBean,安装GsonFormat插件，设置快捷键option+s 进行自动生成
 * @author: Frewen.Wong
 * @time: 2020/6/16 07:25
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class NavigationDestination {
    /**
     * isFragment : true
     * asStarter : false
     * needLogin : false
     * pageUrl : main/tabs/dash
     * className : com.frewen.android.demo.logic.ui.dashboard.DashboardFragment
     * id : 1291879138
     */

    private boolean isFragment;
    private boolean asStarter;
    private boolean needLogin;
    private String pageUrl;
    private String className;
    private int id;

    public boolean isFragment() {
        return isFragment;
    }

    public void setIsFragment(boolean isFragment) {
        this.isFragment = isFragment;
    }

    public boolean asStarter() {
        return asStarter;
    }

    public void setAsStarter(boolean asStarter) {
        this.asStarter = asStarter;
    }

    public boolean isNeedLogin() {
        return needLogin;
    }

    public void setNeedLogin(boolean needLogin) {
        this.needLogin = needLogin;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
