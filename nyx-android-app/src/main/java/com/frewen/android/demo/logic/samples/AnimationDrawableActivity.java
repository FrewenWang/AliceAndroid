package com.frewen.android.demo.logic.samples;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.frewen.android.demo.R;
import com.frewen.android.demo.logic.samples.animation.v1.FrameAnimDrawable;

public class AnimationDrawableActivity extends AppCompatActivity {

    private FrameAnimDrawable mFrameAnimDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_drawable);

        mFrameAnimDrawable = findViewById(R.id.frame_surfaceView);
        mFrameAnimDrawable.startAnim("pay_scan");
    }


}