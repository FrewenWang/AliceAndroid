package com.frewen.android.demo.samples;

import android.graphics.Camera;
import android.os.Bundle;
import android.view.SurfaceView;

import com.frewen.android.demo.R;
import com.frewen.permissions.core.AuraPermissions;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.disposables.Disposable;

/**
 * PermissionActivity
 */
public class PermissionActivity extends AppCompatActivity {
    private static final String TAG = "T:PermissionActivity";

    private Camera mCamera;
    private SurfaceView mSurfaceView;
    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AuraPermissions rxPermissions = new AuraPermissions(this);
        rxPermissions.setLogging(true);

        setContentView(R.layout.activity_permission);


    }
}
