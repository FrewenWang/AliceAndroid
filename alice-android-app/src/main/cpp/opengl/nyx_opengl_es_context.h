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

    void setImageData(int format, int width, int height, uint8_t *pData);

    /**
     * 上层GLSurfaceView的create()
     */
    void onSurfaceCreated();

    void onSurfaceChanged(int width, int height);

    void onDrawFrame();

    void setParamsInt(int i, float d, float d1);

private:
    // 静态单例对象
    static NyxOpenGLRenderContext *mPtr_Context;
    /**
     * 前一个OpenGLSample的指针对象
     */
    GLSampleBase *mPtrCurSample;
    GLSampleBase *mPtrBeforeSample;
    int m_ScreenW;
    int m_ScreenH;
};

#endif //ANDROID_NYX_OPENGL_ES_CONTEXT_H

