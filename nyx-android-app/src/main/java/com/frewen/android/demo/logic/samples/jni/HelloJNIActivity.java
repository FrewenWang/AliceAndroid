package com.frewen.android.demo.logic.samples.jni;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.frewen.android.demo.R;

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
    /**
     * 加载loadLibrary
     * 用于在应用程序启动时加载“ hello-jni”库。
     */
    static {
        System.loadLibrary("hello-jni");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_jni);

        // Example of a call to a native method
        TextView tv = findViewById(R.id.sample_text);
        String msg = stringFromJNI();
        Log.d("HelloJNIActivity", msg);
        tv.setText(secondStringFromJNI2("hello"));

        TextView tv2 = findViewById(R.id.textView2);
        tv2.setText(stringFromJNIUser("hello"));
    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     * 创建本地方法，标记这个一个C或者C++方法
     */
    public native String stringFromJNI();

    public native String stringFromJNI2(String msg);


    /**
     * 第二种Java调用C/C++的方法
     *
     * RegisterNative
     */
    public native String secondStringFromJNI2(String msg);

    /**
     *
     * @param msg
     */
    public native String stringFromJNIUser(String msg);

}