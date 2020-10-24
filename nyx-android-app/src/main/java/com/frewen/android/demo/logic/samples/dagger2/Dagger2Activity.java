package com.frewen.android.demo.logic.samples.dagger2;

import android.os.Bundle;
import android.util.Log;

import com.frewen.android.demo.R;
import com.frewen.android.demo.logic.samples.dagger2.data.InjectData;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @filename: Dagger2Activity
 * @introduction: Dagger2的测试代码
 * @author: Frewen.Wong
 * @time: 2020/4/29 11:31
 *         Copyright ©2019 Frewen.Wong. All Rights Reserved.
 */
public class Dagger2Activity extends AppCompatActivity {
    private static final String TAG = "Dagger2Activity";

    @Inject
    @Named("noParam")
    InjectData mInjectData1;

    @Inject
    @Named("withParam")
    InjectData mInjectData2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger2);
        //  如果我们的Module需要传入一些参数，我们可以考虑自行设置dagger2Module
        DaggerDagger2Component.builder().dagger2Module(new Dagger2Module()).build().injectDagger2Activity(this);

        printData();
    }

    private void printData() {
        Log.d(TAG, "FMsg:printData() called mInjectData = " + mInjectData1.getName());
        Log.d(TAG, "FMsg:printData() called mInjectData = " + mInjectData2.getName());
    }
}
