package com.frewen.nyx.hilt.demo.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log.i
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.frewen.nyx.hilt.demo.NyxHiltApp
import com.frewen.nyx.hilt.demo.R
import com.frewen.nyx.hilt.demo.data.DataSource
import com.frewen.nyx.hilt.demo.data.LoggerDBDataSource
import com.frewen.nyx.hilt.demo.db.entity.Log
import com.frewen.nyx.hilt.demo.module.DatabaseLogger
import com.frewen.nyx.hilt.demo.module.InMemoryLogger
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SecondFragment : Fragment() {

    @DatabaseLogger
    @Inject
    lateinit var logger: DataSource

    private lateinit var recyclerView: RecyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)

        populateFields(context)
    }

    private fun populateFields(context: Context) {
        // DataSource我们通过依赖注入进行实例化
        // logger = (context.applicationContext as NyxHiltApp).roomServiceProvider.loggerLocalDataSource
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_hilt_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
        }
    }

    override fun onResume() {
        super.onResume()
        /**
         * 注意，我么进入下一个SecondFragment的里面进行查询所有的Log进行显示的时候，我们发现为空！！！
         * 为什么呢！！！！！
         * 我们看下面的日志
         * 2020-08-13 23:49:50.207 4564-4564/com.frewen.nyx.hilt.demo I/logger: logger111111111:com.frewen.nyx.hilt.demo.data.LoggerInMemoryDataSource@aab2b52
         * 2020-08-13 23:49:53.839 4564-4564/com.frewen.nyx.hilt.demo I/logger: logger22222222:com.frewen.nyx.hilt.demo.data.LoggerInMemoryDataSource@f448181
         *
         * 可以看到这两个LoggerInMemoryDataSource不是同一个对象，为什么呢？？
         * 主要因为默认注解的@Inject都是重新的对象，但是这样肯定不满足我们的需要，
         * 我们想要在SecondFragment也使用原来的logger
         *
         *我们需要在@Binds的方法加上 @ActivityScoped
         */
        android.util.Log.i("logger", "logger22222222:${logger}")
        // lamda表达式配合高阶函数
        logger.queryAllLogs { logs ->
            recyclerView.adapter =
                    LogsViewAdapter(logs)
        }
    }


}

/**
 * RecyclerView adapter for the logs list.
 */
private class LogsViewAdapter(
        private val logsDataSet: List<Log>
) : RecyclerView.Adapter<LogsViewAdapter.LogsViewHolder>() {

    class LogsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val titleTextView: TextView = view.findViewById<TextView>(R.id.tvTitle)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogsViewHolder {
        return LogsViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_recycler_view_demo, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return logsDataSet.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: LogsViewHolder, position: Int) {
        val log = logsDataSet[position]
        holder.titleTextView.text = "${log.msg}\n\t${log.timestamp}"
    }
}
