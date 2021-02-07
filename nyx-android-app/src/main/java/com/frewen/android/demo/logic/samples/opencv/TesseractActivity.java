package com.frewen.android.demo.logic.samples.opencv;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import com.frewen.android.demo.R;
import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class TesseractActivity extends AppCompatActivity {
    private static final String TAG = "TesseractActivity";
    private static final String DEFAULT_LANGUAGE = "UTF-8";
    private TessBaseAPI tessBaseApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tesseract);


        initTesseract();
    }

    private void initTesseract() {
        try {
            initTessBaseAPI();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void initTessBaseAPI() throws IOException {
        tessBaseApi = new TessBaseAPI();
        String dataPath = Environment.getExternalStorageDirectory()
                + File.separator + "tesseract" + File.separator;
        Log.d(TAG, "initTessBaseAPI dataPath:" + dataPath);

        File dir = new File(dataPath + "data" + File.separator);
        if (!dir.exists()) {
            dir.mkdirs();
            // 读取输入流
            InputStream inputStream = getResources().openRawResource(R.raw.frame10);

            File file = new File(dir, "demo");
            FileOutputStream outputStream = new FileOutputStream(file);

            byte[] buff = new byte[2014];
            int len = 0;
            while ((len = inputStream.read(buff)) != -1) {
                outputStream.write(buff, 0, len);
            }

            inputStream.close();
            outputStream.close();
        }

        boolean success = tessBaseApi.init(dataPath, DEFAULT_LANGUAGE);

        Log.i(TAG, "FMsg:initTessBaseAPI: success : " + success);
    }

    private void recognizeTextImage() {
        if (fileUri == null) return;
        Bitmap bmp = BitmapFactory.decodeFile(fileUri.getPath());
        baseApi.setImage(bmp);
        String recognizedText = baseApi.getUTF8Text();
        TextView txtView = findViewById(R.id.text_result_id);
        if (!recognizedText.isEmpty()) {
            txtView.append("识别结果：\n" + recognizedText);
        }
    }
}