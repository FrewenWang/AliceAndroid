package com.frewen.android.demo.business.samples.jni;

import com.frewen.android.demo.business.model.UserInfo;

/**
 * JNI中访问类的属性字段
 */
public class JniAccessField {


    public static native int callNativeAccessStaticField(UserInfo user);
}
