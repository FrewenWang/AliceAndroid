package com.frewen.android.demo.business.samples.animation.v1;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.annotation.UiThread;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FrameSurfaceViewV1 extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = FrameSurfaceViewV1.class.getSimpleName();
    public static final int MODE_REPEAT = 0;
    public static final int MODE_ONCE = 1;
    public static final int MODE_TWO = 2;
    private int mRepeatMode = MODE_REPEAT;

    private AssetManager assetManager;
    private List<String> mPathList;
    private DrawRunnable mDrawRunnable;
    private AnimationCallback mCb;
    private boolean mSupportInBitmap = false;
    private SparseArray<Bitmap> mBitmapCache;
    private long mFrameInterval = 25L;

    private int mTotalCount;
    private BitmapFactory.Options mOptions;
    // 由于属性动画最大会当大到1.4倍,为了View能正常显示，因此绘图时必须相应都缩小1.4倍
    private static final float ANIM_ZOOM = 1f;
    private SurfaceHolder mSurfaceHolder;
    private Canvas mCanvas;
    private Matrix mDrawMatrix;
    private PaintFlagsDrawFilter mDrawPaint;
    private Paint mPaint = new Paint();
    private Handler mH;
    private HandlerThread mHandlerThread;

    private String mAssertPath;
    private Bitmap currentBitmap;

    public FrameSurfaceViewV1(Context context) {
        this(context, null);
    }

    public FrameSurfaceViewV1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FrameSurfaceViewV1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        assetManager = context.getAssets();
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        init();
    }

    private void init() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
        mSurfaceHolder.addCallback(this);
        setZOrderOnTop(true);
        mDrawPaint = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        mPaint.setFilterBitmap(true);
        configureDrawMatrix();
    }

    public void setFrameInterval(long time) {
        mFrameInterval = time;
    }

    /**
     * 根据当前皮肤主题加载对应皮肤包下的Assets资源
     *
     * @param assertPath
     */
    @UiThread
    public void startAnimalByTheme(String assertPath) {
        // 获取当前的主题
        // String themeValue = Theme.currentThemeValue(SkinUtils.getInstance().getCurrentTheme());
        // assertPath = themeValue + "/" + assertPath;
        // startAnimal(assertPath);
    }

    @UiThread
    public void startAnimal(String assertPath) {
        mAssertPath = assertPath;
        Log.d(TAG, "startAnimal assertPath: " + mAssertPath);
        setVisibility(View.VISIBLE);
        if (mBitmapCache == null) {
            mBitmapCache = new SparseArray<>();
        }

        if (mOptions == null) {
            mOptions = new BitmapFactory.Options();
        }
        try {
            String[] list = assetManager.list(assertPath);
            List<String> images = Arrays.asList(list);
            initPathList(images);
            stop();
            startDraw();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置动画模式：默认是重复播放
     * 沿用 Animation 模式
     *
     * @param repeatMode
     */
    public void setRepeatMode(int repeatMode) {
        mRepeatMode = repeatMode;
        if (mRepeatMode == MODE_ONCE) {
            mSupportInBitmap = true;
        } else {
            mSupportInBitmap = false;
        }
    }

    private int mNums;

    public void setCounts(int count) {
        mNums = count;
    }

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (GONE == visibility || INVISIBLE == visibility) {
            stop();
            clearSurface();
        }
    }

    private void initPathList(List<String> pathList) {
        this.mPathList = pathList;
        if (mPathList == null || mPathList.size() == 0) {
            throw new NullPointerException("pathList is null");
        }
        mTotalCount = mPathList.size();
        Collections.sort(pathList);
    }

    private void startDraw() {
        decodeBitmap();
        if (mDrawRunnable == null) {
            mDrawRunnable = new DrawRunnable(mSurfaceHolder);
        }
        mDrawRunnable.init();
        if (mH == null) {
            mHandlerThread = new HandlerThread(TAG);
            mHandlerThread.start();
            mH = new Handler(mHandlerThread.getLooper());
        }
        mH.post(mDrawRunnable);
    }

    private void decodeBitmap() {
        if (mSupportInBitmap) {
            mOptions.inMutable = true;
            mOptions.inSampleSize = 1;
        }
        mOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        mBitmapCache.clear();
        if (!mSupportInBitmap) {
            for (int i = 0; i < mTotalCount; i++) {
                mBitmapCache.put(i, decodeBitmapReal(mPathList.get(i)));
            }
        }
    }

    private void clearSurface() {
        try {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                mCanvas = mSurfaceHolder.lockHardwareCanvas();
//            } else {
//
//            }
            mCanvas = mSurfaceHolder.lockCanvas();
            if (mCanvas != null) {
                mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null) {
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    public void stop() {
        if (mDrawRunnable != null) {
            mDrawRunnable.isDrawing = false;
        }

        if (mH != null) {
            mH.removeCallbacksAndMessages(null);
        }
    }

    public void resume() {
        if (mDrawRunnable != null && mH != null) {
            mDrawRunnable.isDrawing = true;
            mH.post(mDrawRunnable);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    private void configureDrawMatrix() {
        mDrawMatrix = new Matrix();
//        float scale = 1.0f / ANIM_ZOOM;
//        float dx = Math.round((mWidth - mWidth * scale) * 0.5f);
//        float dy = Math.round((mHeight - mHeight * scale) * 0.5f);
//        RectF srcRect = new RectF(0, 0, 160, 120);
//        RectF dstRect = new RectF(0, 0, mWidth, mHeight);
//        mDrawMatrix.setRectToRect(srcRect, dstRect, Matrix.ScaleToFit.FILL);
//        mDrawMatrix.postScale(scale, scale);
//        mDrawMatrix.postTranslate(dx, dy);
    }

    private Bitmap decodeBitmapReal(String path) {
        Bitmap bitmap = null;
        try {
            InputStream stream = assetManager.open(mAssertPath + File.separator + path);
            bitmap = BitmapFactory.decodeStream(stream, null, mOptions);
            if (mSupportInBitmap) {
                mOptions.inBitmap = bitmap;
            }
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private int mCount;

    private final class DrawRunnable implements Runnable {

        private int position;
        private boolean isDrawing;
        private SurfaceHolder surfaceHolder;

        public DrawRunnable(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        @Override
        public void run() {
            while (isDrawing) {
                try {
                    drawBitmap();
                    Thread.sleep(mFrameInterval);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


        private void drawBitmap() {
            if (position >= mTotalCount) {
                position = 0;
                if (mRepeatMode == MODE_REPEAT) {
//                    position = 0;
                } else if (mRepeatMode == MODE_ONCE) {
                    isDrawing = false;
                    if (mCb != null) {
                        mCb.onFinish();
                    }
                    return;
                } else if (mRepeatMode == MODE_TWO) {
                    if (mCount >= mNums - 1) {
                        if (mCb != null) {
                            mCb.onFinish();
                            Log.d(TAG, "position is: " + position + " | mTotalCount is: " + mTotalCount);
                        }
                        isDrawing = false;
                        return;
                    } else {
                        mCount++;
                    }
                }

            }

            if (mSupportInBitmap) {
                currentBitmap = decodeBitmapReal(mPathList.get(position));
            } else {
                currentBitmap = mBitmapCache.get(position);
            }
            if (currentBitmap == null) {
                isDrawing = false;
                return;
            }
            try {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                    mCanvas = surfaceHolder.lockHardwareCanvas();
//                } else {
//
//                }
                mCanvas = surfaceHolder.lockCanvas();
                if (mCanvas != null && null != currentBitmap) {
                    mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                    mCanvas.setDrawFilter(mDrawPaint);
                    mCanvas.drawBitmap(currentBitmap, mDrawMatrix, mPaint);
                    position++;
                }
//                if (VLog.DEBUG && mCanvas != null) {
//                    VLog.d(TAG, "硬件加速" + mCanvas.isHardwareAccelerated());
//                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (mCanvas != null) {
                    surfaceHolder.unlockCanvasAndPost(mCanvas);
                }
            }
        }

        public void init() {
            position = 0;
            isDrawing = true;
            mCount = 0;
            Log.d(TAG, "init position is: " + position);
        }

    }

    public void setAnimationCallback(AnimationCallback cb) {
        this.mCb = cb;
    }

    public interface AnimationCallback {
        void onFinish();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        release();
        Log.d(TAG, "onDetachedFromWindow");
    }

    public void release() {
        setVisibility(INVISIBLE);
        if (mH != null) {
            mHandlerThread.quit();
            mH.removeCallbacksAndMessages(null);
            mH = null;
        }

        if (mDrawRunnable != null) {
            mDrawRunnable.isDrawing = false;
            mDrawRunnable = null;
        }
        if (mOptions != null) {
            if (mOptions.inBitmap != null) {
                mOptions.inBitmap.recycle();
                mOptions.inBitmap = null;
                mOptions = null;
            }
        }

        if (mBitmapCache != null) {
            mBitmapCache.clear();
            mBitmapCache = null;
        }
        currentBitmap = null;
        mSurfaceHolder.removeCallback(this);
        mCount = 0;
    }

}
