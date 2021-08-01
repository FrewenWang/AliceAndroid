package com.frewen.android.demo.logic.samples.opengles.render;

/**
 * @filename: MyNativeRender
 * @author: Frewen.Wong
 * @time: 2021/7/31 23:14
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
public class MyNativeRender {

    public native void native_OnInit();

    public native void native_OnUnInit();

    public native void native_SetImageData(int format, int width, int height, byte[] bytes);

    public native void native_OnSurfaceCreated();

    public native void native_OnSurfaceChanged(int width, int height);

    public native void native_OnDrawFrame();

}
