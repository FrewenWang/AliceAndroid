//
// Created by Frewen.Wong on 2021/10/10.
//

#ifndef NYX_ANDROID_IMAGE_DEF_H
#define NYX_ANDROID_IMAGE_DEF_H

/**
 *  Native层的Image图片的定义
 *  定义图片的宽度
 *  定义图片的高度
 *  定义图片的格式
 *  定义图片的通道数据
 */
struct NativeImage {
    int width;
    int height;
    int format;
    uint8_t *ppPlane[3];

    NativeImage() {
        width = 0;
        height = 0;
        format = 0;
        ppPlane[0] = nullptr;
        ppPlane[1] = nullptr;
        ppPlane[2] = nullptr;
    }
};

#endif //NYX_ANDROID_IMAGE_DEF_H
