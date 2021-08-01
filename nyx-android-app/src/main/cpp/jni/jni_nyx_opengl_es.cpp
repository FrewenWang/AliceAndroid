#include <jni.h>
#include <base.h>
#include "../opengl/nyx_opengl_es_context.h"


//
// Created by Frewen.Wang on 2021/8/1.
//
/**
 * Jni的静态注册。方法的全路径就是Java中对应类的包路径
 *
 *
 *
 */
#define NATIVE_RENDER_CLASS_NAME "com/frewen/android/demo/logic/samples/opengles/render/MyNativeRender"


/**
 * Class:     com_frewen_android_demo_logic_samples_opengles_render_MyNativeRender
 * Method:    native_OnInit
 * Signature: ()V
 **/
extern "C" JNIEXPORT void JNICALL native_OnInit(JNIEnv *env, jobject nativeRender) {
    NyxOpenGLRenderContext::instance();
}
/**
 * Class:     com_frewen_android_demo_logic_samples_opengles_render_MyNativeRender
 * Method:    native_OnUnInit
 * Signature: ()V
 */
extern "C" JNIEXPORT void JNICALL native_OnUnInit(JNIEnv *env, jobject nativeRender) {

}
/**
 * Class:     com_frewen_android_demo_logic_samples_opengles_render_MyNativeRender
 * Method:    native_SetImageData
 * Signature: (III[B)V  前面的括号里面是参数(III[B) 说明里面有四个参数，前三个int型参数,[B byte数据
 */
extern "C" JNIEXPORT void JNICALL native_SetImageData
        (JNIEnv *env, jobject nativeRender, jint format, jint width, jint height,
         jbyteArray imageData) {

}
/**
 * Class:     com_frewen_android_demo_logic_samples_opengles_render_MyNativeRender
 * Method:    native_OnSurfaceCreated
 * Signature: ()V
 */
extern "C" JNIEXPORT void JNICALL native_OnSurfaceCreated(JNIEnv *env, jobject nativeRender) {
    NyxOpenGLRenderContext::instance()->onSurfaceCreated();
}

/**
 * Class:     com_frewen_android_demo_logic_samples_opengles_render_MyNativeRender
 * Method:    native_OnSurfaceChanged
 * Signature: (II)V
 */
extern "C" JNIEXPORT void JNICALL native_OnSurfaceChanged
        (JNIEnv *env, jobject instance, jint width, jint height) {
    NyxOpenGLRenderContext::instance()->onSurfaceChanged(width, height);
}

/**
 * Class:     com_frewen_android_demo_logic_samples_opengles_render_MyNativeRender
 * Method:    native_OnDrawFrame
 * Signature: ()V
 */
extern "C" JNIEXPORT void JNICALL native_OnDrawFrame(JNIEnv *env, jobject instance) {
    NyxOpenGLRenderContext::instance()->onDrawFrame();
}


static JNINativeMethod g_NativeMethods[] = {
        {"native_OnInit",           "()V",      (void *) (native_OnInit)},
        {"native_OnUnInit",         "()V",      (void *) (native_OnUnInit)},
        {"native_SetImageData",     "(III[B)V", (void *) (native_SetImageData)},
        {"native_OnSurfaceCreated", "()V",      (void *) (native_OnSurfaceCreated)},
        {"native_OnSurfaceChanged", "(II)V",    (void *) (native_OnSurfaceChanged)},
        {"native_OnDrawFrame",      "()V",      (void *) (native_OnDrawFrame)},
};

static int
RegisterNativeMethods(JNIEnv *env, const char *className, JNINativeMethod *methods, int methodNum) {
    LOG_D("RegisterNativeMethods");
    jclass clazz = env->FindClass(className);
    if (clazz == nullptr) {
        LOG_E("RegisterNativeMethods fail. clazz == NULL");
        return JNI_FALSE;
    }
    if (env->RegisterNatives(clazz, methods, methodNum) < 0) {
        LOG_E("RegisterNativeMethods fail");
        return JNI_FALSE;
    }
    return JNI_TRUE;
}
/**
 * 我们使用动态注册来进行Native方法的注册
 */
extern "C" jint JNI_OnLoad(JavaVM *jvm, void *p) {
    // 需要引入#include <base.h>
    // include_directories(base/) 我们需要把base目录放到全局搜索目录下
    LOG_D("===== JNI_OnLoad =====");
    jint jniRet = JNI_ERR;
    JNIEnv *env = NULL;
    if (jvm->GetEnv((void **) (&env), JNI_VERSION_1_6) != JNI_OK) {
        return jniRet;
    }

    jint regRet = RegisterNativeMethods(env, NATIVE_RENDER_CLASS_NAME, g_NativeMethods,
                                        sizeof(g_NativeMethods) /
                                        sizeof(g_NativeMethods[0]));

    if (regRet != JNI_TRUE) {
        return JNI_ERR;
    }
    return JNI_VERSION_1_6;
}