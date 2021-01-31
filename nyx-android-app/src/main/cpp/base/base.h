//
// Created by Frewen.Wong on 1/30/21.
//
// 如果没有定义AURA_JNI_BASE_H，我们就定义一个AURA_JNI_BASE_H
#ifndef AURA_JNI_BASE_H
#define AURA_JNI_BASE_H

#include <android/log.h>
#include <jni.h>

#define LOG_TAG "AURA_JNI"

#define LOG_I(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define LOG_D(...) __android_log_print(ANDROID_LOG_DEBUG,LOG_TAG,__VA_ARGS__)
#define LOG_W(...) __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__)
#define LOG_E(...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

#endif