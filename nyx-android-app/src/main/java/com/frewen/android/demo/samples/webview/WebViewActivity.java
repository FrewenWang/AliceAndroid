package com.frewen.android.demo.samples.webview;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.frewen.android.demo.R;
import com.frewen.aura.framework.ui.BaseActivity;

import androidx.appcompat.widget.Toolbar;

/**
 * @filename: WebViewActivity
 * @introduction: 这个Demo参考：https://github.com/youlookwhat/WebViewStudy
 *
 *         X5浏览器的接入指南：https://x5.tencent.com/docs/access.html
 * @author: Frewen.Wong
 * @time: 2019-05-15 08:18
 *         Copyright ©2019 Frewen.Wong. All Rights Reserved.
 */
public class WebViewActivity extends BaseActivity {

    private WebView mWebView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * 我们如果要使用Toolbar的时候。
     * 在Android 5.x时代，ActionBar已经被Toolbar所替代，
     * 相比于ActionBar独立于布局之外，一直固定出现在顶端，
     * Toolbar可以有更好的个性化定制，不仅可以出现在Layout
     *
     * 不同的层级上，还可以进行自由定制导航栏的布局。如果你想把 Toolbar 当成 ActionBar，
     * 依然沿用Actionbar的方法，可以使用：setSupportActionBar() 方法来实现（不推荐）。
     */
    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("WebView测试");
        toolbar.setSubtitle("点击左侧返回按钮退出");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 初始化WebView
     */
    private void initWebView() {
        mWebView = findViewById(R.id.web_view);
        // 获取WebView的设置对象
        WebSettings webSettings = mWebView.getSettings();
        // 网页内容的宽度是否可大于WebView控件的宽度
        webSettings.setLoadWithOverviewMode(false);
        // 保存表单数据。在Android O 以后Android系统会自动保存表单数据
        // 所以新版本Android系统这个方法已经无效
        webSettings.setSaveFormData(true);
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        // 启动应用缓存
        webSettings.setAppCacheEnabled(true);
        // 设置缓存模式
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // setDefaultZoom  api19被弃用
        // 设置此属性，可任意比例缩放。
        webSettings.setUseWideViewPort(true);
        // 设置WebView默认不缩放
        mWebView.setInitialScale(100);

        // 告诉WebView启用JavaScript执行。默认的是false。
        webSettings.setJavaScriptEnabled(true);
        //  页面加载好以后，再放开图片
        webSettings.setBlockNetworkImage(false);
        // 使用localStorage则必须打开
        webSettings.setDomStorageEnabled(true);
        // 排版适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

        // WebView是否新窗口打开(加了后可能打不开网页)
        // webSettings.setSupportMultipleWindows(true);
        // webview从5.0开始默认不允许混合模式,
        // https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        /** 设置字体默认缩放大小(改变网页字体大小,setTextSize  api14被弃用)*/
        webSettings.setTextZoom(100);
    }

    private void openWebPage() {
        if (null != mWebView) {
            mWebView.loadUrl("https://www.jianshu.com/");
        }
    }

}
