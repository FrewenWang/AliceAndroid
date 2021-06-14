package com.frewen.android.demo.logic.ui

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewTreeObserver
import android.view.Window
import android.view.WindowManager
import android.view.animation.*
import android.widget.ImageView
import butterknife.BindView
import com.frewen.android.demo.R
import com.frewen.android.demo.logic.main.MainPageActivity
import com.frewen.android.demo.logic.samples.AnimationDrawableActivity
import com.frewen.android.demo.logic.samples.androidapi.ActivityDemoActivity
import com.frewen.android.demo.logic.samples.animation.AnimationDemoActivity
import com.frewen.android.demo.logic.samples.jni.HelloJNIActivity
import com.frewen.android.demo.logic.samples.media.camera2.Camera2Activity
import com.frewen.android.demo.logic.samples.network.aura.AuraNetWorkDemoActivity
import com.frewen.android.demo.logic.samples.opencv.OpenCVActivity
import com.frewen.android.demo.logic.samples.view.ViewDemoActivity
import com.frewen.android.demo.performance.LaunchTimeRecord.endRecord
import com.frewen.android.demo.performance.PerformanceActivity
import com.frewen.aura.framework.ui.BaseButterKnifeActivity
import com.frewen.aura.toolkits.common.ResourcesUtils
import com.frewen.aura.toolkits.common.SharedPrefUtils
import com.permissionx.guolindev.PermissionX
import com.permissionx.guolindev.request.ForwardScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * LOGO转圈的动画模仿这个Demo：
 * https://github.com/DigitTeaser/FilmsPeek
 *
 * @author Frewen.Wong
 */
class SplashActivity : BaseButterKnifeActivity() {
    private val TAG = "SplashActivity"
    
    @JvmField
    @BindView(R.id.ivSplashPicture)
    var splashBg: ImageView? = null
    
    @JvmField
    @BindView(R.id.ivSlogan)
    var ivSlogan: ImageView? = null
    private val splashDuration = 3 * 1000L
    
    private val job by lazy { Job() }
    
