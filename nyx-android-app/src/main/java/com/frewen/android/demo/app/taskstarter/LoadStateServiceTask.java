package com.frewen.android.demo.app.taskstarter;

import com.frewen.android.demo.ui.loadstate.EmptyCallback;
import com.frewen.android.demo.ui.loadstate.ErrorCallback;
import com.frewen.android.demo.ui.loadstate.LoadingStateCallback;
import com.frewen.aura.framework.taskstarter.AbsLaunchTask;
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.LoadSir;

/**
 * @filename: LoadStateServiceTask
 * @author: Frewen.Wong
 * @time: 2021/6/27 23:08
 * @version: 1.0.0
 * @introduction: Class File Init
 * @copyright: Copyright ©2021 Frewen.Wong. All Rights Reserved.
 */
public class LoadStateServiceTask extends AbsLaunchTask {
    @Override
    public void execute() {
        initLoadStateServiceTask();
    }

    private void initLoadStateServiceTask() {
        //界面加载管理 初始化
        LoadSir.beginBuilder()
                .addCallback(new LoadingStateCallback())//加载
                .addCallback(new ErrorCallback())//错误
                .addCallback(new EmptyCallback())//空
                .setDefaultCallback(SuccessCallback.class)//设置默认加载状态页
                .commit();
    }
}
