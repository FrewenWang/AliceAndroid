package com.frewen.nyx.hilt.demo.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.frewen.nyx.hilt.demo.R
import com.frewen.nyx.hilt.demo.data.DataSource
import com.frewen.nyx.hilt.demo.data.LoggerDBDataSource
import com.frewen.nyx.hilt.demo.module.InMemoryLogger
import com.frewen.nyx.hilt.demo.navigation.DemoNavigator
import com.frewen.nyx.hilt.demo.navigation.Screens
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FirstFragment : Fragment() {

    /**
     * kotlin.UninitializedPropertyAccessException: lateinit property navigator has not been initialized
     * 在Fragment中进行@Inject标记注入，我们也要记得加上：@AndroidEntryPoint
     * 否则还是无法注入的。会报上面的错误！！！！
     */
    @Inject
    lateinit var navigator: DemoNavigator

    /**
     * 下面，我们需要把DataSource这个依赖项通过依赖注入进行注入
     * 一看这个是一个接口，好的，我们轻车熟路。
     * 创建一个DataSourceModule
     */
    @InMemoryLogger
    @Inject
    lateinit var logger: DataSource

    override fun onAttach(context: Context) {
        super.onAttach(context)

        populateFields(context)
    }

    private fun populateFields(context: Context) {
        // 我们下面这两个依赖项我们也通过依赖注入进行注入
        //  logger = (context.applicationContext as NyxHiltApp).roomServiceProvider.loggerLocalDataSource

        //  navigator = (context.applicationContext as NyxHiltApp).roomServiceProvider.provideNavigator(activity!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_hilt_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        view.findViewById<Button>(R.id.button1).setOnClickListener {
            logger.insertLog("插入日志，时间戳：${System.currentTimeMillis()}")
        }

        view.findViewById<Button>(R.id.all_logs).setOnClickListener {
            navigator.navigateTo(R.id.main_container, Screens.LOGS)
        }

        view.findViewById<Button>(R.id.delete_logs).setOnClickListener {
            logger.removeLogs()
        }
    }
}