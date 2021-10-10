//
// Created by Frewen.Wong on 2021/8/29.
//

#include <OpenGLUtil.h>
#include <utils/AuraLogger.h>
#include "TriangleSample.h"


/**
 * 构造函数
 */
TriangleSample::TriangleSample() {

}

/**
 * 析构函数
 */
TriangleSample::~TriangleSample() {

}

/**
 * 加载图片
 * @param pImage
 */
void TriangleSample::loadImage(NativeImage *pImage) {

}

/**
 * 初始化
 */
void TriangleSample::init() {
    if (mProgramObj != 0) {
        return;
    }
    // TODO 这两个字符数组是什么意思？需要学习
    char vShaderStr[] =
            "#version 300 es                          \n"
            "layout(location = 0) in vec4 vPosition;  \n"
            "void main()                              \n"
            "{                                        \n"
            "   gl_Position = vPosition;              \n"
            "}                                        \n";

    char fShaderStr[] =
            "#version 300 es                              \n"
            "precision mediump float;                     \n"
            "out vec4 fragColor;                          \n"
            "void main()                                  \n"
            "{                                            \n"
            "   fragColor = vec4 ( 1.0, 0.0, 0.0, 1.0 );  \n"
            "}                                            \n";
    mProgramObj = OpenGLUtil::createProgram(vShaderStr, fShaderStr, mVertexShader, mFragmentShader);
}

void TriangleSample::draw(int screenW, int screenH) {
    LOG_E("TriangleSample::Draw");
    GLfloat vVertices[] = {
            0.0f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
    };

    if (mProgramObj == 0)
        return;

    glClear(GL_STENCIL_BUFFER_BIT | GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    glClearColor(1.0, 1.0, 1.0, 1.0);

    // Use the program object
    glUseProgram(mProgramObj);

    // Load the vertex data
    glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 0, vVertices);
    glEnableVertexAttribArray(0);

    glDrawArrays(GL_TRIANGLES, 0, 3);
    glUseProgram(GL_NONE);

}

void TriangleSample::destroy() {
    if (mProgramObj) {
        glDeleteProgram(mProgramObj);
        mProgramObj = GL_NONE;
    }
}



