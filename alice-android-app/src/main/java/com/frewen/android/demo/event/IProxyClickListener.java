package com.frewen.android.demo.event;

import android.view.View;

/**
 * @filename: IProxyClickListener
 * @introduction:
 * @author: Frewen.Wong
 * @time: 2020/9/15 19:31
 *         Copyright ©2020 Frewen.Wong. All Rights Reserved.
 */
public interface IProxyClickListener {

    boolean onProxyClick(WrapClickListener wrap, View v);


    /**
     * 我们定义一个内部类
     */
    class WrapClickListener implements View.OnClickListener {

        public WrapClickListener(View.OnClickListener l, IProxyClickListener proxyListener) {
            mBaseListener = l;
            mProxyListener = proxyListener;
        }

        IProxyClickListener mProxyListener;
        View.OnClickListener mBaseListener;

        @Override
        public void onClick(View v) {
            boolean handled = mProxyListener == null ? false : mProxyListener.onProxyClick(WrapClickListener.this, v);
            if (!handled && mBaseListener != null) {
                mBaseListener.onClick(v);
            }
        }
    }
}
