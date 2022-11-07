//
// Created by Frewen.Wong on 2021/8/1.
//

#ifndef NYX_ANDROID_GL_SAMPLE_BASE_H
#define NYX_ANDROID_GL_SAMPLE_BASE_H

#include <GLES3/gl3.h>
#include <ImageDef.h>
#include <utils/AuraSyncLock.h>

/**
 * OpenGLES的实例的基类声明
 */
class GLSampleBase {

public:
    GLSampleBase() {

    }

    virtual ~GLSampleBase() {

    }

    /**
     * 初始化(纯虚函数)
     */
    virtual void init() = 0;

    /**
     * 进行绘制图形
     * @param screenW
     * @param screenH
     */
    virtual void draw(int screenW, int screenH) = 0;

    /**
     * 销毁
     */
    virtual void destroy() = 0;

protected:
    /**
     * 顶点着色器
     */
    GLuint mVertexShader;
    /**
     * 片段着色器
     */
    GLuint mFragmentShader;
    /**
     * 程序对象
     */
    GLuint mProgramObj;
    /**
     * 同步锁
     */
    AuraSyncLock mLock;
    /**
     * 设置Surface的宽度和高度
     */
    int mSurfaceWidth;
    int mSurfaceHeight;
};


#endif //NYX_ANDROID_GL_SAMPLE_BASE_H
