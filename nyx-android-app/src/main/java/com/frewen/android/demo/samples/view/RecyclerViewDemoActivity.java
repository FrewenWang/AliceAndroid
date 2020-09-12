package com.frewen.android.demo.samples.view;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import com.frewen.android.demo.R;
import com.frewen.android.demo.samples.view.recyclerview.AdapterSampleActivity;
import com.frewen.android.demo.samples.view.recyclerview.AnimatorSampleActivity;

public class RecyclerViewDemoActivity extends AppCompatActivity {
    private static final String TAG = "RecyclerViewActivity";
    public static final String KEY_GRID = "GRID";
    private boolean enabledGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_demo);

        ButterKnife.bind(this);

    }


    @OnClick(R.id.btn_animator_sample)
    public void btnAnimatorSample(View view) {
        Log.d(TAG, "FMsg:btnAnimatorSample() called with: view = [" + view + "]");
        Intent intent = new Intent(this, AnimatorSampleActivity.class);
        intent.putExtra(KEY_GRID, enabledGrid);
        startActivity(intent);
    }


    @OnClick(R.id.btn_adapter_sample)
    public void btnAdapterSample(View view) {
        Log.d(TAG, "FMsg:btnAdapterSample() called with: view = [" + view + "]");
        Intent intent = new Intent(this, AdapterSampleActivity.class);
        intent.putExtra(KEY_GRID, enabledGrid);
        startActivity(intent);
    }


    @OnCheckedChanged(R.id.grid)
    public void setGridTag(CompoundButton button, boolean checked) {
        Log.d(TAG, "FMsg:setGridTag() called with: view = [" + button + "], isChecked = [" + checked + "]");
        enabledGrid = checked;
    }
}