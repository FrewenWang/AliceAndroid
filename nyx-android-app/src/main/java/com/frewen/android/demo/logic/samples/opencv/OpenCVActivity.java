package com.frewen.android.demo.logic.samples.opencv;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.frewen.android.demo.R;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class OpenCVActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "OpenCVActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_cv);


        iniLoadOpenCV();
        Button processBtn = this.findViewById(R.id.process_btn);
        processBtn.setOnClickListener(this);
    }

    private void iniLoadOpenCV() {
        boolean success = OpenCVLoader.initDebug();
        if (success) {
            Log.i(TAG, "OpenCV Libraries loaded...");
        } else {
            Toast.makeText(this.getApplicationContext(),
                    "WARNING: Could not load OpenCV Libraries! ",
                    Toast.LENGTH_LONG).show();
            Log.e(TAG, "Could not load OpenCV Libraries!...");
        }
    }

    @Override
    public void onClick(View v) {
        Bitmap bitmap = BitmapFactory.decodeResource(
                OpenCVActivity.this.getResources(), R.drawable.test_avatar);
        Mat src = new Mat();
        Mat dst = new Mat();
        Log.d(TAG, "FMsg:onClick() called with: bitmap = [" + bitmap + "]");
        Utils.bitmapToMat(bitmap, src);
        Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGRA2GRAY);
        Utils.matToBitmap(dst, bitmap);
        ImageView iv = this.findViewById(R.id.sample_img);
        iv.setImageBitmap(bitmap);
        src.release();
        dst.release();
    }
}