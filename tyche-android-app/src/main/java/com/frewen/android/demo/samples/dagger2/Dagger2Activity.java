package com.frewen.android.demo.samples.dagger2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.frewen.android.demo.R;

import javax.inject.Inject;

public class Dagger2Activity extends AppCompatActivity {
    @Inject
    SingletonData mSingletonData1;
    @Inject
    SingletonData mSingletonData2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger2);
    }
}
