package com.frewen.android.demo.logic.samples.opengles.surface;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ScaleGestureDetector;

import com.frewen.android.demo.logic.samples.opengles.OpenGLESDemoActivity;
import com.frewen.android.demo.logic.samples.opengles.render.MyNativeRender;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static com.frewen.android.demo.logic.samples.opengles.render.MyNativeRender.SAMPLE_TYPE;

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
    private int mRatioWidth;
    private int mRatioHeight;

    public OpenGLESSurfaceView(OpenGLESDemoActivity context, MyGLRender glRender) {
        this(context, glRender, null);
    }

    public OpenGLESSurfaceView(Context context, MyGLRender glRender, AttributeSet attrs) {
        super(context, attrs);
        // TODO API 含义？
        this.setEGLContextClientVersion(2);
        mGLRender = glRender;
        /*If no setEGLConfigChooser method is called,
        then by default the view will choose an RGB_888 surface with a depth buffer depth of at least 16 bits.*/
        setEGLConfigChooser(8, 8, 8, 8, 16, 8);
        setRenderer(mGLRender);
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }

    public void setAspectRatio(int width, int height) {
        Log.d(TAG, "FMsg:setAspectRatio() called with: width: " + width + ", height: " + height + "");
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Size cannot be negative.");
        }
        mRatioWidth = width;
        mRatioHeight = height;
        requestLayout();
    }


    public static class MyGLRender implements Renderer {

        private MyNativeRender mNativeRender;
        private int mSampleType;

        public MyGLRender() {
            mNativeRender = new MyNativeRender();
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
            // Log.d(TAG, "FMsg:onDrawFrame  with: gl: " + gl + "");
            mNativeRender.native_OnDrawFrame();
        }

        public void init() {
            mNativeRender.native_OnInit();
        }

        public void unInit() {
            mNativeRender.native_OnUnInit();
        }

        public void setParamsInt(int paramType, int value0, int value1) {
            if (paramType == SAMPLE_TYPE) {
                mSampleType = value0;
            }
            mNativeRender.native_SetParamsInt(paramType, value0, value1);
        }
    }
}
