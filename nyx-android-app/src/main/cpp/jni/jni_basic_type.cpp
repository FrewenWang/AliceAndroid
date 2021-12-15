//
// Created by Frewen.Wong on 1/30/21.
//
#include <jni.h>
#include <utils/AuraLogger.h>


// JNI的数据类型和C++中的数据类型的对照表
/* Primitive types that match up with Java equivalents. */
//typedef uint8_t  jboolean; /* unsigned 8 bits */
//typedef int8_t   jbyte;    /* signed 8 bits */
//typedef uint16_t jchar;    /* unsigned 16 bits */
//typedef int16_t  jshort;   /* signed 16 bits */
//typedef int32_t  jint;     /* signed 32 bits */
//typedef int64_t  jlong;    /* signed 64 bits */
//typedef float    jfloat;   /* 32-bit IEEE 754 */
//typedef double   jdouble;  /* 64-bit IEEE 754 */

//===============================学习Jni的基础数据类型========================
/**
 * 第一个参数：是JNI的ENV环境
 * 第二个参数：因为我们这些方法在Jave那边都是静态方法，所以我们这个参数类型是jclass
 *           如果是实例方法的话，我们使用的是jobject
 * 后面的参数就是方法是实际参数
 */
extern "C"
JNIEXPORT jint JNICALL
Java_com_frewen_android_demo_logic_samples_jni_JniBasicType_callNativeInt(JNIEnv *env, jclass thiz, jint num) {
    LOG_D("JAVA int value is %d", num);
    int c_num = num * 2;
    LOG_D("JNI int result is %d", c_num);
    return c_num;
}

extern "C"
JNIEXPORT jbyte JNICALL
Java_com_frewen_android_demo_logic_samples_jni_JniBasicType_callNativeByte(JNIEnv *env, jclass thiz, jbyte b) {
    LOG_D("JAVA byte value is %d", b);
    jbyte c_byte = b + (jbyte) 10;
    LOG_D("JNI byte result is %d", c_byte);
    return c_byte;
}

extern "C"
JNIEXPORT jchar JNICALL
Java_com_frewen_android_demo_logic_samples_jni_JniBasicType_callNativeChar(JNIEnv *env, jclass thiz, jchar ch) {

    LOG_D("JAVA char value is %c", ch);
    jchar c_char = ch + (jchar) 3;
    LOG_D("JNI char result is %c", c_char);
    return c_char;
}

extern "C"
JNIEXPORT jshort JNICALL
Java_com_frewen_android_demo_logic_samples_jni_JniBasicType_callNativeShort(JNIEnv *env, jclass thiz, jshort sh) {
    LOG_D("JAVA jshort value is %d", sh);
    jshort c_short = sh + (jshort) 10;
    LOG_D("JNI jshort value is %d", c_short);
    return c_short;
}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_frewen_android_demo_logic_samples_jni_JniBasicType_callNativeLong(JNIEnv *env, jclass thiz, jlong l) {
    LOG_D("JAVA long value is %ld", l);
    jlong c_long = l + 10;
    LOG_D("JNI long result is %ld", l);
    return c_long;
}

extern "C"
JNIEXPORT jfloat JNICALL
Java_com_frewen_android_demo_logic_samples_jni_JniBasicType_callNativeFloat(JNIEnv *env, jclass thiz, jfloat f) {
    LOG_D("JAVA float value is %f", f);
    jfloat c_float = f + (jfloat) 10.0;
    LOG_D("JNI float result is %f", f);
    return c_float;
}

extern "C"
JNIEXPORT jdouble JNICALL
Java_com_frewen_android_demo_logic_samples_jni_JniBasicType_callNativeDouble(JNIEnv *env, jclass thiz, jdouble d) {
    LOG_D("JAVA double value is %f", d);
    jdouble c_double = d + 10.0;
    LOG_D("JNI double result is %f", d);
    return c_double;
}

