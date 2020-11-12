package com.frewen.android.demo.logic.samples;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.frewen.android.demo.R;

/**
 * 我们使用代码来实现帧动画的实现
 */
public class FrameAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_animation);


        testAnimationDrawable();

        testSurfaceView();
    }

    /**
     *  屏幕的显示机制和帧动画类似，也是一帧一帧的连环画，只不过刷新频率很高，感觉像连续的。
     *  为了显示一帧，需要经历计算和渲染两个过程，CPU 先计算出这一帧的图像数据并写入内存，
     *  然后调用 OpenGL 命令将内存中数据渲染成图像存放在 GPU Buffer 中，显示设备每隔一定时间从 Buffer 中获取图像并显示。
     *
     *  上述过程中的计算，对于View来说，就好比在主线程遍历 View树以决定视图画多大（measure），画在哪（layout），
     *  画些啥（draw），计算结果存放在内存中，SurfaceFlinger 会调用 OpenGL 命令将内存中的数据渲染成图像存放在 GPU Buffer 中。
     *  每隔16.6ms，显示器从 Buffer 中取出帧并显示。
     *  所以自定义 View 可以通过重载onMeasure()、onLayout()、onDraw()来定义帧内容，但不能定义帧刷新频率。
     *
     *  SurfaceView可以突破这个限制。而且它可以将计算帧数据放到独立的线程中进行。
     *  下面是自定义SurfaceView的模版代码：
     *
     */
    private void testSurfaceView() {

    }

    /**
     * Android 提供了AnimationDrawable用于实现帧动画。
     * 在动画开始之前，所有帧的图片都被解析并占用内存，一旦动画较复杂帧数较多，在低配置手机上容易发生 OOM。
     * 即使不发生 OOM，也会对内存造成不小的压力。下面代码展示了一个帧数为4的帧动画：
     */
    private void testAnimationDrawable() {
        // 原生帧动画
        AnimationDrawable drawable = new AnimationDrawable();
        int frameDuration = 200;
        drawable.addFrame(getDrawable(R.drawable.ic_bitmap), frameDuration);
        drawable.addFrame(getDrawable(R.drawable.ic_bitmap), frameDuration);
        drawable.addFrame(getDrawable(R.drawable.ic_bitmap), frameDuration);
        drawable.addFrame(getDrawable(R.drawable.ic_bitmap), frameDuration);
        drawable.setOneShot(true);

        ImageView ivFrameAnim = ((ImageView) findViewById(R.id.frame_anim));
        // 给这个ImageView设置AnimationDrawable帧动画Drawable
        ivFrameAnim.setImageDrawable(drawable);
        drawable.start();
    }
}