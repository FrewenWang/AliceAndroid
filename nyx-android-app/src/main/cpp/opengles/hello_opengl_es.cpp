#include <jni.h>
#include <base.h>

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


int native_OnInit;
int native_OnUnInit;
int native_SetImageData;
int native_OnSurfaceCreated;
int native_OnSurfaceChanged;
int native_OnDrawFrame;
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
    if (clazz == NULL) {
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
}


extern "C"
JNIEXPORT void JNICALL
Java_com_frewen_android_demo_logic_samples_opengles_render_MyNativeRender_native_1OnInit(
        JNIEnv *env, jobject nativeRender) {
    // TODO: implement native_OnInit()
}
extern "C"
JNIEXPORT void JNICALL
Java_com_frewen_android_demo_logic_samples_opengles_render_MyNativeRender_native_1OnUnInit(
        JNIEnv *env, jobject nativeRender) {
    // TODO: implement native_OnUnInit()
}
extern "C"
JNIEXPORT void JNICALL
Java_com_frewen_android_demo_logic_samples_opengles_render_MyNativeRender_native_1SetImageData(
        JNIEnv *env, jobject nativeRender, jint format, jint width, jint height, jbyteArray bytes) {
    // TODO: implement native_SetImageData()
}
extern "C"
JNIEXPORT void JNICALL
Java_com_frewen_android_demo_logic_samples_opengles_render_MyNativeRender_native_1OnSurfaceChanged(
        JNIEnv *env, jobject nativeRender, jint width, jint height) {
    // TODO: implement native_OnSurfaceChanged()
}
extern "C"
JNIEXPORT void JNICALL
Java_com_frewen_android_demo_logic_samples_opengles_render_MyNativeRender_native_1OnSurfaceCreated(
        JNIEnv *env, jobject nativeRender) {
    // TODO: implement native_OnSurfaceCreated()
}
extern "C"
JNIEXPORT void JNICALL
Java_com_frewen_android_demo_logic_samples_opengles_render_MyNativeRender_native_1OnDrawFrame(
        JNIEnv *env, jobject nativeRender) {
    // TODO: implement native_OnDrawFrame()
}