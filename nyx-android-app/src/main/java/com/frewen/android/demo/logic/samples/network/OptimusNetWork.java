package com.frewen.android.demo.logic.samples.network;

import com.ainirobot.optimus.network.OptimusNetClient;
import com.google.gson.Gson;

/**
 * @filename: OptimusNetWork
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2019/4/13 19:41
 * Copyright ©2018 Frewen.Wong. All Rights Reserved.
 */
public class OptimusNetWork {

    private final OptimusNetClient mHttpClient;
    private final Gson mGson;

    private OptimusNetWork() {
        mHttpClient = new OptimusNetClient();
        mGson = new Gson();
    }

    private static class OptimusNetWorkHolder {
        private static final OptimusNetWork INSTANCE = new OptimusNetWork();
    }

    public final static OptimusNetWork getInstance() {
        return OptimusNetWorkHolder.INSTANCE;
    }

    /**
     * 发送多媒体指令
     *
     * @param baseReqBean
     */
    public void sendCommand(BaseReqBean baseReqBean) {

    }


}