    companion object {
        /**
         * 是否首次进入APP应用
         */
        var isFirstEntryApp: Boolean
            get() = SharedPrefUtils.getBoolean("is_first_entry_app", true)
            set(value) = SharedPrefUtils.putBoolean("is_first_entry_app", value)
    }
    
    
    private fun startHomeActivity() {
        // When animation set ended, intent to the MainActivity.
        val intent = Intent(this@SplashActivity, HomeActivity::class.java)
        startActivity(intent)
    }
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        // java.lang.RuntimeException: Unable to start activity
        // ComponentInfo{com.frewen.android.demo/com.frewen.android.demo.logic.ui.SplashActivity}:
        // java.lang.IllegalStateException: Already attached
        // 一个onCreate里面不能两次调用super.onCreate。否则会报Already attached异常
        //super.onCreate(savedInstanceState);
        //去除标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        //去除状态栏
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        // Make sure this is before calling super.onCreate
        // 我们在清单文件里面设置的启动的透明主题，来减少白屏，但是在Activity启动之后，我们要设置回来
        // 我们可以通过代码来设置主题。我们通过设置透明主题,来减少白屏
        setTheme(R.style.NyxAppMaterialTheme)
        super.onCreate(savedInstanceState)
        
    }
    
    override fun initView(savedInstanceState: Bundle?) {
        /**
         * 进行APP权限申请
         */
        requestPermissions()
    }
    
    /**
     * 设置SetContentView的布局ID
     */
    override fun getContentViewId(): Int {
        return R.layout.activity_splash
    }
    
    /**
     * 我们使用这个框架：https://github.com/guolindev/PermissionX
     * 添加依赖：implementation 'com.permissionx.guolindev:permission-support:1.2.2'
     */
    private fun requestPermissions() {
        PermissionX.init(this)
            .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .onExplainRequestReason { scope, deniedList ->
                val message = ResourcesUtils.getString(R.string.request_permission_storage)
                scope.showRequestReasonDialog(
                    deniedList,
                    message,
                    ResourcesUtils.getString(R.string.ok),
                    ResourcesUtils.getString(R.string.cancel)
                )
            }
            .onForwardToSettings { scope: ForwardScope, deniedList: List<String?>? ->
                val message = ResourcesUtils.getString(R.string.request_permission_storage)
                scope.showForwardToSettingsDialog(
                    deniedList,
                    message,
                    ResourcesUtils.getString(R.string.settings),
                    ResourcesUtils.getString(R.string.cancel)
                )
            }
            .request { allGranted: Boolean, grantedList: List<String?>?, deniedList: List<String?>? -> requestReadPhoneStatePermission() }
    }
    
    private fun requestReadPhoneStatePermission() {
        PermissionX.init(this)
            .permissions(Manifest.permission.READ_PHONE_STATE)
            .onExplainRequestReason { scope, deniedList ->
                val message =
                    ResourcesUtils.getString(R.string.request_permission_access_phone_info)
                scope.showRequestReasonDialog(
                    deniedList,
                    message,
                    ResourcesUtils.getString(R.string.ok),
                    ResourcesUtils.getString(R.string.cancel)
                )
            }
            .onForwardToSettings { scope, deniedList ->
                val message =
                    ResourcesUtils.getString(R.string.request_permission_access_phone_info)
                scope.showForwardToSettingsDialog(
                    deniedList,
                    message,
                    ResourcesUtils.getString(R.string.settings),
                    ResourcesUtils.getString(R.string.cancel)
                )
            }
            .request { allGranted, grantedList, deniedList -> initAnimation() }
    }
    
    /**
     * 初始化Splash页面的动画
     */
    private fun initAnimation() {
        /// 初始化背景图缩放的动画
        initSplashBgScaleAnimation()
        /// 初始化Logo的展示动画
        initLogoAnimationSets()
        /// 使用协程延迟3秒，进入首页
        CoroutineScope(job).launch {
            delay(splashDuration)
            startHomeActivity()
            finish()
        }
        isFirstEntryApp = false
    }
    
    /**
     * 展示中心Logo的展示动画序列
     */
    private fun initLogoAnimationSets() {
        // Create an animation set for the lens ImageView.
        val animation = AnimationSet(false)
        // Set FillAfter to true, so the view won't reset after animations end.
        animation.fillAfter = true
        
        // Create an animation that make the lens icon move straight left.
        val straightLeftAnimation: Animation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, -0.5f,
            Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f
        )
        straightLeftAnimation.duration = 3000
        // 将LinearInterpolator设置为动画以统一其播放速度。
        straightLeftAnimation.interpolator = LinearInterpolator()
        
        // 一个简单的自定义Animation类，可以水平执行半圆运动。
        val firstSemicircleAnimation: Animation = SemicircleAnimation(0.0f, 1.5f)
        // Set the start offset right after the animation above ended.
        firstSemicircleAnimation.startOffset = 100
        firstSemicircleAnimation.duration = 1000
        firstSemicircleAnimation.interpolator = LinearInterpolator()
        
        // Second semicircle animation for the lens icon.
        val secondSemicircleAnimation: Animation = SemicircleAnimation(0.0f, (-1).toFloat())
        secondSemicircleAnimation.startOffset = 100
        secondSemicircleAnimation.duration = 1000
        secondSemicircleAnimation.interpolator = LinearInterpolator()
        // 缩放为原来两倍大小的动画
        val enlargeAnimation: Animation = ScaleAnimation(
            1.0f, 2.0f, 1.0f, 2.0f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        enlargeAnimation.duration = 1500
        
        // 从半透明到开始进行透明度变换动画
        val alphaAnimation: Animation = AlphaAnimation(0.5f, 1.0f)
        alphaAnimation.duration = 1500
        
        // 将上面四个动画加入到
        animation.addAnimation(straightLeftAnimation)
        animation.addAnimation(firstSemicircleAnimation)
        animation.addAnimation(secondSemicircleAnimation)
        animation.addAnimation(enlargeAnimation)
        animation.addAnimation(alphaAnimation)
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
            }
            
            override fun onAnimationRepeat(animation: Animation) {}
        })
        ivSlogan!!.startAnimation(animation)
    }
    
    private fun initSplashBgScaleAnimation() {
        /// 动画开始时应用的水平缩放系数
        /// 在动画结束时应用的水平缩放系数
        /// 在动画开始时应用的垂直缩放系数
        /// 在动画结束时应用垂直缩放系数
        val scaleAnimation = ScaleAnimation(
            1f,
            1.1f,
            1f,
            1.1f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        scaleAnimation.duration = splashDuration
        scaleAnimation.fillAfter = true
        
        /**
         * TODO 关于View的addOnPreDrawListener需要学习
         * 注意：viewTreeObserver.addOnDrawListener的是最低API要求是16这个需要注意
         */
        splashBg!!.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // 务必需要记得remove掉
                    splashBg!!.viewTreeObserver.removeOnPreDrawListener(this)
                    endRecord("Application")
                    return true
                }
            })
        
        //splashBg!!.doOnPreDraw { endRecord("Application") }
        
        splashBg!!.startAnimation(scaleAnimation)
        
    }
    
    /**
     * onWindowFocusChanged只是首帧的时间的回调。并不代表页面完全展现出来
     * 在进行打点记录启动的时间的时候，很多时候都是在onWindowFocusChanged里面进行调用。
     * 其实这是一个误区：onWindowFocusChanged只是首帧的时间
     */
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        Log.d(TAG, "onWindowFocusChanged() called with: hasFocus = $hasFocus")
    }
    
    /**
     * A simple custom Animation class that does semicircle motion horizontally.
     * 一个简单的自定义Animation类，可以水平执行半圆运动。
     *
     * Note:
     * 1. The semicircle motion only supports in horizontal direction and always go clockwise.
     * 2. The input parameters always represent as percentage (where 1.0 is 100%).
     */
    private inner class SemicircleAnimation(
        private val mFromXValue: Float,
        private val mToXValue: Float
    ) : Animation() {
        private var mRadius = 0f
        override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
            super.initialize(width, height, parentWidth, parentHeight)
            val fromXDelta = resolveSize(RELATIVE_TO_SELF, mFromXValue, width, parentWidth)
            val toXDelta = resolveSize(RELATIVE_TO_SELF, mToXValue, width, parentWidth)
            
            // Calculate the radius of the semicircle motion.
            // Note: Radius can be negative here.
            mRadius = (toXDelta - fromXDelta) / 2
        }
        
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            var dx = 0f
            var dy = 0f
            if (interpolatedTime == 0f) {
                t.matrix.setTranslate(dx, dy)
                return
            }
            val angleDeg = interpolatedTime * 180f % 360
            val angleRad = Math.toRadians(angleDeg.toDouble()).toFloat()
            dx = (mRadius * (1 - Math.cos(angleRad.toDouble()))).toFloat()
            dy = (-mRadius * Math.sin(angleRad.toDouble())).toFloat()
            t.matrix.setTranslate(dx, dy)
        }
        
    }
}