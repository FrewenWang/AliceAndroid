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
    // TODO 顶点着色器和片段着色器的实例化方法需要学习
    // 初始化一个顶点着色器。实例对象是一个char数组
    char vShaderStr[] =
            "#version 300 es                          \n"   //声明着色器版本
            "layout(location = 0) in vec4 vPosition;  \n"   //输入一个属性数组
            "void main()                              \n"
            "{                                        \n"
            "   gl_Position = vPosition;              \n"  //将属性数组拷贝到gl_Position的特殊输出变量中
            "}                                        \n";
    // 初始化一个片段着色器，实例化对象是一个char 数组
    char fShaderStr[] =
            "#version 300 es                              \n"       //声明着色器版本
            "precision mediump float;                     \n"       //声明着色器中浮点变量的默认精度
            "out vec4 fragColor;                          \n"       //声明一个输出变量
            "void main()                                  \n"
            "{                                            \n"
            "   fragColor = vec4 ( 1.0, 0.0, 0.0, 1.0 );  \n"       //给这个输出变量赋值
            "}                                            \n";
    mProgramObj = OpenGLUtil::createProgram(vShaderStr, fShaderStr, mVertexShader, mFragmentShader);
}

void TriangleSample::draw(int screenW, int screenH) {
    LOG_E("TriangleSample::Draw");

    // 第一个点的坐标: (0.0f, 0.5f, 0.0f)、(-0.5f, -0.5f, 0.0f)、( 0.5f, -0.5f, 0.0f)
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



