//
// Created by Frewen.Wong on 2021/8/1.
//

#ifndef NYX_ANDROID_GL_SAMPLE_BASE_H
#define NYX_ANDROID_GL_SAMPLE_BASE_H

#include <GLES3/gl3.h>

class GLSampleBase {
public:
    GLSampleBase() {

    }

    virtual ~GLSampleBase() {

    }

    virtual void destroy() = 0;

    virtual void init() = 0;

    virtual void draw(int screenW, int screenH) = 0;
};


#endif //NYX_ANDROID_GL_SAMPLE_BASE_H
