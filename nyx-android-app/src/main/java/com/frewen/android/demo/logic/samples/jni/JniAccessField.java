package com.frewen.android.demo.logic.samples.jni;

import com.frewen.android.demo.logic.model.UserInfo;

/**
 * JNI中访问类的属性字段
 */
public class JniAccessField {


    public static native int callNativeAccessStaticField(User user);
}
