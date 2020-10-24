package com.frewen.android.demo.logic.samples.net

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.frewen.android.demo.R

/**
 * 文章参考:https://juejin.im/post/6844903582743920648
 *
 */
class ProtobufActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_protobuf)


    }

    override fun onStart() {
        super.onStart()

    }

    override fun onResume() {
        super.onResume()
        finish()
    }
}