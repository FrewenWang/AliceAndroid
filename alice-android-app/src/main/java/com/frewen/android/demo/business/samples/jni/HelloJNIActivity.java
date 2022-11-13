package com.frewen.android.demo.business.samples.jni;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.frewen.android.demo.R;
import com.frewen.android.demo.business.model.UserInfo;

/**
 * @filename: HelloJNIActivity
 * @introduction: JNI的基础概念
 *
 *         1、加载动态链接库   System.loadLibrary("hello-jni");
 *         2、在Java层定义Native的关键字的本地方法
 *         3、在C/C++层创建Java_package_class_method
 *
 *         JNIEnv: Java的本地环境，包含了所有Java的本地环境
 *
 *         JavaVM： Java虚拟机，一个JavaVM会与很多线程，每个线程对应一个JNIEnv
 * @author: Frewen.Wong
 * @time: 2020/7/12 00:05
 * @version: 1.0.0
 * @copyright Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public class HelloJNIActivity extends AppCompatActivity {

    private static final String[] sStrings = {"123", "456", "789"};

    /**
     * 加载loadLibrary
     * 用于在应用程序启动时加载“ hello-jni”库。
     * 用于在应用程序启动时加载"dynamic-register-jni"库。
     */
    static {
        System.loadLibrary("dynamic-register-jni");
        System.loadLibrary("hello-jni");
        // System的load是代码的绝对路径的加载
        //System.load("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_jni);

        TextView basicTv = findViewById(R.id.jniBasicType);
        basicTv.setText(stringFromJNI());


        TextView jniBasicDataType = findViewById(R.id.jniBasicDataType);
        jniBasicDataType.setText("基础数据类型：jint:" + JniBasicType.callNativeInt(1)
                + ", jbyte:" + JniBasicType.callNativeByte((byte) 2)
                + ", jchar:" + JniBasicType.callNativeChar((char) 'A')
                + ", jshort:" + JniBasicType.callNativeShort((short) 4)
                + ", jlong:" + JniBasicType.callNativeLong(5)
                + ", jfloat:" + JniBasicType.callNativeFloat(6.0f)
                + ", jdouble:" + JniBasicType.callNativeDouble(7.0)
                + ", jboolean:" + JniBasicType.callNativeBoolean(false));


        TextView jniStringDataType = findViewById(R.id.jniStringType);
        jniStringDataType.setText(JniBasicType.callNativeString("Hello JNI String"));


        TextView jniReferenceType = findViewById(R.id.jniReferenceType);
        jniReferenceType.setText(JniBasicType.callNativeReferenceType(sStrings));

        UserInfo user = new UserInfo("XiaoMing",18);
        TextView jniAccessField = findViewById(R.id.jniAccessField);
        // jniAccessField.setText(JniAccessField.callNativeAccessStaticField(user));
    }


    /**
     * A native method that is implemented by the 'hello-jni' native library,
     * which is packaged with this application.
     * 创建本地方法，标记这个一个C或者C++方法
     */
    public native String stringFromJNI();

    public native String stringFromJNI2(String msg);

    /**
     * @param msg
     */
    public native String stringFromJNIUser(String msg);

    // =======================动态加载NativeMethods ============================================
    public void dynamicLoadMethods(View view) {
        int result = nativeAddVariable(2, 3);
        String msg = getDynamicMsg();
        Toast.makeText(this, msg + ", 结果：" + result, Toast.LENGTH_SHORT).show();
    }

    /**
     * 通过使用env->RegisterNatives(jClazz, methods, nMethods)
     * 来进行动态注册的NativeMethods
     */
    public native int nativeAddVariable(int x, int y);

    public native String getDynamicMsg();
}