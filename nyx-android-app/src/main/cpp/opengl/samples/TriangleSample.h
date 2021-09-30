//
// Created by Frewen.Wong on 2021/8/29.
//

#ifndef NYX_ANDROID_TRIANGLE_SAMPLE_H
#define NYX_ANDROID_TRIANGLE_SAMPLE_H

#include "GLSampleBase.h"

class TriangleSample : public GLSampleBase {
public:
    TriangleSample();

    virtual ~TriangleSample();

    virtual void init();

    virtual void draw(int screenW, int screenH);

    virtual void loadImage(NativeImage *pImage);

    virtual void destroy();
};


#endif //NYX_ANDROID_TRIANGLE_SAMPLE_H
