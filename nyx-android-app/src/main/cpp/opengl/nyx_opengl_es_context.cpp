//
// Created by Frewen.Wong on 2021/8/1.
//

#include "nyx_opengl_es_context.h"
#include "samples/BeatingHeartSample.h"
#include <base.h>

NyxOpenGLRenderContext *NyxOpenGLRenderContext::mPtr_Context = nullptr;

/**
 * NyxOpenGLRenderContext获取单例指针对象的方法
 * @return
 */
NyxOpenGLRenderContext *NyxOpenGLRenderContext::instance() {
    LOG_D("NyxOpenGLRenderContext::instance");
    if (mPtr_Context == nullptr) {
        mPtr_Context = new NyxOpenGLRenderContext();
    }
    return mPtr_Context;
}

NyxOpenGLRenderContext::NyxOpenGLRenderContext() {
    LOG_D("NyxOpenGLRenderContext::constructor");
    mPtr_CurSample = new BeatingHeartSample();
    mPtr_BeforeSample = nullptr;
}

/**
 * 析构函数的实现
 */
NyxOpenGLRenderContext::~NyxOpenGLRenderContext() {
    // 指针动态申请的空间进行释放
    if (mPtr_CurSample) {
        delete mPtr_CurSample;
        mPtr_CurSample = nullptr;
    }

    if (mPtr_BeforeSample) {
        delete mPtr_BeforeSample;
        mPtr_BeforeSample = nullptr;
    }
}

void NyxOpenGLRenderContext::onSurfaceCreated() {
    LOG_D("NyxOpenGLRenderContext::OnSurfaceCreated");
    glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
}

void NyxOpenGLRenderContext::onSurfaceChanged(int width, int height) {
    LOG_D("NyxOpenGLRenderContext::onSurfaceChanged [w,h] = [%d,%d]", width, height);
    glViewport(0, 0, width, height);
    m_ScreenW = width;
    m_ScreenH = height;
}

void NyxOpenGLRenderContext::onDrawFrame() {
    // LOG_D("NyxOpenGLRenderContext::onDrawFrame");
    glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);

    if (mPtr_BeforeSample) {
        mPtr_BeforeSample->destroy();
        delete mPtr_BeforeSample;
        mPtr_BeforeSample = nullptr;
    }

    if (mPtr_CurSample) {
        mPtr_CurSample->init();
        mPtr_CurSample->draw(m_ScreenW, m_ScreenH);
    }

}
