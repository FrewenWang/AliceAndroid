package com.frewen.nyx.hilt.demo.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.frewen.nyx.hilt.demo.NyxHiltApp
import com.frewen.nyx.hilt.demo.R
import com.frewen.nyx.hilt.demo.data.LoggerLocalDataSource
import com.frewen.nyx.hilt.demo.navigation.DemoNavigator
import com.frewen.nyx.hilt.demo.navigation.Screens

class FirstFragment : Fragment() {

    private lateinit var navigator: DemoNavigator
    private lateinit var logger: LoggerLocalDataSource

    override fun onAttach(context: Context) {
        super.onAttach(context)

        populateFields(context)
    }

    private fun populateFields(context: Context) {
        logger = (context.applicationContext as NyxHiltApp).roomServiceProvider.loggerLocalDataSource

        navigator = (context.applicationContext as NyxHiltApp).roomServiceProvider.provideNavigator(activity!!)
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