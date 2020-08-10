package com.frewen.android.demo.ui

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.frewen.android.demo.R
import com.frewen.android.demo.databinding.ActivityHomeBinding
import com.frewen.android.demo.navigation.NavGraphBuilder
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * HomeActivity作为几个Fragment的Tab页面。一定要去实现HasSupportFragmentInjector这个接口
 * 否则，几个Fragment无法进行注入操作
 * 会报：
 * *Process: com.frewen.android.demo.debug, PID: 2570
 *   java.lang.IllegalArgumentException: No injector was found for com.frewen.android.demo.ui.discovery.DiscoveryFragment
 *   没有对应注入器
 *
 *
 * Hilt:在 Application 类中设置了 Hilt 且有了应用级组件后，Hilt 可以为带有 @AndroidEntryPoint 注释的其他 Android 类提供依赖项：
 */
@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), HasSupportFragmentInjector, BottomNavigationView.OnNavigationItemSelectedListener {

    companion object {
        private const val TAG = "HomeActivity"
    }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // setContentView(R.layout.activity_home)
        // 如果，这个页面也使用DataBinding的的布局,则使用下面的布局setContentView方式
        DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navController = findNavController(R.id.nav_host_fragment)

        // 配置AppBar的配置
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //val appBarConfiguration = AppBarConfiguration(setOf(
        //       R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_profile))
        // setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setOnNavigationItemSelectedListener(this)

        // 使用我们自己的NavGraph
        NavGraphBuilder.build(navController, this, R.id.nav_host_fragment)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.i(TAG, "item == " + item.itemId)
        navController.navigate(item.itemId)
        return !TextUtils.isEmpty(item.title)
    }


    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    override fun supportFragmentInjector() = dispatchingAndroidInjector
}
