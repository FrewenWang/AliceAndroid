#include <jni.h>
#include <string>

// 双引号是从相对路径进行搜索
//#include "people/People.h"
// 而尖括号是从全局搜索路径进行搜索.因为我们已经通过
// include_directories(people/) 将其include文件加入到全局搜索目录下面，
// 所以就可以直接使用尖括号
# include <People.h>

// 定义JNI的Class的Path
#define JNI_CLASS_PATH "com/frewen/android/demo/logic/samples/jni/HelloJNIActivity"


// ==============================Java调用C++的方法，静态加载方法=============================================
//静态的方法弊端就是每个方法都要记上尝尝的包名限定
/**
 * Java虚拟机调用Native方法，会传入两个对象
 * 命名规则：Java_包名_类名_方法名
 * @param env
 */
extern "C"   //添加这句话表示我们想让这个方法按照C编译的方法进行编译
JNIEXPORT jstring JNICALL   //
Java_com_frewen_android_demo_logic_samples_jni_HelloJNIActivity_stringFromJNI(JNIEnv *env,
                                                                              jobject /* this */) {
    // 实例化一个Hello的字符串
    std::string hello = "Hello from C++";
    // return env->NewStringUTF(hello.c_str());
    // 实例化一个people对象。通过关联的people库来获得
    People people;
    // TODO 我们可以通过people.getStringMsg()来获取People对象的信息
    // 至于为什么要c_str()的调用，后续学习
    return env->NewStringUTF(people.getStringMsg().c_str());
}

/**
 * Java虚拟机调用Native方法，会传入两个对象
 * Java_包名_类名_方法名
 * @param env   JNIEnv  访问Java对象
 * @param thiz  JavaObject  调用者的Java对象
 * @param msg   入参，Msg消息
 * @return
 */
extern "C"
JNIEXPORT jstring JNICALL
Java_com_frewen_android_demo_logic_samples_jni_HelloJNIActivity_stringFromJNI2(JNIEnv *env,
                                                                               jobject thiz,
                                                                               jstring msg) {
    const char *str = env->GetStringUTFChars(msg, 0);

    return env->NewStringUTF(str);
}

// ===============C/C++ 调用Java的方法=================
#define JNI_USER_PATH  "com/frewen/android/demo/logic/model/User"
extern "C" JNIEXPORT jstring JNICALL
Java_com_frewen_android_demo_logic_samples_jni_HelloJNIActivity_stringFromJNIUser(JNIEnv *env,
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