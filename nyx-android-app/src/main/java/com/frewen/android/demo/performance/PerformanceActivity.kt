package com.frewen.android.demo.performance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.frewen.android.demo.R

class PerformanceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_performance)
    }
    
    fun enterPerformancePage(view: View) {
        startActivity(Intent(this@PerformanceActivity, TestPerformanceActivity::class.java))
    }
}