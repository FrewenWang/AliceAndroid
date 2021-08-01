//
// Created by Frewen.Wong on 2021/8/1.

#ifndef ANDROID_NYX_OPENGL_ES_CONTEXT_H
#define ANDROID_NYX_OPENGL_ES_CONTEXT_H
//
#include "stdint.h"
#include "samples/GLSampleBase.h"
#include <GLES3/gl3.h>

class NyxOpenGLRenderContext {

    NyxOpenGLRenderContext();

    ~NyxOpenGLRenderContext();

public:

    static NyxOpenGLRenderContext *instance();

    /**
     * 上层GLSurfaceView的create()
     */
    void onSurfaceCreated();

    void onSurfaceChanged(int width, int height);

    void onDrawFrame();

private:
    // 静态单例对象
    static NyxOpenGLRenderContext *mPtr_Context;
    GLSampleBase *mPtr_CurSample;
    GLSampleBase *mPtr_BeforeSample;
    int m_ScreenW;
    int m_ScreenH;
};

#endif //ANDROID_NYX_OPENGL_ES_CONTEXT_H

