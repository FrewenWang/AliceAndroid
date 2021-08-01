package com.frewen.android.demo.logic.samples.opengles.surface;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

import com.frewen.android.demo.logic.samples.opengles.render.MyNativeRender;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @filename: OpenGLESSurfaceView
 * @author: Frewen.Wong
 * @time: 2021/7/31 23:10
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
public class OpenGLESSurfaceView extends GLSurfaceView {
    private static final String TAG = "OpenGLESSurfaceView";

    public static final int IMAGE_FORMAT_RGBA = 0x01;
    public static final int IMAGE_FORMAT_NV21 = 0x02;
    public static final int IMAGE_FORMAT_NV12 = 0x03;
    public static final int IMAGE_FORMAT_I420 = 0x04;

    private MyGLRender mGLRender;
    private MyNativeRender mNativeRender;

    public OpenGLESSurfaceView(Context context) {
        this(context, null);
    }

    public OpenGLESSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO API 含义？
        this.setEGLContextClientVersion(3);
        mNativeRender = new MyNativeRender();

        mGLRender = new MyGLRender(mNativeRender);
        setRenderer(mGLRender);
        setRenderMode(RENDERMODE_CONTINUOUSLY);
    }

    public static class MyGLRender implements Renderer {

        private MyNativeRender mNativeRender;

        MyGLRender(MyNativeRender myNativeRender) {
            mNativeRender = myNativeRender;
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            Log.d(TAG, "FMsg:onSurfaceCreated  with: gl: " + gl + ", config: " + config + "");
            mNativeRender.native_OnSurfaceCreated();
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            Log.d(TAG, "FMsg:onSurfaceChanged  with: gl: " + gl + ", width: " + width + ", height: " + height + "");
            mNativeRender.native_OnSurfaceChanged(width, height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            Log.d(TAG, "FMsg:onDrawFrame  with: gl: " + gl + "");
            mNativeRender.native_OnDrawFrame();
        }
    }
}
