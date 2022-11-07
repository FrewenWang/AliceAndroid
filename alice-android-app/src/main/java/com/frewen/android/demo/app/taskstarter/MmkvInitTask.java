package com.frewen.android.demo.app.taskstarter;

import android.content.Context;

import com.frewen.aura.framework.taskstarter.AbsLaunchTask;
import com.tencent.mmkv.MMKV;

/**
 * @filename: MmkvInitTask
 * @author: Frewen.Wong
 * @time: 2021/6/14 09:45
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
public class MmkvInitTask extends AbsLaunchTask {
    private Context mContext;

    public MmkvInitTask(Context context) {
        this.mContext = context;
    }

    @Override
    public void execute() {
        initMMKV();
    }


    private void initMMKV() {
        // https://github.com/Tencent/MMKV
        String rootDir = MMKV.initialize(mContext.getFilesDir().getAbsolutePath() + "/mmkv");
        System.out.println("mmkv root: " + rootDir);
    }
}
