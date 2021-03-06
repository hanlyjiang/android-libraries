package com.github.hanlyjiang.lib.common.widgets;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.hanlyjiang.lib.common.ui.R;

public class WaveSurfaceView extends SurfaceView {

    private static final String TAG = WaveSurfaceView.class.getSimpleName();
    private int waveColor;
    private int waveBgColor;
    private int waveColumnCount;
    private int waveAnimationDuration;
    private int aHeight;
    private int aWidth;
    private Paint mPaint;
    private Path mPath;
    private float[] waveXIndex;
    private int phase = 180;
    private ObjectAnimator phaseAnimator;
    private final LinearInterpolator linearInterpolator = new LinearInterpolator();
    private float columnWidth;
    private SurfaceHolder mHolder;

    private HandlerThread mDrawHandlerThread;
    private Handler mDrawHandler;

    private Rect mBgRect = new Rect();

    private int mSurfaceHeight;
    private int mSurfaceWidth;

    SurfaceHolder.Callback mSHCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(@NonNull SurfaceHolder holder) {
            mHolder = holder;
            mDrawHandlerThread = new HandlerThread("waveDrawThread");
            mDrawHandlerThread.start();
            mDrawHandler = new Handler(mDrawHandlerThread.getLooper());
        }

        @Override
        public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
            mSurfaceWidth = width;
            mSurfaceHeight = height;
        }

        @Override
        public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
            mHolder = null;
            if (mDrawHandlerThread != null) {
                mDrawHandlerThread.quitSafely();
            }
            mDrawHandlerThread = null;
            mDrawHandler = null;
        }
    };

    public WaveSurfaceView(Context context) {
        super(context);
    }

    public WaveSurfaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public WaveSurfaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    public WaveSurfaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    protected void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray ts = context.getResources().obtainAttributes(attrs, R.styleable.WaveView);
        waveColor = ts.getColor(R.styleable.WaveView_waveColor, Color.WHITE);
        waveColumnCount = ts.getInteger(R.styleable.WaveView_waveColumnCount, 3);
        if (ts.hasValue(R.styleable.WaveView_waveBackgroundColor)) {
            waveBgColor = ts.getColor(R.styleable.WaveView_waveBackgroundColor, Color.WHITE);
        }
        waveAnimationDuration = ts.getInteger(R.styleable.WaveView_waveAnimationDuration, 1000);
        ts.recycle();

        mPaint = new Paint();
        mPath = new Path();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(waveColor);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        getHolder().addCallback(mSHCallback);
        setZOrderOnTop(true);
        // ???????????? ????????????
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        aWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        aHeight = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
        mBgRect.set(getPaddingLeft(), getPaddingTop(),
                MeasureSpec.getSize(widthMeasureSpec) - getPaddingRight(),
                MeasureSpec.getSize(heightMeasureSpec) - getPaddingBottom());
        calcDrawInfo();
        if (phaseAnimator != null) {
            phaseAnimator.cancel();
        }
        phaseAnimator = ObjectAnimator.ofInt(this, "phase", 0, 180);
        phaseAnimator.setDuration(waveAnimationDuration);
        phaseAnimator.setRepeatCount(ValueAnimator.INFINITE);
        phaseAnimator.setRepeatMode(ValueAnimator.RESTART);
        phaseAnimator.setInterpolator(linearInterpolator);
        phaseAnimator.start();
    }

    private void calcDrawInfo() {
        columnWidth = aWidth / (waveColumnCount * 2f - 1);
        mPaint.setStrokeWidth(columnWidth);
        // ??????
        aHeight = (int) (aHeight - columnWidth);
        waveXIndex = new float[waveColumnCount];
        waveXIndex[0] = columnWidth / 2;
        for (int i = 0; i < waveColumnCount - 1; i++) {
            waveXIndex[i + 1] += waveXIndex[i] + columnWidth * 2;
        }
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (phaseAnimator != null) {
            if (visibility == View.VISIBLE) {
                phaseAnimator.start();
            } else {
                phaseAnimator.pause();
            }
        }
    }

    private void doDraw() {
        Log.e(TAG, "doDraw:" + Thread.currentThread().getName());
        if (mHolder == null) {
            return;
        }
        Canvas canvas;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            canvas = mHolder.lockHardwareCanvas();
        } else {
            canvas = mHolder.lockCanvas();
        }
        if (canvas == null) {
            return;
        }
        int A = aHeight >> 1;
        float w = 1;
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        if (waveBgColor != 0 && !mBgRect.isEmpty()) {
            mPaint.setColor(waveBgColor);
            mPaint.setStrokeWidth(0);
            canvas.drawRect(mBgRect, mPaint);
            mPaint.setColor(waveColor);
            mPaint.setStrokeWidth(columnWidth);
        }
        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop() + (columnWidth / 2));
        for (float x : waveXIndex) {
            float y = (int) sin(A, w, x, phase, 0);
            mPath.rewind();
            mPath.moveTo(x, A - y);
            mPath.lineTo(x, A + y);
            canvas.drawPath(mPath, mPaint);
        }
        canvas.restore();
        mHolder.unlockCanvasAndPost(canvas);
    }

    /**
     * ????????????????????????y=Asin(??x+??)+k??????????????????y=Asin(??x+??)+k???????????????????????????????????????sin??????????????????
     * x??????????????????x??????????????????y?????????????????????????????????????????????y??????k??????????????????????k?????????????R????????0???
     *
     * @param A A????????????????????????????????????????????????????????????????????????????????????????????????1/2???
     * @param w (??x+??)???????????????????????????y?????????????????????????????????????? ??????????????????(??????????????????????????????)???
     * @param x X ??????
     * @param p ?????????????????x=0??????????????????????????????????????????????????????????????????
     * @param k k?????????????????????????????????????????????????????????????????????
     * @return ?????????
     */
    public double sin(int A, float w, float x, float p, int k) {
        return A * Math.sin(Math.toRadians(w * x + p)) + k;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
        if (mDrawHandler != null) {
            mDrawHandler.post(this::doDraw);
        }
    }
}
