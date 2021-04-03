package com.frewen.android.demo.logic.samples.course

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.frewen.android.demo.R
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * 学习菜鸟窝教程的Activity
 */
class CaiNiaoCourceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cainiao_course)
        
        // 获取BottomNavigationView
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        // findNavController找到底部导航栏的控制器。他其实是Activity的一个扩展函数
        // 最终调用的是 Navigation.findNavController(this, viewId)
        val navController = findNavController(R.id.nav_host_fragment)
        
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_recommend, R.id.navigation_discovery, R.id.navigation_profile))
        //根据navController和appBarConfiguration 设置ActionBar的显示标题
        /// 注意，这里有个坑，加入你的主题是NoActionBar的话。这个地方会报错
        //        Caused by: java.lang.NullPointerException: Attempt to invoke virtual method 'void androidx.appcompat.app.ActionBar
        //        .setTitle(java.lang.CharSequence)' on a null object reference
        //        at androidx.navigation.ui.ActionBarOnDestinationChangedListener.setTitle(ActionBarOnDestinationChangedListener.java:48)
        //        at androidx.navigation.ui.AbstractAppBarOnDestinationChangedListener.onDestinationChanged(AbstractAppBarOnDestinationChangedListener.java:103)
        //        at androidx.navigation.NavController.addOnDestinationChangedListener(NavController.java:233)
        //        at androidx.navigation.ui.NavigationUI.setupActionBarWithNavController(NavigationUI.java:227)
        //        at androidx.navigation.ui.ActivityKt.setupActionBarWithNavController(Activity.kt:74)
        //        at com.frewen.android.demo.logic.samples.tiktok.TikTokActivity.onCreate(TikTokActivity.kt:29)
        // 所以，切记，调用这个方法，需要保证主题是有ActionBar。否则我们可以不调用这个，也可以设置有标题的ActionBar
        setupActionBarWithNavController(navController, appBarConfiguration)
        // 将navController和BottomNavigationView进行绑定
        navView.setupWithNavController(navController)
    }
}