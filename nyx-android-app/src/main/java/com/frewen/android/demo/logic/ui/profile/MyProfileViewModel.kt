package com.frewen.android.demo.logic.ui.profile

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.tencent.bugly.crashreport.CrashReport
import javax.inject.Inject

/**
 * 这个是ViewModel的实现，我们来简单看一下这个类的实现：
 * 这个ViewModel我们来让他继承自AndroidViewModel
 */
class MyProfileViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext!!

    val loginName = ObservableField<String>()
    val password = ObservableField<String>()

    fun login() {
        Log.i("MyProfileViewModel", "========login=============")
        Toast.makeText(context, "登录 ${loginName.get()},${password.get()}", Toast.LENGTH_LONG).show()

        // TODO  测试Bugly的上报
        CrashReport.testJavaCrash();
    }
}