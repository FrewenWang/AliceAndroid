package com.frewen.nyx.hilt.demo.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.frewen.nyx.hilt.demo.NyxHiltApp
import com.frewen.nyx.hilt.demo.R
import com.frewen.nyx.hilt.demo.data.LoggerLocalDataSource
import com.frewen.nyx.hilt.demo.db.entity.Log

class SecondFragment : Fragment() {

    private lateinit var logger: LoggerLocalDataSource

    private lateinit var recyclerView: RecyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)

        populateFields(context)
    }

    private fun populateFields(context: Context) {
        logger = (context.applicationContext as NyxHiltApp).roomServiceProvider.loggerLocalDataSource
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
