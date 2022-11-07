package com.frewen.android.demo.performance

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.frewen.android.demo.R

/**
 * 在 Java 语言中，可作为 GC Root 的对象包括以下几种：
 *  虚拟机栈（栈帧中的本地变量表）中引用的对象
 *  方法区中类静态属性引用的对象
 *  方法区中常量引用的对象
 *  本地方法栈中 JNI（即一般说的 Native 方法）引用的对象
 *  运行中的线程。
 *  由引导类加载器加载的对象。
 *  GC控制的对象。
 **/
class TestPerformanceActivity : AppCompatActivity() {
    
    companion object {
        private const val TAG = "PerformanceActivity"
        val leakObjList1: MutableList<Handler> = arrayListOf()
        val leakObjList2: MutableList<InnerClass> = arrayListOf()
        val leakObjList3: MutableList<View?> = arrayListOf()
    }
    
    private var testPerformance: TextView? = null
    private val memoryShakeHandler: Handler = object : Handler() {
        
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            // 模拟内存抖动
            for (index in 1..1000) {
                val arr = arrayOfNulls<String>(100000)
            }
            Log.d(TAG, "handleMessage() called with: msg = $msg")
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_performance)
        
        testPerformance = findViewById(R.id.tv_test_performance)

//        testANR()
        
        testMemoryShake()
        
        testMemoryLeak()
    }
    
    /**
     * 测试内存泄漏
     * 1、非静态内部类的静态实例
     * 2、多线程相关的匿名内部类/非静态内部类
     * 3、Handler的内存泄漏
     * 4、未正确使用Context
     * 5、静态的View对象
     * 6、WebView造成的泄漏
     * 7、资源对象没有关闭
     * 8、集合中的对象没有进行清理
     *
     */
    private fun testMemoryLeak() {
//        leakObjList1.add(memoryShakeHandler)
//        leakObjList2.add(InnerClass())
        leakObjList3.add(testPerformance)
    }
    
    /**
     * 测试内存抖动的Demo
     */
    private fun testMemoryShake() {
        memoryShakeHandler.sendEmptyMessageDelayed(1, 1 * 1000)
    }
    
    /**
     * 测试ANR的性能的相关逻辑
     */
    private fun testANR() {
        // 这种方法是在Kotlin中使用线程当然方式
//       Thread(object : Runnable{
//           override fun run() {
//
//           }
//       }).start()
        
        /**
         * Kotlin中实例化一个线程的做法
         * 我们可以后续进行学习
         */
        Thread {
            synchronized(TestPerformanceActivity::class.java) {
                try {
                    Log.d(TAG, "synchronized testANR() called")
                    Thread.sleep(20 * 1000.toLong())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()
        
        
        Thread.sleep(2 * 1000.toLong())
        
        synchronized(TestPerformanceActivity::class.java) {
            Log.d(Companion.TAG, "testANR() called")
            
        }
    }
    
    
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
    }
    
    fun enterPerformancePage(view: View) {}
    
    inner class InnerClass {
        val name: Array<String?> = arrayOfNulls<String>(100000)
    }
}