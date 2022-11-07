package com.frewen.android.demo.business.samples.opengles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.frewen.android.demo.R;
import com.frewen.android.demo.business.adapter.OpenGLESAdapter;
import com.frewen.android.demo.business.samples.opengles.surface.OpenGLESSurfaceView;

import java.util.Arrays;

import static android.opengl.GLSurfaceView.RENDERMODE_CONTINUOUSLY;
import static android.opengl.GLSurfaceView.RENDERMODE_WHEN_DIRTY;
import static com.frewen.android.demo.business.samples.opengles.render.MyNativeRender.SAMPLE_TYPE;
import static com.frewen.android.demo.logic.samples.opengles.render.MyNativeRender.SAMPLE_TYPE_KEY_BEATING_HEART;
import static com.frewen.android.demo.logic.samples.opengles.render.MyNativeRender.SAMPLE_TYPE_TRIANGLE;

/**
 * 代码参考：
 * https://github.com/githubhaohao/NDK_OpenGLES_3_0
 * https://blog.csdn.net/sinat_22657459/category_8874366.html
 * https://blog.csdn.net/Kennethdroid/article/details/95622391
 */
public class OpenGLESDemoActivity extends AppCompatActivity implements ViewTreeObserver.OnGlobalLayoutListener, SensorEventListener {

    private int mSampleSelectedIndex = SAMPLE_TYPE_KEY_BEATING_HEART - SAMPLE_TYPE;

    private static final String[] SAMPLE_TITLES = {
            "DrawTriangle(画三角形)",
            "TextureMap",
            "YUV Rendering",
            "VAO&VBO",
            "FBO Offscreen Rendering",
            "EGL Background Rendering",
            "FBO Stretching",
            "Coordinate System",
            "Basic Lighting",
            "Transform Feedback",
            "Complex Lighting",
            "Depth Testing",
            "Instancing",
            "Stencil Testing",
            "Blending",
            "Particles",
            "SkyBox",
            "Assimp Load 3D Model",
            "PBO",
            "Beating Heart",
            "Cloud",
            "Time Tunnel",
            "Bezier Curve",
            "Big Eyes",
            "Face Slender",
            "Big Head",
            "Rotary Head",
            "Visualize Audio",
            "Scratch Card",
            "3D Avatar",
            "Shock Wave",
            "MRT",
            "FBO Blit",
            "Texture Buffer",
            "Uniform Buffer",
            "OpenGL RGB to YUV",
            "Multi-Thread Render",
            "Text Render",
            "Portrait stay color"
    };

    private OpenGLESSurfaceView.MyGLRender mGLRender = new OpenGLESSurfaceView.MyGLRender();
    private Toolbar mToolBar;
    private ViewGroup mRootView;
    private OpenGLESSurfaceView mGLSurfaceView;
    private AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opengles_demo);

        initToolBar();
        // 初始化传感器相关逻辑
        initSensor();
        // 获取RootView的getViewTreeObserver添加addOnGlobalLayoutListener
        // 在RootView发生改变的时候获得监听
        mRootView = (ViewGroup) findViewById(R.id.rootView);
        mRootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        mGLRender.init();
    }

    private void initSensor() {

    }

    @SuppressLint("ResourceAsColor")
    private void initToolBar() {
        mToolBar = findViewById(R.id.toolbar);
        mToolBar.setBackgroundColor(R.color.color_white_alpha10);
        mToolBar.inflateMenu(R.menu.menu_opengl_es);
        mToolBar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.action_opengl_es_check) {
                showOpenGLSampleDialog();
            }
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGLRender.unInit();
    }

    private void showOpenGLSampleDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View rootView = inflater.inflate(R.layout.layout_action_bar_menu_list, null);
        mDialog = builder.create();
        // Dialog的布局中的确认按钮
        Button confirmBtn = rootView.findViewById(R.id.confirm_btn);
        confirmBtn.setOnClickListener(v -> mDialog.cancel());

        /// 设置Dialog的列表布局
        final RecyclerView resolutionsListView = rootView.findViewById(R.id.resolution_list_view);
        final OpenGLESAdapter openGLESAdapter = new OpenGLESAdapter(this, Arrays.asList(SAMPLE_TITLES));
        openGLESAdapter.setSelectIndex(mSampleSelectedIndex);
        openGLESAdapter.setOnItemClickListener((view, position) -> {
            mRootView.removeView(mGLSurfaceView);
            addGLSurfaceView();
            int selectIndex = openGLESAdapter.getSelectIndex();
            openGLESAdapter.setSelectIndex(position);
            openGLESAdapter.notifyItemChanged(selectIndex);
            openGLESAdapter.notifyItemChanged(position);
            mSampleSelectedIndex = position;
            mGLSurfaceView.setRenderMode(RENDERMODE_WHEN_DIRTY);

            if (mRootView.getWidth() != mGLSurfaceView.getWidth()
                    || mRootView.getHeight() != mGLSurfaceView.getHeight()) {
                mGLSurfaceView.setAspectRatio(mRootView.getWidth(), mRootView.getHeight());
            }

            int samplePosition = position + SAMPLE_TYPE;
            switch (samplePosition) {
                case SAMPLE_TYPE_TRIANGLE:
                    mGLRender.setParamsInt(SAMPLE_TYPE, samplePosition, 0);
                    break;
            }
            mGLSurfaceView.requestRender();
            mDialog.cancel();
        });

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        resolutionsListView.setLayoutManager(manager);
        resolutionsListView.setAdapter(openGLESAdapter);
        resolutionsListView.scrollToPosition(mSampleSelectedIndex);
        mDialog.show();
        mDialog.getWindow().setContentView(rootView);
    }

    /**
     *
     */
    @Override
    public void onGlobalLayout() {
        mRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        addGLSurfaceView();
        // 设置GLSurfaceView的渲染模式
        mGLSurfaceView.setRenderMode(RENDERMODE_CONTINUOUSLY);
    }


    private void addGLSurfaceView() {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        // 在RootView发生改变的时候。我们给Root添加一个OpenGLESSurfaceView
        mGLSurfaceView = new OpenGLESSurfaceView(this, mGLRender);
        mRootView.addView(mGLSurfaceView, lp);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}