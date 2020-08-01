package com.frewen.android.demo.samples.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.frewen.android.demo.R;
import com.frewen.android.demo.samples.view.recyclerview.MainRecyclerViewAdapter;

public class RecyclerViewDemoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_demo);


        /// findViewById
        recyclerView = findViewById(R.id.recycler_view);
        /// 设置纵向的ListView的布局
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /// 设置ItemAnimation的默认的动画形式
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // 增加Item的布局的分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // 自定义
        MainRecyclerViewAdapter adapter = new MainRecyclerViewAdapter(this);




    }
}