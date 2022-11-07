package com.frewen.android.demo.business.samples.view.recyclerview

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.frewen.android.demo.R
import com.frewen.android.demo.business.model.data.SampleData
import com.frewen.android.demo.business.samples.view.RecyclerViewDemoActivity
import kotlinx.android.synthetic.main.activity_animator_sample.*

class AnimatorSampleActivity : AppCompatActivity() {
    private lateinit var layoutManager: LinearLayoutManager

    /**
     * SampleData.LIST.toMutableList()是一个扩展函数
     */
    private val adapter = MainRecyclerViewAdapter(this, SampleData.LIST.toMutableList())

    /**
     * 可见性修饰符 internal 意味着该成员只在相同模块内可见。更具体地说， 一个模块是编译在一起的一套 Kotlin 文件：
     */
    internal enum class AnimationType() {
        FadeIn,
        FadeUp
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animator_sample)

        /// 设置SupportActionBar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(true)

        layoutManager = if (intent.getBooleanExtra(RecyclerViewDemoActivity.KEY_GRID, false)) {
            GridLayoutManager(this, 2)
        } else {
            LinearLayoutManager(this)
        }
        /**
         * 从结构上来看apply函数和run函数很像，
         * 唯一不同点就是它们各自返回的值不一样，
         * run函数是以闭包形式返回最后一行代码的值，而apply函数的返回的是传入对象的本身。
         */
        listView.apply {
            Log.d("AnimatorSampleActivity", "=========listView.apply() called===============")
            adapter = this@AnimatorSampleActivity.adapter
            layoutManager = this@AnimatorSampleActivity.layoutManager
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            listView.setOnScrollChangeListener(object : RecyclerView.OnScrollListener(), View.OnScrollChangeListener {
                override fun onScrollChange(v: View?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                    // 获取RecyclerView的layoutManager
                    val layoutManager: RecyclerView.LayoutManager? = this@AnimatorSampleActivity.listView.layoutManager
                }
            })
        }

        val spinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
        for (type in AnimationType.values()) {
            spinnerAdapter.add(type.name)
        }
        spinner.adapter = spinnerAdapter

        /**
         * 在RecyclerView的第一个可见Item来增加一个Item
         */
        findViewById<View>(R.id.addBtn).setOnClickListener {
            var position = layoutManager.findFirstVisibleItemPosition()
            adapter.add("newly added item", position + 1)
        }

        findViewById<View>(R.id.delBtn).setOnClickListener {
            var position = layoutManager.findFirstVisibleItemPosition()
            adapter.remove(1)
        }
    }


}