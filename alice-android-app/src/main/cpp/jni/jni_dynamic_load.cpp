//
// Created by Frewen.Wong on 1/30/21.
//
// 放到全局搜索的目录下
#include <utils/AuraLogger.h>
#include <jvm.h>
#include <jni.h>


// 定义JNI的Class的Path的字符串宏.后续我们再看看这个字符串宏是怎么生成的？？
#define JNI_CLASS_PATH "com/frewen/android/demo/logic/samples/jni/HelloJNIActivity"

/**
 * jni的变量相加的方法
 * @param env  JNIEnv这个是固定的
 * @param jObj  jObj对象。如果是实例方法，则是jobject 如果是类方法则是jclass
 * @param x
 * @param y
 * @return
 */
jint nativeAddVariable(JNIEnv *env, jobject jObj, int x, int y) {
    return x + y;
}

jstring getDynamicMsg(JNIEnv *env, jobject jObj) {
    return env->NewStringUTF("Msg From Native DynamicMsg");
}



//=============================上面我们声明所有需要向Java层暴露的方法===============================

/**
 * 通过signature来机型JNI函数的动态注册。
 * 静态注册是有弊端的：头文件和方法名需要进行查找
 * 动态加载的时候，将方法动态注册到系统中
 *
 * 什么是signature??
 * Java与C或者C++相互调用的时候的表示函数的参数的描述符
 * 输入参数放在括号()中,输出参数放在括号外边
 * 多个参数之前顺序存放，使用分号隔开
 *
 * 原始类型的Signature
 * boolean      Z
 * byte         B
 * char         C
 * short        S
 * int          I
 * long         L
 * float        F
 * double       D
 * void         V
 *
 * 类的Signature
 * Java对象参数 "L包路径/类名"  举例：(Ljava/lang/String;)Ljava/lang/String;
 * Java数组    "["   举例：([Student;)[Lstudent;  -> Student[]  Xxx(Student[])
 */
static JNINativeMethod g_methods[] = {
        {
                "nativeAddVariable",           //Java中函数名
                "(II)I",                    // 方法输入参数是两个int所以是II 返回值是int 所以是I
                (void *) nativeAddVariable            // Native的函数名称的函数指针，和Java中的函数名不需要一一对应
        },
        {
                "getDynamicMsg",           //Java中函数名
                //这个方法输入参数为空。输出参数是String对象所以是：Ljava/lang/String;
                "()Ljava/lang/String;",
                (void *) getDynamicMsg
        }
};

/**
 * @param env
 * @param name
 * @param methods
 * @param nMethods
 *
 * typedef struct {
 *   const char* name;
 *   const char* signature;
 *   void*       fnPtr;
 *  } JNINativeMethod;
 * @return
 */
int registerNativeMethods(JNIEnv *env, const char *name, JNINativeMethod *methods, jint nMethods) {
    // 找到Java层的类对象
    // jclass clazz = env->FindClass(JNI_CLASS_PATH);
    // 这个jClazz就是上层用来调用 System.loadLibrary("dynamic-register-jni")的class的全路径名
    jclass jClazz = env->FindClass(name);
    if (jClazz == nullptr) {
        return JNI_FALSE;
    }
    /**
     * 我们看一下这个方法的声明
     * jclass clazz  clazz对象
     * JNINativeMethod* methods  方法的结构体数组
     * jint nMethods 方法的个数
     * 如果成功的话，应该是是等于0 所有小于0的时候就是返回是被
     */
    if (env->RegisterNatives(jClazz, methods, nMethods) < 0) {
        return JNI_FALSE;
    }
    return JNI_TRUE;
}

/**
 * JNI_OnLoad 是每个动态库被加载的时候，系统都会回调的一个方法。来进行动态的方法的注册
 * @param vm
 * @param revered
 * @return
 */
JNIEXPORT int JNI_OnLoad(JavaVM *vm, void *revered) {
    // env我们可以理解为一个函数表，通过他我们可以调用jni中为我们提供的所有的函数
    JNIEnv *env = NULL;
    // Jni中，如果调用成功的话，我们就认为是JNI_OK。也就是为0
    // 这个VM是一个指针，我们通过这个VM指针调用他的GetEnv方法。
    // 我们指定JNI的版本。
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return JNI_FALSE;
    }
    // 方法映射表的注册，就是把我们的方法注册到系统中去
    // 第一个参数：env
    // 第二个参数：JNI_CLASS_PATH上层java类的全路径名
    // 第三个参数：所有的方法列表
    // 第四个参数：方法的个数。主要是sizeof计算所有方法的大小 除以  第一个方法的大小。得到方法数
    registerNativeMethods(env, JNI_CLASS_PATH, g_methods, sizeof(g_methods) / sizeof(g_methods[0]));
    LOG_D("JNI Native Methods Load Success");
    // TODO 这里为什么要返回JNI使用的版本
    return JNI_VERSION_1_6;
}