extern "C"
JNIEXPORT jboolean JNICALL
Java_com_frewen_android_demo_logic_samples_jni_JniBasicType_callNativeBoolean(JNIEnv *env, jclass thiz, jboolean value) {
    LOG_D("JAVA boolean value is %d", value);
    jboolean c_bool = (jboolean) !value;
    LOG_D("JNI boolean result is %d", value);
    return c_bool;
}

/**
 * JNI层和Java层的字符串之间的转换
 * Java和JNI之间的类型数据转换
 * JNI中字符串中类型的定义：JNI中的字符串转化成为C语言中的转换
 *
 */
extern "C"
JNIEXPORT jstring JNICALL
Java_com_frewen_android_demo_logic_samples_jni_JniBasicType_callNativeString(JNIEnv *env, jclass clazz, jstring jString) {

    // 定义一个char*的数组的常量.为C中的str字符串分配内存空间
    // 将jString的UTF的编码的String转换成成为C语言风格的字符串
    const char *str = env->GetStringUTFChars(jString, 0);
    LOG_D("传入的Java的String是：%s", str);

    /// 计算传入的字符串的长度。通过env的方法
    int len = env->GetStringUTFLength(jString);
    LOG_D("传入的Java的String的长度是：%d", len);

    /// 计算传入的字符串的0-len - 10的字符串存储到Buff中
    char buff[128];
    //
    env->GetStringUTFRegion(jString, 0, len - 10, buff);
    LOG_D("传入的Java的String的Buffer：%s", buff);

    // 用完之后，进行删除，避免出现内存泄露的问题。
    // 上面GetStringUTFChars和ReleaseStringUTFChars这两个函数是配套使用的
    // 我们开发中
    env->ReleaseStringUTFChars(jString, str);
    // 因为Java都是Unicode编码，所以我们返回的也是NewStringUTF
    return env->NewStringUTF("这是C环境的字符串 %s");

}


//java类型	Native类型	符号属性	字长
//boolean	jboolean	无符号	8位
//byte	jbyte	有符号	8位
//char	jchar	无符号	16位
//short	jshort	有符号	16位
//int	jint	有符号	32位
//long	jlong	有符号	64位
//float	jfloat	有符号	32位
//double	jdouble	有符号	64位



// JNI中的引用数据类型的转换
//java类型	                      Native类型
// ALL Object                     jobject
//java.lang.Class	              jclass
//java.lang.Throwable	          jthrowable
//java.lang.String	              jstring
//java.lang.Object[]	          jobjectArray
//Boolean[]	                      jbooleanArray
//Byte[]	                      jbyteArray
//Char[]	                      jcharArray
//Short[]	                      jshortArray
//int[]	                          jintArray
//long[]	                      jlongArray
//float[]	                      jfloatArray
//double[]	                      jdoubleArray
extern "C"
JNIEXPORT jstring JNICALL
Java_com_frewen_android_demo_logic_samples_jni_JniBasicType_callNativeReferenceType(JNIEnv *env, jclass clazz,jobjectArray string_array) {

    //这个方法对于所有的数组都是公用的.获取数组的长度
    int len = env->GetArrayLength(string_array);
    LOG_D("len is %d ", len);

    //拿到对象数组的首元素
    // 进行对象的静态转换
    jstring firstStr = static_cast<jstring>(env->GetObjectArrayElement(string_array, 0));

//    const char* str:
//    意义：确保*str的内容不会改变，也就是用str这个指针无法改变str这个指针指向的地址的内容，
//    但是可以改变这个指针
//    char const str和const charstr含义一样
//    char* const str:
//    意义：确保str这个指针不会改变，但是这个指针里面的内容可以改变。
    const char *str = env->GetStringUTFChars(firstStr, 0);
    LOG_D("First String is %s ", str);
    // 我们需要释放掉这个String 避免内存泄漏
    env->ReleaseStringUTFChars(firstStr, str);
    return env->NewStringUTF(str);
}