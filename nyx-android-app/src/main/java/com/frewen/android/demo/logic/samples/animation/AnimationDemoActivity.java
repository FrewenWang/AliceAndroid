package com.frewen.android.demo.logic.samples.animation;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import com.frewen.android.demo.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;

import com.frewen.android.demo.logic.samples.animation.ui.main.SectionsPagerAdapter;

public class AnimationDemoActivity extends AppCompatActivity {

    private AnimationDrawable animationDrawable;
    private ImageView mLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_demo);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        initLoading();

    }

    private void initLoading() {
        mLoading = findViewById(R.id.iv_loading);
        animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.anim_loading, null);
        mLoading.setImageDrawable(animationDrawable);
    }
}