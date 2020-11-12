package com.frewen.android.demo.logic.samples.animation.surface;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class BaseSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    /**
     * SurfaceView的刷新帧率
     */
    public static final int DEFAULT_FRAME_DURATION_MILLISECOND = 50;

    //用于计算帧数据的线程
    private HandlerThread handlerThread;
    private Handler handler;

    //帧刷新频率
    private int frameDuration = DEFAULT_FRAME_DURATION_MILLISECOND;

    //用于绘制帧的画布
    private Canvas canvas;
    protected SurfaceHolder mSurfaceHolder;

    private boolean isAlive;

    public BaseSurfaceView(Context context) {
        this(context, null);
    }

    public BaseSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intiView();
    }

    /**
     *
     */
    private void intiView() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        //设置透明背景，否则SurfaceView背景是黑的
        setBackgroundTransparent();
    }

    private void setBackgroundTransparent() {
        // 通过SurfaceHolder来设置透明背景，否则是黑色的
        mSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
        setZOrderOnTop(true);
    }


    protected void setFrameDuration(int frameDuration) {
        this.frameDuration = frameDuration;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isAlive = true;
        startDrawThread();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopDrawThread();
        isAlive = false;
    }

    /**
     * 用HandlerThread作为独立帧绘制线程，
     * 好处是可以通过与其绑定的Handler方便地实现“每隔一段时间刷新”，
     * 而且在Surface被销毁的时候可以方便的调用HandlerThread.quit()来结束线程执行的逻辑。
     */
    private void startDrawThread() {
        handlerThread = new HandlerThread("SurfaceViewThread");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
        handler.post(new DrawTask());
    }

    private void stopDrawThread() {
        if (handlerThread != null) {
            handlerThread.quit();
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    private class DrawTask implements Runnable {
        @Override
        public void run() {
            if (!isAlive) {
                return;
            }
            try {
                //1.获取画布
                canvas = getHolder().lockCanvas();
                //2.绘制一帧
                onFrameDraw(canvas);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != canvas && null != mSurfaceHolder) {
                    //3.将帧数据提交
                    mSurfaceHolder.unlockCanvasAndPost(canvas);
                    //4.一帧绘制结束
                    onFrameDrawFinish();
                }
            }

            //不停的将自己推送到绘制线程的消息队列以实现帧刷新
            handler.postDelayed(this, frameDuration);
        }
    }

    protected abstract void onFrameDrawFinish();

    protected abstract void onFrameDraw(Canvas canvas);
}
