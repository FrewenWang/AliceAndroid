#include <jni.h>
#include <string>

#define JNI_CLASS_PATH "com/frewen/android/demo/samples/jni/HelloJNIActivity"

extern "C" JNIEXPORT jstring JNICALL
/**
 * Java虚拟机调用Native方法，会传入两个对象
 * 命名规则：Java_包名_类名_方法名
 * @param env
 * @return
 */
Java_com_frewen_android_demo_samples_jni_HelloJNIActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL

/**
 * Java虚拟机调用Native方法，会传入两个对象
 * Java_包名_类名_方法名
 * @param env   JNIEnv  访问Java对象
 * @param thiz  JavaObject  调用者的Java对象
 * @param msg   入参，Msg消息
 * @return
 */
Java_com_frewen_android_demo_samples_jni_HelloJNIActivity_stringFromJNI2(JNIEnv *env, jobject thiz,
                                                                         jstring msg) {
    const char *str = env->GetStringUTFChars(msg, 0);

    return env->NewStringUTF(str);
}



extern "C" JNIEXPORT jstring JNICALL
second_StringFromJNI2(JNIEnv *env, jobject thiz, jstring msg) {
    // TODO: implement secondStringFromJNI2()

    return env->NewStringUTF("this is the second method to call JNI");
}

// // 括号里面是输入参数，括号外面是输出参数
/**
 * 什么是signature??
 * Java与C或者C++相互调用的时候的表示函数的参数的描述符
 * 输入参数放在括号()中,出书参数放在括号外边
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
                "secondStringFromJNI2",
                "(Ljava/lang/String;)Ljava/lang/String;",
                (void *) second_StringFromJNI2
        }
};

jint JNI_OnLoad(JavaVM *vm, void *revered) {
    JNIEnv *env = NULL;
    vm->GetEnv((void **) &env, JNI_VERSION_1_6);
    // 找到Java层的类对象
    jclass clazz = env->FindClass(JNI_CLASS_PATH);
    // 方法映射表
    env->RegisterNatives(clazz, g_methods, sizeof(g_methods) / sizeof(g_methods[0]));
    return JNI_VERSION_1_6;
}


// ===============C/C++ 调用Java的方法=================
#define JNI_USER_PATH  "com/frewen/android/demo/bean/User"
extern "C" JNIEXPORT jstring JNICALL
Java_com_frewen_android_demo_samples_jni_HelloJNIActivity_stringFromJNIUser(JNIEnv *env,
                                                                            jobject thiz,
                                                                            jstring msg) {
    //步骤一： 找到User类的全路径名获取Class
    jclass userClass = env->FindClass(JNI_USER_PATH);
    // 步骤二：获取User类的构造方法
    jmethodID method_init_id = env->GetMethodID(userClass, "<init>", "()V");

    // 步骤三：获取User类里面的其他方法
    jmethodID method_get_name_id = env->GetMethodID(userClass, "getName", "()Ljava/lang/String;");
    jmethodID method_set_name_id = env->GetMethodID(userClass, "setName", "(Ljava/lang/String;)V");
    jmethodID method_get_age_id = env->GetMethodID(userClass, "getAge", "()I");
    jmethodID method_set_age_id = env->GetMethodID(userClass, "setAge", "(I)V");

    // 步骤四：通过构造方法，来生成User对象，并且调用其方法
    jobject user = env->NewObject(userClass, method_init_id);

    env->CallVoidMethod(user, method_set_name_id, msg); //调用String为什么不可以？研究一下
    env->CallVoidMethod(user, method_set_age_id, 18);
    int age = env->CallIntMethod(user, method_get_age_id);

    ///使用char tmp[] = {0,};会出现异常。怎么去声明一个不定长的数组
    //char tmp[] = {0,};
    char tmp[80];
    sprintf(tmp, "%d", age);

    std::string hello = "Hello from C++ User: age = ";
    hello.append(tmp);
    return env->NewStringUTF(hello.c_str());
}



