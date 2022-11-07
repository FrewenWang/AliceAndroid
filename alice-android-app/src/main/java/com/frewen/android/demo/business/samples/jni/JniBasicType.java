package com.frewen.android.demo.business.samples.jni;

/**
 * @filename: JniBasicType
 * @introduction:
 * @author: Frewen.Wong
 * @time: 1/30/21 5:57 PM
 *         Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
public class JniBasicType {

    static {
        System.loadLibrary("hello-jni");
    }

    public static native int callNativeInt(int num);

    public static native byte callNativeByte(byte b);

    public static native char callNativeChar(char ch);

    public static native short callNativeShort(short sh);

    public static native long callNativeLong(long l);

    public static native float callNativeFloat(float f);

    public static native double callNativeDouble(double d);

    public static native boolean callNativeBoolean(boolean value);

    public static native String callNativeString(String string);

    /**
     * JNI层和Java层的调用引用数据类型的的调用
     * @param stringArray
     * @return
     */
    public static native String callNativeReferenceType(String[] stringArray);
}
