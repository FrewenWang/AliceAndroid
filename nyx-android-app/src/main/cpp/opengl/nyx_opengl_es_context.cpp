//
// Created by Frewen.Wong on 2021/8/1.
//

#include "nyx_opengl_es_context.h"
#include "samples/BeatingHeartSample.h"
#include "samples/TriangleSample.h"
#include <utils/AuraLogger.h>

NyxOpenGLRenderContext *NyxOpenGLRenderContext::mPtr_Context = nullptr;

/**
 * NyxOpenGLRenderContext获取单例指针对象的方法
 * @return
 */
NyxOpenGLRenderContext *NyxOpenGLRenderContext::instance() {
    // LOG_D("NyxOpenGLRenderContext::instance");
    if (mPtr_Context == nullptr) {
        mPtr_Context = new NyxOpenGLRenderContext();
    }
    return mPtr_Context;
}

NyxOpenGLRenderContext::NyxOpenGLRenderContext() {
    LOG_D("NyxOpenGLRenderContext::constructor");
    mPtrCurSample = new TriangleSample();
    mPtrBeforeSample = nullptr;
}

/**
 * 析构函数的实现
 */
NyxOpenGLRenderContext::~NyxOpenGLRenderContext() {
    // 指针动态申请的空间进行释放
    if (mPtrCurSample) {
        delete mPtrCurSample;
        mPtrCurSample = nullptr;
    }

    if (mPtrBeforeSample) {
        delete mPtrBeforeSample;
        mPtrBeforeSample = nullptr;
    }
}

void NyxOpenGLRenderContext::onSurfaceCreated() {
    LOG_D("NyxOpenGLRenderContext::OnSurfaceCreated");
    // 设置好清除颜色
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

    if (mPtrBeforeSample) {
        mPtrBeforeSample->destroy();
        delete mPtrBeforeSample;
        mPtrBeforeSample = nullptr;
    }

    if (mPtrCurSample) {
        mPtrCurSample->init();
        mPtrCurSample->draw(m_ScreenW, m_ScreenH);
    }
}

void NyxOpenGLRenderContext::setParamsInt(int paramType, float value0, float value1) {
    LOG_D("NyxOpenGLRenderContext::SetParamsFloat paramType=%d, value0=%f, value1=%f", paramType,
          value0, value1);
}
