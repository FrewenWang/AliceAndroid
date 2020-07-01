package com.frewen.android.demo.ui;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.frewen.android.demo.HomeActivity;
import com.frewen.android.demo.R;
import com.frewen.aura.framework.ui.BaseButterKnifeActivity;
import com.frewen.aura.toolkits.common.ResourcesUtils;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.ExplainReasonCallback;
import com.permissionx.guolindev.callback.ForwardToSettingsCallback;
import com.permissionx.guolindev.callback.RequestCallback;
import com.permissionx.guolindev.request.ExplainScope;
import com.permissionx.guolindev.request.ForwardScope;

import java.util.List;

import butterknife.BindView;

/**
 * LOGO转圈的动画模仿这个Demo：
 * https://github.com/DigitTeaser/FilmsPeek
 *
 * @author Frewen.Wong
 */
public class SplashActivity extends BaseButterKnifeActivity {

    @BindView(R.id.ivSplashPicture)
    ImageView splashBg;
    @BindView(R.id.ivSlogan)
    ImageView ivSlogan;

    private long splashDuration = 3 * 1000L;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // java.lang.RuntimeException: Unable to start activity
        // ComponentInfo{com.frewen.android.demo/com.frewen.android.demo.ui.SplashActivity}:
        // java.lang.IllegalStateException: Already attached
        // 一个onCreate里面不能两次调用super.onCreate。否则会报Already attached异常
        //super.onCreate(savedInstanceState);
        //去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // Make sure this is before calling super.onCreate
        // 我们可以通过代码来设置主题。我们通过设置透明主题,来减少白屏
        //setTheme(R.style.LauncherTheme);
        super.onCreate(savedInstanceState);
        /**
         * 进行APP权限申请
         */
        requestPermissions();
    }

    /**
     * 设置SetContentView的布局ID
     */
    @Override
    protected int getContentViewId() {
        return R.layout.activity_splash;
    }

    /**
     * 我们使用这个框架：https://github.com/guolindev/PermissionX
     * 添加依赖：implementation 'com.permissionx.guolindev:permission-support:1.2.2'
     */
    private void requestPermissions() {
        PermissionX.init(this)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .onExplainRequestReason(new ExplainReasonCallback() {
                    /**
                     * 弹出权限身情况
                     * @param scope
                     * @param deniedList
                     */
                    @Override
                    public void onExplainReason(ExplainScope scope, List<String> deniedList) {
                        String message = ResourcesUtils.getString(R.string.request_permission_storage);
                        scope.showRequestReasonDialog(deniedList, message, ResourcesUtils.getString(R.string.ok), ResourcesUtils.getString(R.string.cancel));
                    }
                })
                .onForwardToSettings((scope, deniedList) -> {
                    String message = ResourcesUtils.getString(R.string.request_permission_storage);
                    scope.showForwardToSettingsDialog(deniedList, message, ResourcesUtils.getString(R.string.settings), ResourcesUtils.getString(R.string.cancel));
                })
                .request((allGranted, grantedList, deniedList) -> requestReadPhoneStatePermission());
    }

    private void requestReadPhoneStatePermission() {
        PermissionX.init(this)
                .permissions(Manifest.permission.READ_PHONE_STATE)
                .onExplainRequestReason(new ExplainReasonCallback() {
                    /**
                     * 弹出权限身情况
                     * @param scope
                     * @param deniedList
                     */
                    @Override
                    public void onExplainReason(ExplainScope scope, List<String> deniedList) {
                        String message = ResourcesUtils.getString(R.string.request_permission_access_phone_info);
                        scope.showRequestReasonDialog(deniedList, message, ResourcesUtils.getString(R.string.ok), ResourcesUtils.getString(R.string.cancel));
                    }
                })
                .onForwardToSettings(new ForwardToSettingsCallback() {
                    @Override
                    public void onForwardToSettings(ForwardScope scope, List<String> deniedList) {
                        String message = ResourcesUtils.getString(R.string.request_permission_access_phone_info);
                        scope.showForwardToSettingsDialog(deniedList, message, ResourcesUtils.getString(R.string.settings), ResourcesUtils.getString(R.string.cancel));
                    }
                })
                .request(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                        initAnimation();
                    }
                });
    }

    /**
     * 初始化Splash页面的动画
     */
    private void initAnimation() {
        /// 初始化背景图缩放的动画
        initSplashBgScaleAnimation();
        /// 初始化Logo的展示动画
        initLogoAnimationSets();
    }

    /**
     * 展示中心Logo的展示动画序列
     */
    private void initLogoAnimationSets() {
        // Create an animation set for the lens ImageView.
        AnimationSet animation = new AnimationSet(false);
        // Set FillAfter to true, so the view won't reset after animations end.
        animation.setFillAfter(true);

        // Create an animation that make the lens icon move straight left.
        Animation straightLeftAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, -0.5f,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0);
        straightLeftAnimation.setDuration(3000);
        // 将LinearInterpolator设置为动画以统一其播放速度。
        straightLeftAnimation.setInterpolator(new LinearInterpolator());

        // 一个简单的自定义Animation类，可以水平执行半圆运动。
        Animation firstSemicircleAnimation = new SemicircleAnimation(0, 1.5f);
        // Set the start offset right after the animation above ended.
        firstSemicircleAnimation.setStartOffset(100);
        firstSemicircleAnimation.setDuration(1000);
        firstSemicircleAnimation.setInterpolator(new LinearInterpolator());

        // Second semicircle animation for the lens icon.
        Animation secondSemicircleAnimation = new SemicircleAnimation(0, -1);
        secondSemicircleAnimation.setStartOffset(100);
        secondSemicircleAnimation.setDuration(1000);
        secondSemicircleAnimation.setInterpolator(new LinearInterpolator());
        // 缩放为原来两倍大小的动画
        Animation enlargeAnimation = new ScaleAnimation(1, 2, 1, 2,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        enlargeAnimation.setDuration(1500);

        // 从半透明到开始进行透明度变换动画
        Animation alphaAnimation = new AlphaAnimation(0.5f, 1.0f);
        alphaAnimation.setDuration(1500);

        // 将上面四个动画加入到
        animation.addAnimation(straightLeftAnimation);
        animation.addAnimation(firstSemicircleAnimation);
        animation.addAnimation(secondSemicircleAnimation);
        animation.addAnimation(enlargeAnimation);
        animation.addAnimation(alphaAnimation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // When animation set ended, intent to the MainActivity.
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                // It's IMPORTANT to finish the SplashActivity, so user won't reach it afterwards.
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        ivSlogan.startAnimation(animation);
    }

    private void initSplashBgScaleAnimation() {
        /// 动画开始时应用的水平缩放系数
        /// 在动画结束时应用的水平缩放系数
        /// 在动画开始时应用的垂直缩放系数
        /// 在动画结束时应用垂直缩放系数
        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 1.1f, 1f, 1.1f
                , Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(splashDuration);
        scaleAnimation.setFillAfter(true);
        splashBg.startAnimation(scaleAnimation);
    }

    /**
     * A simple custom Animation class that does semicircle motion horizontally.
     * <p>
     * Note:
     * 1. The semicircle motion only supports in horizontal direction and always go clockwise.
     * 2. The input parameters always represent as percentage (where 1.0 is 100%).
     */
    private class SemicircleAnimation extends Animation {

        private final float mFromXValue, mToXValue;
        private float mRadius;

        private SemicircleAnimation(float fromX, float toX) {
            mFromXValue = fromX;
            mToXValue = toX;
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);

            float fromXDelta = resolveSize(Animation.RELATIVE_TO_SELF, mFromXValue, width, parentWidth);
            float toXDelta = resolveSize(Animation.RELATIVE_TO_SELF, mToXValue, width, parentWidth);

            // Calculate the radius of the semicircle motion.
            // Note: Radius can be negative here.
            mRadius = (toXDelta - fromXDelta) / 2;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            float dx = 0;
            float dy = 0;

            if (interpolatedTime == 0) {
                t.getMatrix().setTranslate(dx, dy);
                return;
            }

            float angleDeg = (interpolatedTime * 180f) % 360;
            float angleRad = (float) Math.toRadians(angleDeg);

            dx = (float) (mRadius * (1 - Math.cos(angleRad)));
            dy = (float) (-mRadius * Math.sin(angleRad));

            t.getMatrix().setTranslate(dx, dy);
        }
    }
}

