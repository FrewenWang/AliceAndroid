package com.frewen.android.demo.business.samples.navigation.annotation.publish

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.frewen.android.aura.annotations.ActivityDestination
import com.frewen.android.demo.R

@ActivityDestination(pageUrl = "main/tabs/publish", needLogin = true)
class PublishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publish)
    }
}