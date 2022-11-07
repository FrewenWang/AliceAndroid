package com.frewen.android.demo.business.samples.window;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.frewen.android.demo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

/**
 * 学习Window的基础知识
 */
public class WindowDemoActivity extends AppCompatActivity implements View.OnTouchListener {
    private static final String TAG = "WindowDemoActivity";
    private Button mCreateWindowButton;
    private Button mFloatingButton;
    private WindowManager.LayoutParams mLayoutParams;
    private WindowManager mWindowManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_demo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mCreateWindowButton = (Button) findViewById(R.id.buttonWindow);
        // WindowManager是系统服务，可以通过getSystemService来进行获取
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        requestOverlayPermission();
    }

    /**
     * 动态申请
     */
    private void requestOverlayPermission() {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            return;
        }
        Intent myIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        myIntent.setData(Uri.parse("package:" + getPackageName()));
        startActivityForResult(myIntent, 1000);
    }


    /**
     * Caused by: android.view.WindowManager$BadTokenException: Unable to add window android.view.ViewRootImpl$W@f8a7a2a
     * -- permission denied for window type 2010
     * 这个代码创建附着在Window上面的Button.结果出现了异常。
     * 尝试解决：
     * https://stackoverflow.com/questions/46208897/android-permission-denied-for-window-type-2038-using-type-application-overlay
     * https://www.jianshu.com/p/529755edc3c5
     * https://www.jianshu.com/p/529755edc3c5
     * 首先，在Android O(Android 8) 以上，google严格显示层叠Window的权限。导致我们需要主动付给权限{@link #requestOverlayPermission}
     *
     * @param view
     */
    public void createWindow(View view) {
        /// 将一个Button添加到屏幕坐标为（500,800）的位置上。
        // WindowManager.LayoutParams中的flags和type这两个参数比较重要，下面对其进行说明。
        if (view == mCreateWindowButton) {
            mFloatingButton = new Button(this);
            mFloatingButton.setText("WindowButton");
            mLayoutParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, 0, 0,
                    PixelFormat.TRANSPARENT);
            // Flags参数表示Window的属性，它有很多选项，通过这些选项可以控制Window的显示特性，
            // 这里主要介绍几个比较常用的选项，剩下的请查看官方文档。
            // FLAG_NOT_FOCUSABLE 表示Window不需要获取焦点，也不需要接收各种输入事件，
            // 此标记会同时启用FLAG_NOT_TOUCH_MODAL，最终事件会直接传递给下层的具有焦点的Window。

            // FLAG_NOT_TOUCH_MODAL  在此模式下，系统会将当前Window区域以外的单击事件传递给底层的Window，
            // 当前Window区域以内的单击事件则自己处理。这个标记很重要，一般来说都需要开启此标记，否则其他Window将无法收到单击事件。

            // FLAG_SHOW_WHEN_LOCKED  开启此模式可以让Window显示在锁屏的界面上。
            mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;

            // Type参数表示Window的类型，
            // Window有三种类型，分别是应用Window、子Window和系统Window。
            // 应用类Window：对应着一个Activity。
            // 子Window：不能单独存在，它需要附属在特定的父Window之中，比如常见的一些Dialog就是一个子Window。
            // 系统Window：是需要声明权限在能创建的Window，比如Toast和系统状态栏这些都是系统Window。

            // Window是分层的，每个Window都有对应的z-ordered，层级大的会覆盖在层级小的Window的上面，这和HTML中的z-index的概念是完全一致的。
            // 在三类Window中，
            // 应用Window的层级范围是1～99，
            // 子Window的层级范围是1000～1999，
            // 系统Window的层级范围是2000～2999，
            // 这些层级范围对应着WindowManager.LayoutParams的type参数。

            // 很显然系统Window的层级是最大的，而且系统层级有很多值，
            // 一般我们可以选用TYPE_SYSTEM_OVERLAY或者TYPE_SYSTEM_ERROR，
            // 如果采用TYPE_SYSTEM_ERROR，只需要为type参数指定这个层级即可：
            // mLayoutParams.type =LayoutParams.TYPE_SYSTEM_ERROR；
            // 同时声明权限：<uses-permissionandroid:name="android.permission.SYSTEM_ALERT_WINDOW" />。
            // 当然这些方法，已经不行了。在最新的Android8上面，我们还需要动态申请权限。

            // mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mLayoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
            }
            //
            mLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
            // X position for this window.  With the default gravity it is ignored.
            mLayoutParams.x = 500;
            mLayoutParams.y = 800;
            mFloatingButton.setOnTouchListener(this);
            mWindowManager.addView(mFloatingButton, mLayoutParams);
        }
    }


    public void removeButtonWindow(View view) {
        try {
            mWindowManager.removeView(mFloatingButton);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        //getRawX()是表示相对于屏幕左上角的x坐标值(注意:这个屏幕左上角是手机屏幕左上角,不管activity是否有titleBar或是否全屏幕)
        // getRawY()一样的道理
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        Log.d(TAG, "FMsg:onTouch() called with: rawX = [" + rawX + "], rawY = [" + rawY + "]");

        // getX()是表示Widget相对于自身左上角的X坐标; getY()是表示Widget相对于自身左上角的Y坐标,
        int x = (int) event.getX();
        int y = (int) event.getY();
        Log.d(TAG, "FMsg:onTouch() called with: getX = [" + x + "], getY = [" + y + "]");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                break;
            }
            case MotionEvent.ACTION_MOVE: {

                mLayoutParams.x = rawX;
                mLayoutParams.y = rawY;
                mWindowManager.updateViewLayout(mFloatingButton, mLayoutParams);
            }
            break;
            case MotionEvent.ACTION_UP:
            default:
                break;
        }
        return false;
    }

    /**
     * addView窗体泄漏问题：android.view.WindowLeaked
     * android.view.WindowLeaked一般会发生在Activity与Dialog的显示。
     * （1）dialog、PopupWindow窗体
     *
     * 原因：我们知道Android的每一个Activity都有个WindowManager窗体管理器，
     * 同样，构建在某个Activity之上的dialog、PopupWindow也有相应的WindowManager窗体管理器。
     * 因为dialog、PopupWindown不能脱离Activity而单独存在着，
     * 所以当某个Dialog或者某个PopupWindow正在显示的时候我们去finish()了承载该Dialog(或PopupWindow)的Activity时，
     * 就会抛Window Leaked异常了，因为这个Dialog(或PopupWindow)的WindowManager已经没有谁可以附属了，所以它的窗体管理器已经泄漏了。
     *
     * 解决方法：关闭(finish)某个Activity前，要确保附属在上面的Dialog或PopupWindow已经关闭(dismiss)了。
     *
     * （2）activity窗体
     * 和dialog不同，WindowManager在AddView后，在activity的onDestroy中移除View时用removeViewImmediate​()，而不能用removeView()。
     *
     * 2、removeView报not attached to window manager问题
     * 原因：若当前view已经remove了，再次调用removeView会报以上错误。
     * 解决方法：removeView时需要先判断当前view是否已经移除过。
     *
     * TODO 这怎么判断WindowManager是否已经移除某个View
     */
    @Override
    protected void onDestroy() {
        try {
            mWindowManager.removeViewImmediate(mFloatingButton);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

}