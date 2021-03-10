package com.frewen.android.demo.performance

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.frewen.android.demo.R

class PerformanceActivity : AppCompatActivity() {
    
    companion object {
        private const val TAG = "PerformanceActivity"
    }
    
    @SuppressLint("HandlerLeak")
    private val memoryShakeHandler: Handler = object : Handler() {
        
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            // 模拟内存抖动
            for (index in 1..1000) {
                // val arr = Array<String>(100000)
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_performance)

//        testANR()
        
        testMemoryShake()
    }
    
    /**
     * 测试内存抖动的Demo
     */
    private fun testMemoryShake() {
    
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
            synchronized(PerformanceActivity::class.java) {
                try {
                    Log.d(TAG, "synchronized testANR() called")
                    Thread.sleep(20 * 1000.toLong())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()
        
        
        Thread.sleep(2 * 1000.toLong())
        
        synchronized(PerformanceActivity::class.java) {
            Log.d(Companion.TAG, "testANR() called")
            
        }
    }
    
    
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
    }
}