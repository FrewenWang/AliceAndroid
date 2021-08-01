//
// Created by Frewen.Wong on 2021/8/1.
//

#ifndef NYX_ANDROID_BEATING_HEART_SAMPLE_H
#define NYX_ANDROID_BEATING_HEART_SAMPLE_H

#include "GLSampleBase.h"

class BeatingHeartSample : public GLSampleBase {
public:
    BeatingHeartSample();

    virtual ~BeatingHeartSample();

    /**
     * TODO 为什么基类里面已经写了这几个虚函数，为什么这里面还需要在写
     */
    virtual void init();

    virtual void draw(int screenW, int screenH);

    virtual void destroy();

protected:
    GLuint m_ProgramObj;

};


#endif //NYX_ANDROID_BEATING_HEART_SAMPLE_H
